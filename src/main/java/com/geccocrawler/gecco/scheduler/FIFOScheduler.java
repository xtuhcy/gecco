package com.geccocrawler.gecco.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 采用阻塞的先进先出队列，最大支持Integer.MAX_VALUE个元素
 * FIFO队列可以实现广度优先遍历的爬虫
 * 
 * @author huchengyi
 *
 */
public class FIFOScheduler implements Scheduler {
	
	private static Log log = LogFactory.getLog(FIFOScheduler.class);

	private LinkedBlockingQueue<HttpRequest> starQueue;
	
	private ConcurrentLinkedQueue<HttpRequest> queue;
	
	public FIFOScheduler() {
		starQueue = new LinkedBlockingQueue<HttpRequest>();
		queue = new ConcurrentLinkedQueue<HttpRequest>();
	}
	
	@Override
	public void start(HttpRequest request) {
		if(log.isDebugEnabled()) {
			log.debug("<===[start]"+request.getUrl());
		}
		try {
			if(StringUtils.isNotEmpty(request.getUrl())) {
				starQueue.put(request);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 入队列，超过边界会阻塞等待
	 */
	@Override
	public void into(HttpRequest request) {
		if(request == null) {
			return;
		}
		if(queue.offer(request)) {
			if(log.isDebugEnabled()) {
				log.debug("<==="+request.getUrl());
			}
		} else {
			log.error(request.getUrl());
		}
	}

	/**
	 * 出队列，队列为空会阻塞等待
	 */
	@Override
	public synchronized HttpRequest out() {
		HttpRequest request = queue.poll();
		if(request == null) {
			try {
				request = starQueue.take();
				if(log.isDebugEnabled()) {
					log.debug("[start]===>"+request.getUrl());
				}
				start(request);
				return request;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			if(log.isDebugEnabled()) {
				log.debug("===>"+request.getUrl());
			}
			return request;
		}
	}
}
