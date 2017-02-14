package com.geccocrawler.gecco.demo.jd;

import java.util.List;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * 抓取京东的某个商品列表页
 * 
 * @author memory
 *
 */
@Gecco(matchUrl="https://list.jd.com/list.html?cat={cat}&delivery={delivery}&page={page}&JL={JL}&go=0", pipelines={"consolePipeline", "productListPipeline"})
public class ProductList implements HtmlBean {
	
	private static final long serialVersionUID = 4369792078959596706L;
	
	@Request
	private HttpRequest request;
	
	/**
	 * 抓取列表项的详细内容，包括titile，价格，详情页地址等
	 */
	@HtmlField(cssPath="#plist .gl-item")
	private List<ProductBrief> details;
	/**
	 * 获得商品列表的当前页
	 */
	@Text
	@HtmlField(cssPath="#J_topPage > span > b")
	private int currPage;
	/**
	 * 获得商品列表的总页数
	 */
	@Text
	@HtmlField(cssPath="#J_topPage > span > i")
	private int totalPage;
	
	public List<ProductBrief> getDetails() {
		return details;
	}

	public void setDetails(List<ProductBrief> details) {
		this.details = details;
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

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	
}
