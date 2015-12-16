package com.memory.gecco.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Gecco {

	/**
	 * 匹配的url正则表达式
	 * 
	 * @return
	 */
	String matchUrl();
	
	/**
	 * bean渲染类型
	 * 
	 * @return
	 */
	RenderType render() default RenderType.HTML;
	
	String[] pipelines() default "";
}
