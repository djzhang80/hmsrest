/**
 * 
 */
package cn.edu.xmut.parameter;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.xmut.configuration.ProjectConfiguration;
import cn.edu.xmut.hechms.BasinTree;
import cn.edu.xmut.hechms.InputNode;

public class ParameterService extends ServerResource {

	@Get("txt")
	public String search() {
		try {
			String projectName = (String) this.getRequest().getAttributes()
					.get("projectname");
			String fileName = (String) this.getRequest().getAttributes()
					.get("filename");
			String path = (String) this.getRequest().getAttributes()
					.get("path");
			path = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8);
			path=path.replace("hash", "#");
			path=path.replace("asterisk", "*");
			fileName = ProjectConfiguration.getString("basePath") + projectName
					+ "/" + fileName;
			BasinTree bTree = new BasinTree();
			InputNode root = bTree.loadTree(fileName);
			//String path = "#.Control: Control 19730331.Start Date: 2 April 1973";
			//String path2 = "#.Control: Control 19730331.Start Date:";
			//String path = "#.Basin: ProjChua.Enable Quality Routing: Yes";
			bTree.modifyParamter(path, root);
			HashMap<String, String> result = new HashMap<String, String>();
			bTree.getParamter(path, root, result);

			ObjectMapper mapper = new ObjectMapper();

			return mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			return "failed";
		}
	}

	@Post("txt")
	public String update() {
		String projectName = (String) this.getRequest().getAttributes()
				.get("projectname");
		String fileName = (String) this.getRequest().getAttributes()
				.get("filename");
		String path = (String) this.getRequest().getAttributes().get("path");
		path = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8);
		path=path.replace("hash", "#");
		path=path.replace("asterisk", "*");
		
		fileName = ProjectConfiguration.getString("basePath") + projectName
				+ "/" + fileName;
		BasinTree bTree = new BasinTree();
		InputNode root = bTree.loadTree(fileName);
		//String path = "#.Control: Control 19730331.Start Date: 2 April 1973";
		//String path = "#.Basin: ProjChua.Enable Quality Routing: Yes";
		bTree.modifyParamter(path, root);
		bTree.saveTree(fileName, root);

		return "success";
	}

}
