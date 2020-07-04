package com.geccocrawler.gecco.demo;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="https://github.com/{user}/{project}", pipelines="consolePipeline", timeout=1000)
public class MyGithub implements HtmlBean {

	private static final long serialVersionUID = -7127412585200687225L;
	
	@Request
	private HttpRequest request;
	
	@RequestParameter("user")
	private String user;
	
	@RequestParameter("project")
	private String project;
	
	@Text(own=false)
	@HtmlField(cssPath="#repo-meta-edit span.text-gray-dark.mr-2")
	private String title;
	
	@Text(own=false)
	@HtmlField(cssPath=".pagehead-actions li:nth-child(2) .social-count")
	private String star;

	@Text
	@HtmlField(cssPath=".pagehead-actions li:nth-child(3) .social-count")
	private int fork;

	@Href
	@HtmlField(cssPath="ul.numbers-summary > li:nth-child(4) > a")
	private String contributors;
	
	//@HtmlField(cssPath=".entry-content")
	private String readme;

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getReadme() {
		return readme;
	}

	public void setReadme(String readme) {
		this.readme = readme;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public int getFork() {
		return fork;
	}

	public void setFork(int fork) {
		this.fork = fork;
	}
	
	public String getContributors() {
		return contributors;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public static void main(String[] args) {
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo")
		//开始抓取的页面地址
		.seed("https://github.com/xtuhcy/gecco")
		.seed("https://github.com/xtuhcy/gecco-spring")
		//开启几个爬虫线程,线程数量最好不要大于seed request数量
		.thread(2)
		//单个爬虫每次抓取完一个请求后的间隔时间
		.interval(2000)
		//循环抓取
		.loop(true)
		//采用pc端userAgent
		.mobile(false)
		//是否开启debug模式，跟踪页面元素抽取
		.debug(false)
		//非阻塞方式运行
		.start();
	}

}
