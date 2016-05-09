package com.geccocrawler.gecco.scheduler;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 保证队列内容唯一，剔除重复抓取
 * 
 * @author huchengyi
 *
 */
public class UniqueSpiderScheduler implements Scheduler {
	
	private static Log log = LogFactory.getLog(UniqueSpiderScheduler.class);
	
	private NavigableSet<HttpRequest> set;
	
	public UniqueSpiderScheduler() {
		set = new ConcurrentSkipListSet<HttpRequest>(new Comparator<HttpRequest>() {

			@Override
			public int compare(HttpRequest o1, HttpRequest o2) {
				if(o1.hashCode() == o2.hashCode()) {
					return 0;
				}
				if(o1.equals(o2)) {
					return 0;
				}
				return (int)(o1.getPriority() - o2.getPriority());
			}
			
		});
	}

	@Override
	public HttpRequest out() {
		HttpRequest request = set.pollFirst();
		if(request != null && log.isDebugEnabled()) {
			log.debug("OUT:"+request.getUrl()+"(Referer:"+request.getHeaders().get("Referer")+")");
		}
		return request;
	}

	@Override
	public void into(HttpRequest request) {
		request.setPriority(System.nanoTime());
		boolean success = set.add(request);
		if(success && log.isDebugEnabled()) {
			log.debug("INTO:"+request.getUrl()+"(Referer:"+request.getHeaders().get("Referer")+")");
		}
	}
}
