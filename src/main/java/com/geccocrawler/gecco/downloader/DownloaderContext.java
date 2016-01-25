package com.geccocrawler.gecco.downloader;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;

public class DownloaderContext {
	
	public static HttpResponse download(HttpRequest request) throws DownloaderException {
		return SpiderThreadLocal.get().getEngine().getDownloader().download(request);
	}

}
