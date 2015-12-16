package com.memory.gecco.spider.render;

import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;

import com.memory.gecco.annotation.RenderType;
import com.memory.gecco.spider.render.html.CustomFieldRenderFactory;

public class RenderFactory {
	
	private Map<RenderType, Render> renders;
	
	public RenderFactory(Reflections reflections) {
		CustomFieldRenderFactory customFieldRenderFactory = new CustomFieldRenderFactory(reflections);
		renders = new HashMap<RenderType, Render>();
		renders.put(RenderType.HTML, new HtmlRender(customFieldRenderFactory));
		renders.put(RenderType.JSON, new JsonRender());
	}
	
	public Render getRender(RenderType type) {
		return renders.get(type);
	}
	
}
