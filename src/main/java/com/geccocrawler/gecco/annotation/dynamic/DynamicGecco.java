package com.geccocrawler.gecco.annotation.dynamic;

public class DynamicGecco {
	
	public static JavassistDynamicBean htmlBean(String htmlBeanName, boolean create) {
		return new JavassistDynamicBean(htmlBeanName, JavassistDynamicBean.HtmlBean, create);
	}
	
	public static JavassistDynamicBean jsonBean(String jsonBeanName, boolean create) {
		return new JavassistDynamicBean(jsonBeanName, JavassistDynamicBean.JsonBean, create);
	}
}
