![ci](https://api.travis-ci.org/xtuhcy/gecco.svg?branch=master)
![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)

##Gecco是什么
Gecco是一款用java语言开发的轻量化的易用的网络爬虫。Gecco整合了jsoup、httpclient、fastjson、spring、htmlunit、redission等优秀框架，让您只需要配置一些jquery风格的选择器就能很快的写出一个爬虫。Gecco框架有优秀的可扩展性，框架基于开闭原则进行设计，对修改关闭、对扩展开放。同时Gecco基于十分开放的MIT开源协议，无论你是使用者还是希望共同完善Gecco的开发者，欢迎pull request。如果你喜欢这款爬虫框架请[star 或者 fork](https://github.com/xtuhcy/gecco)!

[参考手册](https://xtuhcy.gitbooks.io/geccocrawler/content/index.html)

##主要特征

* [x] 简单易用，使用jquery风格的选择器抽取元素
* [x] 支持页面中的异步ajax请求
* [x] 支持页面中的javascript变量抽取
* [x] 利用Redis实现分布式抓取,参考[gecco-redis](https://github.com/xtuhcy/gecco-redis)
* [x] 支持结合Spring开发业务逻辑,参考[gecco-spring](https://github.com/xtuhcy/gecco-spring)
* [x] 支持htmlunit扩展,参考[gecco-htmlunit](https://github.com/xtuhcy/gecco-htmlunit)
* [x] 支持插件扩展机制
* [x] 支持下载时UserAgent随机选取
* [x] 支持下载代理服务器随机选取

##框架概述
![架构图](https://raw.githubusercontent.com/xtuhcy/gecco/master/doc/%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)
###GeccoEngine
>GeccoEngine是爬虫引擎，每个爬虫引擎最好是一个独立进程，在分布式爬虫场景下，建议每台爬虫服务器（物理机或者虚机）运行一个GeccoEngine。爬虫引擎包括Scheduler、Downloader、Spider、SpiderBeanFactory、PipelineFactory5个主要模块。

###Scheduler
>通常爬虫需要一个有效管理下载地址的角色，Scheduler负责下载地址的管理。gecco对初始地址的管理使用StartScheduler，StartScheduler内部采用一个阻塞的FIFO的队列。初始地址通常会派生出很多其他待抓取的地址，派生出来的其他地址采用SpiderScheduler进行管理，SpiderScheduler内部采用线程安全的非阻塞FIFO队列。这种设计使的gecco对初始地址采用了深度遍历的策略，即一个线程抓取完一个初始地址后才会去抓取另外一个初始地址；对初始地址派生出来的地址，采用广度优先策略。

###Downloader
>Downloader负责从Scheduler中获取需要下载的请求，gecco默认采用httpclient4.x作为下载引擎。通过实现Downloader接口可以自定义自己的下载引擎。你也可以对每个请求定义BeforeDownload和AfterDownload，实现不同的请求下载的个性需求。

###SpiderBeanFactory
>Gecco将下载下来的内容渲染为SpiderBean，所有爬虫渲染的JavaBean都统一继承SpiderBean，SpiderBean又分为HtmlBean和JsonBean分别对应html页面的渲染和json数据的渲染。SpiderBeanFactroy会根据请求的url地址，匹配相应的SpiderBean，同时生成该SpiderBean的上下文SpiderBeanContext。上下文SpiderBeanContext会告知这个SpiderBean采用什么渲染器，采用那个下载器，渲染完成后采用哪些pipeline处理等相关上下文信息。

###PipelineFactory
>pipeline是SpiderBean渲染完成的后续业务处理单元，PipelineFactory是pipeline的工厂类，负责pipeline实例化。通过扩展PipelineFactory就可以实现和Spring等业务处理框架的整合。

###Spider
>Gecco框架最核心的类应该是Spider线程，一个爬虫引擎可以同时运行多个Spider线程。Spider描绘了这个框架运行的基本骨架，先从Scheduler获取请求，再通过SpiderBeanFactory匹配SpiderBeanClass，再通过SpiderBeanClass找到SpiderBean的上下文，下载网页并对SpiderBean做渲染，将渲染后的SpiderBean交个pipeline处理。

##下载
###通过Maven下载

	<dependency>
	    <groupId>com.geccocrawler</groupId>
	    <artifactId>gecco</artifactId>
	    <version>x.x.x</version>
	</dependency>
	
![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)

###依赖项目
httpclient，jsoup，fastjson，reflections，cglib，rhino，log4j，jmxutils，commons-lang3

##快速开始
	@Gecco(matchUrl="https://github.com/{user}/{project}", pipelines="consolePipeline")
    public class MyGithub implements HtmlBean {

        private static final long serialVersionUID = -7127412585200687225L;
    	//url中的{user}值
        @RequestParameter("user")
        private String user;
    	//url中的{project}值
        @RequestParameter("project")
        private String project;
    	//抽取页面中的title
        @Text
        @HtmlField(cssPath=".repository-meta-content")
        private String title;
    	//抽取页面中的star
        @Text
        @HtmlField(cssPath=".pagehead-actions li:nth-child(2) .social-count")
        private int star;
    	//抽取页面中的fork
        @Text
        @HtmlField(cssPath=".pagehead-actions li:nth-child(3) .social-count")
        private int fork;
    	//抽取页面中的readme
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
			//开始运行
            .run();
        }
    }

##完整演示
[教您使用java爬虫gecco抓取JD全部商品信息（一）](http://my.oschina.net/u/2336761/blog/620158)

[教您使用java爬虫gecco抓取JD全部商品信息（二）](http://my.oschina.net/u/2336761/blog/620827)

[教您使用java爬虫gecco抓取JD全部商品信息（三）](http://my.oschina.net/u/2336761/blog/624683)

[集成Htmlunit下载页面](http://my.oschina.net/u/2336761/blog/631959)

[爬虫的监控](http://my.oschina.net/u/2336761/blog/644330)

##交流联系

- 博客：http://my.oschina.net/u/2336761/blog
- 邮箱：xtuhcy@163.com
- QQ群：531949844

##开源协议
请遵守开源协议[MIT](https://raw.githubusercontent.com/xtuhcy/gecco/master/LICENSE)