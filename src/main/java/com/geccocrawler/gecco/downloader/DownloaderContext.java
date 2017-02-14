package com.geccocrawler.gecco.downloader;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBeanContext;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;

/**
 * 获得当前线程，正在抓取的SpiderBean的下载器
 * 
 * @author huchengyi
 *
 */
public class DownloaderContext {
	
	public static HttpResponse download(HttpRequest request) throws DownloadException {
		SpiderBeanContext context = SpiderThreadLocal.get().getSpiderBeanContext();
		return context.getDownloader().download(request, context.getTimeout());
	}
	
	public static HttpResponse defaultDownload(HttpRequest request) throws DownloadException {
		SpiderBeanContext context = SpiderThreadLocal.get().getSpiderBeanContext();
		Downloader downloader = SpiderThreadLocal.get().getEngine().getSpiderBeanFactory().getDownloaderFactory().defaultDownloader();
		return downloader.download(request, context.getTimeout());
	}
	

}
