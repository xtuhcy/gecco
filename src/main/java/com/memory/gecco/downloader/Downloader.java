package com.memory.gecco.downloader;

import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;

public interface Downloader {

	public HttpResponse download(HttpRequest request) throws DownloaderException;
	
	public void timeout(long timeout);
	
	public void userAgent(String userAgent);
	
	public void shutdown();
}
