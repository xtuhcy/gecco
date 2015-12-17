package com.memory.gecco.spider.render;

import java.util.List;

import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;

/**
 * 渲染器，将下载下来的各种格式内容对象化，并将需要继续抓取的链接抽取出来
 * 
 * @author huchengyi
 *
 */
public interface Render {

	public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response);
	
	public List<HttpRequest> requests(HttpRequest request, SpiderBean bean);
}
