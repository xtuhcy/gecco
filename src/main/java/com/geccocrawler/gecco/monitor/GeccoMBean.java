package com.geccocrawler.gecco.monitor;

import org.weakref.jmx.Managed;

import com.geccocrawler.gecco.annotation.MBean;

@MBean("gecco")
public class GeccoMBean {
	
	/**
	 * 爬虫启动时间
	 */
	private String startTime;
	
	/**
	 * 初始抓取地址数量
	 */
	private int starUrlCount;
	
	/**
	 * 线程数量
	 */
	private int threadCount;
	
	/**
	 * 抓取间隔时间
	 */
	private int interval;

	@Managed
	public String getStartTime() {
		return startTime;
	}

	@Managed
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Managed
	public int getStarUrlCount() {
		return starUrlCount;
	}

	@Managed
	public void setStarUrlCount(int starUrlCount) {
		this.starUrlCount = starUrlCount;
	}

	@Managed
	public int getThreadCount() {
		return threadCount;
	}

	@Managed
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	@Managed
	public int getInterval() {
		return interval;
	}

	@Managed
	public void setInterval(int interval) {
		this.interval = interval;
	}

	@Managed
	public void monitor() {
		this.setInterval(GeccoMonitor.getInterval());
		this.setStartTime(GeccoMonitor.getStartTime());
		this.setStarUrlCount(GeccoMonitor.getStarUrlCount());
		this.setThreadCount(GeccoMonitor.getThreadCount());
	}
}
