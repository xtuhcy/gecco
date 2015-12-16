package com.memory.gecco.downloader;

import com.memory.gecco.request.HttpRequest;

public interface BeforeDownload {
	
	public void process(HttpRequest request);

}
