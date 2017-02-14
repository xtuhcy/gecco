package com.geccocrawler.gecco.downloader.proxy;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * 代理ip从classpath下的proxys文件里加载
 * 多代理支持，classpath根目录下放置proxys文件，文件格式如下
 * 127.0.0.1:8888
 * 127.0.0.1:8889
 * 支持记录代理成功率，自动发现无效代理
 * 支持在线添加代理
 * 
 * @author huchengyi
 *
 */
public class FileProxys implements Proxys {
	
	private static Log log = LogFactory.getLog(FileProxys.class);
	
	private ConcurrentLinkedQueue<Proxy> proxyQueue;
	
	private Map<String, Proxy> proxys = null;
	
	public FileProxys() {
		try {
			proxys = new ConcurrentHashMap<String, Proxy>();
			proxyQueue = new ConcurrentLinkedQueue<Proxy>();
			URL url = Resources.getResource("proxys");
			File file = new File(url.getPath());
			List<String> lines = Files.readLines(file, Charsets.UTF_8);
			if(lines.size() > 0) {
				for(String line : lines) {
					line = line.trim();
					if(line.startsWith("#")) {
						continue;
					}
					String[] hostPort = line.split(":");
					if(hostPort.length == 2) {
						String host = hostPort[0];
						int port = NumberUtils.toInt(hostPort[1], 80);
						addProxy(host, port);
					}
				}
			}
		} catch(Exception ex) {
			log.info("proxys not load");
		}
	}

	@Override
	public boolean addProxy(String host, int port) {
		return addProxy(host, port, null);
	}

	@Override
	public boolean addProxy(String host, int port, String src) {
		Proxy proxy = new Proxy(host, port);
		if(StringUtils.isNotEmpty(src)) {
			proxy.setSrc(src);
		}
		if(proxys.containsKey(proxy.toHostString())) {
			return false;
		} else {
			proxys.put(host+":"+port, proxy);
			proxyQueue.offer(proxy);
			if(log.isDebugEnabled()) {
				log.debug("add proxy : " + host + ":" + port);
			}
			return true;
		}
	}

	@Override
	public void failure(String host, int port) {
		Proxy proxy = proxys.get(host+":"+port);
		if(proxy != null) {
			long failure = proxy.getFailureCount().incrementAndGet();
			long success = proxy.getSuccessCount().get();
			reProxy(proxy, success, failure);
		}
	}

	@Override
	public void success(String host, int ip) {
		Proxy proxy = proxys.get(host+":"+ip);
		if(proxy != null) {
			long success = proxy.getSuccessCount().incrementAndGet();
			long failure = proxy.getFailureCount().get();
			reProxy(proxy, success, failure);
		}
	}
	
	private void reProxy(Proxy proxy, long success, long failure) {
		long sum = failure + success;
		if(sum < 20) {
			proxyQueue.offer(proxy);
		} else {
			if((success / (float)sum) >= 0.5f) {
				proxyQueue.offer(proxy);
			}
		}
	}

	@Override
	public HttpHost getProxy() {
		if(proxys == null || proxys.size() == 0) {
			return null;
		}
		Proxy proxy = proxyQueue.poll();
		if(log.isDebugEnabled()) {
			log.debug("use proxy : " + proxy);
		}
		if(proxy == null) {
			return null;
		}
		return proxy.getHttpHost();
	}

}
