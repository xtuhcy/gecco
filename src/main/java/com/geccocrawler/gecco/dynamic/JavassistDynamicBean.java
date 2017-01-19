package com.geccocrawler.gecco.dynamic;

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

	private CtClass clazz;

	private ClassFile cfile;

	private ConstPool cpool;

	public JavassistDynamicBean(String spiderBeanName) {
		try {
			clazz = pool.get(spiderBeanName);
		} catch (NotFoundException e) {
			log.error(spiderBeanName + " not found");
		}
	}

	/**
	 * 构造类
	 * 
	 * @param spiderBeanName
	 *            名称
	 * @param beanType
	 *            类型html/json
	 */
	public JavassistDynamicBean(String spiderBeanName, String beanType) {
		try {
			clazz = pool.get(spiderBeanName);
		} catch (NotFoundException e) {
			log.debug(spiderBeanName + " not found, to be create!");
			try {
				clazz = pool.makeClass(spiderBeanName);
				if (beanType.equals(HtmlBean)) {
					CtClass htmlBeanInterface = pool.get("com.geccocrawler.gecco.spider.HtmlBean");
					clazz.addInterface(htmlBeanInterface);
				} else {
					CtClass jsonBeanInterface = pool.get("com.geccocrawler.gecco.spider.JsonBean");
					clazz.addInterface(jsonBeanInterface);
				}
			} catch (NotFoundException cex) {
				// create error
				log.error("create class " + spiderBeanName + " error.", cex);
			}
		}
		if (clazz.isFrozen()) {
			clazz.defrost();
			log.info(spiderBeanName + " is frozen");
		}
		cfile = clazz.getClassFile();
		cpool = cfile.getConstPool();
	}

	@Override
	public JavassistDynamicBean gecco(String matchUrl, String... pipelines) {
		gecco(new String[]{matchUrl}, pipelines);
		return this;
	}

	@Override
	public JavassistDynamicBean gecco(String matchUrl, String downloader, int timeout, String... pipelines) {
		gecco(new String[]{matchUrl}, "", 3000, pipelines);
		return this;
	}

	@Override
	public JavassistDynamicBean gecco(String[] matchUrl, String... pipelines) {
		gecco(matchUrl, "", 3000, pipelines);
		return this;
	}

	@Override
	public JavassistDynamicBean gecco(String[] matchUrl, String downloader, int timeout, String... pipelines) {
		AnnotationsAttribute attr = new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);

		Annotation annot = new Annotation(Gecco.class.getName(), cpool);
		// matchUrl
		//annot.addMemberValue("matchUrl", new StringMemberValue(matchUrl, cpool));
		ArrayMemberValue arrayMemberValueMatchUrl = new ArrayMemberValue(cpool);
		MemberValue[] elementMatchUrls = new StringMemberValue[matchUrl.length];
		for (int i = 0; i < matchUrl.length; i++) {
			elementMatchUrls[i] = new StringMemberValue(matchUrl[i], cpool);
		}
		arrayMemberValueMatchUrl.setValue(elementMatchUrls);
		annot.addMemberValue("matchUrl", arrayMemberValueMatchUrl);
		
		
		// downloader
		annot.addMemberValue("downloader", new StringMemberValue(downloader, cpool));
		// timeout
		annot.addMemberValue("timeout", new IntegerMemberValue(cpool, timeout));
		// pipelines
		ArrayMemberValue arrayMemberValue = new ArrayMemberValue(cpool);
		MemberValue[] elements = new StringMemberValue[pipelines.length];
		for (int i = 0; i < pipelines.length; i++) {
			elements[i] = new StringMemberValue(pipelines[i], cpool);
		}
		arrayMemberValue.setValue(elements);
		annot.addMemberValue("pipelines", arrayMemberValue);

		attr.addAnnotation(annot);
		cfile.addAttribute(attr);
		return this;
	}

	@Override
	public DynamicBean removeField(String fieldName) {
		try {
			clazz.removeField(clazz.getField(fieldName));
			clazz.removeMethod(clazz.getDeclaredMethod("get" + StringUtils.capitalize(fieldName)));
			clazz.removeMethod(clazz.getDeclaredMethod("set" + StringUtils.capitalize(fieldName)));
		} catch (NotFoundException e) {
			e.printStackTrace();
			log.error("can't remove field : " + fieldName);
		}
		return this;
	}

	/**
	 * 返回已经存在的属性
	 */
	@Override
	public DynamicField existField(String fieldName) {
		return new JavassistDynamicField(this, clazz, cpool, fieldName);
	}

	/**
	 * 由于有歧义，已经被existField代替
	 */
	@Override
	@Deprecated
	public DynamicField field(String fieldName) {
		return existField(fieldName);
	}

	/**
	 * 如果当前属性不存在先创建属性以及setter/getter方法和注解。 如果已经属性返回当前属性
	 */
	@Override
	public DynamicField field(String fieldName, CtClass fieldType) {
		try {
			clazz.getField(fieldName);
		} catch (NotFoundException e) {
			try {
				CtField f = new CtField(fieldType, fieldName, clazz);
				clazz.addField(f);
				getter(fieldName, f);
				setter(fieldName, f);
			} catch (CannotCompileException ex) {
				ex.printStackTrace();
			}
		}
		return new JavassistDynamicField(this, clazz, cpool, fieldName);
	}

	@Override
	public DynamicField field(String fieldName, Class<?> fieldClass) {
		return field(fieldName, FieldType.type(fieldClass));
	}

	private void getter(String fieldName, CtField field) {
		try {
			CtMethod m = CtNewMethod.getter("get" + StringUtils.capitalize(fieldName), field);
			clazz.addMethod(m);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void setter(String fieldName, CtField field) {
		try {
			CtMethod m = CtNewMethod.setter("set" + StringUtils.capitalize(fieldName), field);
			clazz.addMethod(m);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public DynamicField stringField(String fieldName) {
		return field(fieldName, FieldType.stringType);
	}

	@Override
	public DynamicField intField(String fieldName) {
		return field(fieldName, FieldType.intType);
	}

	@Override
	public DynamicField longField(String fieldName) {
		return field(fieldName, FieldType.longType);
	}

	@Override
	public DynamicField floatField(String fieldName) {
		return field(fieldName, FieldType.floatType);
	}

	@Override
	public DynamicField doubleField(String fieldName) {
		return field(fieldName, FieldType.doubleType);
	}

	@Override
	public DynamicField requestField(String fieldName) {
		return field(fieldName, FieldType.requestType);
	}

	@Override
	public DynamicField listField(String fieldName, Class<?> memberClass) {
		return field(fieldName, FieldType.listType(memberClass.getName()));
	}

	@Override
	public Class<?> loadClass() {
		try {
			Class<?> loadClass = clazz.toClass(GeccoClassLoader.get(), null);
			log.debug("load class : " + clazz.getName());
			return loadClass;
		} catch (CannotCompileException e) {
			e.printStackTrace();
			log.error(clazz.getName() + " cannot compile," + e.getMessage());
			return null;
		} finally {
			// clazz.detach();
		}
	}

	@Override
	public Class<?> register() {
		Class<?> loadClass = loadClass();
		if (loadClass.getAnnotation(Gecco.class) != null) {
			GeccoClassLoader.get().addClass(loadClass.getName(), loadClass);
		}
		log.debug("register class : " + clazz.getName());
		return loadClass;
	}

	@Override
	public void unloadClass() {
		if (clazz != null) {
			clazz.detach();
		}
	}

	@Override
	public ConstPool getConstPool() {
		return cpool;
	}

}
