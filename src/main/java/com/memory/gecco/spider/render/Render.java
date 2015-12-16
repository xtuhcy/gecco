package com.memory.gecco.spider.render;

import java.util.List;

import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;

public interface Render {

	public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response);
	
	public List<HttpRequest> requests(HttpRequest request, SpiderBean bean);
}
