package com.geccocrawler.gecco.downloader;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * 随机获取userAgent，通过在classpath根目录下放置userAgents文件，配置多个userAgent，随机选择，如果希望某个ua概率较高请配置多个
 * 
 * @author huchengyi
 *
 */
public class UserAgent {
	
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";
	
	private static final String DEFAULT_MOBILE_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25";
	
	private static List<String> userAgents = null;
	static {
		try {
			URL url = Resources.getResource("userAgents");
			File file = new File(url.getPath());
			userAgents = Files.readLines(file, Charsets.UTF_8);
		} catch(Exception ex) {}
	}
	
	private static List<String> mobileUserAgents = null;
	static {
		try {
			URL url = Resources.getResource("mobileUserAgents");
			File file = new File(url.getPath());
			mobileUserAgents = Files.readLines(file, Charsets.UTF_8);
		} catch(Exception ex) {}
	}
	
	public static String getUserAgent(boolean isMobile) {
		if(isMobile) {
			if(mobileUserAgents == null || mobileUserAgents.size() == 0) {
				return DEFAULT_MOBILE_USER_AGENT;
			}
			Collections.shuffle(mobileUserAgents);
			return mobileUserAgents.get(0);
		} else {
			if(userAgents == null || userAgents.size() == 0) {
				return DEFAULT_USER_AGENT;
			}
			Collections.shuffle(userAgents);
			return userAgents.get(0);
		}
	}

}
