package com.geccocrawler.gecco.pipeline;

import com.geccocrawler.gecco.spider.SpiderBean;

public interface Pipeline {

	public void process(SpiderBean bean);

}
