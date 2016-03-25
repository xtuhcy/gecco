package com.geccocrawler.gecco.demo.ideajava;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://www.ideajava.org/article/{code}", pipelines="consolePipeline")
public class IdeajavaDetail implements HtmlBean {

	private static final long serialVersionUID = 2012767596437557366L;
	
	@HtmlField(cssPath=".panel-title h3")
	private String title;
	
	@HtmlField(cssPath=".panel-body.article")
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
