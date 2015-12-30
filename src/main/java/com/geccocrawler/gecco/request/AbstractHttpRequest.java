package com.geccocrawler.gecco.request;

import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;

public abstract class AbstractHttpRequest implements HttpRequest {
	
	protected String url;
	
	protected String charset;
	
	protected Map<String, String> parameters;
	
	protected Map<String, String> cookies;
	
	protected Map<String, String> headers;
	
	public AbstractHttpRequest(String url) {
		this.parameters = new HashMap<String, String>(1);
		this.headers = new HashMap<String, String>(1);
		this.cookies = new HashMap<String, String>(1);
		this.url = url;
	}

	public void addCookie(String name, String value) {
		cookies.put(name, value);
	}
	
	public void addParameter(String name, String value) {
		parameters.put(name, value);
	}
	
	@Override
	public void setParameters(Map<String, String> parameters) {
		this.parameters.putAll(parameters);
	}

	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public HttpRequest subRequest(String url) {
		try {
			BeanCopier copier = BeanCopier.create(this.getClass(), this.getClass(), false);
			HttpRequest request = this.getClass().getConstructor(String.class).newInstance(url);
			copier.copy(this, request, null);
			request.refer(this.getUrl());
			return request;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public Map<String, String> getHeaders() {
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> entry : cookies.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
		}
		headers.put("Cookie", sb.toString());
		return headers;
	}

	@Override
	public void refer(String refer) {
		this.addHeader("Referer", refer);
	}

	@Override
	public Map<String, String> getParameters() {
		return parameters;
	}

	@Override
	public String getCharset() {
		return charset;
	}

	@Override
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

}
