package com.geccocrawler.gecco.pipeline;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.spider.SpiderBean;

@PipelineName("consolePipeline")
public class ConsolePipeline implements Pipeline {

	@Override
	public void process(SpiderBean bean) {
		System.out.println(JSON.toJSONString(bean));
	}

}
