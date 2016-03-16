package com.geccocrawler.gecco.monitor;

import java.util.Set;

import org.weakref.jmx.Managed;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.MBean;

/**
 * downloader相关的统计信息的mbean
 * 
 * @author huchengyi
 *
 */
@MBean("downloader")
public class DownloadMBean {
	
	private String statistics;
	
	private String host;
	
    @Managed
	public String getHost() {
		return host;
	}

    @Managed
	public void setHost(String host) {
		this.host = host;
	}
    
    @Managed
    public void hosts() {
    	Set<String> hosts = DownloadMonitor.getHosts();
    	setHost(JSON.toJSONString(hosts));
    }
    
    @Managed
    public String getStatistics() {
		return statistics;
	}

    @Managed
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	@Managed
    public void statistics(String host) {
    	DownloadStatistics ds = DownloadMonitor.getStatistics(host);
    	setStatistics(JSON.toJSONString(ds));
    	setHost(JSON.toJSONString(host));
    }
    
}
