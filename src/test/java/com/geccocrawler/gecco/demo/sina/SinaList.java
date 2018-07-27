package com.geccocrawler.gecco.demo.sina;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.HtmlBean;

@PipelineName("SinaList")
@Gecco(matchUrl="http://news.sina.com.cn/china/", pipelines="SinaList")
public class SinaList implements HtmlBean, Pipeline<SinaList> {
	
	private static final long serialVersionUID = 6683895914723213684L;
	
	@HtmlField(cssPath="#subShowContent1_static .news-item h2 a")
	private List<Item> items;
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public static class Item implements HtmlBean {
		
		private static final long serialVersionUID = 5243013123370386328L;

		@Attr("href")
		@HtmlField(cssPath="a")
		private String url;
		
		@Text
		@HtmlField(cssPath="a")
		private String tag;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		@Override
		public String toString() {
			return "Item{" +
					"url='" + url + '\'' +
					", tag='" + tag + '\'' +
					'}';
		}
	}

	@Override
	public void process(SinaList bean) {
		for (Item item : bean.getItems()) {
			System.out.println(item);
		}
	}

	public static void main(String[] args) {
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.sina")
		.start("http://news.sina.com.cn/china/")
		.run();
	}
}
