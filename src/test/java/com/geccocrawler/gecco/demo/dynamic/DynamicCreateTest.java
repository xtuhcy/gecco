package com.geccocrawler.gecco.demo.dynamic;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.dynamic.DynamicGecco;
import com.geccocrawler.gecco.dynamic.FieldType;

/**
 * 不需要定SpiderBean实现页面抓取
 * 
 * @author huchengyi
 *
 */
public class DynamicCreateTest {

	public static void main(String[] args) throws Exception {
		//定义爬取规则
		DynamicGecco
		//抓取一个html页面
		.html()
		//matchUrl和pipeline定义
		.gecco("https://github.com/xtuhcy/gecco", "consolePipeline")
		//定义字段抓取规则
		.field("title", FieldType.stringType).csspath(".repository-meta-content").text(false).build()
		.field("star", FieldType.intType).csspath(".pagehead-actions li:nth-child(2) .social-count").text(false).build()
		.field("fork", FieldType.intType).csspath(".pagehead-actions li:nth-child(3) .social-count").text().build()
		//注册抓取规则
		.register();
		
		//启动爬虫
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic")
		.start("https://github.com/xtuhcy/gecco")
		.run();
	}

}
