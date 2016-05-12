package com.geccocrawler.gecco.spider.render;

import org.reflections.Reflections;

import com.geccocrawler.gecco.spider.render.html.HtmlRender;
import com.geccocrawler.gecco.spider.render.json.JsonRender;
import com.geccocrawler.gecco.spider.render.jsonp.JsonpRender;

public class DefaultRenderFactory extends RenderFactory {

	public DefaultRenderFactory(Reflections reflections) {
		super(reflections);
	}

	public HtmlRender createHtmlRender() {
		return new HtmlRender();
	}

	public JsonRender createJsonRender() {
		return new JsonRender();
	}

	@Override
	public JsonpRender createJsonpRender() {
		return new JsonpRender();
	}

}
