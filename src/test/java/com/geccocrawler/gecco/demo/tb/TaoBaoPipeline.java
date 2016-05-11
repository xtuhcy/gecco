package com.geccocrawler.gecco.demo.tb;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.geccocrawler.gecco.spider.HrefBean;

@PipelineName("taoBaoPipeline")
public class TaoBaoPipeline implements Pipeline<TaoBao> {

	@Override
	public void process(TaoBao allType) {
		List<Type> types = allType.getAllType();
		for(Type type : types) {
			List<HrefBean> hrefs = type.getTypes();
			for(HrefBean href : hrefs) {
				String url = href.getUrl();
				HttpRequest currRequest = allType.getRequest();

				String listUrl = "https://s.taobao.com/list?data-key=s&data-value=60&ajax=true&_ksTS=1462789160341_524&callback=jsonp525&spm="
				+ currRequest.getParameter("code") + StringUtils.substringAfter(url, "?");

			    SchedulerContext.into(currRequest.subRequest(listUrl));
			}
		}
	}

}