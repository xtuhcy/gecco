package com.geccocrawler.gecco.utils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Set;

import org.reflections.ReflectionUtils;

/**
 * 泛型，Type的相关知识
 * 
 * http://developer.51cto.com/art/201103/250028.htm http://lsy.iteye.com/blog/220264
 * 
 * @author huchengyi
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReflectUtils {

	/**
	 * 获得类的所有基类和接口
	 * 
	 * @param clazz 类
	 * @return 所有基类的集合
	 */
	public static Set<Class<?>> getAllSuperType(Class clazz) {
		return ReflectionUtils.getAllSuperTypes(clazz);
	}

	/**
	 * 是否继承某个基类
	 * 
	 * @param childClazz
	 *            子类
	 * @param superClazz
	 *            基类
	 * @return 是否继承某个基类
	 */
	public static boolean haveSuperType(Class childClazz, Class superClazz) {
		for (Class<?> clazz : getAllSuperType(childClazz)) {
			if (clazz.equals(superClazz)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否继承某个基类
	 * 
	 * @param bean 需要判断的对象bean
	 * @param superClazz 基类
	 * @return 是否继承某个基类
	 */
	public static boolean haveSuperType(Object bean, Class superClazz) {
		return haveSuperType(bean.getClass(), superClazz);
	}

	public static Class getGenericClass(Type type, int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		} else if (type instanceof TypeVariable) { // 处理泛型擦拭对象
			return getGenericClass(((TypeVariable) type).getBounds()[0], 0);
		} else {// class本身也是type，强制转型
			return (Class) type;
		}
	}

	private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
		Object genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class) ((ParameterizedType) genericClass).getRawType();
		} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
		} else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return getGenericClass(((TypeVariable) genericClass).getBounds()[0], 0);
		} else {
			return (Class) genericClass;
		}
	}

}
