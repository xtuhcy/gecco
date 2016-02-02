package com.geccocrawler.gecco.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * html页面上很多元素是通过ajax请求获取，gecco爬虫支持ajax请求。ajax请求会在html的基本元素渲染完成后调用，可以通过[value]
 * 获取当前已经渲染完成的属性值，通过{value}方式获取request的属性值。
 * 
 * @author huchengyi
 *
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSVar {

	String var();
	
	String jsonpath() default "";
}
