package com.geccocrawler.gecco.spider;

/**
 * 爬虫线程本地变量，后续操作可以提取当前爬虫相关数据
 * 
 * @author huchengyi
 *
 */
public class SpiderThreadLocal {
	
	private static ThreadLocal<Spider> spiderThreadLocal = new ThreadLocal<Spider>();
	
	public static void set(Spider spider) {
		spiderThreadLocal.set(spider);
	}
	
	public static Spider get() {
		return spiderThreadLocal.get();
	}
	
}
