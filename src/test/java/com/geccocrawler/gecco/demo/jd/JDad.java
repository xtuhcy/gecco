package com.geccocrawler.gecco.demo.jd;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.annotation.JSONPath;
import com.geccocrawler.gecco.spider.JsonBean;

public class JDad implements JsonBean {

	private static final long serialVersionUID = 2250225801616402995L;

	@JSONPath("$.ads[0].ad")
	private String ad;

	@JSONPath("$.ads")
	private List<JSONObject> ads;
	
	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}
	
	public List<JSONObject> getAds() {
		return ads;
	}

	public void setAds(List<JSONObject> ads) {
		this.ads = ads;
	}

}
