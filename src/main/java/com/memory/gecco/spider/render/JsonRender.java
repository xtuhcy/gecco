package com.memory.gecco.spider.render;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;

public class JsonRender implements Render {

	@Override
	public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response) {
		String json = response.getContent();
		if(json == null) {
			return null;
		}
		json = json.trim();
		if(json.startsWith("[")) {
			JSONArray array = JSON.parseArray(json);
			if(array.size() > 0) {
				JSONObject jo = array.getJSONObject(0);
				SpiderBean bean = JSON.toJavaObject(jo, clazz);
				return bean;
			}
		} else {
			SpiderBean bean = JSON.parseObject(json, clazz); //反序列化
			return bean;
		}
		return null;
	}

	@Override
	public List<HttpRequest> requests(HttpRequest request, SpiderBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

}
