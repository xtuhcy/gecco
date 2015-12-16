package com.memory.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.reflections.ReflectionUtils;

import com.memory.gecco.GeccoEngineThreadLocal;
import com.memory.gecco.annotation.Ajax;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;
import com.memory.gecco.spider.render.JsonRender;
import com.memory.gecco.utils.ReflectUtils;
import com.memory.gecco.utils.UrlMatcher;

public class AjaxFieldRender implements FieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> ajaxFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(Ajax.class));
		for(Field ajaxField : ajaxFields) {
			Object value = injectAjaxField(request, beanMap, ajaxField);
			fieldMap.put(ajaxField.getName(), value);
		}
		beanMap.putAll(fieldMap);
	}
	
	private Object injectAjaxField(HttpRequest request, BeanMap beanMap, Field field) {
		Class clazz = field.getType();
		if(!ReflectUtils.haveSuperType(clazz, SpiderBean.class)) {
			return null;
		}
		Ajax ajax = field.getAnnotation(Ajax.class);
		String url = ajax.url();
		url = UrlMatcher.replaceParams(url, request.getParameters());
		url = UrlMatcher.replaceFields(url, beanMap);
		HttpRequest subRequest = request.subRequest(url);
		try {
			HttpResponse subReponse = GeccoEngineThreadLocal.get().getDownloader().download(subRequest);
			JsonRender jsonRender = new JsonRender();
			return jsonRender.inject(clazz, subRequest, subReponse);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
