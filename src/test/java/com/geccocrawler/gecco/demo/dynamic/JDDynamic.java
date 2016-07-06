package com.geccocrawler.gecco.demo.dynamic;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.dynamic.DynamicGecco;
import com.geccocrawler.gecco.annotation.dynamic.FieldType;

/**
 * 京东列表页的动态生成spiderBean的例子
 * 
 * @author huchengyi
 *
 */
public class JDDynamic {
	
	public static void main(String[] args) throws Exception {
		
		DynamicGecco.htmlBean("com.geccocrawler.gecco.demo.dynamic.jd.ProductBrief", true)
		.field("code", FieldType.stringType).htmlField(".j-sku-item").attr("data-sku").build()
		.field("title", FieldType.stringType).htmlField(".p-name> a > em").text().build()
		.field("detailUrl", FieldType.stringType).htmlField(".p-name > a").href().build()
		.loadClass();
		
		DynamicGecco.htmlBean("com.geccocrawler.gecco.demo.dynamic.jd.ProductList", true)
		.gecco("http://list.jd.com/list.html?cat={cat}", "consolePipeline", "jdDynamicListPipeline")
		.field("request", FieldType.requestType).request().build()
		.field("details", FieldType.listType("com.geccocrawler.gecco.demo.dynamic.jd.ProductBrief")).htmlField("#plist .gl-item").build()
		.field("currPage", FieldType.intType).htmlField("#J_topPage > span > b").text().build()
		.field("totalPage", FieldType.intType).htmlField("#J_topPage > span > i").text().build()
		.loadClass();
		
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic.jd")
		.start("http://list.jd.com/list.html?cat=9987,653,655")
		.interval(5000)
		.run();
	}

}
