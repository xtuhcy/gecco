package com.geccocrawler.gecco.scheduler;

import com.geccocrawler.gecco.GeccoEngineThreadLocal;
import com.geccocrawler.gecco.request.HttpRequest;

public class SchedulerContext {
	
	public static void into(HttpRequest request) {
		GeccoEngineThreadLocal.getScheduler().into(request);
	}

}
