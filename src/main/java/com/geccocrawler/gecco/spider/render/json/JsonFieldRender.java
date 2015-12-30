package com.geccocrawler.gecco.spider.render.json;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.ReflectionUtils;

import net.sf.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.conversion.Conversion;
import com.geccocrawler.gecco.spider.render.FieldRender;

public class JsonFieldRender implements FieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> jsonPathFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(JSONPath.class));
		Object json = JSON.parse(response.getContent(response.getCharset()));
		for(Field field : jsonPathFields) {
			JSONPath JSONPath = field.getAnnotation(JSONPath.class);
			String jsonPath = JSONPath.value();
			Object value = Conversion.getValue(field.getType(), com.alibaba.fastjson.JSONPath.eval(json, jsonPath));
			fieldMap.put(field.getName(), value);
		}
		beanMap.putAll(fieldMap);
	}

}
