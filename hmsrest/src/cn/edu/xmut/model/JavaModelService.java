/**
 * 
 */
package cn.edu.xmut.model;

import java.nio.charset.StandardCharsets;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import cn.edu.xmut.configuration.ProjectConfiguration;
import hms.model.Project;

public class JavaModelService extends ServerResource {

	
	@Post("txt")
	public String exeJava() {
		String projectName = (String) this.getRequest().getAttributes()
				.get("projectname");
		String modelName = (String) this.getRequest().getAttributes()
				.get("modelname");
		String computeName = (String) this.getRequest().getAttributes()
				.get("computename");
		computeName = java.net.URLDecoder.decode(computeName,
				StandardCharsets.UTF_8);
		System.out.println(ProjectConfiguration.getString("basePath")+projectName+"/" + modelName + ".hms");
		Project p = Project.open(ProjectConfiguration.getString("basePath")+projectName+"/" + modelName + ".hms");		
		p.computeRun(computeName);
		p.close();
		return "Model run successfully";
	}
	
	
	
	
}
