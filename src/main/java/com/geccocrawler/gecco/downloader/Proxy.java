package com.geccocrawler.gecco.downloader;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpHost;

public class Proxy {
	
	private HttpHost httpHost;
	
	private AtomicLong successCount;
	
	private AtomicLong failureCount;
	
	public Proxy(String host, int port) {
		this.httpHost = new HttpHost(host, port);
		this.successCount = new AtomicLong(0);
		this.failureCount = new AtomicLong(0);
	}

	public HttpHost getHttpHost() {
		return httpHost;
	}

	public void setHttpHost(HttpHost httpHost) {
		this.httpHost = httpHost;
	}

	public AtomicLong getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(AtomicLong successCount) {
		this.successCount = successCount;
	}

	public AtomicLong getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(AtomicLong failureCount) {
		this.failureCount = failureCount;
	}

	public String toHostString() {
		return httpHost.toHostString();
	}

	@Override
	public String toString() {
		return "Proxy [httpHost=" + httpHost + ", successCount=" + successCount
				+ ", failureCount=" + failureCount + "]";
	}
	
}
