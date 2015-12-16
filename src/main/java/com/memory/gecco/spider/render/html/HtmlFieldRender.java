package com.memory.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanMap;

import org.reflections.ReflectionUtils;

import com.memory.gecco.annotation.HtmlField;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;
import com.memory.gecco.spider.render.HtmlParser;
import com.memory.gecco.utils.ReflectUtils;

public class HtmlFieldRender implements FieldRender {

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> htmlFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(HtmlField.class));
		for(Field htmlField : htmlFields) {
			Object value = injectHtmlField(request, response, htmlField, bean.getClass());
			fieldMap.put(htmlField.getName(), value);
		}
		beanMap.putAll(fieldMap);
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
				return parser.$basicList(cssPath, field);
			}
		} else {
			if(ReflectUtils.haveSuperType(type, SpiderBean.class)) {
				//SpiderBean
				return parser.$bean(cssPath, request, (Class<? extends SpiderBean>)type);
			} else {
				//Object
				return parser.$basic(cssPath, field);
			}
		}
	}
	
}
