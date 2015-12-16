package com.memory.gecco.spider;

import java.util.List;

import com.memory.gecco.downloader.AfterDownload;
import com.memory.gecco.downloader.BeforeDownload;
import com.memory.gecco.pipeline.Pipeline;
import com.memory.gecco.spider.render.Render;

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
