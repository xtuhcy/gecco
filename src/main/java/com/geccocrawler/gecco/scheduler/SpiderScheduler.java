package com.geccocrawler.gecco.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 线程安全的非阻塞FIFO队列
 * 
 * @author huchengyi
 *
 */
@Deprecated
public class SpiderScheduler implements Scheduler {
	
	private static Log log = LogFactory.getLog(SpiderScheduler.class);
	
	private ConcurrentLinkedQueue<HttpRequest> queue;
	
	public SpiderScheduler() {
		queue = new ConcurrentLinkedQueue<HttpRequest>();
	}

	@Override
	public HttpRequest out() {
		HttpRequest request = queue.poll();
		if(request != null) {
			if(log.isDebugEnabled()) {
				log.debug("OUT:"+request.getUrl()+"(Referer:"+request.getHeaders().get("Referer")+")");
			}
		}
		return request;
	}

	@Override
	public void into(HttpRequest request) {
		queue.offer(request);
		if(log.isDebugEnabled()) {
			log.debug("INTO:"+request.getUrl()+"(Referer:"+request.getHeaders().get("Referer")+")");
		}
	}
}
