/**
 * 
 */
package cn.edu.xmut.hechms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import cn.edu.xmut.util.StringUtil;

public class BasinTree {

	public static void main(String[] args) {
		//test modify parameter and get parameter
		/*
		String fileName = "E:\\hms410\\CCXHMS19730331\\projchua.basin2";
		String outputFileName = "E:\\hms410\\CCXHMS19730331\\projchua.out";
		BasinTree bTree = new BasinTree();
		InputNode root = bTree.loadTree(fileName);
		String path = "#.Basin Layer Properties:.Element Layer:.Name: Icons2";
		String path2 = "#.Basin Layer Properties:.Element Layer:.Name:";
		//String path = "#.Basin: ProjChua.Enable Quality Routing: Yes";
		bTree.modifyParamter(path, root);
		HashMap<String, String> result = new HashMap<String,String>();
		bTree.getParamter(path2,root,result);
		System.out.println(result);
		System.out.println(root.getFirstToken());
		bTree.saveTree(outputFileName, root);
*/
		//test modify control information and retrieve information
		String fileName = "E:\\hms410\\CCXHMS19730331\\Control_19730331.control";
		String outputFileName = "E:\\hms410\\CCXHMS19730331\\Control_19730331.control2";
		BasinTree bTree = new BasinTree();
		InputNode root = bTree.loadTree(fileName);
		String path = "#.Control: Control 19730331.Start Date: 2 April 1973";
		String path2 = "#.Control: Control 19730331.Start Date:";
		//String path = "#.Basin: ProjChua.Enable Quality Routing: Yes";
		bTree.modifyParamter(path, root);
		HashMap<String, String> result = new HashMap<String,String>();
		bTree.getParamter(path2,root,result);
		System.out.println(result);
		System.out.println(root.getFirstToken());
		bTree.saveTree(outputFileName, root);
	}

	public Set<InputNode> findAllSubNodes(InputNode root) {
		Set<InputNode> sets = new HashSet<InputNode>();
		findAllSubNodes(root, sets);
		return sets;
	}

	public void findAllSubNodes(InputNode root, Set<InputNode> sets) {
		sets.add(root);
		for (InputNode inputNode : root.getChildren()) {
			findAllSubNodes(inputNode, sets);
		}
	}

	public void getParamter(String path, InputNode root,
			Map<String, String> result) {

		String parts[] = path.split("\\-");
		
		if (parts.length == 1) {
			//System.out.println("aaaaaaaaaaaaaaaa");
			String tokens[] = parts[0].split(":");
			if (root.getFirstToken() != null
					&& root.getFirstToken().trim().equals(tokens[0])) {
				result.put(root.getFirstToken(), root.getSecondToken());
			}
		} else {
			if (parts[0].equals("#")) {
				for (InputNode child : root.getChildren()) {
					path = path.replaceFirst("#-", "");
					getParamter(path, child, result);
				}
			}

			if (parts[0].equals("*")) {
				Set<InputNode> sets = findAllSubNodes(root);
				for (InputNode child : sets) {
					path = path.replaceFirst("*-", "");
					getParamter(path, child, result);
				}
			}
            //normal level
			String tokens[] = parts[0].split(":");
			if (tokens.length < 2) {
				path = path.replaceFirst(parts[0] + "-", "");
				if (root.getFirstToken() != null) {
					if (root.getFirstToken().trim().equals(tokens[0])) {
						for (InputNode child : root.getChildren()) {
							getParamter(path, child, result);
						}
					}
				}
			} else {
				//level with "*" in first part
				tokens[1] = parts[0].replaceFirst(tokens[0] + ":", "");
				path = path.replaceFirst(parts[0] + "-", "");
				if (root.getFirstToken() != null
						&& root.getSecondToken() != null) {
					if (tokens[1].equals("*")
							|| root.getSecondToken().equals(tokens[1])) {
						if (root.getFirstToken().trim().equals(tokens[0])) {
							for (InputNode child : root.getChildren()) {
								getParamter(path, child, result);
							}
						}
					}
				}
			}
		}

	}

	public void modifyParamter(String path_to_value, InputNode root) {

		String parts[] = path_to_value.split("\\-");

		if (parts.length == 1) {
			String tokens[] = parts[0].split(":");
			if (tokens.length < 2)
				System.out.println("invalidate path_to_value.");
			else {
				tokens[1] = parts[0].replaceFirst(tokens[0] + ":", "");
				if (root.getFirstToken() != null
						&& root.getFirstToken().trim().equals(tokens[0])) {
					root.setSecondToken(tokens[1]);
					System.out.println("node value has been changed.");
				} else
					System.out.println("node not found.");
			}
		} else {
			if (parts[0].equals("#")) {
				for (InputNode child : root.getChildren()) {
					path_to_value = path_to_value.replaceFirst("#-", "");
					modifyParamter(path_to_value, child);
				}
				return;
			}

			if (parts[0].equals("*")) {
				Set<InputNode> sets = findAllSubNodes(root);
				for (InputNode child : sets) {
					path_to_value = path_to_value.replaceFirst("*-", "");
					modifyParamter(path_to_value, child);
				}
				return;
			}

			String tokens[] = parts[0].split(":");
			if (tokens.length < 2) {
				path_to_value = path_to_value.replaceFirst(parts[0] + "-", "");
				if (root.getFirstToken() != null) {
					if (root.getFirstToken().trim().equals(tokens[0])) {
						for (InputNode child : root.getChildren()) {
							modifyParamter(path_to_value, child);
						}
					} else
						System.out.println("node not found.");
				}
			} else {
				tokens[1] = parts[0].replaceFirst(tokens[0] + ":", "");
				path_to_value = path_to_value.replaceFirst(parts[0] + "-", "");
				if (root.getFirstToken() != null
						&& root.getSecondToken() != null) {
					if (tokens[1].equals("*")
							|| root.getSecondToken().equals(tokens[1])) {
						if (root.getFirstToken().trim().equals(tokens[0])) {
							for (InputNode child : root.getChildren()) {
								modifyParamter(path_to_value, child);
							}
						} else
							System.out.println("node not found.");
					} else {
						System.out.println("node not found.");
					}
				}
			}
		}

	}

	public void saveTree(String path, InputNode father) {
		List<String> lines = new ArrayList<String>();

		saveTree(lines, father);
		lines.remove(0);
		try {
			Files.write(Path.of(path), lines);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveTree(List<String> lines, InputNode father) {

		if (father.getFirstToken() == null) {
			System.out.println();
			lines.add("");
		} else {
			String line = father.getFirstToken();
			line = line + ":";
			if (father.getSecondToken() != null) {
				line = line + father.getSecondToken();
			}
			lines.add(line);
			System.out.println(line);
		}

		if (father.getChildren() != null) {

			for (int i = 0; i < father.getChildren().size(); i++) {

				saveTree(lines, father.getChildren().get(i));
			}
		}

		if (father.getThirdToken() == null) {
			//System.out.println("\r\n");
			//lines.add("");
		} else {
			String line = father.getThirdToken();
			line = line + ":";
			if (father.getFourthToken() != null) {
				line = line + father.getFourthToken();
			}
			lines.add(line);
			System.out.println(line);
		}

	}

	public void loadTree(List<String> lines, InputNode father) {
		for (int i = 0; i < lines.size();) {
			//如果为空行，创建 一个空块，将i移到下一行
			if (lines.get(i).trim().equals("")) {
				String line1 = lines.get(i);
				InputNode child = new InputNode();
				father.addChild(child);
				i++;
				System.out.println(line1 + "blank");
				continue;
			}

			//计算当前块的结束行的行号
			int k = i;

			if ((k + 1) < lines.size()
					&& (lines.get(k + 1).trim().startsWith("End:")
							|| lines.get(k + 1).trim().startsWith("End "))) {
				k++;//空块，有两行没内容
			} else {
				boolean hasChild = false;
				while ((k + 1) < lines.size()
						&& StringUtil.isChildNodeIgnoreSpace(lines.get(i),
								lines.get(k + 1))) {
					k++;
					hasChild = true;
				}
				if (hasChild)
					k++;
			}

			if ((k - i) >= 1 && (lines.get(k).trim().startsWith("End:")
					|| lines.get(k).trim().startsWith("End "))) {
				InputNode child = new InputNode();
				String line1 = lines.get(i);
				String tokens[] = lines.get(i).split(":");
				child.setFirstToken(tokens[0]);
				if (tokens.length == 2)
					child.setSecondToken(tokens[1]);

				if (tokens.length > 2) {
					String tmpToken = String.join(":", tokens);
					tmpToken = tmpToken.replaceFirst(tokens[0] + ":", "");
					child.setSecondToken(tmpToken);
				}
				String endtokens[] = lines.get(k).split(":");
				String line2 = lines.get(k);
				child.setThirdToken(endtokens[0]);
				if (endtokens.length > 1) {
					child.setFourthToken(endtokens[1]);
				}
				System.out.println(line1);
				this.loadTree(lines.subList(i + 1, k), child);
				father.addChild(child);
				i = k + 1;
				System.out.println(line2);
			} else {
				String line1 = lines.get(i);
				InputNode child = new InputNode();
				String tokens[] = lines.get(i).split(":");
				child.setFirstToken(tokens[0]);
				if (tokens.length == 2)
					child.setSecondToken(tokens[1]);

				if (tokens.length > 2) {
					String tmpToken = String.join(":", tokens);
					tmpToken = tmpToken.replaceFirst(tokens[0] + ":", "");
					child.setSecondToken(tmpToken);
				}
				father.addChild(child);
				i++;
				System.out.println(line1);
			}

		}

	}

	public InputNode loadTree(String fileName) {
		try {
			List<String> lines = Files.readAllLines(Path.of(fileName));
			InputNode root = new InputNode();
			root.setFirstToken("root");
			root.setSecondToken(fileName);
			loadTree(lines, root);
			return root;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @return the children
	 */
	public List<InputNode> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void addChild(InputNode child) {
		this.children.add(child);
	}

	private List<InputNode> children = new ArrayList<InputNode>();

}
