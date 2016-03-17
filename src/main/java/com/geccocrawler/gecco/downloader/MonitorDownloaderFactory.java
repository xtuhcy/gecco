package com.geccocrawler.gecco.downloader;

import net.sf.cglib.proxy.Enhancer;

import org.reflections.Reflections;

import com.geccocrawler.gecco.monitor.DownloadMointorIntercetor;

/**
 * 带监控的下载器工厂类
 * 
 * @author huchengyi
 *
 */
public class MonitorDownloaderFactory extends DownloaderFactory {
	
	public MonitorDownloaderFactory(Reflections reflections) {
		super(reflections);
	}

	@Override
	protected Object createDownloader(Class<?> downloaderClass)	throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(downloaderClass);
		enhancer.setCallback(new DownloadMointorIntercetor());
		Object o = enhancer.create();
		return o;
	}

}
