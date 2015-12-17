package com.memory.gecco.spider;

import java.util.List;

import com.memory.gecco.downloader.AfterDownload;
import com.memory.gecco.downloader.BeforeDownload;
import com.memory.gecco.pipeline.Pipeline;
import com.memory.gecco.spider.render.Render;

/**
 * 渲染bean的上下文对象。
 * 包括下载前的自定义操作beforeDownload,下载后的自定义操作afterDownload。
 * 使用的哪种渲染器渲染bean，目前支持html、json、xml。
 * 渲染完成后通过管道过滤器进行bean的进步一部清洗和整理。
 * 
 * @author huchengyi
 *
 */
public class SpiderBeanContext {
	
	private Render render;
	
	private BeforeDownload beforeDownload;
	
	private AfterDownload afterDownload;
	
	private List<Pipeline> pipelines;

	public Render getRender() {
		return render;
	}

	public void setRender(Render render) {
		this.render = render;
	}

	public BeforeDownload getBeforeDownload() {
		return beforeDownload;
	}

	public void setBeforeDownload(BeforeDownload beforeDownload) {
		this.beforeDownload = beforeDownload;
	}

	public AfterDownload getAfterDownload() {
		return afterDownload;
	}

	public void setAfterDownload(AfterDownload afterDownload) {
		this.afterDownload = afterDownload;
	}

	public List<Pipeline> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<Pipeline> pipelines) {
		this.pipelines = pipelines;
	}

}
