package com.geccocrawler.gecco.spring;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;

@Service("consolePipeline")
public class ConsolePipeline implements Pipeline<SpiderBean>{
	
	@Override
	public void process(SpiderBean bean) {
		System.out.println(JSON.toJSONString(bean));
	}

}
