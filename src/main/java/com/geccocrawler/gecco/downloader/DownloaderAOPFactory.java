package com.geccocrawler.gecco.downloader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.geccocrawler.gecco.annotation.GeccoClass;

public class DownloaderAOPFactory {
	
	private Map<String, BeforeDownload> beforeDownloads;
	
	private Map<String, AfterDownload> afterDownloads;
	
	public DownloaderAOPFactory(Reflections reflections) {
		this.beforeDownloads = new HashMap<String, BeforeDownload>();
		this.afterDownloads = new HashMap<String, AfterDownload>();
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(GeccoClass.class);
		for(Class<?> aopClass : classes) {
			GeccoClass geccoClass = (GeccoClass)aopClass.getAnnotation(GeccoClass.class);
			try {
				Class<?>[] geccoClasses = geccoClass.value();
				for(Class<?> c : geccoClasses) {
					String name = c.getName();
					Object o = aopClass.newInstance();
					if(o instanceof BeforeDownload) {
						beforeDownloads.put(name, (BeforeDownload)o);
					} else if(o instanceof AfterDownload) {
						afterDownloads.put(name, (AfterDownload)o);
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public BeforeDownload getBefore(String spiderName) {
		return beforeDownloads.get(spiderName);
	}
	
	public AfterDownload getAfter(String spiderName) {
		return afterDownloads.get(spiderName);
	}

}
