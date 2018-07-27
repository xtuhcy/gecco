package com.geccocrawler.gecco.demo.sogouwx;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;


@Gecco(matchUrl="https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncementlist&lang=zh_CN", pipelines="consolePipeline")
public class Article implements HtmlBean {

	private static final long serialVersionUID = 5310736056776105882L;

	@Text(own=false)
	@HtmlField(cssPath=".mp_news_list > .mp_news_item")
	private List<News> body;

	public List<News> getBody() {
		return body;
	}

	public void setBody(List<News> body) {
		this.body = body;
	}

	public static void main(String[] args) {
		GeccoEngine.create()
				.classpath("com.geccocrawler.gecco.demo.sogouwx")
				//开始抓取的页面地址
				.start("https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncementlist&lang=zh_CN")
//		.start("https://github.com/xtuhcy/gecco-spring")
				//开启几个爬虫线程,线程数量最好不要大于start request数量
				.thread(1)
				//单个爬虫每次抓取完一个请求后的间隔时间
				.interval(2000)
				//循环抓取
				.loop(false)
				//采用pc端userAgent
				.mobile(false)
				//是否开启debug模式，跟踪页面元素抽取
				.debug(false)
				//非阻塞方式运行
				.start();
	}

	public static class News implements HtmlBean {

		@Attr("href")
		@HtmlField(cssPath="a")
		private String url;


		@HtmlField(cssPath="a > strong")
		@Text(own=false)
		private String desc;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

}
