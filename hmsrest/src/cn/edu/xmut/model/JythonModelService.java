/**
 * 
 */
package cn.edu.xmut.model;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import cn.edu.xmut.configuration.ProjectConfiguration;
import hms.model.JythonHms;

public class JythonModelService extends ServerResource {

	@Post("txt")
	public String exeJython() {
		String projectName = (String) this.getRequest().getAttributes()
				.get("projectname");
		String modelName = (String) this.getRequest().getAttributes()
				.get("modelname");
		String computeName = (String) this.getRequest().getAttributes()
				.get("computename");
		computeName = java.net.URLDecoder.decode(computeName,
				StandardCharsets.UTF_8);
		System.out.println(ProjectConfiguration.getString("basePath")
				+ projectName + "/" + modelName + ".hms");

		return this.exeModel(projectName, modelName, computeName);
	}

	public String exeModel(String projectName, String modelName,
			String computeName) {

		String fileName = this.generateExectionScript(projectName, modelName,
				computeName);
		JythonHms.runScript(fileName);
		return "Model run successfully.";
	}

	public String generateExectionScript(String projectName, String modelName,
			String computeName) {
		String fileName = ProjectConfiguration.getString("basePath")
				+ projectName + "/run.py";
		try {
			PrintStream pStream = new PrintStream(
					ProjectConfiguration.getString("basePath") + projectName
							+ "/run.py");
			pStream.println("from hms.model.JythonHms import *");
			pStream.println("OpenProject(\"" + projectName + "\",\""
					+ ProjectConfiguration.getString("basePath") + projectName
					+ "\")");
			pStream.println("Compute(\"" + computeName + "\")");
			pStream.println("Exit(1)");
			pStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return fileName;
	}
}
