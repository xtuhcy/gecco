package com.geccocrawler.gecco.annotation.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeccoClassLoader extends ClassLoader {
	
	private static final Log LOG = LogFactory.getLog(GeccoClassLoader.class);
	
	private Map<String, Class<?>> classes;
	
	private GeccoClassLoader() {
		classes = new HashMap<String, Class<?>>();
	}
	
	private static GeccoClassLoader instance;
	
	public static synchronized GeccoClassLoader create() {
		instance = new GeccoClassLoader();
		return instance;
	}
	
	public static synchronized GeccoClassLoader get() {
		if(instance == null) {
			instance = create();
		}
		return instance;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> clazz = classes.get(name);
		if(clazz == null) {
			throw new ClassNotFoundException(name);
		}
		LOG.debug("find from GeccoClassLoader : " + name);
		return clazz;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return super.loadClass(name);
	}

	public void addClass(String key, Class<?> clazz) {
		classes.put(key, clazz);
	}

	public Map<String, Class<?>> getClasses() {
		return classes;
	}

}
