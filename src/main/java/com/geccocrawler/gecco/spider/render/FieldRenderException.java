package com.geccocrawler.gecco.spider.render;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.geccocrawler.gecco.spider.SpiderThreadLocal;

/**
 * 注入某个属性异常
 * 
 * @author huchengyi
 *
 */
public class FieldRenderException extends Exception {
	
	private static Log log = LogFactory.getLog(FieldRenderException.class);

	private static final long serialVersionUID = 5698150653455275921L;

	private Field field;

	public FieldRenderException(Field field, String message) {
		super(message);
		this.field = field;
	}

	public FieldRenderException(Field field, String message, Throwable cause) {
		super(message, cause);
		this.field = field;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public static void log(Field field, String message, Throwable cause) {
		boolean debug = SpiderThreadLocal.get().getEngine().isDebug();
		log.error(field.getName() + " render error : " + message);
		if(debug && cause != null) {
			log.error(message, cause);
		}
	}

	public static void log(Field field, String message) {
		log(field, message, null);
	}
}
