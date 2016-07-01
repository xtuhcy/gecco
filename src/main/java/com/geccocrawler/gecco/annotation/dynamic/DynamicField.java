package com.geccocrawler.gecco.annotation.dynamic;

public interface DynamicField {
	
public DynamicField request();
	
	public DynamicField requestParameter(String param);
	
	public DynamicField requestParameter();

	public DynamicField htmlField(String cssPath);
	
	public DynamicField text(boolean own);
	
	public DynamicField text();
	
	public DynamicField html(boolean outer);
	
	public DynamicField href(boolean click, String... value);
	
	public DynamicField href(String... value);
	
	public DynamicField image(String download, String... value);
	
	public DynamicField attr(String value);
	
	public DynamicField ajax(String url);
	
	public DynamicField jsvar(String var, String jsonpath);
	
	public DynamicField jsvar(String var);
	
	public DynamicField jsonpath(String value);
	
	public DynamicBean build();
}
