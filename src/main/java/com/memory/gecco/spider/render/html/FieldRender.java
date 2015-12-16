package com.memory.gecco.spider.render.html;

import net.sf.cglib.beans.BeanMap;

import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;

public interface FieldRender {
	
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean);

}
