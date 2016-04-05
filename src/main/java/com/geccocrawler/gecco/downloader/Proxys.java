package com.geccocrawler.gecco.downloader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * 多代理支持，classpath根目录下放置proxys文件，文件格式如下
 * 127.0.0.1:8888
 * 127.0.0.1:8889
 * 
 * @author huchengyi
 *
 */
public class Proxys {
	
	private static Log log = LogFactory.getLog(Proxys.class);
	
	private static List<HttpHost> proxys = null;
	static{
		try {
			URL url = Resources.getResource("proxys");
			File file = new File(url.getPath());
			List<String> lines = Files.readLines(file, Charsets.UTF_8);
			if(lines.size() > 0) {
				proxys = new ArrayList<HttpHost>(lines.size());
				for(String line : lines) {
					line = line.trim();
					if(line.startsWith("#")) {
						continue;
					}
					String[] hostPort = line.split(":");
					if(hostPort.length == 2) {
						String host = hostPort[0];
						int port = NumberUtils.toInt(hostPort[1], 80);
						proxys.add(new HttpHost(host, port));
					}
				}
			}
		} catch(Exception ex) {
			log.info("proxys not load");
		}
	}
	
	public static HttpHost getProxy() {
		if(proxys == null || proxys.size() == 0) {
			return null;
		}
		Collections.shuffle(proxys);
		return proxys.get(0);
	}

}
