package com.geccocrawler.gecco.demo.spring;

import org.springframework.stereotype.Service;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.spring.SpringGeccoEngine;

@Service
public class InitGecco extends SpringGeccoEngine{

	@Override
	public void init() {
		GeccoEngine.create()
		.pipelineFactory(springPipelineFactory)
		.classpath("com.geccocrawler.gecco.demo.spring")
		.start("https://github.com/xtuhcy/gecco")
		.interval(3000)
		.start();
	}
}
