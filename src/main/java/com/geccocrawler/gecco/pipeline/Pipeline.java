package com.geccocrawler.gecco.pipeline;

import com.geccocrawler.gecco.spider.SpiderBean;

public interface Pipeline<T extends SpiderBean> {

	public void process(T bean);

}
