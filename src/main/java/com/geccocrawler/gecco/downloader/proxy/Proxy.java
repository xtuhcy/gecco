package com.geccocrawler.gecco.downloader.proxy;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpHost;

public class Proxy {
	
	private HttpHost httpHost;
	
	private AtomicLong successCount;
	
	private AtomicLong failureCount;
	
	private String src;//来源
	
	public Proxy(String host, int port) {
		this.httpHost = new HttpHost(host, port);
		this.src = "custom";
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
	
	public String getIP() {
		return this.getHttpHost().getHostName();
	}
	
	public int getPort() {
		return this.getHttpHost().getPort();
	}

	public String toHostString() {
		return httpHost.toHostString();
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Override
	public String toString() {
		return "Proxy [httpHost=" + httpHost + ", successCount=" + successCount
				+ ", failureCount=" + failureCount + "]";
	}
	
}
