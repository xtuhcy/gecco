package com.geccocrawler.gecco.demo.osc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;

/**
 * @description:
 * @author rook
 * @date 2016-4-8 下午10:48:33
 */
@Service
public class OscCrawlerMain implements ApplicationContextAware,InitializingBean {

	private ApplicationContext context;

	public void afterPropertiesSet() throws Exception {
		PipelineFactory springPipelineFactory = (PipelineFactory)context.getBean("springPipelineFactory");
		HttpGetRequest start = new HttpGetRequest("http://www.oschina.net/news/65846/");
		start.setCharset("GBK");
		GeccoEngine.create()
			.classpath("com.geccocrawler.gecco.demo.osc.crawler")
			.pipelineFactory(springPipelineFactory)
			.interval(1000)
			.start(start)
			.run();
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext1.xml");
		System.in.read();
		context.close();
		/*J4pClient client = new J4pClient("http://localhost:8080/jolokia");
	       J4pReadRequest request = 
	           new J4pReadRequest("java.lang:type=Memory","HeapMemoryUsage");
	       request.setPath("used");
	       J4pReadResponse response = client.execute(request);
	       System.out.println("Memory used: " + response.getValue());*/
	}
}
