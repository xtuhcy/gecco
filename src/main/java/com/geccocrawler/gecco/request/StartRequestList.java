package com.geccocrawler.gecco.request;

import java.util.HashMap;
import java.util.Map;

public class StartRequestList {

	private String url;

	private String charset;

	private Map<String, String> cookies;

	private Map<String, String> headers;

	private Map<String, String> posts;
	
	public StartRequestList() {
		cookies = new HashMap<String, String>();
		headers = new HashMap<String, String>();
		posts = new HashMap<String, String>();
	}
	
	public HttpRequest toRequest() {
		if(posts != null && posts.size() > 0) {
			HttpPostRequest post = new HttpPostRequest(this.getUrl());
			post.setCharset(charset);
			post.setFields(posts);
			post.setCookies(cookies);
			post.setHeaders(headers);
			return post;
		} else {
			HttpGetRequest get = new HttpGetRequest(this.getUrl());
			get.setCharset(charset);
			get.setCookies(cookies);
			get.setHeaders(headers);
			return get;
		}
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public void addCookie(String name, String value) {
		cookies.put(name, value);
	}
	
	public void addPost(String name, String value) {
		posts.put(name, value);
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getPosts() {
		return posts;
	}

	public void setPosts(Map<String, String> posts) {
		this.posts = posts;
	}

}
