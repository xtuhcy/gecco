package com.geccocrawler.gecco.monitor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DownloadMonitor {
	
	private static Log log = LogFactory.getLog(DownloadMonitor.class);
	
	private static Map<String, DownloadStatistics> statistics = new ConcurrentHashMap<String, DownloadStatistics>();
	
	//不公平重入锁,用来控制host的创建
	private static Lock lock = new ReentrantLock();
	
	public static Set<String> getHosts() {
		return statistics.keySet();
	}
	
	public static Map<String, DownloadStatistics> getStatistics() {
		return statistics;
	}
	
	/**
	 * 双重检查机制锁
	 * 
	 * @param host host
	 * @return DownloadStatistics
	 */
	public static DownloadStatistics getStatistics(String host) {
		DownloadStatistics downloadStatistics = statistics.get(host);
		if(downloadStatistics != null) {
			return downloadStatistics;
		}
		lock.lock();
		try{
			downloadStatistics = statistics.get(host);
			if(downloadStatistics == null) {
				downloadStatistics = new DownloadStatistics();
				statistics.put(host, downloadStatistics);
			}
		} finally {
			lock.unlock();
		}
		return downloadStatistics;
	}
	
	private static String getHost(String url) {
		try {
			URL requestUrl = new URL(url);
			String host = requestUrl.getHost();
			return host;
		} catch (MalformedURLException e) {
			log.error(e);
			return url;
		}
	}
	
	public static void incrSuccess(String url) {
		getStatistics(getHost(url)).incrSuccess();
	}
	
	public static void incrServerError(String url) {
		getStatistics(getHost(url)).incrServerError();
	}
	
	public static void incrException(String url) {
		getStatistics(getHost(url)).incrException();
	}
}
