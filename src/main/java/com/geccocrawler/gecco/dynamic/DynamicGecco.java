package com.geccocrawler.gecco.dynamic;

import org.apache.commons.lang3.RandomStringUtils;

public class DynamicGecco {
	
	public static JavassistDynamicBean htmlBean(String htmlBeanName) {
		return new JavassistDynamicBean(htmlBeanName, JavassistDynamicBean.HtmlBean, false);
	}
	
	public static JavassistDynamicBean jsonBean(String jsonBeanName) {
		return new JavassistDynamicBean(jsonBeanName, JavassistDynamicBean.JsonBean, false);
	}
	
	public static JavassistDynamicBean html() {
		return new JavassistDynamicBean("com.geccocrawler.gecco.dynamic.HtlmBean"+RandomStringUtils.randomAlphabetic(6)+System.nanoTime(), JavassistDynamicBean.HtmlBean, true);
	}
	
	public static JavassistDynamicBean json() {
		return new JavassistDynamicBean("com.geccocrawler.gecco.dynamic.JsonBean"+RandomStringUtils.randomAlphabetic(6)+System.nanoTime(), JavassistDynamicBean.JsonBean, true);
	}
}
