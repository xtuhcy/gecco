package com.geccocrawler.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.reflections.ReflectionUtils;

import com.geccocrawler.gecco.GeccoEngineThreadLocal;
import com.geccocrawler.gecco.annotation.Ajax;
import com.geccocrawler.gecco.annotation.RenderType;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.JsonRender;
import com.geccocrawler.gecco.spider.render.Render;
import com.geccocrawler.gecco.utils.ReflectUtils;
import com.geccocrawler.gecco.utils.UrlMatcher;

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
			HttpResponse subReponse = GeccoEngineThreadLocal.getDownloader().download(subRequest);
			Render jsonRender = GeccoEngineThreadLocal.getRender(RenderType.JSON);
			return jsonRender.inject(clazz, subRequest, subReponse);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
