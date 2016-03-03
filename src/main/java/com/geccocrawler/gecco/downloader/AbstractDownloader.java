package com.geccocrawler.gecco.downloader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDownloader implements Downloader {

	private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");

	private String getCharsetFromContentType(String contentType) {
		if (contentType == null)
			return null;

		Matcher m = charsetPattern.matcher(contentType);
		if (m.find()) {
			return m.group(1).trim().toUpperCase();
		}
		return null;
	}
	
	protected String getCharset(String requestCharset, String contentType) {
		//先取contentType的字符集
		String charset = getCharsetFromContentType(contentType);
		if(charset == null) {
			//再取request指定的字符集
			charset = requestCharset;
		}
		if(charset == null) {
			//默认采用utf-8
			charset = "UTF-8";
		}
		return charset;
	}

}
