package com.vision.ssh.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesManager {

	private Properties properties;

	public PropertiesManager(final String path) {
		this(path, Thread.currentThread().getClass());
	}
	
	public PropertiesManager(final String path, Class<?> clazz) {
		try {
			properties = loadProp(path, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Properties loadProp(String path, Class<?> clazz) throws IOException {
		Class<?> _clazz = getClass();
		if(clazz != null) {
			_clazz = clazz;
		}
		
		Properties properties = new Properties();
		InputStream inputStream = _clazz.getResourceAsStream(path);
		properties.load(inputStream);
		inputStream.close();
		return properties;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void putAll(Properties otherProperties) {
		properties.putAll(otherProperties);
	}
	
	public Object get(final String key) {
		return properties.get(key);
	}
	
	public void put(final String key, Object value) {
		properties.put(key, value);
	}
	
	public Set<Object> getKeys(){
		return properties.keySet();
	}
	
	public Map<String, Object> getPropertiesMap(){
		Map<String, Object> resultMap = new HashMap<>();
		for (Object key :  properties.keySet()) {
			resultMap.put((String)key, properties.getProperty((String)key));
        }
		return resultMap;
	}
	
	public boolean containsKey(final String key) {
		return properties.containsKey(key);
	}
	
	public boolean containsValue(final String value) {
		return properties.containsValue(value);
	}
	
	public void sysOut() {
		if(properties != null) {
			properties.list(System.out);
		} else {
			throw new NullPointerException();
		}
	}
}
