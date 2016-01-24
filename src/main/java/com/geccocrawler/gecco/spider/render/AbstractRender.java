package com.geccocrawler.gecco.spider.render;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;

import com.geccocrawler.gecco.annotation.FieldRenderName;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.CustomFieldRender;
import com.geccocrawler.gecco.spider.render.CustomFieldRenderFactory;
import com.geccocrawler.gecco.spider.render.Render;
import com.geccocrawler.gecco.spider.render.RequestFieldRender;
import com.geccocrawler.gecco.spider.render.RequestParameterFieldRender;
import com.geccocrawler.gecco.utils.ReflectUtils;

/**
 * render抽象方法，主要包括注入基本的属性和自定义属性注入。将特定的html、json、xml注入放入实现类
 * 
 * @author huchengyi
 *
 */
public abstract class AbstractRender implements Render {
	
	private RequestFieldRender requestFieldRender;
	
	private RequestParameterFieldRender requestParameterFieldRender;
	
	private CustomFieldRenderFactory customFieldRenderFactory;
	
	public AbstractRender(CustomFieldRenderFactory customFieldRenderFactory) {
		this.requestFieldRender = new RequestFieldRender();
		this.requestParameterFieldRender = new RequestParameterFieldRender();
		this.customFieldRenderFactory = customFieldRenderFactory;
	}
	
	@Override
	public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response) {
		try {
			SpiderBean bean = clazz.newInstance();
			BeanMap beanMap = BeanMap.create(bean);
			requestFieldRender.render(request, response, beanMap, bean);
			requestParameterFieldRender.render(request, response, beanMap, bean);
			render(request, response, beanMap, bean);
			Set<Field> customFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(FieldRenderName.class));
			for(Field customField : customFields) {
				FieldRenderName fieldRender = customField.getAnnotation(FieldRenderName.class);
				String name = fieldRender.value();
				CustomFieldRender customFieldRender = customFieldRenderFactory.getCustomFieldRender(name);
				if(customFieldRender != null) {
					customFieldRender.render(request, response, beanMap, bean, customField);
				}
			}
			requests(request, bean);
			return bean;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public abstract void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean);

	@Override
	public List<HttpRequest> requests(HttpRequest request, SpiderBean bean) {
		List<HttpRequest> subRequests = new ArrayList<HttpRequest>();
		BeanMap beanMap = BeanMap.create(bean);
		Set<Field> hrefFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(Href.class));
		for(Field hrefField : hrefFields) {
			Href href = hrefField.getAnnotation(Href.class);
			if(href.click()) {
				Object o = beanMap.get(hrefField.getName());
				if(o == null) {
					continue;
				}
				boolean isList = ReflectUtils.haveSuperType(o.getClass(), List.class);//是List类型
				if(isList) {
					List<String> list = (List<String>)o;
					for(String url : list) {
						if(StringUtils.isNotEmpty(url)) {
							subRequests.add(request.subRequest(url));
						}
					}
				} else {
					String url = (String)o;
					if(StringUtils.isNotEmpty(url)) {
						subRequests.add(request.subRequest(url));
					}
				}
			}
		}
		return subRequests;
	}
	
}
