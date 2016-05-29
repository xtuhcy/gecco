package com.geccocrawler.gecco.demo.osc.crawler;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.weakref.jmx.internal.guava.base.Strings;

import com.geccocrawler.gecco.demo.osc.exec.DownloadAction;
import com.geccocrawler.gecco.demo.osc.exec.Executors;
import com.geccocrawler.gecco.demo.osc.model.Oscnews;
import com.geccocrawler.gecco.demo.osc.service.OscnewsService;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

@Service
public class NewsPipeline implements Pipeline<News>{

	@Resource(name = "oscnewsServiceImpl")
	private OscnewsService oscnewsService;
	
	private Executors executors = Executors.create();
	
	@Override
	public void process(News arg0) {
		
		int currPage = arg0.getPage();
		
		Oscnews news = new Oscnews();
		news.setTitle(arg0.getTitle());
		news.setPubDate(arg0.getPubDate());
		news.setTextContent(arg0.getTextContent());
		news.setImage(arg0.getImage());
		news.setNewsLinks(arg0.getNewsLinks());
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		news.setCreateDate(dateFormater.format(date));
		news.setPage(String.valueOf(currPage));
		oscnewsService.save(news);
		if(!Strings.isNullOrEmpty(arg0.getImage())){
			executors.getDefaultActionQueue().enqueue(
					new DownloadAction(executors.getDefaultActionQueue(), arg0.getImage(), "d:image"));
		}
		int nextPage = currPage - 1;
		String nextUrl = "http://www.oschina.net/news/" + nextPage +"/";
		HttpRequest request = arg0.getRequest();
		SchedulerContext.into(request.subRequest(nextUrl));
	}

}
