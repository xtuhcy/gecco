package com.memory.gecco.downloader;

import com.memory.gecco.response.HttpResponse;

public interface AfterDownload {
	
	public void process(HttpResponse response);

}
