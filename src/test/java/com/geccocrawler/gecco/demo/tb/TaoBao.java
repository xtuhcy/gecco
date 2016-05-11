package com.geccocrawler.gecco.demo.tb;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = "https://www.taobao.com/market/nvzhuang/index.php?spm={code}", pipelines = {
		"consolePipeline", "taoBaoPipeline" })
public class TaoBao implements HtmlBean {

	private static final long serialVersionUID = 2L;

	@Request
	private HttpRequest request;

	@RequestParameter
	private String code;

	// 女装
	@HtmlField(cssPath = ".sm-cat-list-main")
	private List<Type> allType;

	public List<Type> getAllType() {
		return allType;
	}

	public void setAllType(List<Type> allType) {
		this.allType = allType;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static void main(String[] args) {
		HttpGetRequest start = new HttpGetRequest(
				"https://www.taobao.com/market/nvzhuang/index.php?spm=a21bo.50862.201867-main.2.tTaSrd");
		// HttpGetRequest start = new
	//	 HttpGetRequest("https://s.taobao.com/list?spm=a217f.7278617.a214d5w-static.2.NwPmeE&style=grid&seller_type=taobao&oeid=3587000&oeid=4561000&cps=yes&cat=51108009");
		start.setCharset("GBK");
		GeccoEngine.create().classpath("com.geccocrawler.gecco.demo.tb")
		// 开始抓取的页面地址
		// .start("http://www.jd.com/allSort.aspx")
		// .start("http://list.jd.com/list.html?cat=9987,653,659&delivery=1&page=1&JL=4_10_0&go=0")
		// .start("http://item.jd.com/1861098.html")
				.start(start)
				// 开启几个爬虫线程
				.thread(1)
				// 单个爬虫每次抓取完一个请求后的间隔时间
				.interval(2000).run();
	}
}
