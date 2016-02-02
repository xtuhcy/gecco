package com.geccocrawler.gecco.spider.render.html;

import net.sf.cglib.beans.BeanMap;

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.AbstractRender;
import com.geccocrawler.gecco.spider.render.CustomFieldRenderFactory;

/**
 * 将下载下来的html映射到bean中
 * 
 * @author huchengyi
 *
 */
public class HtmlRender extends AbstractRender {
	
	private HtmlFieldRender htmlFieldRender;
	
	private AjaxFieldRender ajaxFieldRender;
	
	private JSVarFieldRender jsVarFieldRender;
	
	public HtmlRender(CustomFieldRenderFactory customFieldRenderFactory) {
		super(customFieldRenderFactory);
		this.htmlFieldRender = new HtmlFieldRender();
		this.ajaxFieldRender = new AjaxFieldRender();
		this.jsVarFieldRender = new JSVarFieldRender();
	}

	@Override
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		htmlFieldRender.render(request, response, beanMap, bean);
		ajaxFieldRender.render(request, response, beanMap, bean);
		jsVarFieldRender.render(request, response, beanMap, bean);
	}

}
