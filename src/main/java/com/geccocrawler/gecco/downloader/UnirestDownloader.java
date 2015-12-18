package com.geccocrawler.gecco.downloader;

import java.io.IOException;

import com.geccocrawler.gecco.request.HttpPostRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

public class UnirestDownloader implements Downloader {
	
	private String userAgent;
	
	public UnirestDownloader() {
		Unirest.setConcurrency(200, 20);
	}
	
	@Override
	public HttpResponse download(HttpRequest request) throws DownloaderException {
		try {
			request.addHeader("User-Agent", userAgent);
			com.mashape.unirest.http.HttpResponse<String> response = null;
			if(request instanceof HttpPostRequest) {
				HttpPostRequest post = (HttpPostRequest)request;
				HttpRequestWithBody httpRequestWithBody = Unirest.post(post.getUrl());
				httpRequestWithBody.headers(post.getHeaders());
				httpRequestWithBody.body(post.getBody());
				httpRequestWithBody.fields(post.getFields());
				response = httpRequestWithBody.asString();
			} else {
				response = Unirest.get(request.getUrl()).headers(request.getHeaders()).asString();
			}
			HttpResponse resp = new HttpResponse();
			resp.setContent(response.getBody());
			resp.setRaw(response.getRawBody());
			resp.setStatus(response.getStatus());
			return resp;
		} catch (UnirestException e) {
			throw new DownloaderException(e);
		}
	}

	@Override
	public void timeout(long timeout) {
		if(timeout > 0) {
			Unirest.setTimeouts(timeout, timeout);
		} else {
			Unirest.setTimeouts(3000, 3000);
		}
	}

	@Override
	public void shutdown() {
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void userAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
