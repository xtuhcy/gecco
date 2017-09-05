package com.geccocrawler.gecco.spider.conversion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Conversion {

	@SuppressWarnings({ "rawtypes" })
	private static final Map<Class<?>, TypeHandle> TYPE_HANDLERS = new HashMap<Class<?>, TypeHandle>();
	static {
		// int, float, long, double, java.util.Date, boolean, String
		TYPE_HANDLERS.put(Integer.class, new IntegerTypeHandle());
		TYPE_HANDLERS.put(int.class, new IntegerTypeHandle());
		TYPE_HANDLERS.put(Long.class, new LongTypeHandle());
		TYPE_HANDLERS.put(long.class, new LongTypeHandle());
		TYPE_HANDLERS.put(Float.class, new FloatTypeHandle());
		TYPE_HANDLERS.put(float.class, new FloatTypeHandle());
		TYPE_HANDLERS.put(Double.class, new DoubleTypeHandle());
		TYPE_HANDLERS.put(double.class, new DoubleTypeHandle());
		TYPE_HANDLERS.put(Boolean.class, new BooleanTypeHandle());
		TYPE_HANDLERS.put(boolean.class, new BooleanTypeHandle());
		TYPE_HANDLERS.put(Date.class, new DateTypeHandle());
		TYPE_HANDLERS.put(BigDecimal.class, new BigDecimalTypeHandle());
	}

	@SuppressWarnings({ "rawtypes" })
	public static void register(Class<?> type, TypeHandle typeHandle) {
		TYPE_HANDLERS.put(type, typeHandle);
	}

	public static void unregister(Class<?> type) {
		TYPE_HANDLERS.remove(type);
	}

	@SuppressWarnings({ "rawtypes" })
	public static Object getValue(Class<?> type, Object value) throws Exception {
		TypeHandle th = TYPE_HANDLERS.get(type);
		if (th != null && value != null) {
			return th.getValue(value);
		}
		return value;
	}

	public static Object getDateValue(Object value, String format) throws Exception {
		DateTypeHandle th = (DateTypeHandle) TYPE_HANDLERS.get(Date.class);
		return th.getValue(value, format);
	}
}
