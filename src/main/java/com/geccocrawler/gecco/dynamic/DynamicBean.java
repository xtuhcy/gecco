package com.geccocrawler.gecco.dynamic;

import javassist.CtClass;

/**
 * 动态生成SpiderBean
 * 
 * @author huchengyi
 *
 */
public interface DynamicBean {
	
	/**
	 * 构造一个SpiderBean
	 * 
	 * @param matchUrl 匹配url
	 * @param pipelines 管道过滤器
	 * @return SpiderBean
	 */
	public JavassistDynamicBean gecco(String matchUrl, String... pipelines);
	
	/**
	 * 构造一个SpiderBean
	 * 
	 * @param matchUrl 匹配url
	 * @param downloader 下载器
	 * @param timeout 超时时间
	 * @param pipelines 管道过滤器
	 * @return SpiderBean
	 */
	public JavassistDynamicBean gecco(String matchUrl, String downloader, int timeout, String... pipelines);
	
	/**
	 * 获取一个已有字段
	 * 
	 * @param fieldName 已有字段名称
	 * @return 字段
	 */
	public DynamicField field(String fieldName);
	
	/**
	 * 新增一个字段
	 * 
	 * @param fieldName 字段名称
	 * @param fieldType 字段类型
	 * @return
	 */
	public DynamicField field(String fieldName, CtClass fieldType);
	
	/**
	 * string类型字段
	 * 
	 * @param fieldName 字段名
	 * @return 字段
	 */
	public DynamicField stringField(String fieldName);
	
	/**
	 * int类型字段
	 * 
	 * @param fieldName 字段名
	 * @return 字段
	 */
	public DynamicField intField(String fieldName);
	
	/**
	 * boolean类型字段
	 * 
	 * @param fieldName 字段名
	 * @return 字段
	 */
	public DynamicField booleanField(String fieldName);
	
	/**
	 * long类型字段
	 * 
	 * @param fieldName 字段名
	 * @return 字段
	 */
	public DynamicField longField(String fieldName);
	
	/**
	 * float类型字段
	 * 
	 * @param fieldName 字段名
	 * @return 字段
	 */
	public DynamicField floatField(String fieldName);
	
	/**
	 * double类型字段
	 * 
	 * @param fieldName 字段名
	 * @return 字段
	 */
	public DynamicField doubleField(String fieldName);
	
	/**
	 * HttpRequest类型字段
	 * 
	 * @param fieldName 字段名
	 * @return 字段
	 */
	public DynamicField requestField(String fieldName);
	
	/**
	 * List类型的字段
	 * 
	 * @param fieldName 字段名
	 * @param memberClass list的成员类型
	 * @return 字段
	 */
	public DynamicField listField(String fieldName, Class<?> memberClass);
	
	/**
	 * 注册Bean
	 * 
	 * @return
	 */
	public Class<?> register();
	
	/**
	 * 已经被register代替
	 * 
	 * @return
	 */
	@Deprecated
	public Class<?> loadClass();
	
}
