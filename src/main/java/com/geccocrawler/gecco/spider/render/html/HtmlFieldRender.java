package com.geccocrawler.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.ReflectionUtils;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.FieldRender;
import com.geccocrawler.gecco.utils.ReflectUtils;

public class HtmlFieldRender implements FieldRender {

	private static Log log = LogFactory.getLog(HtmlFieldRender.class);
	
	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> htmlFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(HtmlField.class));
		for(Field htmlField : htmlFields) {
			Object value = injectHtmlField(request, response, htmlField, bean.getClass());
			if(value != null) {
				fieldMap.put(htmlField.getName(), value);
			}
		}
		try {
			beanMap.putAll(fieldMap);
		} catch(Exception ex) {
			System.out.println(fieldMap.toString());
			ex.printStackTrace();
		}
	}
	
	private Object injectHtmlField(HttpRequest request, HttpResponse response, Field field, Class<? extends SpiderBean> clazz) {
		HtmlField htmlField = field.getAnnotation(HtmlField.class);
		HtmlParser parser = new HtmlParser(request.getUrl(), response.getContent());
		parser.setLogClass(clazz);
		String cssPath = htmlField.cssPath();
		Class<?> type = field.getType();//类属性的类
		boolean isList = ReflectUtils.haveSuperType(type, List.class);//是List类型
		if(isList) {
			Type genericType = field.getGenericType();//获得包含泛型的类型
			Class genericClass = ReflectUtils.getGenericClass(genericType, 0);//泛型类
			if(ReflectUtils.haveSuperType(genericClass, SpiderBean.class)) {
				//List<spiderBean>
				return parser.$beanList(cssPath, request, genericClass);
			} else {
				//List<Object>
				try {
					return parser.$basicList(cssPath, field);
				} catch(Exception ex) {
					log.error("field [" + field.getName() + "] render error");
					return null;
				}
			}
		} else {
			if(ReflectUtils.haveSuperType(type, SpiderBean.class)) {
				//SpiderBean
				return parser.$bean(cssPath, request, (Class<? extends SpiderBean>)type);
			} else {
				//Object
				try {
					return parser.$basic(cssPath, field);
				} catch(Exception ex) {
					log.error("field [" + field.getName() + "] render error");
					return null;
				}
			}
		}
	}
	
}
