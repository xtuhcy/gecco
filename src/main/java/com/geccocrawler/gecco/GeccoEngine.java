package com.geccocrawler.gecco;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.monitor.GeccoJmx;
import com.geccocrawler.gecco.monitor.GeccoMonitor;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.request.StartRequestList;
import com.geccocrawler.gecco.scheduler.NoLoopStartScheduler;
import com.geccocrawler.gecco.scheduler.Scheduler;
import com.geccocrawler.gecco.scheduler.StartScheduler;
import com.geccocrawler.gecco.spider.Spider;
import com.geccocrawler.gecco.spider.SpiderBeanFactory;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * 爬虫引擎，每个爬虫引擎最好独立进程，在分布式爬虫场景下，可以单独分配一台爬虫服务器。引擎包括Scheduler、Downloader、Spider、
 * SpiderBeanFactory4个主要模块
 * 
 * @author huchengyi
 *
 */
public class GeccoEngine extends Thread {
	
	private static Log log = LogFactory.getLog(GeccoEngine.class);
	
	private Date startTime;
	
	private List<HttpRequest> startRequests = new ArrayList<HttpRequest>();
	
	private Scheduler scheduler;
	
	public SpiderBeanFactory spiderBeanFactory;
	
	public PipelineFactory pipelineFactory;
	
	private List<Spider> spiders;
	
	private String classpath;
	
	private int threadCount;
	
	private CountDownLatch cdl;
	
	private int interval;
	
	private boolean loop;
	
	private boolean mobile;
	
	private boolean debug;
	
	private GeccoEngine() {}
	
	public static GeccoEngine create() {
		GeccoEngine geccoEngine = new GeccoEngine();
		geccoEngine.setName("GeccoEngine");
		return geccoEngine;
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
	
	public GeccoEngine thread(int count) {
		this.threadCount = count;
		return this;
	}
	
	public GeccoEngine interval(int interval) {
		this.interval = interval;
		return this;
	}
	
	public GeccoEngine loop(boolean loop) {
		this.loop = loop;
		return this;
	}
	
	public GeccoEngine mobile(boolean mobile) {
		this.mobile = mobile;
		return this;
	}
	
	public GeccoEngine debug(boolean debug) {
		this.debug = debug;
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
		if(debug) {
			Logger log = LogManager.getLogger("com.geccocrawler.gecco.spider.render");
			log.setLevel(Level.DEBUG);
		}
		if(scheduler == null) {
			if(loop) {
				scheduler = new StartScheduler();
			} else {
				scheduler = new NoLoopStartScheduler();
			}
		}
		if(spiderBeanFactory == null) {
			if(StringUtils.isEmpty(classpath)) {
				//classpath不为空
				throw new IllegalArgumentException("classpath cannot be empty");
			}
			spiderBeanFactory = new SpiderBeanFactory(classpath, pipelineFactory);
		}
		if(threadCount <= 0) {
			threadCount = 1;
		}
		this.cdl = new CountDownLatch(threadCount);
		startsJson();
		if(startRequests.isEmpty()) {
			//startRequests不为空
			throw new IllegalArgumentException("startRequests cannot be empty");
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
		startTime = new Date();
		//监控爬虫基本信息
		GeccoMonitor.monitor(this);
		//启动导出jmx信息
		GeccoJmx.export(classpath);
		//非循环模式等待线程执行完毕后关闭
		closeUnitlComplete();
	}
	
	private GeccoEngine startsJson() {
		try {
			URL url = Resources.getResource("starts.json");
			File file = new File(url.getPath());
			if(file.exists()) {
				String json = Files.toString(file, Charset.forName("UTF-8"));
				List<StartRequestList> list = JSON.parseArray(json, StartRequestList.class);
				for(StartRequestList start : list) {
					start(start.toRequest());
				}
			}
		} catch(IllegalArgumentException ex) {
			log.info("starts.json not found");
		} catch(IOException ioex) {
			log.error(ioex);
		}
		return this;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public SpiderBeanFactory getSpiderBeanFactory() {
		return spiderBeanFactory;
	}

	public int getInterval() {
		return interval;
	}

	public Date getStartTime() {
		return startTime;
	}

	public List<HttpRequest> getStartRequests() {
		return startRequests;
	}

	public List<Spider> getSpiders() {
		return spiders;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public boolean isLoop() {
		return loop;
	}

	public boolean isMobile() {
		return mobile;
	}
	
	public boolean isDebug() {
		return debug;
	}

	public void notifyComplemet() {
		this.cdl.countDown();
	}
	
	public void closeUnitlComplete() {
		if(!loop) {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				log.error(e);
			}
			if(spiderBeanFactory != null) {
				spiderBeanFactory.getDownloaderFactory().closeAll();
			}
			GeccoJmx.unexport();
			log.info("close gecco!");
		}
	}
}
