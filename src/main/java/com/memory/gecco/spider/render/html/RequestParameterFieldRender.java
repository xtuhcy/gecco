package com.memory.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;

import com.memory.gecco.annotation.RequestParameter;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;
import com.memory.gecco.spider.conversion.Conversion;

public class RequestParameterFieldRender implements FieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> requestParameterFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(RequestParameter.class));
		for(Field field : requestParameterFields) {
			RequestParameter requestParameter = field.getAnnotation(RequestParameter.class);
			String key = requestParameter.value();
			if(StringUtils.isEmpty(key)) {
				key = field.getName();
			}
			Object value = Conversion.getValue(field.getType(), request.getParameter(key));
			fieldMap.put(field.getName(), value);
		}
		beanMap.putAll(fieldMap);
	}
	
}
