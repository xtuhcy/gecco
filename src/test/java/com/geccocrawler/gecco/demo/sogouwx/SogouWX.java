package com.geccocrawler.gecco.demo.sogouwx;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://weixin.sogou.com/weixin?type=2&query={keyword}", pipelines={"consolePipeline"})
public class SogouWX implements HtmlBean {

	private static final long serialVersionUID = 7504646787612579665L;

	@RequestParameter
	private String keyword;
	
	@HtmlField(cssPath=".results .wx-rb")
	private List<WeiXin> weixins;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<WeiXin> getWeixins() {
		return weixins;
	}

	public void setWeixins(List<WeiXin> weixins) {
		this.weixins = weixins;
	}
	
	public static void main(String[] args) {
		HttpGetRequest start = new HttpGetRequest("http://weixin.sogou.com/weixin?type=2&query=%E6%B7%B1%E5%9C%B3");

		//这个样例应该是为了验证 BeforeArticleDownloader

		start.addCookie("SNUID", "1D22392EF4F6C4A92076C208F4DE3AAB");
		start.addCookie("SUID", "EED1CDDA6B20900A00000000570E1872");
		start.addCookie("IPLOC", "CN1100");
		//start.addCookie("SUV", "1460541527037365");
		start.addHeader("Host", "weixin.sogou.com");
		start.addHeader("Upgrade-Insecure-Requests", "1");
		start.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		start.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		start.addHeader("Cache-Control", "max-age=0");
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.sogouwx")
//		.start(start)
		.start("https://mp.weixin.qq.com/s?src=11&timestamp=1532404725&ver=1017&signature=*jZ6-p5r8H1OW-SXTrldAxpCI71SY8R*fcV3CvJtlR*y0WXe9wWGJ1kcPbvxg6gC*AhBKNqpEzTPy7ZbSKsGGcX9YBwzUpVhX2IAHim-vISY-uFjFg2Knzl1jEeipy99&new=1")
		.interval(5000)
		.run();
	}
}
