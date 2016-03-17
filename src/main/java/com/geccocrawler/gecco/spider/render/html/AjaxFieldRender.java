package com.geccocrawler.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.reflections.ReflectionUtils;

import com.geccocrawler.gecco.annotation.Ajax;
import com.geccocrawler.gecco.downloader.DownloaderContext;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.JsonBean;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.FieldRender;
import com.geccocrawler.gecco.spider.render.FieldRenderException;
import com.geccocrawler.gecco.spider.render.Render;
import com.geccocrawler.gecco.spider.render.RenderContext;
import com.geccocrawler.gecco.spider.render.RenderType;
import com.geccocrawler.gecco.utils.ReflectUtils;
import com.geccocrawler.gecco.utils.UrlMatcher;

/**
 * 渲染@Ajax属性
 * 
 * @author huchengyi
 *
 */
public class AjaxFieldRender implements FieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) throws FieldRenderException {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> ajaxFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(Ajax.class));
		for(Field ajaxField : ajaxFields) {
			Object value = injectAjaxField(request, beanMap, ajaxField);
			fieldMap.put(ajaxField.getName(), value);
		}
		beanMap.putAll(fieldMap);
	}
	
	private Object injectAjaxField(HttpRequest request, BeanMap beanMap, Field field) throws FieldRenderException {
		Class clazz = field.getType();
		//ajax的属性类型必须是spiderBean
		if(ReflectUtils.haveSuperType(clazz, JsonBean.class)) {
			Ajax ajax = field.getAnnotation(Ajax.class);
			String url = ajax.url();
			url = UrlMatcher.replaceParams(url, request.getParameters());
			url = UrlMatcher.replaceFields(url, beanMap);
			HttpRequest subRequest = request.subRequest(url);
			try {
				HttpResponse subReponse = DownloaderContext.download(subRequest);
				Render jsonRender = RenderContext.getRender(RenderType.JSON);
				return jsonRender.inject(clazz, subRequest, subReponse);
			} catch(Exception ex) {
				throw new FieldRenderException(field, request.getUrl(), ex);
			}
		} else {
			throw new FieldRenderException(field, request.getUrl());
		}
	}
}
