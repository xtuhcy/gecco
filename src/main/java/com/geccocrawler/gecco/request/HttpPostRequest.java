package com.geccocrawler.gecco.request;

import java.util.HashMap;
import java.util.Map;

public class HttpPostRequest extends AbstractHttpRequest {

	private String body;
	
	private Map<String, Object> fields;
	
	public HttpPostRequest(String url) {
		super(url);
		fields = new HashMap<String, Object>();
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public void addField(String name, Object field) {
		fields.put(name, field);
	}
}
