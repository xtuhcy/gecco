package com.geccocrawler.gecco.downloader;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;

import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpPostRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.utils.ResponseUtils;
import com.mashape.unirest.request.HttpRequestWithBody;

public class UnirestDownloader implements Downloader {
	
	private static Log log = LogFactory.getLog(UnirestDownloader.class);
	
	public UnirestDownloader() {
		Unirest.setConcurrency(1000, 50);
	}
	
	@Override
	public HttpResponse download(HttpRequest request) throws DownloaderException {
		if(log.isDebugEnabled()) {
			log.debug("downloading..." + request.getUrl());
		}
		try {
			HttpHost proxy = Proxys.getProxy();
			if(proxy != null) {
				Unirest.setProxy(proxy);
			} else {
				Unirest.setProxy(null);
			}
			request.addHeader("User-Agent", UserAgent.getUserAgent());
			com.mashape.unirest.http.HttpResponse<String> response = null;
			if(request instanceof HttpPostRequest) {
				HttpPostRequest post = (HttpPostRequest)request;
				HttpRequestWithBody httpRequestWithBody = Unirest.post(post.getUrl());
				httpRequestWithBody.headers(post.getHeaders());
				httpRequestWithBody.fields(post.getFields());
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
}
