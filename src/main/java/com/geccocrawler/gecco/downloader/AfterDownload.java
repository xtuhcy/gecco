package com.geccocrawler.gecco.downloader;

import com.geccocrawler.gecco.response.HttpResponse;

public interface AfterDownload {
	
	public void process(HttpResponse response);

}
