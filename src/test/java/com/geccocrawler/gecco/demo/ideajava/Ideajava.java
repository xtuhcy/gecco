package com.geccocrawler.gecco.demo.ideajava;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

public class Ideajava implements HtmlBean {

	private static final long serialVersionUID = 2012767596437557366L;
	
	@Attr("datetime")
	@HtmlField(cssPath="time")
	private String time;

	@Href(click=true)
	@HtmlField(cssPath=".title")
	private String detailUrl;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
	
}
