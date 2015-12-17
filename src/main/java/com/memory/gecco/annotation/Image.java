package com.memory.gecco.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示该字段是一个图片类型的元素，jsoup会默认获取元素的src属性值。属性必须是String类型。
 * 
 * @author huchengyi
 *
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Image {

	/**
	 * 默认获取src属性值，可以多选，按顺序查找
	 * 
	 * @return
	 */
	String[] value() default "src";
	
	/**
	 * 表示是否需要将图片下载到本地
	 * 
	 * @return
	 */
	boolean download() default false;
	
}
