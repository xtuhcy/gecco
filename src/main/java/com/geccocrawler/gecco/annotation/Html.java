package com.geccocrawler.gecco.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 获取html元素的整个节点内容。属性必须是String类型。
 * 
 * @author huchengyi
 *
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Html {
	/**
	 * 是否取外部Html，默认为false
	 * 
	 * <pre>
	 *  true  - 取外部HTML内容
	 *  false - 取内容HTML内容（默认）
	 * </pre>
	 * 
	 * @author LiuJunGuang
	 * @return 是否取外部Html
	 */
	public boolean outer() default false;
}
