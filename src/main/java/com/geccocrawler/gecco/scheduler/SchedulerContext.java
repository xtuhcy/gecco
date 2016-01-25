package com.geccocrawler.gecco.scheduler;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;

public class SchedulerContext {
	
	public static void into(HttpRequest request) {
		SpiderThreadLocal.get().getSpiderScheduler().into(request);
	}

}
