package com.geccocrawler.gecco;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.downloader.Downloader;
import com.geccocrawler.gecco.downloader.UnirestDownloader;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.request.StartRequestList;
import com.geccocrawler.gecco.scheduler.Scheduler;
import com.geccocrawler.gecco.scheduler.StartScheduler;
import com.geccocrawler.gecco.spider.Spider;
import com.geccocrawler.gecco.spider.SpiderBeanFactory;
import com.google.common.io.Files;

/**
 * 爬虫引擎，每个爬虫引擎最好独立进程，在分布式爬虫场景下，可以单独分配一台爬虫服务器。引擎包括Scheduler、Downloader、Spider、
 * SpiderBeanFactory4个主要模块
 * 
 * @author huchengyi
 *
 */
public class GeccoEngine {
	
	private List<HttpRequest> startRequests = new ArrayList<HttpRequest>();
	
	private Scheduler scheduler;
	
	private Downloader downloader;
	
	public SpiderBeanFactory spiderBeanFactory;
	
	public PipelineFactory pipelineFactory;
	
	private List<Spider> spiders;
	
	private String classpath;
	
	private String userAgent;
	
	private int threadCount;
	
	private int interval;
	
	private int timeout;
	
	private GeccoEngine() {}
	
	public static GeccoEngine create() {
		return new GeccoEngine();
	}

	public GeccoEngine start(File file) {
		try {
			String json = Files.toString(file, Charset.forName("UTF-8"));
			List<StartRequestList> list = JSON.parseArray(json, StartRequestList.class);
			for(StartRequestList start : list) {
				start(start.toRequest());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return this;
	}
	
	public GeccoEngine start(String url) {
		return start(new HttpGetRequest(url));
	}
	
	public GeccoEngine start(HttpRequest request) {
		this.startRequests.add(request);
		return this;
	}
	
	public GeccoEngine start(List<HttpRequest> requests) {
		this.startRequests = requests;
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
	
	public GeccoEngine timeout(int timeout) {
		this.timeout = timeout;
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
	
	public GeccoEngine pipelineFactory(PipelineFactory pipelineFactory) {
		this.pipelineFactory = pipelineFactory;
		return this;
	}
	
	public void run() {
		if(scheduler == null) {
			scheduler = new StartScheduler();
		}
		if(downloader == null) {
			downloader = new UnirestDownloader();
			downloader.userAgent(userAgent);
			downloader.timeout(timeout);
		}
		if(spiderBeanFactory == null) {
			if(StringUtils.isEmpty(classpath)) {
				classpath = "";
			}
			spiderBeanFactory = new SpiderBeanFactory(classpath, pipelineFactory);
		}
		if(threadCount <= 0) {
			threadCount = 1;
		}
		for(HttpRequest startRequest : startRequests) {
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
