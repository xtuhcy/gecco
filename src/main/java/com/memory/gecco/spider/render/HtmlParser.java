package com.memory.gecco.spider.render;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.memory.gecco.GeccoEngineThreadLocal;
import com.memory.gecco.annotation.Attr;
import com.memory.gecco.annotation.Href;
import com.memory.gecco.annotation.Html;
import com.memory.gecco.annotation.Image;
import com.memory.gecco.annotation.RenderType;
import com.memory.gecco.request.HttpRequest;
import com.memory.gecco.response.HttpResponse;
import com.memory.gecco.spider.SpiderBean;
import com.memory.gecco.spider.conversion.Conversion;

public class HtmlParser {
	
	private Log log;
	
	private Document document;
	
	private String baseUri;
	
	public HtmlParser(String baseUri, String content) {
		long beginTime = System.currentTimeMillis();
		log = LogFactory.getLog(HtmlParser.class);
		this.baseUri = baseUri;
		this.document = Jsoup.parse(content, baseUri);
		long endTime = System.currentTimeMillis();
		log.debug("init html parser : " + (endTime - beginTime) + "ms");
	}
	
	public String baseUri() {
		return baseUri;
	}
	
	public Object $basic(String selector, Field field) {
		if(field.isAnnotationPresent(Html.class)) {//@Html
			return $html(selector);
		} else if(field.isAnnotationPresent(Image.class)) {//@Image
			Image image = field.getAnnotation(Image.class);
			return $image(selector, image.value());
		} else if(field.isAnnotationPresent(Href.class)) {//@Href
			Href href = field.getAnnotation(Href.class);
			return $href(selector, href.value());
		} else if(field.isAnnotationPresent(Attr.class)) {//@Attr
			Attr attr = field.getAnnotation(Attr.class);
			String name = attr.value();
			return $attr(selector, name);
		} else {//@Text
			String value = $text(selector);
			return Conversion.getValue(field.getType(), value);
		}
	}
	
	public List<Object> $basicList(String selector, Field field) {
		List<Object> list = new ArrayList<Object>();
		Elements els = $(selector);
		for(Element el : els) {
			if(field.isAnnotationPresent(Html.class)) {//@Html
				list.add(el.html());
			} else if(field.isAnnotationPresent(Image.class)) {//@Image
				Image image = field.getAnnotation(Image.class);
				list.add($image(el, image.value()));
			} else if(field.isAnnotationPresent(Href.class)) {//@Href
				Href href = field.getAnnotation(Href.class);
				list.add($href(el, href.value()));
			} else if(field.isAnnotationPresent(Attr.class)) {
				Attr attr = field.getAnnotation(Attr.class);
				String name = attr.value();
				list.add(Conversion.getValue(field.getType(), $attr(el, name)));
			} else {//@Text
				list.add(Conversion.getValue(field.getType(), el.ownText()));
			}
		}
		return list;
	}
	
	public SpiderBean $bean(String selector, HttpRequest request, Class<? extends SpiderBean> clazz) {
		String subHtml = $html(selector);
		HttpResponse subResponse = HttpResponse.createSimple(subHtml);
		Render render = GeccoEngineThreadLocal.getRender(RenderType.HTML);
		return render.inject(clazz, request, subResponse);
	}
	
	public List<SpiderBean> $beanList(String selector, HttpRequest request, Class<? extends SpiderBean> clazz) {
		List<SpiderBean> list = new ArrayList<SpiderBean>();
		List<String> els = $list(selector);
		for(String el : els) {
			HttpResponse subResponse = HttpResponse.createSimple(el);
			Render render = GeccoEngineThreadLocal.getRender(RenderType.HTML);
			SpiderBean subBean = render.inject(clazz, request, subResponse);
			list.add(subBean);
		}
		return list;
	}

	public Elements $(String selector) {
		Elements elements = document.select(selector);
		if(log.isDebugEnabled()) {
			//log.debug(selector + " >>>>> " + elements);
		}
		return elements;
	}
	
	public Element $element(String selector) {
		Elements elements = $(selector);
		if(elements != null && elements.size() > 0) {
			return elements.first();
		}
		return null;
	}
	
	public List<String> $list(String selector) {
		List<String> list = new ArrayList<String>();
		Elements elements = $(selector);
		if(elements != null) {
			for(Element ele : elements) {
				list.add(ele.outerHtml());
			}
		}
		return list;
	}
	
	public String $html(String selector) {
		Elements elements = $(selector);
		if(elements != null) {
			return elements.html();
		}
		return null;
	}
	
	public String $text(String selector) {
		Elements elements = $(selector);
		if(elements != null && elements.size() > 0) {
			return elements.first().ownText();
		}
		return null;
	}
	
	public String $attr(Element element, String attr) {
		return element.attr(attr);
	}
	
	public String $attr(String selector, String attr) {
		Element element = $element(selector);
		return element.attr(attr);
	}
	
	public String $href(Element href, String attr) {
		if(href == null) {
			return null;
		}
		return href.absUrl(attr);
	}
	
	public String $href(Element href, String... attrs) {
		if(href == null) {
			return null;
		}
		for(String attr : attrs) {
			String value = $href(href, attr);
			if(StringUtils.isNotEmpty(value)) {
				return value;
			}
		}
		return $href(href, "href");
	}
	
	public String $href(String selector, String attr) {
		return $href($element(selector), attr);
	}
	
	public String $href(String selector, String... attrs) {
		return $href($element(selector), attrs);
	}
	
	public String $image(Element img, String attr) {
		if(img == null) {
			return null;
		}
		return img.absUrl(attr);
	}
	
	public String $image(Element img, String... attrs) {
		if(img == null) {
			return null;
		}
		for(String attr : attrs) {
			String value = $image(img, attr);
			if(StringUtils.isNotEmpty(value)) {
				return value;
			}
		}
		return $image(img, "src");
	}
	
	public String $image(String selector, String attr) {
		return $image($element(selector), attr);
	}
	
	public String $image(String selector, String... attrs) {
		return $image($element(selector), attrs);
	}
	
	public void setLogClass(Class<? extends SpiderBean> spiderBeanClass) {
		log = LogFactory.getLog(spiderBeanClass);
	}
	
}
