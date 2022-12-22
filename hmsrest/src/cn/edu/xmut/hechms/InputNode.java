/**
 * 
 */
package cn.edu.xmut.hechms;

import java.util.ArrayList;
import java.util.List;

public class InputNode {

	private String firstToken;
	private String secondToken;
	private String thirdToken;
	/**
	 * @return the thirdToken
	 */
	public String getThirdToken() {
		return thirdToken;
	}

	/**
	 * @param thirdToken the thirdToken to set
	 */
	public void setThirdToken(String thirdToken) {
		this.thirdToken = thirdToken;
	}

	/**
	 * @return the fourthToken
	 */
	public String getFourthToken() {
		return fourthToken;
	}

	/**
	 * @param fourthToken the fourthToken to set
	 */
	public void setFourthToken(String fourthToken) {
		this.fourthToken = fourthToken;
	}

	private String fourthToken;

	/**
	 * @return the firstToken
	 */
	public String getFirstToken() {
		return firstToken;
	}

	/**
	 * @param firstToken
	 *            the firstToken to set
	 */
	public void setFirstToken(String firstToken) {
		this.firstToken = firstToken;
	}

	/**
	 * @return the secondToken
	 */
	public String getSecondToken() {
		return secondToken;
	}

	/**
	 * @param secondToken
	 *            the secondToken to set
	 */
	public void setSecondToken(String secondToken) {
		this.secondToken = secondToken;
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
