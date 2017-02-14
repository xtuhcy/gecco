package com.geccocrawler.gecco.downloader.proxy;

import org.apache.http.HttpHost;

public interface Proxys {
	
	public HttpHost getProxy();

	public boolean addProxy(String host, int port);
	
	public boolean addProxy(String host, int port, String src);
	
	public void failure(String host, int port);
	
	public void success(String host, int port);
	
}
