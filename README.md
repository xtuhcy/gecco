![ci](https://api.travis-ci.org/xtuhcy/gecco.svg?branch=master)
![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)

##What is Gecco
Gecco is a easy to use lightweight web crawler developed with java language.Gecco integriert jsoup, httpclient, fastjson, spring, htmlunit, redission ausgezeichneten framework,Let you only need to configure a number of jQuery style selector can be very quick to write a crawler.Gecco framework has excellent scalability, the framework based on the principle of open and close design, to modify the closure, the expansion of open.At the same time Gecco is based on a very open MIT open source protocol, whether you are a user or want to jointly improve the Gecco developer, welcome to request pull.If you like the crawler framework,please [star or fork](https://github.com/xtuhcy/gecco)!

* [中文说明](https://github.com/xtuhcy/gecco/blob/master/README_CN.md)

* [中文参考手册](https://xtuhcy.gitbooks.io/geccocrawler/content/index.html)

##Main features

* [x] Easy to use, use jQuery style selector to extract elements
* [x] Support for asynchronous Ajax requests in the page
* [x] Support page JavaScript variable extraction
* [x] Using Redis to realize distributed crawling,reference [gecco-redis](https://github.com/xtuhcy/gecco-redis)
* [x] Support the development of business logic with Spring,reference [gecco-spring](https://github.com/xtuhcy/gecco-spring)
* [x] Support htmlunit extension,reference [gecco-htmlunit](https://github.com/xtuhcy/gecco-htmlunit)
* [x] Support extension mechanism
* [x] Support download UserAgent random selection
* [x] Support the download proxy server randomly selected

##Framework overview
![架构图](https://raw.githubusercontent.com/xtuhcy/gecco/master/doc/%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)

##Download
###Download via Maven

	<dependency>
	    <groupId>com.geccocrawler</groupId>
	    <artifactId>gecco</artifactId>
	    <version>x.x.x</version>
	</dependency>

![maven](https://img.shields.io/maven-central/v/com.geccocrawler/gecco.svg?style=flat-square)

###Dependent project
httpclient，jsoup，fastjson，reflections，cglib，rhino，log4j，jmxutils，commons-lang3

##Quick start
	@Gecco(matchUrl="https://github.com/{user}/{project}", pipelines="consolePipeline")
    public class MyGithub implements HtmlBean {

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
            .classpath("com.geccocrawler.gecco.demo")
            .start("https://github.com/xtuhcy/gecco")
            .thread(1)
            .interval(2000)
            .loop(true)
            .mobile(false)
            .run();
        }
    }

##Demo
[教您使用java爬虫gecco抓取JD全部商品信息（一）](http://my.oschina.net/u/2336761/blog/620158)

[教您使用java爬虫gecco抓取JD全部商品信息（二）](http://my.oschina.net/u/2336761/blog/620827)

[教您使用java爬虫gecco抓取JD全部商品信息（三）](http://my.oschina.net/u/2336761/blog/624683)

[集成Htmlunit下载页面](http://my.oschina.net/u/2336761/blog/631959)

[爬虫的监控](http://my.oschina.net/u/2336761/blog/644330)

##Contact and communication

- blog：http://my.oschina.net/u/2336761/blog
- email：xtuhcy@163.com

##License
Please follow the open source protocol [MIT](https://raw.githubusercontent.com/xtuhcy/gecco/master/LICENSE)!