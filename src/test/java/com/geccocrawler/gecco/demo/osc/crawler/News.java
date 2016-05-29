package com.geccocrawler.gecco.demo.osc.crawler;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Image;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = "http://www.oschina.net/news/{page}/{word}", pipelines = { "consolePipeline", "newsPipeline" })
public class News implements HtmlBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1418936215695245711L;

	@Request
	private HttpRequest request;
	
	@RequestParameter
	private int page;
	
	@HtmlField(cssPath=".NewsEntity h1")
	private String title;
	
	@Html
	@HtmlField(cssPath=".NewsEntity .PubDate")
	private String pubDate;
	
	@Html
	@HtmlField(cssPath=".NewsEntity .TextContent")
	private String textContent;
	
	@Image
	@HtmlField(cssPath=".NewsEntity .TextContent image")
	private String image;
	
	@Html
	@HtmlField(cssPath=".NewsEntity .NewsLinks")
	private String newsLinks;

	
	
	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNewsLinks() {
		return newsLinks;
	}

	public void setNewsLinks(String newsLinks) {
		this.newsLinks = newsLinks;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public static void main(String[] args) {
		HttpGetRequest start = new HttpGetRequest("http://www.oschina.net/news/73761/");
		start.setCharset("GBK");
		GeccoEngine.create()
				.classpath("com.road.crawler.oscnews")
				.start(start)
				.run();
	}
}
