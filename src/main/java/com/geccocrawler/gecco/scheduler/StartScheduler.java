package com.geccocrawler.gecco.scheduler;

import java.util.concurrent.LinkedBlockingQueue;

import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 阻塞队列
 * 
 * @author huchengyi
 *
 */
public class StartScheduler implements Scheduler {
	
	private LinkedBlockingQueue<HttpRequest> startQueue;
	
	public StartScheduler() {
		startQueue = new LinkedBlockingQueue<HttpRequest>();
	}

	@Override
	public HttpRequest out() {
		try {
			//take:Retrieves and removes the head of this queue,
			//waiting if necessary until an element becomes available.
			//Block
			return startQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void into(HttpRequest request) {
		try {
			//put:Inserts the specified element at the tail of this queue,
			//waiting if necessary for space to become available.
			//Block
			startQueue.put(request);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
