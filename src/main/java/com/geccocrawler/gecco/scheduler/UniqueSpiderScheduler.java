package com.geccocrawler.gecco.scheduler;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpPostRequest;
import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 保证队列内容唯一，剔除重复抓取
 * 
 * @author huchengyi
 *
 */
public class UniqueSpiderScheduler implements Scheduler {
	
	private static Log log = LogFactory.getLog(UniqueSpiderScheduler.class);
	
	private NavigableSet<SortHttpRequest> set;
	
	public UniqueSpiderScheduler() {
		set = new ConcurrentSkipListSet<SortHttpRequest>(new Comparator<SortHttpRequest>() {

			@Override
			public int compare(SortHttpRequest o1, SortHttpRequest o2) {
				if(o1.getHttpRequest().hashCode() == o2.getHttpRequest().hashCode()) {
					if(o1.getHttpRequest().equals(o2.getHttpRequest())) {
						return 0;
					}
				}
				return (int)(o1.getPriority() - o2.getPriority());
			}
			
		});
	}

	@Override
	public HttpRequest out() {
		SortHttpRequest sortHttpRequest = set.pollFirst();
		if(sortHttpRequest == null) {
			return null;
		}
		HttpRequest request = sortHttpRequest.getHttpRequest();
		if(request != null && log.isDebugEnabled()) {
			log.debug("OUT:"+request.getUrl()+"(Referer:"+request.getHeaders().get("Referer")+")");
		}
		return request;
	}

	@Override
	public void into(HttpRequest request) {
		boolean success = set.add(new SortHttpRequest(System.nanoTime(), request));
		if(success && log.isDebugEnabled()) {
			log.debug("INTO:"+request.getUrl()+"(Referer:"+request.getHeaders().get("Referer")+")");
		}
		if(!success) {
			log.error("not unique request : " + request.getUrl());
		}
	}
	
	private class SortHttpRequest {
		
		private long priority;
		
		private HttpRequest httpRequest;

		public SortHttpRequest(long priority, HttpRequest httpRequest) {
			super();
			this.priority = priority;
			this.httpRequest = httpRequest;
		}

		public long getPriority() {
			return priority;
		}

		public HttpRequest getHttpRequest() {
			return httpRequest;
		}

	}
	
	public static void main(String[] args) {
		UniqueSpiderScheduler uss = new UniqueSpiderScheduler();
		HttpPostRequest post1 = new HttpPostRequest();
		post1.setUrl("http://www.1631.com");
		post1.addField("a", "a");
		HttpPostRequest post2 = new HttpPostRequest();
		post2.setUrl("http://www.1632.com");
		post2.addField("a", "b");
		uss.into(post1);
		uss.into(post2);
		
		HttpGetRequest get1 = new HttpGetRequest();
		get1.setUrl("http://www.163.com");
		get1.addParameter("1", "1");
		HttpGetRequest get2 = new HttpGetRequest();
		get2.setUrl("http://www.163.com");
		uss.into(get1);
		uss.into(get2);
		
		System.out.println(uss.out());
		System.out.println(uss.out());
		System.out.println(uss.out());
		System.out.println(uss.out());
	}
}
