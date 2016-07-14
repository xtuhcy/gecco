package com.geccocrawler.gecco.demo.dynamic;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.dynamic.DynamicGecco;

/**
 * 对已经存在的bean动态增加注解的方法示例，bean必须实现HtmlBean/JsonBean接口
 * 
 * 1、原理是利用Javassist进行字节码修改
 * 2、前提是该bean还没有被class loader到jvm中
 * 3、java对main方法的类会进行loader，因此main方法不能放在spiderBean中
 * 4、利用动态注解方法能灵活的通过文件、数据库等配置修改页面抽取规则
 * 
 * @author huchengyi
 *
 */
public class DynamicAnnotationTest {
	
	public static void main(String[] args) throws Exception {
		//动态增加注解
		DynamicGecco.html("com.geccocrawler.gecco.demo.dynamic.MyGithub")
		.gecco("https://github.com/xtuhcy/gecco", "consolePipeline")
		.existField("title").csspath(".repository-meta-content").text(false).build()
		.existField("star").csspath(".pagehead-actions li:nth-child(2) .social-count").text(false).build()
		.existField("fork").csspath(".pagehead-actions li:nth-child(3) .social-count").text().build()
		.existField("contributors").csspath("ul.numbers-summary > li:nth-child(4) > a").href().build()
		.existField("request").request().build()
		.existField("user").requestParameter("user").build()
		.existField("project").requestParameter().build()
		.register();
		
		//开始抓取
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic")
		.start("https://github.com/xtuhcy/gecco")
		.run();
	}
	
}
