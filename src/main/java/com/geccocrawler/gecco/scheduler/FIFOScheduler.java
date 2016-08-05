package com.geccocrawler.gecco.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

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
@Deprecated
public class FIFOScheduler implements Scheduler {
	
	private static Log log = LogFactory.getLog(FIFOScheduler.class);

	private LinkedBlockingQueue<HttpRequest> starQueue;
	
	private ConcurrentLinkedQueue<HttpRequest> queue;
	
	private ReentrantLock outLock;
	
	public FIFOScheduler() {
		starQueue = new LinkedBlockingQueue<HttpRequest>();
		queue = new ConcurrentLinkedQueue<HttpRequest>();
		outLock = new ReentrantLock();
	}
	
	/*@Override
	public void start(List<HttpRequest> requests) {
		try {
			for(HttpRequest request : requests) {
				if(StringUtils.isNotEmpty(request.getUrl())) {
					starQueue.put(request);
					if(log.isDebugEnabled()) {
						log.debug("<===[start]"+request.getUrl());
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
	
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
	public HttpRequest out() {
		outLock.lock();
		try {
			HttpRequest request = queue.poll();
			if(request == null) {
					request = starQueue.take();
					if(log.isDebugEnabled()) {
						log.debug("[start]===>"+request.getUrl());
					}
					starQueue.put(request);
					if(log.isDebugEnabled()) {
						log.debug("<===[start]"+request.getUrl());
					}
					return request;
			} else {
				if(log.isDebugEnabled()) {
					log.debug("===>"+request.getUrl());
				}
				return request;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} finally {
			outLock.unlock();
		}
	}
}
