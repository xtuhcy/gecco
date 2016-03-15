package com.geccocrawler.gecco.monitor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	
	private enum DownloadError {
        ServerError, Exception, Success;
    }
	
	private static ConcurrentHashMap<String, AtomicLong> downloadSuccess = new ConcurrentHashMap<String, AtomicLong>();
	
	private static ConcurrentHashMap<String, AtomicLong> downloadException = new ConcurrentHashMap<String, AtomicLong>();
	
	private static ConcurrentHashMap<String, AtomicLong> downloadServerError = new ConcurrentHashMap<String, AtomicLong>();
	
	//不公平重入锁
	private static Lock lock = new ReentrantLock();
	
	private static void incr(ConcurrentHashMap<String, AtomicLong> download, String url, DownloadError error) {
		try {
			lock.lock();
			URL requestUrl = new URL(url);
			String host = requestUrl.getHost();
			AtomicLong atomicLong = download.get(host);
			if(atomicLong == null) {
				atomicLong = new AtomicLong(0);
				download.put(host, atomicLong);
			}
			atomicLong.incrementAndGet();
		} catch (MalformedURLException e) {}
		finally {
			lock.unlock();
		}
	}
	
	public static void incrSuccess(String url) {
		incr(downloadSuccess, url, DownloadError.Success);
	}
	
	public static void incrServerError(String url) {
		incr(downloadServerError, url, DownloadError.ServerError);
	}
	
	public static void incrException(String url) {
		incr(downloadException, url, DownloadError.Exception);
	}
	
	public static ConcurrentHashMap<String, AtomicLong> successStatics() {
		return downloadSuccess;
	}
	
	public static ConcurrentHashMap<String, AtomicLong> exceptionStatics() {
		return downloadException;
	}
	
	public static ConcurrentHashMap<String, AtomicLong> serverErrorStatics() {
		return downloadServerError;
	}

	public static void main(String[] args) throws Exception {
		final CountDownLatch cdl = new CountDownLatch(20);
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
	
				@Override
				public void run() {
					incrSuccess("http://www.163.com");
					cdl.countDown();
				}
				
			}).start();
		}
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
	
				@Override
				public void run() {
					incrServerError("http://www.sina.com");
					cdl.countDown();
				}
				
			}).start();
		}
		cdl.await();
		System.out.println(downloadSuccess);
		System.out.println(downloadServerError);
	}
}
