package com.geccocrawler.gecco.demo.ajax;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Ajax;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Image;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl="https://item.jd.com/{code}.html", pipelines="consolePipeline")
public class JDDetail implements HtmlBean {

	private static final long serialVersionUID = -377053120283382723L;

	@RequestParameter
	private String code;
	
	@Ajax(url="https://p.3.cn/prices/get?type=1&pdtk=&pdbp=0&skuid=J_{code}")
	private JDPrice price;
	
	@Text
	@HtmlField(cssPath="#name > h1")
	private String title;
	
	@Ajax(url="https://cd.jd.com/promotion/v2?skuId={code}&area=1_2805_2855_0&cat=737%2C794%2C798")
	private JDad jdAd;
	
	@HtmlField(cssPath="#product-detail-2")
	private String detail;

	@Image(download="d:/gecco/jd/img")
	@HtmlField(cssPath="#spec-n1 img")
	private String image;
	
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public static void main(String[] args) throws Exception {
		HttpRequest request = new HttpGetRequest("https://item.jd.com/1455427.html");
		request.setCharset("GBK");
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.ajax")
		//开始抓取的页面地址
		.start(request)
		//开启几个爬虫线程
		.thread(1)
		//单个爬虫每次抓取完一个请求后的间隔时间
		.interval(2000)
		.start();
	}
}
