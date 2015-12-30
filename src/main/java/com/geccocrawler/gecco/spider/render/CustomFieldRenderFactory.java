package com.geccocrawler.gecco.spider.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.geccocrawler.gecco.annotation.FieldRenderName;

public class CustomFieldRenderFactory {
	
	private Map<String, CustomFieldRender> map;
	
	public CustomFieldRenderFactory(Reflections reflections) {
		this.map = new HashMap<String, CustomFieldRender>();
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(FieldRenderName.class);
		for(Class<?> clazz : classes) {
			FieldRenderName fieldRenderName = (FieldRenderName)clazz.getAnnotation(FieldRenderName.class);
			try {
				map.put(fieldRenderName.value(), (CustomFieldRender)clazz.newInstance());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public CustomFieldRender getCustomFieldRender(String name) {
		return map.get(name);
	}

}
