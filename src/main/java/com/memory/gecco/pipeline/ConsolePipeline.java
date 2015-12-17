package com.memory.gecco.pipeline;

import com.alibaba.fastjson.JSON;
import com.memory.gecco.annotation.PipelineName;
import com.memory.gecco.spider.SpiderBean;

@PipelineName("consolePipeline")
public class ConsolePipeline implements Pipeline {

	@Override
	public void process(SpiderBean bean) {
		System.out.println(JSON.toJSONString(bean));
	}

}
