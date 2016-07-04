package com.geccocrawler.gecco.demo.dynamic;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.dynamic.FieldType;
import com.geccocrawler.gecco.annotation.dynamic.JavassistDynamicBean;

/**
 * 不但可以实现动态增加注解还可以实现动态创建类和属性
 * 
 * @author huchengyi
 *
 */
public class DynamicCreateGecco {

	public static void main(String[] args) throws Exception {
		JavassistDynamicBean myGithub2 = JavassistDynamicBean.htmlBean("com.geccocrawler.gecco.demo.dynamic.MyGithub2", true);
		myGithub2.gecco("https://github.com/xtuhcy/gecco", "consolePipeline")
		.field("title", FieldType.stringType).htmlField(".repository-meta-content").text(false).build()
		.field("star", FieldType.intType).htmlField(".pagehead-actions li:nth-child(2) .social-count").text(false).build()
		.field("fork", FieldType.intType).htmlField(".pagehead-actions li:nth-child(3) .social-count").text().build();
		System.out.println(myGithub2.loadClass().getName());
		
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic")
		.start("https://github.com/xtuhcy/gecco")
		.run();
	}

}
