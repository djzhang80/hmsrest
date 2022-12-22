/**
 * 
 */
package cn.edu.xmut.configuration;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ProjectConfiguration {

	    private static PropertiesConfiguration config = null;  
	      
	    static {  
	        try {  
	            config = new PropertiesConfiguration(  
	                    "configure.properties");  
	        } catch (ConfigurationException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	      
	    public static String getString(String key) {  
	        return config.getString(key);  
	    }  
	  
	    public static String getString(String key, String def) {  
	        return config.getString(key, def);  
	    }  
	  
	    public static int getInt(String key) {  
	        return config.getInt(key);  
	    }  
	  
	    public static int getInt(String key, int def) {  
	        return config.getInt(key, def);  
	    }  
	  
	    public static long getLong(String key) {  
	        return config.getLong(key);  
	    }  
	  
	    public static long getLong(String key, long def) {  
	        return config.getLong(key, def);  
	    }  
	  
	    public static float getFloat(String key) {  
	        return config.getFloat(key);  
	    }  
	  
	    public static float getFloat(String key, float def) {  
	        return config.getFloat(key, def);  
	    }  
	  
	    public static double getDouble(String key) {  
	        return config.getDouble(key);  
	    }  
	  
	    public static double getDouble(String key, double def) {  
	        return config.getDouble(key, def);  
	    }  
	  
	    public static boolean getBoolean(String key) {  
	        return config.getBoolean(key);  
	    }  
	  
	    public static boolean getBoolean(String key, boolean def) {  
	        return config.getBoolean(key, def);  
	    }  
	  
	    public static String[] getStringArray(String key) {  
	        return config.getStringArray(key);  
	    }  
	  
	    @SuppressWarnings("unchecked")  
	    public static List getList(String key) {  
	        return config.getList(key);  
	    }  
	  
	    @SuppressWarnings("unchecked")  
	    public static List getList(String key, List def) {  
	        return config.getList(key, def);  
	    }  
	  
	    public static void setProperty(String key, Object value) {  
	        config.setProperty(key, value);  
	    }  
	  

}
