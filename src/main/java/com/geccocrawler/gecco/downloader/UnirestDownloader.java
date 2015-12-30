package com.geccocrawler.gecco.downloader;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.request.HttpPostRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.scheduler.FIFOScheduler;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.utils.ResponseUtils;
import com.mashape.unirest.request.HttpRequestWithBody;

public class UnirestDownloader implements Downloader {
	
	private static Log log = LogFactory.getLog(UnirestDownloader.class);
	
	private String userAgent;
	
	public UnirestDownloader() {
		Unirest.setConcurrency(1000, 50);
	}
	
	@Override
	public HttpResponse download(HttpRequest request) throws DownloaderException {
		if(log.isDebugEnabled()) {
			log.debug("downloading..." + request.getUrl());
		}
		try {
			request.addHeader("User-Agent", userAgent);
			com.mashape.unirest.http.HttpResponse<String> response = null;
			if(request instanceof HttpPostRequest) {
				HttpPostRequest post = (HttpPostRequest)request;
				HttpRequestWithBody httpRequestWithBody = Unirest.post(post.getUrl());
				httpRequestWithBody.headers(post.getHeaders());
				for(Map.Entry<String, String> entry : post.getFields().entrySet()) {
					httpRequestWithBody.field(entry.getKey(), entry.getValue());
				}
				response = httpRequestWithBody.asString();
			} else {
				response = Unirest.get(request.getUrl()).headers(request.getHeaders()).asString();
			}
			String contentType = response.getHeaders().getFirst("Content-Type");
			HttpResponse resp = new HttpResponse();
			resp.setStatus(response.getStatus());
			resp.setRaw(response.getRawBody());
			resp.setContent(response.getBody());
			resp.setContentType(contentType);
			resp.setCharset(getCharset(request, contentType));
			return resp;
		} catch (UnirestException e) {
			throw new DownloaderException(e);
		}
	}
	
	private String getCharset(HttpRequest request, String contentType) {
		String charset = ResponseUtils.getCharsetFromContentType(contentType);
		if(charset == null) {
			charset = request.getCharset();
		}
		if(charset == null) {
			charset = "UTF-8";
		}
		return charset;
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
