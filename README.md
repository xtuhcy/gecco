# GECCO(易用的轻量化的网络爬虫)
####初衷：
>现在开发应用已经离不开爬虫，网络信息浩如烟海，对互联网的信息加以利用是如今所有应用程序都必须要掌握的技术。了解过现在的一些爬虫软件，python语言编写的爬虫框架[scrapy](https://github.com/scrapy/scrapy)得到了较为广泛的应用。gecco的设计和架构受到了scrapy一些启发，结合java语言的特点，形成了如下软件框架。
##结构图
![架构图](https://raw.githubusercontent.com/xtuhcy/gecco/master/doc/%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)
##基本构件介绍
###GeccoEngine
>是爬虫引擎，每个爬虫引擎最好独立进程，在分布式爬虫场景下，可以单独分配一台爬虫服务器。引擎包括Scheduler、Downloader、Spider、SpiderBeanFactory4个主要模块
###Scheduler
>需要下载的请求都放在这里管理，可以认为这里是一个队列，保存了所有待抓取的请求。系统默认采用FIFO的方式管理请求。
###Downloader
>下载器，负责将Scheduler里的请求下载下来，系统默认采用[Unirest](https://github.com/Mashape/unirest-java)作为下载引擎。
###Spider
>一个爬虫引擎可以包含多个爬虫，每个爬虫可以认为是一个单独线程，爬虫会从Scheduler中获取需要待抓取的请求。爬虫的任务就是下载网页并渲染相应的JavaBean。
###SpiderBeanFactory
>SpiderBean是爬虫渲染的JavaBean的统一接口类，所有Bean均继承该接口。SpiderBeanFactroy会根据请求的url地址，匹配相应的SpiderBean，同时生成该SpiderBean的上下文SpiderBeanContext.
SpiderBeanContext包括需要改SpiderBean的渲染类（目前支持HTML、JSON两种Bean的渲染方式）、下载前处理类、下载后处理类以及渲染完成后对SpiderBean的后续处理Pipeline。
##QuikStart
>maven下载近期放出
###启动爬虫引擎
	public static void main(String[] args) {
		GeccoEngine.create()
		//爬虫userAgent设置
		.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36")
		//开始抓取的页面地址
		.start("http://list.jd.com/list.html?cat=737%2C794%2C798&delivery=1&page=2&stock=1&JL=4_7_0")
		//开启几个爬虫线程
		.thread(1)
		//单个爬虫每次抓取完一个请求后的间隔时间
		.interval(2000)
		.run();
	}
###配置需要渲染的SpiderBean
	/**
	 * 抓取京东的某个商品列表页,将渲染后的bean通过consolePipeline输出到控制台
	 * 
	 * @author memory
	 *
	 */
	@Gecco(matchUrl="http://list.jd.com/list.html?cat={cat}&delivery={delivery}&page={page}&stock={stock}&JL={JL}", pipelines="consolePipeline")
	public class JD implements SpiderBean {
		private static final long serialVersionUID = 4369792078959596706L;
		/**
		 * 抓取列表项的详细内容，包括titile，价格，详情页地址等
		 */
		@Bean
		@HtmlField(cssPath="#plist .gl-item")
		private List<JDList> details;
		/**
		 * 抓取所有商品的代码
		 */
		@Attr("data-sku")
		@HtmlField(cssPath="#plist .gl-item .j-sku-item")
		private List<String> codes;
		/**
		 * 利用制定的属性渲染器下载各个商品的价格
		 */
		@FieldRenderName("jdPricesFieldRender")
		private List<JDPrice> prices;
		/**
		 * 获取所有商品的详情页地址,click为ture表示链接会被放入Scheduler继续抓取
		 */
		@Href(click=true)
		@HtmlField(cssPath="#plist .gl-item .p-name > a")
		private ArrayList<String> detailUrls;
		/**
		 * 活动商品列表的当前页
		 */
		@HtmlField(cssPath="#J_topPage > span > b")
		private int currPage;
		/**
		 * 获得商品列表的总页数
		 */
		@HtmlField(cssPath="#J_topPage > span > i")
		private int totalPage;
		/**
		 * 获得列表页的下一页的请求地址，并放入Scheduler继续抓取
		 */
		@Href(click=true)
		@HtmlField(cssPath="#J_topPage > a.fp-next")
		private String nextUrl;
		
		public List<JDList> getDetails() {
			return details;
		}
	
		public void setDetails(List<JDList> details) {
			this.details = details;
		}
		
		public List<String> getCodes() {
			return codes;
		}
	
		public void setCodes(List<String> codes) {
			this.codes = codes;
		}
	
		public List<JDPrice> getPrices() {
			return prices;
		}
	
		public void setPrices(List<JDPrice> prices) {
			this.prices = prices;
		}
	
		public ArrayList<String> getDetailUrls() {
			return detailUrls;
		}
	
		public void setDetailUrls(ArrayList<String> detailUrls) {
			this.detailUrls = detailUrls;
		}
	
		public int getCurrPage() {
			return currPage;
		}
	
		public void setCurrPage(int currPage) {
			this.currPage = currPage;
		}
	
		public int getTotalPage() {
			return totalPage;
		}
	
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
	
		public String getNextUrl() {
			return nextUrl;
		}
	
		public void setNextUrl(String nextUrl) {
			this.nextUrl = nextUrl;
		}
	
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}
##HTML渲染器注解说明
###@Gecco
>定义一个SpiderBean必须有的注解，告诉爬虫引擎什么样的url转换成该java bean，使用什么渲染器渲染，java bean渲染完成后传递给哪些管道过滤器继续处理

- matchUrl：摒弃正则表达式的匹配方式，采用更容易理解的{value}方式，如：https://github.com/{user}/{project}。user和project变量将会在request中获取。
- render：bean渲染类型，计划支持html、json、xml、rss
- pipelines：bean渲染完成后，后续的管道过滤器

###@HtmlField
>html属性定义，表示该属性是通过html查找解析，在html的渲染器下使用

- cssPath：jquery风格的元素选择器，使用[jsoup](https://github.com/jhy/jsoup)实现。jsoup在分析html方面提供了极大的便利。计划实现xpath风格的元素选择器。

###@Href
>表示该字段是一个链接类型的元素，jsoup会默认获取元素的href属性值。属性必须是String类型。

- value：默认获取href属性值，可以多选，按顺序查找
- click：表示是否点击打开，继续让爬虫抓取

###@Image
>表示该字段是一个图片类型的元素，jsoup会默认获取元素的src属性值。属性必须是String类型。

- value：默认获取src属性值，可以多选，按顺序查找
- download：表示是否需要将图片下载到本地

###@Attr
>获取html元素的attribute。属性支持java基本类型的自动转换。

- value:表示属性名称

###@Html
>获取html元素的整个节点内容。属性必须是String类型。

###@Text
>默认类型，表示获取html元素的inner text。属性支持java基本类型的自动转换。

###@Ajax
>html页面上很多元素是通过ajax请求获取，gecco爬虫支持ajax请求。ajax请求会在html的基本元素渲染完成后调用，可以通过[value]获取当前已经渲染完成的属性值，通过{value}方式获取request的属性值。

- url:ajax请求地址，如：http://p.3.cn/prices/mgets?skuIds=J\_[code]或者http://p.3.cn/prices/mgets?skuIds=J\_{code}

###@FieldRenderName
>属性的渲染有时会较复杂，不能用上述注解描述，gecco爬虫支持属性渲染的自定义方式，自定义渲染器实现CustomFieldRender接口，并定义属性渲染器名称。

- value：使用的自定义属性渲染器的名称