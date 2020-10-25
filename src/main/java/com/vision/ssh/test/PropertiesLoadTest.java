package com.vision.ssh.test;

import java.io.IOException;
import java.util.Properties;

import com.vision.ssh.core.PropertiesManager;

public class PropertiesLoadTest {

	    public static void main(String[] args) throws IOException {
	    	//works
//	    	PropertiesManager applicationPropertiesManager = new PropertiesManager("/com/vision/ssh/test/application.properties", PropertiesLoadTest.class);
//	    	PropertiesManager applicationPropertiesManager = new PropertiesManager("/com/vision/ssh/test/application.properties", PropertiesManager.class);
//	    	PropertiesManager applicationPropertiesManager = new PropertiesManager("application.properties", PropertiesLoadTest.class);
	    	PropertiesManager applicationPropertiesManager = new PropertiesManager("/com/vision/ssh/test/application.properties");
	    	
	    	// not works
//	    	PropertiesManager applicationPropertiesManager = new PropertiesManager("application.properties", PropertiesManager.class);
//	    	PropertiesManager applicationPropertiesManager = new PropertiesManager("application.properties"); // not works
	    	applicationPropertiesManager.sysOut();

	    	
	    	PropertiesManager applicationProdPropertiesManager = new PropertiesManager("application-prod.properties", PropertiesLoadTest.class);
	    	applicationPropertiesManager.putAll(applicationProdPropertiesManager.getProperties());
	    	
	    	applicationPropertiesManager.sysOut();

	        Properties dummy = new Properties();
	        dummy.put("demo.type", "dummy");
	        dummy.put("demo.temp", "temp");
	        applicationPropertiesManager.putAll(dummy);
	        applicationPropertiesManager.sysOut();

	        System.out.println("----------------------------------------------------------");
	        Properties properties = applicationPropertiesManager.getProperties();
	        Object type = properties.get("demo.type");
	        System.out.println("demo.type : " + (String)type);

	        System.out.println("----------------------------------------------------------");
	        
	        for (String key : properties.stringPropertyNames()) {
	            Object value = properties.getProperty(key);
	            System.out.println("value : " + (String)value);
	        }
	        
	        System.out.println("----------------------------------------------------------");
	        
	        for (Object key :  properties.keySet()) {
	            Object value = properties.getProperty((String)key);
	            System.out.println("value : " + (String)value);
	        }

	        System.out.println("----------------------------------------------------------");
	        
	        System.out.println("containsKey  demo.type : " + properties.containsKey("demo.type"));

	        System.out.println("containsValue  demo.type : " + properties.containsValue("dummy"));

	        Object result = properties.computeIfAbsent("undefined", value -> returnNull(value));
	        System.out.println("result value is " + result);
	    }

	    public static Object returnNull(Object key) {
	        System.out.println(key + " value is null.");
	        return null;
	    }
}
