/**
 * 
 */
package cn.edu.xmut.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.python.antlr.PythonParser.if_stmt_return;

public class StringUtil {

	private static final Pattern LEADING = Pattern.compile("^\\s+");
	private static final Pattern TRAILING = Pattern.compile("\\s+$");

	public static int  getLeadingSpaces(String input) {
	    Matcher m = LEADING.matcher(input);
	    if(m.find()){
	        return m.group(0).length();
	    }
	    return 0;  
	}
	
	public static boolean  isChildNode(String input1,String input2) {
		return (getLeadingSpaces(input1)-getLeadingSpaces(input2))<0;
	}
	
	
	public static boolean  isChildNodeIgnoreSpace(String input1,String input2) {
		if(input2.trim().equals("")) return true;
		return (getLeadingSpaces(input1)-getLeadingSpaces(input2))<0;
	}
	
	public static void main(String[] args) {

	System.out.println(getLeadingSpaces("   abc"));
		

	}
	


}
