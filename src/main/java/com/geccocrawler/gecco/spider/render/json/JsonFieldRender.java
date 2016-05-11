package com.geccocrawler.gecco.spider.render.json;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;

import net.sf.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.conversion.Conversion;
import com.geccocrawler.gecco.spider.render.FieldRender;
import com.geccocrawler.gecco.spider.render.FieldRenderException;
import com.geccocrawler.gecco.spider.render.Render;
import com.geccocrawler.gecco.spider.render.RenderContext;
import com.geccocrawler.gecco.spider.render.RenderException;
import com.geccocrawler.gecco.spider.render.RenderType;
import com.geccocrawler.gecco.utils.ReflectUtils;

public class JsonFieldRender implements FieldRender {
	
	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) throws FieldRenderException {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> jsonPathFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(JSONPath.class));
		String jsonStr = response.getContent();
		jsonStr = StringUtils.substringBetween(jsonStr, "(", ")");
		jsonStr = StringUtils.trim(jsonStr);
		Object json = JSON.parse(jsonStr);
		for(Field field : jsonPathFields) {
			try {
				Object value = injectJsonField(request, field, json);
				fieldMap.put(field.getName(), value);
			} catch(Exception ex) {
				throw new FieldRenderException(field, ex.getMessage(), ex);
			}
			/*JSONPath JSONPath = field.getAnnotation(JSONPath.class);
			String jsonPath = JSONPath.value();
			Object src = com.alibaba.fastjson.JSONPath.eval(json, jsonPath);
			if(src == null) {
				throw new FieldRenderException(field, jsonPath + " not found in : " + json);
			}
			try {
				Object value = Conversion.getValue(field.getType(), src);
				fieldMap.put(field.getName(), value);
			} catch(Exception ex) {
				throw new FieldRenderException(field, "Conversion error : " + src, ex);
			}*/
		}
		beanMap.putAll(fieldMap);
	}
	
	private Object injectJsonField(HttpRequest request, Field field, Object json) throws RenderException, FieldRenderException {
		JSONPath JSONPath = field.getAnnotation(JSONPath.class);
		String jsonPath = JSONPath.value();
		Class<?> type = field.getType();//类属性的类
		Object src = com.alibaba.fastjson.JSONPath.eval(json, jsonPath);
		boolean isList = ReflectUtils.haveSuperType(type, List.class);//是List类型
		if(isList) {
			Type genericType = field.getGenericType();//获得包含泛型的类型
			Class genericClass = ReflectUtils.getGenericClass(genericType, 0);//泛型类
			if(ReflectUtils.haveSuperType(genericClass, SpiderBean.class)) {
				//List<spiderBean>
				return spiderBeanRender(src, genericClass, request);
			} else {
				//List<Object>
				return objectRender(src, field, jsonPath, json);
			}
		} else {
			//Object
			return objectRender(src, field, jsonPath, json);
		}
	}
	
	private List<SpiderBean> spiderBeanRender(Object src, Class genericClass, HttpRequest request) throws RenderException {
		List<SpiderBean> list = new ArrayList<SpiderBean>();
		JSONArray ja = (JSONArray)src;
		for(Object jo : ja) {
			HttpResponse subResponse = HttpResponse.createSimple(jo.toString());
			Render render = RenderContext.getRender(RenderType.JSON);
			SpiderBean subBean = render.inject(genericClass, request, subResponse);
			list.add(subBean);
		}
		return list;
	}
	
	private Object objectRender(Object src, Field field, String jsonPath, Object json) throws FieldRenderException {
		if(src == null) {
			throw new FieldRenderException(field, jsonPath + " not found in : " + json);
		}
		try {
			return Conversion.getValue(field.getType(), src);
		} catch(Exception ex) {
			throw new FieldRenderException(field, "Conversion error : " + src, ex);
		}
	}
}
