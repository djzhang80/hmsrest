/**
 * 
 */
package cn.edu.xmut.timeseries;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.xmut.configuration.ProjectConfiguration;
import hec.heclib.dss.HecDataManager;
import hec.heclib.dss.HecTimeSeries;
import hec.heclib.util.HecTime;
import hec.heclib.util.HecTimeArray;
import hec.io.TimeSeriesContainer;

public class TimeSeriesService extends ServerResource {
	//http://localhost:8182/input/timeseries/tenk/Jan_96_storm.dss/blank/113/FLOW/01JAN1996 - 21JAN1996/1HOUR/RUN:JAN 96 STORM/
	@Get("txt")
	public String search() {
		try {
			String projectName = (String) this.getRequest().getAttributes()
					.get("projectname");
			String dssFile = (String) this.getRequest().getAttributes()
					.get("dssfile");
			String p1 = (String) this.getRequest().getAttributes().get("p1");
			String p2 = (String) this.getRequest().getAttributes().get("p2");
			String p3 = (String) this.getRequest().getAttributes().get("p3");
			String p4 = (String) this.getRequest().getAttributes().get("p4");
			String p5 = (String) this.getRequest().getAttributes().get("p5");
			String p6 = (String) this.getRequest().getAttributes().get("p6");
			String t1 = (String) this.getRequest().getAttributes().get("t1");
			String t2 = (String) this.getRequest().getAttributes().get("t2");
			String path = "/" + p1 + "/" + p2 + "/" + p3 + "/" + p4 + "/" + p5
					+ "/" + p6 + "/";
			path = path.replaceAll("blank", "");
			path = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8);
			System.out.println(path);

			TimeSeriesContainer tscRead = new TimeSeriesContainer();
			tscRead.setName(path);
			if (t1 != null && t2 != null) {
				String[] sedays = java.net.URLDecoder.decode(p4, StandardCharsets.UTF_8).split("-");
				String sday=sedays[0].trim();
				String eday=sedays[1].trim();
				System.out.println(t1);
				System.out.println(t2);
				tscRead.setStartTime(new HecTime(sday, t1));
				tscRead.setEndTime(new HecTime(eday.trim(), t2));
			}

			//tscRead.setStartTime(new HecTime("01JAN1996", "2400"));
			//tscRead.setEndTime(new HecTime("02JAN1996", "0100"));
			HecTimeSeries dssTimeSeriesRead = new HecTimeSeries();
			dssTimeSeriesRead
					.setDSSFileName(ProjectConfiguration.getString("basePath")
							+ projectName + "/" + dssFile);
			System.out.println(ProjectConfiguration.getString("basePath")
					+ projectName + "/" + dssFile);
			int status = dssTimeSeriesRead.read(tscRead, false);
			dssTimeSeriesRead.done();
			if (status != 0)
				return "a error ocurred when reading the timeseries";
			HecTimeArray hTimes = tscRead.getTimes();
			double vals[] = tscRead.getValues();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (int i = 0; i < vals.length; i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(hTimes.element(i).dateAndTime(), vals[i] + "");
				list.add(map);
			}
			HecDataManager.closeAllFiles();

			ObjectMapper mapper = new ObjectMapper();
			return mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(list);

			//return "success";

		} catch (Exception e) {
			return e.toString();
		}

	}

	//http://localhost:8182/input/timeseries/tenk/Jan_96_storm.dss/blank/113/FLOW/01JAN1996 - 21JAN1996/1HOUR/RUN:JAN 96 STORM/
	@Put("txt")
	public String insert(Form form) {
		String projectName = (String) this.getRequest().getAttributes()
				.get("projectname");
		String dssFile = (String) this.getRequest().getAttributes()
				.get("dssfile");
		String p1 = (String) this.getRequest().getAttributes().get("p1");
		String p2 = (String) this.getRequest().getAttributes().get("p2");
		String p3 = (String) this.getRequest().getAttributes().get("p3");
		String p4 = (String) this.getRequest().getAttributes().get("p4");
		String p5 = (String) this.getRequest().getAttributes().get("p5");
		String p6 = (String) this.getRequest().getAttributes().get("p6");
		String path = "/" + p1 + "/" + p2 + "/" + p3 + "/" + p4 + "/" + p5 + "/"
				+ p6 + "/";

		String stime = form.getFirstValue("stime");
		String etime = form.getFirstValue("etime");
		String sday = form.getFirstValue("sday");
		String eday = form.getFirstValue("eday");

		String values = form.getFirstValue("values");

		System.out.println(
				stime + "-" + etime + "-" + sday + "-" + eday + "-" + values);

		return this.write(projectName, dssFile, p1, p2, p3, p4, p5, p6, stime,
				etime, sday, eday, values);

	}

	public String write(String projectName, String dssFile, String p1,
			String p2, String p3, String p4, String p5, String p6, String stime,
			String etime, String sday, String eday, String values) {
		try {

			String path = "/" + p1 + "/" + p2 + "/" + p3 + "/" + p4 + "/" + p5
					+ "/" + p6 + "/";
			path = path.replaceAll("blank", "");
			path = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8);
			System.out.println(path);

			TimeSeriesContainer tscWrite = new TimeSeriesContainer();
			List<Double> vList = new ArrayList<Double>();

			String[] tokens = values.split(",");
			double[] dvalues = new double[tokens.length];
			int i = 0;
			for (String item : tokens) {
				dvalues[i] = Double.parseDouble(item);
				i++;
			}

			tscWrite.setValues(dvalues);
			tscWrite.setName(path);

			tscWrite.setStartTime(new HecTime(sday, stime));
			tscWrite.setEndTime(new HecTime(eday, etime));
			HecTimeSeries dssTimeSeriesWrite = new HecTimeSeries();
			dssTimeSeriesWrite
					.setDSSFileName(ProjectConfiguration.getString("basePath")
							+ projectName + "/" + dssFile);
			System.out.println(ProjectConfiguration.getString("basePath")
					+ projectName + "/" + dssFile);
			int status = dssTimeSeriesWrite.write(tscWrite);
			dssTimeSeriesWrite.done();
			if (status != 0)
				return "a error ocurred when writting the timeseries";

			HecDataManager.closeAllFiles();

			return "Data successfully added.";

			//return "success";

		} catch (Exception e) {
			return e.toString();
		}

	}

	public String retrieve(String projectName, String dssFile, String p1,
			String p2, String p3, String p4, String p5, String p6) {

		try {

			String path = "/" + p1 + "/" + p2 + "/" + p3 + "/" + p4 + "/" + p5
					+ "/" + p6 + "/";
			path = path.replaceAll("blank", "");
			path = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8);
			System.out.println(path);

			TimeSeriesContainer tscRead = new TimeSeriesContainer();
			tscRead.setName(path);
			//tscRead.setStartTime(new HecTime("01JAN1996", "2400"));
			//tscRead.setEndTime(new HecTime("21JAN1996", "2300"));
			HecTimeSeries dssTimeSeriesRead = new HecTimeSeries();
			dssTimeSeriesRead
					.setDSSFileName(ProjectConfiguration.getString("basePath")
							+ projectName + "/" + dssFile);
			System.out.println(ProjectConfiguration.getString("basePath")
					+ projectName + "/" + dssFile);
			int status = dssTimeSeriesRead.read(tscRead, false);
			dssTimeSeriesRead.done();
			if (status != 0)
				return "a error ocurred when reading the timeseries";
			HecTimeArray hTimes = tscRead.getTimes();
			double vals[] = tscRead.getValues();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (int i = 0; i < vals.length; i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(hTimes.element(i).dateAndTime(), vals[i] + "");
				list.add(map);
			}
			HecDataManager.closeAllFiles();

			ObjectMapper mapper = new ObjectMapper();
			return mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(list);

			//return "success";

		} catch (Exception e) {
			return e.toString();
		}

	}
}
