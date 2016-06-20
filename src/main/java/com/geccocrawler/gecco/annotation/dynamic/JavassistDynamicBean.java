package com.geccocrawler.gecco.annotation.dynamic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.annotation.Gecco;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

public class JavassistDynamicBean implements DynamicBean {

	private static Log log = LogFactory.getLog(JavassistDynamicBean.class);
	
	private static ClassPool pool = ClassPool.getDefault();
	
	private CtClass clazz;
	
	private ClassFile cfile;
	
	private ConstPool cpool;
	
	public JavassistDynamicBean(String htmlBeanName) {
		try {
			clazz = pool.get(htmlBeanName);
			/*clazz = pool.makeClass(htmlBeanName);
			CtClass htmlBeanInterface = pool.get("com.geccocrawler.gecco.spider.HtmlBean");
			clazz.addInterface(htmlBeanInterface);*/
			cfile = clazz.getClassFile();
			cpool = cfile.getConstPool();
			//clazz.defrost();
			if(clazz.isFrozen()) {
				log.error(htmlBeanName + " is frozen");
			}
		} catch (NotFoundException e) {
			log.error(htmlBeanName + " not found");
		}
	}
	
	public static JavassistDynamicBean create(String htmlBeanName) {
		return new JavassistDynamicBean(htmlBeanName);
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

	@Override
	public DynamicField field(String fieldName) {
		/*try {
			CtField f = new CtField(CtClass.charType, fieldName, clazz);
			clazz.addField(f);
		} catch(Exception ex) {
			ex.printStackTrace();
		}*/
		return new JavassistDynamicField(this, clazz, cpool, fieldName);
	}

	@Override
	public void loadClass() {
		try {
			clazz.toClass();
		} catch (CannotCompileException e) {
			log.error(clazz.getName() + " cannot compile,"+e.getMessage());
		}
        clazz.detach();
	}
}
