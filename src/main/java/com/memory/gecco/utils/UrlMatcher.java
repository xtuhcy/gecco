package com.memory.gecco.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UrlMatcher {

	private static Log log = LogFactory.getLog(UrlMatcher.class);
	
	public static String replaceParams(String regex, String name, String value) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put(name, value);
		return replaceParams(regex, map);
	}
	
	public static String replaceParams(String srcUrl, Map<String, String> params) {
		return replaceRegexs(srcUrl, "\\{(.*?)\\}", params);
	}
	
	public static String replaceFields(String regex, String name, String value) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put(name, value);
		return replaceFields(regex, map);
	}
	
	public static String replaceFields(String srcUrl, Map<String, String> params) {
		return replaceRegexs(srcUrl, "\\[(.*?)\\]", params);
	}
	
	public static String replaceRegexs(String srcUrl, String regex, Map<String, String> params) {
		if(params == null) {
			return srcUrl;
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(srcUrl);
		while(matcher.find()) {
			String name = matcher.group(1);
			String value = params.get(name);
			if(StringUtils.isNotEmpty(value)) {
				matcher.appendReplacement(sb, value);
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static Map<String, String> match(String url, String regex) {
		String regexSrc = StringUtils.replace(regex, "?", "\\?");
		String regex1 = "\\{(.*?)\\}";
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile(regex1);
		Matcher matcher = pattern.matcher(regexSrc);
		List<String> names = new ArrayList<String>();
		while(matcher.find()) {
			matcher.appendReplacement(sb, "(.*)");
			String name = matcher.group(1);
			names.add(name);
		}
		if(names.size() > 0) {
			matcher.appendTail(sb);
			String regex2 = sb.toString();
			if(log.isDebugEnabled()) {
				log.debug(regex2);
			}
			Pattern pattern2 = Pattern.compile(regex2);
			Matcher matcher2 = pattern2.matcher(url);
			if(matcher2.find()) {
				Map<String, String> params = new HashMap<String, String>(names.size());
				for(int i = 1; i <= matcher2.groupCount(); i++) {
					String value = matcher2.group(i);
					try {
						value = URLDecoder.decode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					params.put(names.get(i-1), value);
				}
				return params;
			}
		} else {
			//如果没有变量，返回空map
			if(url.equals(regex)) {
				return new HashMap<String, String>(0);
			}
		}
		//适配失败返回null
		return null;
	}
	
	public static void main(String[] args) {
		String regex = "http://list.jd.com/list.html?cat={cat}&delivery={delivery}&page={page}&stock={stock}&JL=4_7_0";
		String url = "http://list.jd.com/list.html?cat={cat}&delivery=1&page=1&stock=1&JL=[cat]";
		//System.out.println(match(url, regex));
		System.out.println(replaceParams(url, "cat", "aaaaaaaaaaa"));
		System.out.println(replaceFields(url, "cat", "aaaaaaaaaaa"));
	}
}
