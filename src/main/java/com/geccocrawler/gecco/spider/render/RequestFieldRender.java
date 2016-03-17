package com.geccocrawler.gecco.spider.render;

import java.lang.reflect.Field;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.reflections.ReflectionUtils;

import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;

public class RequestFieldRender implements FieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) throws FieldRenderException {
		Set<Field> requestFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(Request.class));
		if(requestFields.size() > 0) {
			Field field = requestFields.iterator().next();
			beanMap.put(field.getName(), request);
		}
	}
	
}
