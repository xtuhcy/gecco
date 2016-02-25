package com.geccocrawler.gecco.demo.jd;

import java.util.List;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.geccocrawler.gecco.spider.HrefBean;

@PipelineName("allSortPipeline")
public class AllSortPipeline implements Pipeline<AllSort> {

	@Override
	public void process(AllSort allSort) {
		List<Category> categorys = allSort.getMobile();
		for(Category category : categorys) {
			List<HrefBean> hrefs = category.getCategorys();
			for(HrefBean href : hrefs) {
				String url = href.getUrl()+"&delivery=1&page=1&JL=4_10_0&go=0";
				HttpRequest currRequest = allSort.getRequest();
				SchedulerContext.into(currRequest.subRequest(url));
			}
		}
	}

}