package com.geccocrawler.gecco.spider.render;

import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;

import com.geccocrawler.gecco.spider.render.html.HtmlRender;
import com.geccocrawler.gecco.spider.render.json.JsonRender;

public class RenderFactory {
	
	private Map<RenderType, Render> renders;
	
	public RenderFactory(Reflections reflections) {
		CustomFieldRenderFactory customFieldRenderFactory = new CustomFieldRenderFactory(reflections);
		renders = new HashMap<RenderType, Render>();
		renders.put(RenderType.HTML, new HtmlRender(customFieldRenderFactory));
		renders.put(RenderType.JSON, new JsonRender(customFieldRenderFactory));
	}
	
	public Render getRender(RenderType type) {
		return renders.get(type);
	}
	
}
