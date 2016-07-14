package com.geccocrawler.gecco.demo.dynamic;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.JsonPipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@PipelineName("allSortJsonPipeline")
public class AllSortJsonPipeline extends JsonPipeline {
	
	public static List<HttpRequest> sortRequests = new ArrayList<HttpRequest>();

	@Override
	public void process(JSONObject allSort) {
		HttpRequest currRequest = HttpGetRequest.fromJson(allSort.getJSONObject("request"));
		JSONArray categorys = allSort.getJSONArray("mobile");
		process(currRequest, categorys);
		/*List<Category> domestics = allSort.getDomestic();
		process(allSort, domestics);
		List<Category> bodys = allSort.getBaby();
		process(allSort, bodys);*/
	}
	
	private void process(HttpRequest currRequest, JSONArray categorys) {
		if(categorys == null) {
			return;
		}
		for(int i = 0; i < categorys.size(); i++) {
			JSONObject category = categorys.getJSONObject(i);
			JSONArray hrefs = category.getJSONArray("categorys");
			for(int j = 0; j < hrefs.size(); j++) {
				String url = hrefs.getJSONObject(j).getString("url")+"&delivery=1&page=1&JL=4_10_0&go=0";
				SchedulerContext.into(currRequest.subRequest(url));
			}
		}
	}

}