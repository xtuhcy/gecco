package com.geccocrawler.gecco.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GeccoClassLoader extends ClassLoader {
	
	private static final Log LOG = LogFactory.getLog(GeccoClassLoader.class);
	
	private Map<String, Class<?>> classes;
	
	private static GeccoClassLoader instance;
	
	/**
	 * 创建一个新的GeccoClassLoader
	 * @return GeccoClassLoader
	 */
	public static synchronized GeccoClassLoader create() {
		if(instance != null) {
			instance.classes.clear();
		}
		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		if(parent != null) {
			instance = new GeccoClassLoader(parent);
		} else {
			instance = new GeccoClassLoader();
		}
		return instance;
	}
	
	public static synchronized GeccoClassLoader get() {
		if(instance == null) {
			instance = create();
		}
		return instance;
	}
	
	public GeccoClassLoader() {
		classes = new HashMap<String, Class<?>>();
	}
	
	public GeccoClassLoader(ClassLoader parent) {
		super(parent);
		classes = new HashMap<String, Class<?>>();
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
		LOG.debug("load from GeccoClassLoader : " + name);
		return super.loadClass(name);
	}

	public void addClass(String key, Class<?> clazz) {
		classes.put(key, clazz);
	}

	public Map<String, Class<?>> getClasses() {
		return classes;
	}

}
