package com.geccocrawler.gecco.spider.render.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.JsonBean;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.conversion.Conversion;
import com.geccocrawler.gecco.spider.render.FieldRender;
import com.geccocrawler.gecco.spider.render.FieldRenderException;
import com.geccocrawler.gecco.spider.render.Render;
import com.geccocrawler.gecco.spider.render.RenderContext;
import com.geccocrawler.gecco.spider.render.RenderException;
import com.geccocrawler.gecco.spider.render.RenderType;
import com.geccocrawler.gecco.utils.ReflectUtils;

import net.sf.cglib.beans.BeanMap;

public class JsonFieldRender implements FieldRender {

	@Override
	@SuppressWarnings({ "unchecked" })
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> jsonPathFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(JSONPath.class));
		String jsonStr = response.getContent();
		jsonStr = jsonp2Json(jsonStr);
		if (jsonStr == null) {
			return;
		}
		try {
			Object json = JSON.parse(jsonStr);
			for (Field field : jsonPathFields) {
				Object value = injectJsonField(request, field, json);
				if(value != null) {
					fieldMap.put(field.getName(), value);
				}
			}
		} catch(JSONException ex) {
			//throw new RenderException(ex.getMessage(), bean.getClass());
			RenderException.log("json parse error : " + request.getUrl(), bean.getClass(), ex);
		}
		beanMap.putAll(fieldMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object injectJsonField(HttpRequest request, Field field, Object json) {
		JSONPath JSONPath = field.getAnnotation(JSONPath.class);
		String jsonPath = JSONPath.value();
		Class<?> type = field.getType();// 类属性的类
		Object src = com.alibaba.fastjson.JSONPath.eval(json, jsonPath);
		boolean isArray = type.isArray();
		boolean isList = ReflectUtils.haveSuperType(type, List.class);// 是List类型
		if (isList) {
			Type genericType = field.getGenericType();// 获得包含泛型的类型
			Class genericClass = ReflectUtils.getGenericClass(genericType, 0);// 泛型类
			if (ReflectUtils.haveSuperType(genericClass, SpiderBean.class)) {
				// List<spiderBean>
				return spiderBeanListRender(src, genericClass, request);
			} else {
				// List<Object>
				return objectRender(src, field, jsonPath, json);
			}
		} else if (isArray) {
			Class genericClass = type.getComponentType();
			if (ReflectUtils.haveSuperType(genericClass, SpiderBean.class)) {
				// SpiderBean[]
				List<SpiderBean> list = spiderBeanListRender(src, genericClass, request);
				SpiderBean[] a = (SpiderBean[]) Array.newInstance(genericClass, list.size());
				return list.toArray(a);
			} else {
				// Object[]
				return ((List<Object>) objectRender(src, field, jsonPath, json)).toArray();
			}
		} else {
			if (ReflectUtils.haveSuperType(type, SpiderBean.class)) {
				if(src == null) {
					return null;
				}
				// spiderBean
				return spiderBeanRender(src, type, request);
			} else {
				// Object
				return objectRender(src, field, jsonPath, json);
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private List<SpiderBean> spiderBeanListRender(Object src, Class genericClass, HttpRequest request) {
		List<SpiderBean> list = new ArrayList<SpiderBean>();
		Iterable ja = (Iterable) src;
		for (Object jo : ja) {
			if(jo != null) {
				SpiderBean subBean = this.spiderBeanRender(jo, genericClass, request);
				list.add(subBean);
			}
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SpiderBean spiderBeanRender(Object src, Class genericClass, HttpRequest request) {
		HttpResponse subResponse = HttpResponse.createSimple(src.toString());
		Render render = null;
		if(ReflectUtils.haveSuperType(genericClass, JsonBean.class)) {
			render = RenderContext.getRender(RenderType.JSON);
		} else {
			render = RenderContext.getRender(RenderType.HTML);
		}
		SpiderBean subBean = render.inject(genericClass, request, subResponse);
		return subBean;
	}

	private Object objectRender(Object src, Field field, String jsonPath, Object json) {
		if (src == null) {
			//throw new FieldRenderException(field, jsonPath + " not found in : " + json);
			FieldRenderException.log(field, jsonPath + " not found in : " + json);
		}
		try {
			return Conversion.getValue(field.getType(), src);
		} catch (Exception ex) {
			//throw new FieldRenderException(field, "Conversion error : " + src, ex);
			FieldRenderException.log(field, "Conversion error : " + src, ex);
		}
		return null;
	}

	private String jsonp2Json(String jsonp) {
		if (jsonp == null) {
			return null;
		}
		jsonp = StringUtils.trim(jsonp);

		if(jsonp.startsWith("try")||StringUtils.endsWith(jsonp, ")")){
			if(jsonp.indexOf("catch")!=-1){
				jsonp = jsonp.substring(0,jsonp.indexOf("catch"));
			}
			int fromIndex = jsonp.indexOf('(');
			int toIndex = jsonp.lastIndexOf(')');
			if(fromIndex!=-1&&toIndex!=-1){
				jsonp = jsonp.substring(fromIndex+1,toIndex).trim();
				return jsonp;
			}
		}

		if (StringUtils.endsWith(jsonp, ";")) {
			jsonp = StringUtils.substringBeforeLast(jsonp, ";");
			jsonp = StringUtils.trim(jsonp);
		}
		/*if (StringUtils.endsWith(jsonp, ")")) {
			String jsonStr = StringUtils.substring(jsonp, "(", ")");
			jsonStr = StringUtils.trim(jsonStr);
			return jsonStr;
		}*/
		return jsonp;
	}

}
