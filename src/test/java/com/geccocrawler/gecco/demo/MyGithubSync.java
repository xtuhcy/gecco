package com.geccocrawler.gecco.demo;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = "https://github.com/{user}/{project}", pipelines = "syncReturnPipeline", timeout = 1000)
public class MyGithubSync implements HtmlBean {

    @Request
    private HttpRequest request;

    @RequestParameter("user")
    private String user;

    @RequestParameter("project")
    private String project;

    @Text(own = false)
    @HtmlField(cssPath = "#repo-meta-edit span.text-gray-dark.mr-2")
    private String title;

    @Text(own = false)
    @HtmlField(cssPath = ".pagehead-actions li:nth-child(2) .social-count")
    private String star;

    @Text
    @HtmlField(cssPath = ".pagehead-actions li:nth-child(3) .social-count")
    private int fork;

    @Href
    @HtmlField(cssPath = "ul.numbers-summary > li:nth-child(4) > a")
    private String contributors;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
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

    public static void main(String[] args) throws Exception {
        //同步的方式爬取，该方式只支持一个seed页面，不建议在生产环境中使用
        MyGithubSync mgs = (MyGithubSync)GeccoEngine.create().classpath("com.geccocrawler.gecco.demo")
                .seed("https://github.com/xtuhcy/gecco").call();
        System.out.println(mgs.getProject());
    }
}
