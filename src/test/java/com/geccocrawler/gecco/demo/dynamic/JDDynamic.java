package com.geccocrawler.gecco.demo.dynamic;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.dynamic.DynamicGecco;

/**
 * 京东列表页的动态生成spiderBean的例子
 * 
 * @author huchengyi
 *
 */
public class JDDynamic {
	
	public static void main(String[] args) throws Exception {
		
		Class<?> productBrief = DynamicGecco.html()
		.stringField("code").csspath(".j-sku-item").attr("data-sku").build()
		.stringField("title").csspath(".p-name> a > em").text().build()
		.stringField("detailUrl").csspath(".p-name > a").href().build()
		.register();
		
		DynamicGecco.html()
		.gecco("http://list.jd.com/list.html?cat={cat}", "consolePipeline", "jdDynamicListPipeline")
		.requestField("request").request().build()
		.listField("details", productBrief).csspath("#plist .gl-item").build()
		.intField("currPage").csspath("#J_topPage > span > b").text().build()
		.intField("totalPage").csspath("#J_topPage > span > i").text().build()
		.register();
		
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic.jd")
		.start("http://list.jd.com/list.html?cat=9987,653,655")
		.interval(5000)
		.run();
	}

}
