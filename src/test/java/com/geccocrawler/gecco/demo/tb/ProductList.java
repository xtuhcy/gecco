package com.geccocrawler.gecco.demo.tb;

import java.util.List;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.JsonpPath;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * 抓取京东的某个商品列表页
 *
 * @author memory
 *
 */
@Gecco(matchUrl = "https://s.taobao.com/list?{param}", pipelines = "consolePipeline")
public class ProductList implements HtmlBean {

	private static final long serialVersionUID = 3L;

	@RequestParameter
	private String param;

	@Request
	private HttpRequest request;

	/**
	 * 抓取列表项的详细内容，包括titile，价格，详情页地址等
	 */
	@JsonpPath
	private List<ProductBrief> details;

	public List<ProductBrief> getDetails() {
		return details;
	}

	public void setDetails(List<ProductBrief> details) {
		this.details = details;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
