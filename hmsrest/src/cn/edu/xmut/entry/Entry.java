package cn.edu.xmut.entry;


import org.restlet.Component;
import org.restlet.data.Protocol;
import cn.edu.xmut.model.JavaModelService;
import cn.edu.xmut.model.JythonModelService;
import cn.edu.xmut.parameter.ParameterService;
import cn.edu.xmut.timeseries.TimeSeriesService;

public class Entry {
	public static void main(String[] args) throws Exception {
		//creating a listener
		System.out.println("starting...");
		
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 8182);
		component.getClients().add(Protocol.FILE);
		component.getDefaultHost().attach("/input/timeseries/{projectname}/{dssfile}/{p1}/{p2}/{p3}/{p4}/{p5}/{p6}",
				TimeSeriesService.class);
		component.getDefaultHost().attach("/input/timeseries/{projectname}/{dssfile}/{p1}/{p2}/{p3}/{p4}/{p5}/{p6}/{t1}/{t2}",
				TimeSeriesService.class);
		component.getDefaultHost().attach("/output/timeseries/{projectname}/{dssfile}/{p1}/{p2}/{p3}/{p4}/{p5}/{p6}",
				TimeSeriesService.class);
		// Then attach it to the local host
		component.getDefaultHost().attach("/model/jython/{projectname}/{modelname}/{computename}",
				JythonModelService.class);
		component.getDefaultHost().attach("/model/java/{projectname}/{modelname}/{computename}",
				JavaModelService.class);
		component.getDefaultHost().attach("/input/parameter/{projectname}/{filename}/{path}",
				ParameterService.class);


		// Now, let's start the component!
		// Note that the HTTP server connector is also automatically started.
		component.start();
	}

	
}
