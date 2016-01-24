package com.geccocrawler.gecco.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.geccocrawler.gecco.request.HttpRequest;

public class SpiderScheduler implements Scheduler {
	
	private ConcurrentLinkedQueue<HttpRequest> queue;
	
	public SpiderScheduler() {
		queue = new ConcurrentLinkedQueue<HttpRequest>();
	}

	@Override
	public HttpRequest out() {
		return queue.poll();
	}

	@Override
	public void into(HttpRequest request) {
		queue.offer(request);
	}

}
