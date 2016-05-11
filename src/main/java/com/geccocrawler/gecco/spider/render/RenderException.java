package com.geccocrawler.gecco.spider.render;

import com.geccocrawler.gecco.spider.SpiderBean;

public class RenderException extends Exception {

	private static final long serialVersionUID = 5034687491589622988L;

	private Class<? extends SpiderBean> spiderBeanClass;
	
	public RenderException(String message, Class<? extends SpiderBean> spiderBeanClass) {
		super(message);
		this.spiderBeanClass = spiderBeanClass;
	}
	
	public RenderException(String message, Class<? extends SpiderBean> spiderBeanClass, FieldRenderException cause) {
		super(message, cause);
		this.spiderBeanClass = spiderBeanClass;
	}

	public Class<? extends SpiderBean> getSpiderBeanClass() {
		return spiderBeanClass;
	}

	public void setSpiderBeanClass(Class<? extends SpiderBean> spiderBeanClass) {
		this.spiderBeanClass = spiderBeanClass;
	}

}
