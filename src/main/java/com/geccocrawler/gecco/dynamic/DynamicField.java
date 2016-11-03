package com.geccocrawler.gecco.dynamic;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public interface DynamicField {
	
	/**
	 * 动态生成属性注解
	 * 
	 * @return DynamicField
	 */
	public DynamicField request();
	
	public DynamicField requestParameter(String param);
	
	public DynamicField requestParameter();

	/**
	 * replace by csspath
	 * @param cssPath cssPath
	 * @return DynamicField
	 */
	@Deprecated
	public DynamicField htmlField(String cssPath);
	
	public DynamicField csspath(String cssPath);
	
	public DynamicField text(boolean own);
	
	public DynamicField text();
	
	public DynamicField html(boolean outer);
	
	public DynamicField href(boolean click, String... value);
	
	public DynamicField href(String... value);
	
	public DynamicField image(String download, String... value);
	
	public DynamicField image();
	
	public DynamicField attr(String value);
	
	public DynamicField ajax(String url);
	
	public DynamicField jsvar(String var, String jsonpath);
	
	public DynamicField jsvar(String var);
	
	public DynamicField jsonpath(String value);
	
	public DynamicField renderName(String value);
	
	public DynamicBean build();

	public DynamicField customAnnotation(Annotation annotation);
	
	public ConstPool getConstPool();
}
