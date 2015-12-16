package com.memory.gecco.spider;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.memory.gecco.GeccoEngine;
import com.memory.gecco.GeccoEngineThreadLocal;
import com.memory.gecco.downloader.AfterDownload;
import com.memory.gecco.downloader.BeforeDownload;
import com.memory.gecco.pipeline.Pipeline;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.render.HtmlParser;
import com.memory.gecco.spider.render.Render;

public class Spider implements Runnable {
	
	private static Log log = LogFactory.getLog(HtmlParser.class);

	public GeccoEngine engine;
	
	public Class<? extends SpiderBean> currSpiderBeanClass;
	
	public Spider(GeccoEngine engine) {
		this.engine = engine;
	}
	
	public void run() {
		//将engine放入线程本地变量，之后需要使用
		GeccoEngineThreadLocal.set(engine);
		while(true) {
			HttpRequest request = engine.getScheduler().out();
			log.info(request.getUrl());
			currSpiderBeanClass = engine.getSpiderBeanFactory().matchSpider(request);
			if(currSpiderBeanClass == null) {
				continue;
			}
			//bean config：beforeDownloader,afterDownloader,render,pipelines
			SpiderBeanContext context = engine.getSpiderBeanFactory().getContext(currSpiderBeanClass);
			//download
			HttpResponse response = download(context, request);
			if(response != null) {
				//render
				Render render = context.getRender();
				SpiderBean spiderBean = render.inject(currSpiderBeanClass, request, response);
				//pipelines
				List<Pipeline> pipelines = context.getPipelines();
				if(pipelines != null) {
					for(Pipeline pipeline : pipelines) {
						pipeline.process(spiderBean);
					}
				}
				//other requests
				List<HttpRequest> requests = render.requests(request, spiderBean);//???
				if(requests != null && requests.size() > 0) {
					for(HttpRequest nextRequest : requests) {
						engine.getScheduler().into(nextRequest);
					}
				} else {
					engine.getScheduler().into(request);
				}
			}
			int interval = engine.getInterval();
			if(interval > 0) {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	private HttpResponse download(SpiderBeanContext config, HttpRequest startRequest) {
		try {
			BeforeDownload before = config.getBeforeDownload();
			if(before != null) {
				before.process(startRequest);
			}
			HttpResponse response = engine.getDownloader().download(startRequest);
			AfterDownload after = config.getAfterDownload();
			if(after != null) {
				after.process(response);
			}
			return response;
		} catch(Exception ex) {
			//下载失败，加入jmx监控
			ex.printStackTrace();
			return null;
		}
	}

	public GeccoEngine getEngine() {
		return engine;
	}
}
