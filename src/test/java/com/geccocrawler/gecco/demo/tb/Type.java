package com.geccocrawler.gecco.demo.tb;

import java.util.List;

import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HrefBean;
import com.geccocrawler.gecco.spider.HtmlBean;

public class Type implements HtmlBean {

	private static final long serialVersionUID = 1L;

	@Text
	@HtmlField(cssPath="dt > div > a")
	private String typeName;

	@HtmlField(cssPath="dd")
	private List<HrefBean> types;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<HrefBean> getTypes() {
		return types;
	}

	public void setTypes(List<HrefBean> types) {
		this.types = types;
	}

}
