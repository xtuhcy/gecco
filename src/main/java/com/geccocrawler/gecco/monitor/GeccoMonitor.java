package com.geccocrawler.gecco.monitor;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.geccocrawler.gecco.GeccoEngine;

public class GeccoMonitor {
	
	/**
	 * 爬虫启动时间
	 */
	private static String startTime;
	
	/**
	 * 初始抓取地址数量
	 */
	private static int starUrlCount;
	
	/**
	 * 线程数量
	 */
	private static int threadCount;
	
	/**
	 * 抓取间隔时间
	 */
	private static int interval;
	
	public static String getStartTime() {
		return startTime;
	}

	public static void setStartTime(String startTime) {
		GeccoMonitor.startTime = startTime;
	}

	public static int getStarUrlCount() {
		return starUrlCount;
	}

	public static void setStarUrlCount(int starUrlCount) {
		GeccoMonitor.starUrlCount = starUrlCount;
	}

	public static int getThreadCount() {
		return threadCount;
	}

	public static void setThreadCount(int threadCount) {
		GeccoMonitor.threadCount = threadCount;
	}

	public static int getInterval() {
		return interval;
	}

	public static void setInterval(int interval) {
		GeccoMonitor.interval = interval;
	}

	public static void monitor(GeccoEngine engine) {
		setStartTime(DateFormatUtils.format(engine.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		setStarUrlCount(engine.getStartRequests().size());
		setThreadCount(engine.getThreadCount());
		setInterval(engine.getInterval());
	}
}
