package com.geccocrawler.gecco.spider.render;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;

/**
 * 注入bean的多个filed异常
 * 
 * @author huchengyi
 *
 */
public class RenderException extends Exception {
	
	private static Log log = LogFactory.getLog(RenderException.class);

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

	public static void log(String message, Class<? extends SpiderBean> spiderBeanClass, Throwable cause) {
		boolean debug = SpiderThreadLocal.get().getEngine().isDebug();
		log.error(spiderBeanClass.getName() + " render error : " + message);
		if(debug && cause != null) {
			log.error(message, cause);
		}
	}
	
	public static void log(String message, Class<? extends SpiderBean> spiderBeanClass) {
		log(message, spiderBeanClass, null);
	}
}
