package com.geccocrawler.gecco.demo.jd;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Image;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

public class ProductBrief implements HtmlBean {

	private static final long serialVersionUID = -377053120283382723L;

	@Attr("data-sku")
	@HtmlField(cssPath=".j-sku-item")
	private String code;
	
	@Text
	@HtmlField(cssPath=".p-name> a > em")
	private String title;
	
	@Image({"data-lazy-img", "src"})
	@HtmlField(cssPath=".p-img > a > img")
	private String preview;
	
	@Href(click=true)
	@HtmlField(cssPath=".p-name > a")
	private String detailUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
