package com.geccocrawler.gecco.annotation.dynamic;

public interface DynamicBean {
	
	public JavassistDynamicBean gecco(String matchUrl, String... pipelines);
	
	public JavassistDynamicBean gecco(String matchUrl, String downloader, int timeout, String... pipelines);
	
	public DynamicField field(String fieldName);
	
	public void loadClass();

}
