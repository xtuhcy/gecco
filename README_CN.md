![ci](https://api.travis-ci.org/xtuhcy/gecco.svg?branch=master)
![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)

## Gecco 是什么

Gecco 是一款用 java 语言开发的轻量化的易用的网络爬虫。Gecco 整合了 jsoup、httpclient、fastjson、spring、htmlunit、redission 等优秀框架，让您只需要配置一些 jquery 风格的选择器就能很快的写出一个爬虫。Gecco 框架有优秀的可扩展性，框架基于开闭原则进行设计，对修改关闭、对扩展开放。同时 Gecco 基于十分开放的 MIT 开源协议，无论你是使用者还是希望共同完善 Gecco 的开发者，欢迎 pull request。如果你喜欢这款爬虫框架请[star 或者 fork](https://gitee.com/xtuhcy/gecco)!

[参考手册](http://www.geccocrawler.com/)

## 主要特征

- [x] 简单易用，使用 jquery 风格的选择器抽取元素
- [x] 支持爬取规则的动态配置和加载
- [x] 支持页面中的异步 ajax 请求
- [x] 支持页面中的 javascript 变量抽取
- [x] 利用 Redis 实现分布式抓取,参考[gecco-redis](https://github.com/xtuhcy/gecco-redis)
- [x] 支持结合 Spring 开发业务逻辑,参考[gecco-spring](https://github.com/xtuhcy/gecco-spring)
- [x] 支持 htmlunit 扩展,参考[gecco-htmlunit](https://github.com/xtuhcy/gecco-htmlunit)
- [x] 支持插件扩展机制
- [x] 支持下载时 UserAgent 随机选取
- [x] 支持下载代理服务器随机选取

## 框架概述

![架构图](https://raw.githubusercontent.com/xtuhcy/gecco/master/doc/%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)

### GeccoEngine

> GeccoEngine 是爬虫引擎，每个爬虫引擎最好是一个独立进程，在分布式爬虫场景下，建议每台爬虫服务器（物理机或者虚机）运行一个 GeccoEngine。爬虫引擎包括 Scheduler、Downloader、Spider、SpiderBeanFactory、PipelineFactory5 个主要模块。

### Scheduler

> 通常爬虫需要一个有效管理下载地址的角色，Scheduler 负责下载地址的管理。gecco 对初始地址的管理使用 StartScheduler，StartScheduler 内部采用一个阻塞的 FIFO 的队列。初始地址通常会派生出很多其他待抓取的地址，派生出来的其他地址采用 SpiderScheduler 进行管理，SpiderScheduler 内部采用线程安全的非阻塞 FIFO 队列。这种设计使的 gecco 对初始地址采用了深度遍历的策略，即一个线程抓取完一个初始地址后才会去抓取另外一个初始地址；对初始地址派生出来的地址，采用广度优先策略。

### Downloader

> Downloader 负责从 Scheduler 中获取需要下载的请求，gecco 默认采用 httpclient4.x 作为下载引擎。通过实现 Downloader 接口可以自定义自己的下载引擎。你也可以对每个请求定义 BeforeDownload 和 AfterDownload，实现不同的请求下载的个性需求。

### SpiderBeanFactory

> Gecco 将下载下来的内容渲染为 SpiderBean，所有爬虫渲染的 JavaBean 都统一继承 SpiderBean，SpiderBean 又分为 HtmlBean 和 JsonBean 分别对应 html 页面的渲染和 json 数据的渲染。SpiderBeanFactroy 会根据请求的 url 地址，匹配相应的 SpiderBean，同时生成该 SpiderBean 的上下文 SpiderBeanContext。上下文 SpiderBeanContext 会告知这个 SpiderBean 采用什么渲染器，采用那个下载器，渲染完成后采用哪些 pipeline 处理等相关上下文信息。

### PipelineFactory

> pipeline 是 SpiderBean 渲染完成的后续业务处理单元，PipelineFactory 是 pipeline 的工厂类，负责 pipeline 实例化。通过扩展 PipelineFactory 就可以实现和 Spring 等业务处理框架的整合。

### Spider

> Gecco 框架最核心的类应该是 Spider 线程，一个爬虫引擎可以同时运行多个 Spider 线程。Spider 描绘了这个框架运行的基本骨架，先从 Scheduler 获取请求，再通过 SpiderBeanFactory 匹配 SpiderBeanClass，再通过 SpiderBeanClass 找到 SpiderBean 的上下文，下载网页并对 SpiderBean 做渲染，将渲染后的 SpiderBean 交个 pipeline 处理。

## 下载

### 通过 Maven 下载

```xml
<dependency>
    <groupId>com.geccocrawler</groupId>
    <artifactId>gecco</artifactId>
    <version>x.x.x</version>
</dependency>
```

![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)

### 依赖项目

httpclient，jsoup，fastjson，reflections，cglib，rhino，log4j，jmxutils，commons-lang3

## 快速开始

```java
@Gecco(matchUrl="https://github.com/{user}/{project}", pipelines="consolePipeline")
public class MyGithub implements HtmlBean {

    private static final long serialVersionUID = -7127412585200687225L;

    @RequestParameter("user")
    private String user;//url中的{user}值

    @RequestParameter("project")
    private String project;//url中的{project}值

    @Text
    @HtmlField(cssPath=".pagehead-actions li:nth-child(2) .social-count")
    private String star;//抽取页面中的star

    @Text
    @HtmlField(cssPath=".pagehead-actions li:nth-child(3) .social-count")
    private String fork;//抽取页面中的fork

    @Html
    @HtmlField(cssPath=".entry-content")
    private String readme;//抽取页面中的readme

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
        //工程的包路径
        .classpath("com.geccocrawler.gecco.demo")
        //开始抓取的页面地址
        .start("https://github.com/xtuhcy/gecco")
        //开启几个爬虫线程
        .thread(1)
        //单个爬虫每次抓取完一个请求后的间隔时间
        .interval(2000)
        //循环抓取
        .loop(true)
        //使用pc端userAgent
        .mobile(false)
        //非阻塞方式运行
        .start();
    }
}
```

## DynamicGecco

DynamicGecco 的目的是在不定义 SpiderBean 的情况下实现爬取规则的运行时配置。其实现原理是采用字节码编程，动态生成 SpiderBean，而且通过自定义的 GeccoClassLoader 实现了抓取规则的热部署。下面是一个简单 Demo，更复杂的 Demo 可以参考 com.geccocrawler.gecco.demo.dynamic 下的例子。

下面的代码实现了爬取规则的运行时配置：

    DynamicGecco.html()
    .gecco("https://github.com/{user}/{project}", "consolePipeline")
    .requestField("request").request().build()
    .stringField("user").requestParameter("user").build()
    .stringField("project").requestParameter().build()
    .stringField("star").csspath(".pagehead-actions li:nth-child(2) .social-count").text(false).build()
    .stringField("fork").csspath(".pagehead-actions li:nth-child(3) .social-count").text().build()
    .stringField("contributors").csspath("ul.numbers-summary > li:nth-child(4) > a").href().build()
    .register();

    //开始抓取
    GeccoEngine.create()
    .classpath("com.geccocrawler.gecco.demo")
    .start("https://github.com/xtuhcy/gecco")
    .run();

可以看到，DynamicGecco 的方式相比传统的注解方式代码量大大减少，而且很酷的一点是 DynamicGecco 支持运行时定义和修改规则。

## 完整演示

[教您使用 java 爬虫 gecco 抓取 JD 全部商品信息（一）](http://my.oschina.net/u/2336761/blog/620158)

[教您使用 java 爬虫 gecco 抓取 JD 全部商品信息（二）](http://my.oschina.net/u/2336761/blog/620827)

[教您使用 java 爬虫 gecco 抓取 JD 全部商品信息（三）](http://my.oschina.net/u/2336761/blog/624683)

[集成 Htmlunit 下载页面](http://my.oschina.net/u/2336761/blog/631959)

[爬虫的监控](http://my.oschina.net/u/2336761/blog/644330)

[一个完整的例子，分页处理，结合 spring，mysql 入库](http://git.oschina.net/xiaomaoguai/gecco-demo)

## 交流联系

- 博客：http://my.oschina.net/u/2336761/blog
- 邮箱：xtuhcy@163.com
- QQ 群：531949844

## 请作者喝杯咖啡

Gecco 的发展离不开大家支持，扫一扫请作者喝杯咖啡～

![支付宝](http://www.geccocrawler.com/content/images/jz-zfb.jpg?xx=2)
![支付宝](http://www.geccocrawler.com/content/images/jz-wx.png)

## 开源协议

请遵守开源协议[MIT](https://raw.githubusercontent.com/xtuhcy/gecco/master/LICENSE)
