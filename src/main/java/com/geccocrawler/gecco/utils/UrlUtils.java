package com.geccocrawler.gecco.utils;

import java.net.URL;

public class UrlUtils {

	/**
	 * 相对地址转绝对地址
	 * 
	 * @param absolutePath 当前绝对地址
	 * @param relativePath 相对地址
	 * @return 绝对地址
	 */
	public static String relative2Absolute(String absolutePath, String relativePath) {
		if(relativePath == null) {
			return null;
		}
		//relativePath = relativePath.toLowerCase();
		if(relativePath.startsWith("http")) {
			return relativePath;
		}
		try {
			// 以下方法对相对路径进行转换
			URL absoluteUrl = new URL(absolutePath);
			URL parseUrl = new URL(absoluteUrl, relativePath);
			return parseUrl.toString();
		} catch(Exception ex) {
			return relativePath;
		}
	}

}
