package com.geccocrawler.gecco.demo.dynamic;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.JsonPipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("jdDynamicListPipeline")
public class JDDynamicListPipeline extends JsonPipeline {

	/**
	 * 由于bean是动态生成，pipline的process方法只能传入SpiderBean类型，一般通过将bean转换为json对象进行处理
	 * 
	 */
	@Override
	public void process(JSONObject jo) {
		HttpRequest currRequest = (HttpGetRequest)JSON.toJavaObject(jo.getJSONObject("request"), HttpGetRequest.class);
		//下一页继续抓取
		int currPage = jo.getIntValue("currPage");
		int nextPage = currPage + 1;
		int totalPage = jo.getIntValue("totalPage");
		if(nextPage <= totalPage) {
			String nextUrl = "";
			String currUrl = currRequest.getUrl();
			if(currUrl.indexOf("page=") != -1) {
				nextUrl = StringUtils.replaceOnce(currUrl, "page=" + currPage, "page=" + nextPage);
			} else {
				nextUrl = currUrl + "&" + "page=" + nextPage;
			}
			SchedulerContext.into(currRequest.subRequest(nextUrl));
		}
	}

}
