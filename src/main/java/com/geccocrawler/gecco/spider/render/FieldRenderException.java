package com.geccocrawler.gecco.spider.render;

import java.lang.reflect.Field;

public class FieldRenderException extends Exception {

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

}
