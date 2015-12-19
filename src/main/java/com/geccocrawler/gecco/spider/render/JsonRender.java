package com.geccocrawler.gecco.spider.render;

import java.util.List;

import net.sf.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.render.html.HtmlFieldRender;
import com.geccocrawler.gecco.spider.render.html.RequestParameterFieldRender;
import com.geccocrawler.gecco.spider.render.json.JsonFieldRender;

/**
 * 将下载下来的json映射到bean中
 * 
 * @author huchengyi
 *
 */
public class JsonRender implements Render {
	
	private RequestParameterFieldRender requestParameterFieldRender;

	private JsonFieldRender jsonFieldRender;
	
	public JsonRender() {
		this.requestParameterFieldRender = new RequestParameterFieldRender();
		this.jsonFieldRender = new JsonFieldRender();
	}
	
	@Override
	public SpiderBean inject(Class<? extends SpiderBean> clazz, HttpRequest request, HttpResponse response) {
		try {
			SpiderBean bean = clazz.newInstance();  
			BeanMap beanMap = BeanMap.create(bean);
			requestParameterFieldRender.render(request, response, beanMap, bean);
			jsonFieldRender.render(request, response, beanMap, bean);
			return bean;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		/*String json = response.getContent();
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
			//JSONPath.eval(bean, path)
			return bean;
		}
		return null;*/
	}

	@Override
	public List<HttpRequest> requests(HttpRequest request, SpiderBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

}