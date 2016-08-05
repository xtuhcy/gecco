package com.geccocrawler.gecco.demo.sogouwx;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

public class WeiXin implements HtmlBean {

	private static final long serialVersionUID = 5821685160506822729L;

	@Text
	@HtmlField(cssPath=".txt-box h4 a")
	private String title;
	
	@Text
	@HtmlField(cssPath=".txt-box p")
	private String text;
	
	@Href(click=true)
	@HtmlField(cssPath=".txt-box h4 a")
	private String detailUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
}
