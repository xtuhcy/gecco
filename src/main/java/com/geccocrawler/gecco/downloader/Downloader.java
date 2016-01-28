package com.geccocrawler.gecco.downloader;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;

/**
 * 下载器，负责将Scheduler里的请求下载下来，系统默认采用[Unirest](https://github.com/Mashape/unirest-java)作为下载引擎。
 * 
 * @author huchengyi
 *
 */
public interface Downloader {

	public HttpResponse download(HttpRequest request) throws DownloaderException;
	
	public void timeout(long timeout);
	
	public void shutdown();
}
