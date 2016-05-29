package com.geccocrawler.gecco.demo.osc.model;

import java.io.Serializable;

public class Oscnews implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6186974682934500456L;

	private int id;
	
	private String title;
	
	private String pubDate;
	
	private String textContent;
	
	private String image;
	
	private String newsLinks;
	
	private String page;
	
	private String createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
