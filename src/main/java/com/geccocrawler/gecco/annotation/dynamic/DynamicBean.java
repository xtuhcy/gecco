package com.geccocrawler.gecco.annotation.dynamic;

import javassist.CtClass;

public interface DynamicBean {
	
	public JavassistDynamicBean gecco(String matchUrl, String... pipelines);
	
	public JavassistDynamicBean gecco(String matchUrl, String downloader, int timeout, String... pipelines);
	
	public DynamicField field(String fieldName, CtClass fieldType);
	
	public DynamicField field(String fieldName);
	
	public Class<?> loadClass();

}
