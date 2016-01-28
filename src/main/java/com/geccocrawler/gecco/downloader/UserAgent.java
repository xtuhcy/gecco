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
	
	private static List<String> userAgents = null;
	static {
		URL url = Resources.getResource("userAgents");
		if(url != null) {
			File file = new File(url.getPath());
			try {
				userAgents = Files.readLines(file, Charsets.UTF_8);
			} catch(Exception ex) {}
		}
	}
	
	public static String getUserAgent() {
		if(userAgents == null || userAgents.size() == 0) {
			return DEFAULT_USER_AGENT;
		}
		Collections.shuffle(userAgents);
		return userAgents.get(0);
	}

}
