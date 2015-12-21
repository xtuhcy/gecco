package com.geccocrawler.gecco.request;

import java.util.HashMap;
import java.util.Map;

public class HttpPostRequest extends AbstractHttpRequest {

	private Map<String, String> fields;
	
	public HttpPostRequest(String url) {
		super(url);
		fields = new HashMap<String, String>();
	}
	
	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public void addField(String name, String field) {
		fields.put(name, field);
	}
}
