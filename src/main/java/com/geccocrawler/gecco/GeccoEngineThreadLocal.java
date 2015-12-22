package com.geccocrawler.gecco;

import com.geccocrawler.gecco.downloader.Downloader;
import com.geccocrawler.gecco.scheduler.Scheduler;
import com.geccocrawler.gecco.spider.render.Render;
import com.geccocrawler.gecco.spider.render.RenderType;

/**
 * 爬虫引擎线程本地变量， 每个Spider线程均持有当前爬虫引擎对象
 * 
 * @author huchengyi
 *
 */
public class GeccoEngineThreadLocal {
	
	private static ThreadLocal<GeccoEngine> engineThreadLocal = new ThreadLocal<GeccoEngine>();
	
	public static void set(GeccoEngine engine) {
		engineThreadLocal.set(engine);
	}
	
	public static GeccoEngine get() {
		return engineThreadLocal.get();
	}
	
	public static Scheduler getScheduler() {
		return get().getScheduler();
	}
	
	public static Downloader getDownloader() {
		return get().getDownloader();
	}
	
	public static Render getRender(RenderType type) {
		return get().getSpiderBeanFactory().getRenderFactory().getRender(type);
	}

}
