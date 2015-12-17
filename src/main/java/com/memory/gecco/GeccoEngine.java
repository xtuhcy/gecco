package com.memory.gecco;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.memory.gecco.downloader.Downloader;
import com.memory.gecco.downloader.UnirestDownloader;
import com.memory.gecco.request.HttpGetRequest;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.scheduler.FIFOScheduler;
import com.memory.gecco.scheduler.Scheduler;
import com.memory.gecco.spider.Spider;
import com.memory.gecco.spider.SpiderBeanFactory;

/**
 * 爬虫引擎，每个爬虫引擎最好独立进程，在分布式爬虫场景下，可以单独分配一台爬虫服务器。引擎包括Scheduler、Downloader、Spider、
 * SpiderBeanFactory4个主要模块
 * 
 * @author huchengyi
 *
 */
public class GeccoEngine {
	
	private HttpRequest startRequest;
	
	private Scheduler scheduler;
	
	private Downloader downloader;
	
	public SpiderBeanFactory spiderBeanFactory;
	
	private List<Spider> spiders;
	
	private String classpath;
	
	private String userAgent;
	
	private int threadCount;
	
	private int interval;
	
	private GeccoEngine() {}
	
	public static GeccoEngine create() {
		return new GeccoEngine();
	}

	public GeccoEngine start(String url) {
		return start(new HttpGetRequest(url));
	}
	
	public GeccoEngine start(HttpRequest request) {
		this.startRequest = request;
		return this;
	}
	
	public GeccoEngine scheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
		return this;
	}
	
	public GeccoEngine downloader(Downloader downloader) {
		this.downloader = downloader;
		return this;
	}
	
	public GeccoEngine thread(int count) {
		this.threadCount = count;
		return this;
	}
	
	public GeccoEngine interval(int interval) {
		this.interval = interval;
		return this;
	}
	
	public GeccoEngine userAgent(String userAgent) {
		this.userAgent = userAgent;
		return this;
	}
	
	public GeccoEngine classpath(String classpath) {
		this.classpath = classpath;
		return this;
	}
	
	public void run() {
		if(scheduler == null) {
			scheduler = new FIFOScheduler();
		}
		if(downloader == null) {
			downloader = new UnirestDownloader();
			downloader.userAgent(userAgent);
		}
		if(spiderBeanFactory == null) {
			if(StringUtils.isEmpty(classpath)) {
				classpath = "";
			}
			spiderBeanFactory = new SpiderBeanFactory(classpath);
		}
		if(threadCount <= 0) {
			threadCount = 1;
		}
		if(startRequest != null) {
			scheduler.into(startRequest);
		}
		
		spiders = new ArrayList<Spider>(threadCount);
		for(int i = 0; i < threadCount; i++) {
			Spider spider = new Spider(this);
			spiders.add(spider);
			Thread thread = new Thread(spider, "Spider-"+i);
			thread.start();
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public Downloader getDownloader() {
		return downloader;
	}

	public SpiderBeanFactory getSpiderBeanFactory() {
		return spiderBeanFactory;
	}

	public int getInterval() {
		return interval;
	}
	
}
