package com.geccocrawler.gecco.dynamic;

import java.util.List;

import javassist.CtClass;
import javassist.bytecode.ConstPool;

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
	public JavassistDynamicBean gecco(String[] matchUrl, String... pipelines);
	
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
	public JavassistDynamicBean gecco(String[] matchUrl, String downloader, int timeout, String... pipelines);
	
	public JavassistDynamicBean gecco(String matchUrl, String downloader, int timeout, String... pipelines);
	
	/**
	 * 获取一个已有字段
	 * 
	 * @param fieldName 已有字段名称
	 * @return 字段
	 */
	public DynamicField existField(String fieldName);
	
	/**
	 * 由于有歧义，已经被existField代替
	 * 
	 * @param fieldName 字段名称
	 * @return DynamicField
	 */
	@Deprecated
	public DynamicField field(String fieldName);
	
	/**
	 * 新增一个字段，如果已经存在返回当前字段
	 * 
	 * @param fieldName 字段名称
	 * @param fieldType 字段类型
	 * @return DynamicField
	 */
	public DynamicField field(String fieldName, CtClass fieldType);
	
	public DynamicField field(String fieldName, Class<?> fieldClass);
	
	/**
	 * 删除一个属性
	 * 
	 * @param fieldName 字段名
	 * @return DynamicBean
	 */
	public DynamicBean removeField(String fieldName);
	
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
	 * 将加载的bean注册到爬虫引擎中。
	 * 主要应用在先定义Bean后期的爬虫引擎的情况。
	 * 
	 * @return spiderBeanClass
	 */
	public Class<?> register();
	
	/**
	 * 加载bean到classloader中
	 * 
	 * @return spiderBeanClass
	 */
	public Class<?> loadClass();
	
	/**
	 * 卸载bean
	 */
	public void unloadClass();
	
	/**
	 * Constant pool table.
	 * 
	 * @return ConstPool
	 */
	public ConstPool getConstPool();
}
