package com.memory.gecco.spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.memory.gecco.annotation.Gecco;
import com.memory.gecco.annotation.RenderType;
import com.memory.gecco.downloader.DownloaderAOPFactory;
import com.memory.gecco.pipeline.Pipeline;
import com.memory.gecco.pipeline.PipelineFactory;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.spider.render.RenderFactory;
import com.memory.gecco.utils.UrlMatcher;

/**
 * SpiderBean的工厂类，实例化@Gecco注解的所有SpiderBean，并且生成SpiderBean的Context
 * 
 * @author huchengyi
 *
 */
public class SpiderBeanFactory {
	
	private Map<String, Class<? extends SpiderBean>> spiderBeans;
	
	private Map<String, SpiderBeanContext> spiderBeanContexts;
	
	private DownloaderAOPFactory downloaderAOPFactory;
	
	private RenderFactory renderFactory;
	
	private PipelineFactory pipelineFactory;
	
	public SpiderBeanFactory(String classPath) {
		Reflections reflections = new Reflections("com.memory.gecco", classPath);
		this.downloaderAOPFactory = new DownloaderAOPFactory(reflections);
		this.renderFactory = new RenderFactory(reflections);
		this.pipelineFactory = new PipelineFactory(reflections);
		this.spiderBeans = new HashMap<String, Class<? extends SpiderBean>>();
		this.spiderBeanContexts = new HashMap<String, SpiderBeanContext>();
		loadSpiderBean(reflections);
	}
	
	private void loadSpiderBean(Reflections reflections) {
		Set<Class<?>> spiderBeanClasses = reflections.getTypesAnnotatedWith(Gecco.class);
		for(Class<?> spiderBeanClass : spiderBeanClasses) {
			Gecco gecco = (Gecco)spiderBeanClass.getAnnotation(Gecco.class);
			String matchUrl = gecco.matchUrl();
			try {
				//SpiderBean spider = (SpiderBean)spiderBeanClass.newInstance();
				//判断是不是SpiderBeanClass????
				spiderBeans.put(matchUrl, (Class<? extends SpiderBean>)spiderBeanClass);
				SpiderBeanContext context = initContext(spiderBeanClass);
				spiderBeanContexts.put(spiderBeanClass.getName(), context);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public Class<? extends SpiderBean> matchSpider(HttpRequest request) {
		String url = request.getUrl();
		for(Map.Entry<String, Class<? extends SpiderBean>> entrys : spiderBeans.entrySet()) {
			String urlPattern = entrys.getKey();
			Map<String, String> params = UrlMatcher.match(url, urlPattern);
			if(params != null) {
				request.setParameters(params);
				Class<? extends SpiderBean> spider = entrys.getValue();
				return spider;
			}
		}
		return null;
	}
	
	public SpiderBeanContext getContext(Class<? extends SpiderBean> spider) {
		return spiderBeanContexts.get(spider.getName());
	}
	
	private SpiderBeanContext initContext(Class<?> spiderBeanClass) {
		SpiderBeanContext context = new SpiderBeanContext();
		
		String spiderBeanName = spiderBeanClass.getName();
		downloadContext(context, spiderBeanName);

		Gecco gecco = spiderBeanClass.getAnnotation(Gecco.class);
		renderContext(context, gecco.render());
		
		String[] pipelineNames = gecco.pipelines();
		pipelineContext(context, pipelineNames);
		
		return context;
	}
	
	private void downloadContext(SpiderBeanContext context, String spiderName) {
		context.setBeforeDownload(downloaderAOPFactory.getBefore(spiderName));
		context.setAfterDownload(downloaderAOPFactory.getAfter(spiderName));
	}
	
	private void renderContext(SpiderBeanContext context, RenderType renderType) {
		context.setRender(renderFactory.getRender(renderType));
	}
	
	private void pipelineContext(SpiderBeanContext context, String[] pipelineNames) {
		if(pipelineNames != null && pipelineNames.length > 0) {
			List<Pipeline> pipelines = new ArrayList<Pipeline>();
			for(String pipelineName : pipelineNames) {
				Pipeline pipeline = pipelineFactory.getPipeline(pipelineName);
				if(pipeline != null) {
					pipelines.add(pipeline);
				}
			}
			context.setPipelines(pipelines);
		}
	}

	public DownloaderAOPFactory getDownloaderAOPFactory() {
		return downloaderAOPFactory;
	}

	public RenderFactory getRenderFactory() {
		return renderFactory;
	}

	public PipelineFactory getPipelineFactory() {
		return pipelineFactory;
	}

}
