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
		
		String productBrief = DynamicGecco.html()
		.field("code", FieldType.stringType).csspath(".j-sku-item").attr("data-sku").build()
		.field("title", FieldType.stringType).csspath(".p-name> a > em").text().build()
		.field("detailUrl", FieldType.stringType).csspath(".p-name > a").href().build()
		.register().getName();
		
		DynamicGecco.html()
		.gecco("http://list.jd.com/list.html?cat={cat}", "consolePipeline", "jdDynamicListPipeline")
		.field("request", FieldType.requestType).request().build()
		.field("details", FieldType.listType(productBrief)).csspath("#plist .gl-item").build()
		.field("currPage", FieldType.intType).csspath("#J_topPage > span > b").text().build()
		.field("totalPage", FieldType.intType).csspath("#J_topPage > span > i").text().build()
		.register();
		
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic.jd")
		.start("http://list.jd.com/list.html?cat=9987,653,655")
		.interval(5000)
		.run();
	}

}
