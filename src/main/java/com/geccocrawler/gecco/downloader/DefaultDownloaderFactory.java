package com.geccocrawler.gecco.downloader;

import org.reflections.Reflections;

/**
 * 下载器工厂类
 * 
 * @author huchengyi
 *
 */
public class DefaultDownloaderFactory extends DownloaderFactory {

	public DefaultDownloaderFactory(Reflections reflections) {
		super(reflections);
	}

	protected Object createDownloader(Class<?> downloaderClass) throws Exception {
		return downloaderClass.newInstance();
	}
}
