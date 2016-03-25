package com.geccocrawler.gecco.demo.ideajava;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://www.ideajava.org/articleIndex", pipelines="consolePipeline")
public class IdeajavaList implements HtmlBean {

	private static final long serialVersionUID = 2012767596437557366L;
	
	@HtmlField(cssPath="body > div > div > div.col-md-9 > div > div.panel-body > .article-item")
	private List<Ideajava> ideajavas;

	public List<Ideajava> getIdeajavas() {
		return ideajavas;
	}

	public void setIdeajavas(List<Ideajava> ideajavas) {
		this.ideajavas = ideajavas;
	}

	public static void main(String[] args) {
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo")
		//开始抓取的页面地址
		.start("http://www.ideajava.org/articleIndex")
		//开启几个爬虫线程,线程数量最好不要大于start request数量
		.thread(1)
		//单个爬虫每次抓取完一个请求后的间隔时间
		.interval(2000)
		.run();
	}
}
