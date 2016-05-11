package com.geccocrawler.gecco.spider.render.jsonp;

import net.sf.cglib.beans.BeanMap;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.AbstractRender;
import com.geccocrawler.gecco.spider.render.FieldRenderException;

/**
 * 将下载下来的json映射到bean中
 *
 * @author huchengyi
 *
 */
public class JsonpRender extends AbstractRender {

	private JsonpFieldRender jsonpFieldRender;

	public JsonpRender() {
		super();
		this.jsonpFieldRender = new JsonpFieldRender();
	}

	@Override
	public void fieldRender(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) throws FieldRenderException {
		jsonpFieldRender.render(request, response, beanMap, bean);
	}

}