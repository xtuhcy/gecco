# GECCO(绝对易用和轻量化的网络爬虫)
####初衷：
现在开发应用已经离不开爬虫，网络信息浩如烟海，对互联网的信息加以利用是如今所有应用程序都必须要掌握的技术。了解过现在的一些爬虫软件，python语言编写的爬虫 框架scrapy得到了较为广泛的应用。gecco的设计和架构受到了scrapy一些启发，结合java语言的特点，形成了如下软件框架。
##结构图
>![架构图](https://raw.githubusercontent.com/xtuhcy/gecco/master/doc/%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)
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
jar的maven下载近期放出
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