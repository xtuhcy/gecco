package com.geccocrawler.gecco.downloader.proxy;

import com.geccocrawler.gecco.spider.SpiderThreadLocal;

public class ProxysContext {
	
	public static Proxys get() {
		return SpiderThreadLocal.get().getEngine().getProxysLoader();
	}
	
	public static boolean isEnableProxy() {
		return SpiderThreadLocal.get().getEngine().isProxy();
	}

}
