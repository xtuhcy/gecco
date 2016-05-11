package com.geccocrawler.gecco.spider.render;

import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;

import com.geccocrawler.gecco.spider.render.html.HtmlRender;
import com.geccocrawler.gecco.spider.render.json.JsonRender;
import com.geccocrawler.gecco.spider.render.jsonp.JsonpRender;

public abstract class RenderFactory {

	private Map<RenderType, Render> renders;

	public RenderFactory(Reflections reflections) {
		CustomFieldRenderFactory customFieldRenderFactory = new CustomFieldRenderFactory(reflections);
		renders = new HashMap<RenderType, Render>();

		AbstractRender htmlRender = createHtmlRender();
		htmlRender.setCustomFieldRenderFactory(customFieldRenderFactory);

		AbstractRender jsonRender = createJsonRender();
		jsonRender.setCustomFieldRenderFactory(customFieldRenderFactory);

		AbstractRender jsonpRender = createJsonpRender();
		jsonpRender.setCustomFieldRenderFactory(customFieldRenderFactory);

		renders.put(RenderType.HTML, htmlRender);
		renders.put(RenderType.JSON, jsonRender);
		renders.put(RenderType.JSONP, jsonpRender);
	}

	public Render getRender(RenderType type) {
		return renders.get(type);
	}

	public abstract HtmlRender createHtmlRender();

	public abstract JsonRender createJsonRender();

	public abstract JsonpRender createJsonpRender();

}
