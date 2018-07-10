package com.geccocrawler.gecco.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 不需要循环抓取的start队列
 * 
 * @author huchengyi
 *
 */
public class NoLoopStartScheduler implements Scheduler {
	
	private ConcurrentLinkedQueue<HttpRequest> queue;
	
	public NoLoopStartScheduler() {
		queue = new ConcurrentLinkedQueue<HttpRequest>();
	}

	@Override
	public HttpRequest out() {
		//Retrieves and removes the head of this queue,
		//or returns {@code null} if this queue is empty
		//No Block
		HttpRequest request = queue.poll();
		return request;
	}

	@Override
	public void into(HttpRequest request) {
		//添加一个元素并返回true,如果队列已满，则返回false
		queue.offer(request);
	}

}
