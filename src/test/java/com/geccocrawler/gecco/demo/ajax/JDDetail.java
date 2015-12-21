package com.geccocrawler.gecco.demo.ajax;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.Ajax;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.StartRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="http://item.jd.com/{code}.html", pipelines="consolePipeline")
public class JDDetail implements HtmlBean {

	private static final long serialVersionUID = -377053120283382723L;

	@RequestParameter
	private String code;
	
	@Ajax(url="http://p.3.cn/prices/mgets?skuIds=J_{code}")
	private JDPrice price;
	
	@Text
	@HtmlField(cssPath="#name > h1")
	private String title;
	
	@Ajax(url="http://cd.jd.com/promotion/v2?skuId={code}&area=1_2805_2855_0&cat=737%2C794%2C798")
	private JDad jdAd;
	
	@HtmlField(cssPath="detail-content")
	private String detail;
	
	public JDPrice getPrice() {
		return price;
	}

	public void setPrice(JDPrice price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JDad getJdAd() {
		return jdAd;
	}

	public void setJdAd(JDad jdAd) {
		this.jdAd = jdAd;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static void main(String[] args) throws Exception {
		StartRequest startRequest = new StartRequest();
		startRequest.setUrl("http://item.jd.com/1455427.html");
		startRequest.setCharset("GBK");
		startRequest.addHeader("header", "value");
		startRequest.addCookie("cookie", "value");
		StartRequest startRequest2 = new StartRequest();
		startRequest2.setUrl("http://item.jd.com/1455427.html");
		startRequest2.setCharset("GBK");
		startRequest2.addHeader("header", "value");
		startRequest2.addCookie("cookie", "value");
		startRequest2.addPost("field", "value");
		List<StartRequest> list = new ArrayList<StartRequest>();
		list.add(startRequest);
		list.add(startRequest2);
		System.out.println(JSON.toJSONString(list));
		
		/*GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo")
		//爬虫userAgent设置
		.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36")
		//开始抓取的页面地址
		.start(request)
		//开启几个爬虫线程
		.thread(1)
		//单个爬虫每次抓取完一个请求后的间隔时间
		.interval(2000)
		.run();*/
	}
}
