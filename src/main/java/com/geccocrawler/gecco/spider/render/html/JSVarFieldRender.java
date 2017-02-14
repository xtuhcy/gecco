package com.geccocrawler.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Element;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJSON;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;
import org.reflections.ReflectionUtils;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.JSVar;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.conversion.Conversion;
import com.geccocrawler.gecco.spider.render.FieldRender;

import net.sf.cglib.beans.BeanMap;

/**
 * 解析页面中的javascript变量
 * 
 * @author huchengyi
 *
 */
public class JSVarFieldRender implements FieldRender {

	private static Log log = LogFactory.getLog(JSVarFieldRender.class);

	@Override
	@SuppressWarnings({ "unchecked" })
	public void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
		Context cx = Context.enter();
		ScriptableObject scope = cx.initSafeStandardObjects();
		String windowScript = "var window = {};var document = {};";
		cx.evaluateString(scope, windowScript, "window", 1, null);
		HtmlParser parser = new HtmlParser(request.getUrl(), response.getContent());
		for (Element ele : parser.$("script")) {
			String sc = ele.html();
			if (StringUtils.isNotEmpty(sc)) {
				try {
					cx.evaluateString(scope, sc, "", 1, null);
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		}
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		Set<Field> jsVarFields = ReflectionUtils.getAllFields(bean.getClass(), ReflectionUtils.withAnnotation(JSVar.class));
		for (Field jsVarField : jsVarFields) {
			Object value = injectJsVarField(request, beanMap, jsVarField, cx, scope);
			if(value != null) {
				fieldMap.put(jsVarField.getName(), value);
			}
		}
		beanMap.putAll(fieldMap);
		Context.exit();
	}

	@SuppressWarnings({ "rawtypes" })
	private Object injectJsVarField(HttpRequest request, BeanMap beanMap, Field field, Context cx, ScriptableObject scope) {
		Class clazz = field.getType();
		JSVar jsVar = field.getAnnotation(JSVar.class);
		String var = jsVar.var();
		Object jsObj = scope.get(var, scope);
		if (jsObj instanceof NativeObject || jsObj instanceof NativeArray) {
			String jsonPath = jsVar.jsonpath();
			// 将javascript变量格式化为json对象
			Object jsonObj = NativeJSON.stringify(cx, scope, jsObj, null, null);
			// 使用fastjson将json字符串格式化为JSONObject
			Object json = JSON.parse(jsonObj.toString());
			// 解析jsonpath
			Object src = com.alibaba.fastjson.JSONPath.eval(json, jsonPath);
			// 如果解析出来的是字符串，尝试转换为json对象
			try {
				if (src instanceof String) {
					src = JSON.parse(src.toString());
				}
			} catch (Exception ex) {
			}
			// 将json对象转换为javabean属性
			try {
				Object value = Conversion.getValue(clazz, src);
				return value;
			} catch (Exception e) {
				log.error("field [" + field.getName() + "] conversion error, value=" + src);
			}
		} else if (jsObj instanceof Boolean || jsObj instanceof Number || jsObj instanceof String) {
			try {
				Object value = Conversion.getValue(clazz, jsObj);
				return value;
			} catch (Exception e) {
				log.error("field [" + field.getName() + "] conversion error, value=" + jsObj);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Object json = JSON.parse("{ads:[{id:1},{id:2}],test:'test111'}");
		Object src = com.alibaba.fastjson.JSONPath.eval(json, "$.ads");
		if (src instanceof String) {
			src = JSON.parse(src.toString());
		}
		System.out.println(src);
		Object src2 = com.alibaba.fastjson.JSONPath.eval(json, "$.test");
		if (src2 instanceof String) {
			src2 = JSON.parse(src2.toString());
		}
		System.out.println(src2);
	}
}
