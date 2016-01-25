package com.geccocrawler.gecco.spider.render;

import com.geccocrawler.gecco.spider.SpiderThreadLocal;

public class RenderContext {
	
	public static Render getRender(RenderType type){
		return SpiderThreadLocal.get().getEngine().getSpiderBeanFactory().getRenderFactory().getRender(type);
	}

}
