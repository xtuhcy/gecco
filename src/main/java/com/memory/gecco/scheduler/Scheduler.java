package com.memory.gecco.scheduler;

import java.util.List;

import com.memory.gecco.request.HttpRequest;

public interface Scheduler {
	
	public HttpRequest out();
	
	public void into(HttpRequest request);
	
	public void into(List<HttpRequest> requests);

}
