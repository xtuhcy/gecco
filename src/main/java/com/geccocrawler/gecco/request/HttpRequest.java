package com.geccocrawler.gecco.request;

import java.util.Map;

public interface HttpRequest {
	
	public String getUrl();
	
	public void setParameters(Map<String, String> parameters);
	
	public String getParameter(String name);
	
	public Map<String, String> getParameters();
	
	public void addHeader(String name, String value);
	
	public Map<String, String> getHeaders();

	public void refer(String refer);
	
	public String getCharset();
	
	public void setCharset(String charset);
	
	public HttpRequest subRequest(String url);
}
