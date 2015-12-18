package com.geccocrawler.gecco.downloader;

import com.geccocrawler.gecco.request.HttpRequest;

public interface BeforeDownload {
	
	public void process(HttpRequest request);

}
