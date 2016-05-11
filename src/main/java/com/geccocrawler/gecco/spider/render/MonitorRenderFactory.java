package com.geccocrawler.gecco.spider.render;

import net.sf.cglib.proxy.Enhancer;

import org.reflections.Reflections;

import com.geccocrawler.gecco.monitor.RenderMointorIntercetor;
import com.geccocrawler.gecco.spider.render.html.HtmlRender;
import com.geccocrawler.gecco.spider.render.json.JsonRender;
import com.geccocrawler.gecco.spider.render.jsonp.JsonpRender;

public class MonitorRenderFactory extends RenderFactory {

	public MonitorRenderFactory(Reflections reflections) {
		super(reflections);
	}

	@Override
	public HtmlRender createHtmlRender() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HtmlRender.class);
		enhancer.setCallback(new RenderMointorIntercetor());
		return (HtmlRender)enhancer.create();
	}

	@Override
	public JsonRender createJsonRender() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(JsonRender.class);
		enhancer.setCallback(new RenderMointorIntercetor());
		return (JsonRender)enhancer.create();
	}

	@Override
	public JsonpRender createJsonpRender() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(JsonpRender.class);
		enhancer.setCallback(new RenderMointorIntercetor());
		return (JsonpRender)enhancer.create();
	}



}
