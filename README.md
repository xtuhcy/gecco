![ci](https://api.travis-ci.org/xtuhcy/gecco.svg?branch=master)
![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

## What is Gecco

Gecco is a easy to use lightweight web crawler developed with java language.Gecco integriert jsoup, httpclient, fastjson, spring, htmlunit, redission ausgezeichneten framework,Let you only need to configure a number of jQuery style selector can be very quick to write a crawler.Gecco framework has excellent scalability, the framework based on the principle of open and close design, to modify the closure, the expansion of open.At the same time Gecco is based on a very open MIT open source protocol, whether you are a user or want to jointly improve the Gecco developer, welcome to request pull.If you like the crawler framework,please [star or fork](https://github.com/xtuhcy/gecco)!

- [中文说明](https://github.com/xtuhcy/gecco/blob/master/README_CN.md)

- [中文参考手册](http://www.geccocrawler.com/)

## Main features

- [x] Easy to use, use jQuery style selector to extract elements
- [x] Support for asynchronous Ajax requests in the page
- [x] Support page JavaScript variable extraction
- [x] Using Redis to realize distributed crawling,reference [gecco-redis](https://github.com/xtuhcy/gecco-redis)
- [x] Support the development of business logic with Spring,reference [gecco-spring](https://github.com/xtuhcy/gecco-spring)
- [x] Support htmlunit extension,reference [gecco-htmlunit](https://github.com/xtuhcy/gecco-htmlunit)
- [x] Support extension mechanism
- [x] Support download UserAgent random selection
- [x] Support the download proxy server randomly selected

## Framework overview

![架构图](https://raw.githubusercontent.com/xtuhcy/gecco/master/doc/%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)

## Download

### Download via Maven

```xml
<dependency>
    <groupId>com.geccocrawler</groupId>
    <artifactId>gecco</artifactId>
    <version>x.x.x</version>
</dependency>
```

![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)

### Dependent project

httpclient，jsoup，fastjson，reflections，cglib，rhino，log4j，jmxutils，commons-lang3

## Quick start

```java
@Gecco(matchUrl="https://github.com/{user}/{project}", pipelines="consolePipeline")
public class MyGithub implements HtmlBean {

    private static final long serialVersionUID = -7127412585200687225L;

    @RequestParameter("user")
    private String user;

    @RequestParameter("project")
    private String project;

    @Text
    @HtmlField(cssPath=".pagehead-actions li:nth-child(2) .social-count")
    private String star;

    @Text
    @HtmlField(cssPath=".pagehead-actions li:nth-child(3) .social-count")
    private String fork;

    @Html
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

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    public static void main(String[] args) {
        GeccoEngine.create()
        .classpath("com.geccocrawler.gecco.demo")
        .start("https://github.com/xtuhcy/gecco")
        .thread(1)
        .interval(2000)
        .loop(true)
        .mobile(false)
        .start();
    }
}
```

## DynamicGecco

The purpose of DynamicGecco is to implement the runtime configuration of the crawl rule without defining the SpiderBean.In fact, the principle is the use of byte code programming, dynamic generation of SpiderBean, but also through the custom GeccoClassLoader to achieve the rule of hot deployment.Below is a simple Demo, more complex Demo can refer to the example below com.geccocrawler.gecco.demo.dynamic.

The following code implements the runtime configuration of the crawl rule:

    DynamicGecco.html()
    .gecco("https://github.com/{user}/{project}", "consolePipeline")
    .requestField("request").request().build()
    .stringField("user").requestParameter("user").build()
    .stringField("project").requestParameter().build()
    .stringField("star").csspath(".pagehead-actions li:nth-child(2) .social-count").text(false).build()
    .stringField("fork").csspath(".pagehead-actions li:nth-child(3) .social-count").text().build()
    .stringField("contributors").csspath("ul.numbers-summary > li:nth-child(4) > a").href().build()
    .register();

    GeccoEngine.create()
    .classpath("com.geccocrawler.gecco.demo")
    .start("https://github.com/xtuhcy/gecco")
    .run();

You can see that the DynamicGecco way compared to the traditional way of annotation code greatly reduced, and a very cool point is DynamicGecco to support the operation of the definition and modification of rules.

## Demo

[教您使用 java 爬虫 gecco 抓取 JD 全部商品信息（一）](http://my.oschina.net/u/2336761/blog/620158)

[教您使用 java 爬虫 gecco 抓取 JD 全部商品信息（二）](http://my.oschina.net/u/2336761/blog/620827)

[教您使用 java 爬虫 gecco 抓取 JD 全部商品信息（三）](http://my.oschina.net/u/2336761/blog/624683)

[集成 Htmlunit 下载页面](http://my.oschina.net/u/2336761/blog/631959)

[爬虫的监控](http://my.oschina.net/u/2336761/blog/644330)

[一个完整的例子，分页处理，结合 spring，mysql 入库](http://git.oschina.net/xiaomaoguai/gecco-demo)

## Similar Tool Comparison

A list of similar tools and how they compare is available here:

[Web Archiving Software Comparision](https://github.com/archivers-space/research/tree/master/web_archiving)

## Contact and communication

- blog：http://my.oschina.net/u/2336761/blog
- email：xtuhcy@163.com

## 请作者喝杯咖啡

Gecco 的发展离不开大家支持，扫一扫请作者喝杯咖啡～

![支付宝](http://www.geccocrawler.com/content/images/jz-zfb.jpg?xx=2)
![支付宝](http://www.geccocrawler.com/content/images/jz-wx.png)

## License

Please follow the open source protocol [MIT](https://raw.githubusercontent.com/xtuhcy/gecco/master/LICENSE)!
