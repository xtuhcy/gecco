package com.geccocrawler.gecco.spider;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.downloader.AfterDownload;
import com.geccocrawler.gecco.downloader.BeforeDownload;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.scheduler.Scheduler;
import com.geccocrawler.gecco.scheduler.SpiderScheduler;
import com.geccocrawler.gecco.spider.render.Render;

/**
 * 一个爬虫引擎可以包含多个爬虫，每个爬虫可以认为是一个单独线程，爬虫会从Scheduler中获取需要待抓取的请求。
 * 爬虫的任务就是下载网页并渲染相应的JavaBean。
 * 
 * @author huchengyi
 *
 */
public class Spider implements Runnable {
	
	private static Log log = LogFactory.getLog(Spider.class);

	public GeccoEngine engine;
	
	public Class<? extends SpiderBean> currSpiderBeanClass;
	
	public Scheduler spiderScheduler;
	
	public Spider(GeccoEngine engine) {
		this.engine = engine;
		this.spiderScheduler = new SpiderScheduler();
	}
	
	public void run() {
		//将engine放入线程本地变量，之后需要使用
		SpiderThreadLocal.set(this);
		while(true) {
			boolean start = false;
			HttpRequest request = spiderScheduler.out();
			if(request == null) {
				//startScheduler
				request = engine.getScheduler().out();
				start = true;
			}
			if(log.isDebugEnabled()) {
				log.debug("match url : " + request.getUrl());
			}
			currSpiderBeanClass = engine.getSpiderBeanFactory().matchSpider(request);
			if(currSpiderBeanClass == null) {
				log.info("cant't match url : " + request.getUrl());
				HttpResponse response = download(null, null, request);
				if(response != null) {
					if(response.getStatus() == 302 || response.getStatus() == 301){
						spiderScheduler.into(request.subRequest(response.getContent()));
					}
				}
				continue;
			}
			//bean config：beforeDownloader,afterDownloader,render,pipelines
			SpiderBeanContext context = engine.getSpiderBeanFactory().getContext(currSpiderBeanClass);
			//download
			HttpResponse response = download(context.getBeforeDownload(), context.getAfterDownload(), request);
			if(response != null) {
				if(response.getStatus() == 200) {
					//render
					Render render = context.getRender();
					SpiderBean spiderBean = render.inject(currSpiderBeanClass, request, response);
					//pipelines
					List<Pipeline> pipelines = context.getPipelines();
					if(pipelines != null) {
						for(Pipeline pipeline : pipelines) {
							try {
								pipeline.process(spiderBean);
							} catch(Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				} else if(response.getStatus() == 302 || response.getStatus() == 301){
					HttpRequest sub = request.subRequest(response.getContent());
					spiderScheduler.into(sub);
				}
			} else {
				//如果没有抓取到任何信息，重新加入请求队列？？重试次数
				//spiderScheduler.into(request);
			}
			int interval = engine.getInterval();
			if(interval > 0) {
				try {
					Thread.sleep(randomInterval(interval));
				} catch (InterruptedException e) {}
			}
			if(start) {
				//如果是一个开始抓取请求，再返回开始队列中
				engine.getScheduler().into(request);
			}
		}
	}
	
	private HttpResponse download(BeforeDownload before, AfterDownload after, HttpRequest request) {
		try {
			if(before != null) {
				before.process(request);
			}
			HttpResponse response = engine.getDownloader().download(request);
			int status = response.getStatus();
			if(status != 200 && status != 301 && status != 302) {
				log.error("download error " + request.getUrl() + " : " + response.getStatus());
				return null;
			}
			if(after != null) {
				after.process(request, response);
			}
			return response;
		} catch(Exception ex) {
			//下载失败，加入jmx监控
			log.error("download error " + request.getUrl() + " : " + ex.getMessage());
			return null;
		}
	}

	/**
	 * 间隔时间在左右1s的范围内随机
	 * 
	 * @param interval
	 * @return
	 */
	private int randomInterval(int interval) {
		int min = interval - 1000;
		if(min < 1) {
			min = 1;
		}
		int max = interval + 1000;
		return (int)Math.rint(Math.random()*(max-min)+min);
	}
	
	public GeccoEngine getEngine() {
		return engine;
	}

	public Scheduler getSpiderScheduler() {
		return spiderScheduler;
	}

	public void setSpiderScheduler(Scheduler spiderScheduler) {
		this.spiderScheduler = spiderScheduler;
	}

}
