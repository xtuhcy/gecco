package com.geccocrawler.gecco.demo.dynamic;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.dynamic.DynamicGecco;
import com.geccocrawler.gecco.annotation.dynamic.FieldType;

/**
 * 不但可以实现动态增加注解还可以实现动态创建类和属性
 * 
 * @author huchengyi
 *
 */
public class DynamicCreateTest {

	public static void main(String[] args) throws Exception {
		DynamicGecco.htmlBean("com.geccocrawler.gecco.demo.dynamic.MyGithub2", true)
		.gecco("https://github.com/xtuhcy/gecco", "consolePipeline")
		.field("title", FieldType.stringType).htmlField(".repository-meta-content").text(false).build()
		.field("star", FieldType.intType).htmlField(".pagehead-actions li:nth-child(2) .social-count").text(false).build()
		.field("fork", FieldType.intType).htmlField(".pagehead-actions li:nth-child(3) .social-count").text().build()
		.loadClass();
		
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic")
		.start("https://github.com/xtuhcy/gecco")
		.run();
	}

}
