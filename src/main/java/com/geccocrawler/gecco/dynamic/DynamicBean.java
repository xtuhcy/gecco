package com.geccocrawler.gecco.dynamic;

import javassist.CtClass;

/**
 * 动态生成SpiderBean
 * 
 * @author huchengyi
 *
 */
public interface DynamicBean {
	
	public JavassistDynamicBean gecco(String matchUrl, String... pipelines);
	
	public JavassistDynamicBean gecco(String matchUrl, String downloader, int timeout, String... pipelines);
	
	public DynamicField field(String fieldName, CtClass fieldType);
	
	public DynamicField field(String fieldName);
	
	public Class<?> register();
	
	@Deprecated
	public Class<?> loadClass();
	
}
