package com.memory.gecco.spider.render;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;

import com.memory.gecco.annotation.FieldRenderName;
import com.memory.gecco.annotation.Href;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;
import com.memory.gecco.spider.render.html.AjaxFieldRender;
import com.memory.gecco.spider.render.html.CustomFieldRender;
import com.memory.gecco.spider.render.html.CustomFieldRenderFactory;
import com.memory.gecco.spider.render.html.HtmlFieldRender;
import com.memory.gecco.spider.render.html.RequestParameterFieldRender;
import com.memory.gecco.utils.ReflectUtils;

/**
 * 将下载下来的html映射到bean中
 * 
 * @author huchengyi
 *
 */
public class HtmlRender implements Render {
	
	private RequestParameterFieldRender requestParameterFieldRender;
	
	private HtmlFieldRender htmlFieldRender;
	
	private AjaxFieldRender ajaxFieldRender;
	
	private CustomFieldRenderFactory customFieldRenderFactory;
	
	public HtmlRender(CustomFieldRenderFactory customFieldRenderFactory) {
		this.requestParameterFieldRender = new RequestParameterFieldRender();
		this.htmlFieldRender = new HtmlFieldRender();
		this.ajaxFieldRender = new AjaxFieldRender();
		this.customFieldRenderFactory = customFieldRenderFactory;
	}
	
	@Override
	public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response) {
		try {
			SpiderBean bean = clazz.newInstance();  
			BeanMap beanMap = BeanMap.create(bean);
			requestParameterFieldRender.render(request, response, beanMap, bean);
			htmlFieldRender.render(request, response, beanMap, bean);
			ajaxFieldRender.render(request, response, beanMap, bean);
			Set<Field> customFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(FieldRenderName.class));
			for(Field customField : customFields) {
				FieldRenderName fieldRender = customField.getAnnotation(FieldRenderName.class);
				String name = fieldRender.value();
				CustomFieldRender customFieldRender = customFieldRenderFactory.getCustomFieldRender(name);
				if(customFieldRender != null) {
					customFieldRender.render(request, response, beanMap, bean, customField);
				}
			}
			return bean;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<HttpRequest> requests(HttpRequest request, SpiderBean bean) {
		List<HttpRequest> requests = new ArrayList<HttpRequest>();
		BeanMap beanMap = BeanMap.create(bean);
		Set<Field> hrefFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(Href.class));
		for(Field hrefField : hrefFields) {
			Href href = hrefField.getAnnotation(Href.class);
			if(href.click()) {
				Object o = beanMap.get(hrefField.getName());
				boolean isList = ReflectUtils.haveSuperType(o.getClass(), List.class);//是List类型
				if(isList) {
					List<String> list = (List<String>)o;
					for(String url : list) {
						if(StringUtils.isNotEmpty(url)) {
							requests.add(request.subRequest(url));
						}
					}
				} else {
					String url = (String)o;
					if(StringUtils.isNotEmpty(url)) {
						requests.add(request.subRequest(url));
					}
				}
			}
		}
		return requests;
	}
	
}
