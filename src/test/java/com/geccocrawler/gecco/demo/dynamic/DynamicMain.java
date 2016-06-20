package com.geccocrawler.gecco.demo.dynamic;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.dynamic.JavassistDynamicBean;

/**
 * 对spiderBean动态增加注解的方法示例
 * 
 * 1、原理是利用Javassist进行字节码修改
 * 2、前提是该bean还没有被class loader到jvm中
 * 3、java对main方法的类会进行loader，因此main方法不能放在spiderBean中
 * 4、利用动态注解方法能灵活的通过文件、数据库等配置修改页面抽取规则
 * 
 * @author huchengyi
 *
 */
public class DynamicMain {
	
	public static void main(String[] args) throws Exception {
		//动态增加注解
		JavassistDynamicBean.create("com.geccocrawler.gecco.demo.dynamic.MyGithub")
		
		//@Gecco(matchUrl="https://github.com/{user}/{project}", pipelines="consolePipeline")
		.gecco("https://github.com/xtuhcy/gecco", "consolePipeline")
		
		//@Text(own=false)
		//@HtmlField(cssPath=".repository-meta-content")
		.field("title").htmlField(".repository-meta-content").text(false).build()
		
		//@Text(own=false)
		//@HtmlField(cssPath=".pagehead-actions li:nth-child(2) .social-count")
		.field("star").htmlField(".pagehead-actions li:nth-child(2) .social-count").text(false).build()
		
		//@Text
		//@HtmlField(cssPath=".pagehead-actions li:nth-child(3) .social-count")
		.field("fork").htmlField(".pagehead-actions li:nth-child(3) .social-count").text().build()
		
		//@Href
		//@HtmlField(cssPath="ul.numbers-summary > li:nth-child(4) > a")
		.field("contributors").htmlField("ul.numbers-summary > li:nth-child(4) > a").href().build()
		
		//@Request
		.field("request").request().build()
		
		//@RequestParameter("user")
		.field("user").requestParameter("user").build()
		
		//@RequestParameter
		.field("project").requestParameter().build()
		
		//载入class
		.loadClass();
		
		//开始抓取
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic")
		.start("https://github.com/xtuhcy/gecco")
		.run();
	}
	
}
