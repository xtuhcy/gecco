package com.memory.gecco.demo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.memory.gecco.annotation.Gecco;
import com.memory.gecco.annotation.HtmlField;
import com.memory.gecco.annotation.RequestParameter;
import com.memory.gecco.annotation.Text;
import com.memory.gecco.spider.SpiderBean;

@Gecco(matchUrl="https://github.com/{user}/{project}", pipelines="consolePipeline")
public class MyGithub implements SpiderBean {

	private static final long serialVersionUID = -7127412585200687225L;
	
	@RequestParameter("user")
	private String user;
	
	@RequestParameter("project")
	private String project;
	
	@Text
	@HtmlField(cssPath=".repository-meta-content")
	private String title;
	
	@Text
	@HtmlField(cssPath=".pagehead-actions li:nth-child(2) .social-count")
	private int star;
	
	@Text
	@HtmlField(cssPath=".pagehead-actions li:nth-child(3) .social-count")
	private int fork;
	
	@HtmlField(cssPath=".entry-content")
	private String readme;

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

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getFork() {
		return fork;
	}

	public void setFork(int fork) {
		this.fork = fork;
	}

}
