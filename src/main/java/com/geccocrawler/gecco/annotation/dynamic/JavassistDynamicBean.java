package com.geccocrawler.gecco.annotation.dynamic;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.annotation.Gecco;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * 动态生成SpiderBean，支持类，属性和注解全部动态生成，也支持只动态生成注解
 * 
 * @author huchengyi
 *
 */
public class JavassistDynamicBean implements DynamicBean {

	private static Log log = LogFactory.getLog(JavassistDynamicBean.class);
	
	public static final String HtmlBean = "html";
	
	public static final String JsonBean = "json";
	
	private static ClassPool pool;
	static {
		pool = ClassPool.getDefault();
		pool.insertClassPath(new ClassClassPath(JavassistDynamicBean.class));
	}
	
	private boolean create;
	
	private CtClass clazz;
	
	private ClassFile cfile;
	
	private ConstPool cpool;
	
	/**
	 * 构造类
	 * 
	 * @param spiderBeanName 名称
	 * @param beanType 类型html/json
	 * @param create 是否新建类和属性。true表示创建写的类和属性已经setter/getter方法，false表示只动态生成注解
	 */
	public JavassistDynamicBean(String spiderBeanName, String beanType, boolean create) {
		this.create = create;
		try {
			if(create) {
				clazz = pool.makeClass(spiderBeanName);
				if(beanType.equals(HtmlBean)) {
					CtClass htmlBeanInterface = pool.get("com.geccocrawler.gecco.spider.HtmlBean");
					clazz.addInterface(htmlBeanInterface);
				} else {
					CtClass jsonBeanInterface = pool.get("com.geccocrawler.gecco.spider.JsonBean");
					clazz.addInterface(jsonBeanInterface);
				}
			} else {
				clazz = pool.get(spiderBeanName);
			}
			cfile = clazz.getClassFile();
			cpool = cfile.getConstPool();
			//clazz.defrost();
			if(clazz.isFrozen()) {
				log.error(spiderBeanName + " is frozen");
			}
		} catch (NotFoundException e) {
			log.error(spiderBeanName + " not found");
		}
	}
	
	@Override
	public JavassistDynamicBean gecco(String matchUrl, String... pipelines) {
		gecco(matchUrl, "", 3000, pipelines);
		return this;
	}
	
	@Override
	public JavassistDynamicBean gecco(String matchUrl, String downloader, int timeout, String... pipelines) {
		AnnotationsAttribute attr = new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);
		
		Annotation annot = new Annotation(Gecco.class.getName(), cpool);
		//matchUrl
        annot.addMemberValue("matchUrl", new StringMemberValue(matchUrl, cpool));
        //downloader
        annot.addMemberValue("downloader", new StringMemberValue(downloader, cpool));
        //timeout
        annot.addMemberValue("timeout", new IntegerMemberValue(cpool, timeout));
        //pipelines
        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(cpool);
        MemberValue[] elements = new StringMemberValue[pipelines.length];
        for(int i = 0; i < pipelines.length; i++) {
        	elements[i] = new StringMemberValue(pipelines[i], cpool);
        }
        arrayMemberValue.setValue(elements);
        annot.addMemberValue("pipelines", arrayMemberValue);
        
		attr.addAnnotation(annot);
		cfile.addAttribute(attr);
		return this;
	}
	
	/**
	 * 只生成注解
	 */
	@Override
	public DynamicField field(String fieldName) {
		return new JavassistDynamicField(this, clazz, cpool, fieldName);
	}

	/**
	 * 生成属性，setter/getter方法和注解
	 */
	@Override
	public DynamicField field(String fieldName, CtClass fieldType) {
		if(create) {
			try {
				CtField f = new CtField(fieldType, fieldName, clazz);
				clazz.addField(f);
				getter(fieldName, f);
				setter(fieldName, f);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return new JavassistDynamicField(this, clazz, cpool, fieldName);
	}

	private void getter(String fieldName, CtField field) {
		try {
			CtMethod m = CtNewMethod.getter("get"+StringUtils.capitalize(fieldName), field);
			clazz.addMethod(m);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void setter(String fieldName, CtField field) {
		try {
			CtMethod m = CtNewMethod.setter("set"+StringUtils.capitalize(fieldName), field);
			clazz.addMethod(m);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public Class<?> loadClass() {
		try {
			GeccoClassLoader gcl = GeccoClassLoader.get();
			Class<?> loadClass = clazz.toClass(gcl, null);
			if(loadClass.getAnnotation(Gecco.class) != null) {
				gcl.addClass(loadClass.getName(), loadClass);
			}
			log.debug("load class : " + clazz.getName());
			return loadClass;
		} catch (CannotCompileException e) {
			log.error(clazz.getName() + " cannot compile,"+e.getMessage());
			return null;
		} finally {
			//clazz.detach();
		}
        
	}
}
