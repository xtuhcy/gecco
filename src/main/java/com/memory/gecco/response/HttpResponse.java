package com.memory.gecco.response;

import java.io.InputStream;

public class HttpResponse {
	private InputStream raw;

	private String content;

	private String contentType;

	private int status;
	
	public static HttpResponse createSimple(String content) {
		HttpResponse response = new HttpResponse();
		response.setContent(content);
		return response;
	}

	public InputStream getRaw() {
		return raw;
	}

	public void setRaw(InputStream raw) {
		this.raw = raw;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
