package com.geccocrawler.gecco.spider.render.html;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.Image;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;
import com.geccocrawler.gecco.spider.conversion.Conversion;
import com.geccocrawler.gecco.spider.render.Render;
import com.geccocrawler.gecco.spider.render.RenderContext;
import com.geccocrawler.gecco.spider.render.RenderType;

public class HtmlParser {

	private Log log;

	private Document document;

	private String baseUri;

	public HtmlParser(String baseUri, String content) {
		long beginTime = System.currentTimeMillis();
		log = LogFactory.getLog(HtmlParser.class);
		this.baseUri = baseUri;
		if (isTable(content)) {
			this.document = Jsoup.parse(content, baseUri, Parser.xmlParser());
		} else {
			this.document = Jsoup.parse(content, baseUri);
		}
		long endTime = System.currentTimeMillis();
		if (log.isTraceEnabled()) {
			log.trace("init html parser : " + (endTime - beginTime) + "ms");
		}
	}

	public String baseUri() {
		return baseUri;
	}

	public Object $basic(String selector, Field field) throws Exception {
		if (field.isAnnotationPresent(Text.class)) {// @Text
			Text text = field.getAnnotation(Text.class);
			String value = $text(selector, text.own());
			return Conversion.getValue(field.getType(), value);
		} else if (field.isAnnotationPresent(Image.class)) {// @Image
			Image image = field.getAnnotation(Image.class);
			String imageSrc = $image(selector, image.value());
			/*String localPath = DownloadImage.download(image.download(), imageSrc);
			if (StringUtils.isNotEmpty(localPath)) {
				return localPath;
			}*/
			return imageSrc;
		} else if (field.isAnnotationPresent(Href.class)) {// @Href
			Href href = field.getAnnotation(Href.class);
			String url = $href(selector, href.value());
			return url;
		} else if (field.isAnnotationPresent(Attr.class)) {// @Attr
			Attr attr = field.getAnnotation(Attr.class);
			String name = attr.value();
			return Conversion.getValue(field.getType(), $attr(selector, name));
		} else if (field.isAnnotationPresent(Html.class)) {// @Html
			Html html = field.getAnnotation(Html.class);
			return $html(selector, html.outer());
		} else {// @Html
			return $html(selector);
		}
	}

	public List<Object> $basicList(String selector, Field field) throws Exception {
		List<Object> list = new ArrayList<Object>();
		Elements els = $(selector);
		for (Element el : els) {
			if (field.isAnnotationPresent(Text.class)) {// @Text
				Text text = field.getAnnotation(Text.class);
				list.add(Conversion.getValue(field.getType(), $text(el, text.own())));
			} else if (field.isAnnotationPresent(Image.class)) {// @Image
				Image image = field.getAnnotation(Image.class);
				String imageSrc = $image(el, image.value());
				/*String localPath = DownloadImage.download(image.download(), imageSrc);
				if (StringUtils.isNotEmpty(localPath)) {
					list.add(localPath);
				}*/
				list.add(imageSrc);
			} else if (field.isAnnotationPresent(Href.class)) {// @Href
				Href href = field.getAnnotation(Href.class);
				String url = $href(el, href.value());
				list.add(url);
			} else if (field.isAnnotationPresent(Attr.class)) {// @Attr
				Attr attr = field.getAnnotation(Attr.class);
				String name = attr.value();
				list.add(Conversion.getValue(field.getType(), $attr(el, name)));
			} else if (field.isAnnotationPresent(Html.class)) {// @Html
				Html html = field.getAnnotation(Html.class);
				list.add(html.outer() ? el.outerHtml() : el.html());
			} else {// Other
				list.add(el.html());
			}
		}
		return list;
	}

	public SpiderBean $bean(String selector, HttpRequest request, Class<? extends SpiderBean> clazz) {
		String subHtml = $html(selector);
		// table
		HttpResponse subResponse = HttpResponse.createSimple(subHtml);
		Render render = RenderContext.getRender(RenderType.HTML);
		return render.inject(clazz, request, subResponse);
	}

	public List<SpiderBean> $beanList(String selector, HttpRequest request, Class<? extends SpiderBean> clazz) {
		List<SpiderBean> list = new ArrayList<SpiderBean>();
		List<String> els = $list(selector);
		for (String el : els) {
			// table
			HttpResponse subResponse = HttpResponse.createSimple(el);
			Render render = RenderContext.getRender(RenderType.HTML);
			SpiderBean subBean = render.inject(clazz, request, subResponse);
			list.add(subBean);
		}
		return list;
	}

	public Elements $(String selector) {
		Elements elements = document.select(selector);
		if (SpiderThreadLocal.get().getEngine().isDebug()) {
			if (!selector.equalsIgnoreCase("script")) {
				// log.debug("["+selector+"]--->["+elements+"]");
				System.out.println("[" + selector + "]--->[" + elements + "]");
			}
		}
		return elements;
	}

	public Element $element(String selector) {
		Elements elements = $(selector);
		if (elements != null && elements.size() > 0) {
			return elements.first();
		}
		return null;
	}

	public List<String> $list(String selector) {
		List<String> list = new ArrayList<String>();
		Elements elements = $(selector);
		if (elements != null) {
			for (Element ele : elements) {
				list.add(ele.outerHtml());
			}
		}
		return list;
	}
	
	public String $html(String selector) {
		return $html(selector, false);
	}

	public String $html(String selector, boolean isOuter) {
		Elements elements = $(selector);
		if (elements != null) {
			if(isOuter) {
				return elements.outerHtml();
			}
			return elements.html();
		}
		return null;
	}

	public String $text(Element element, boolean own) {
		if (element == null) {
			return null;
		}
		String text = "";
		if (own) {
			text = element.ownText();
		} else {
			text = element.text();
		}
		// 替换掉空格信息
		return StringUtils.replace(text, "\u00A0", "");
	}

	public String $text(String selector, boolean own) {
		Element element = $element(selector);
		if (element != null) {
			return $text(element, own);
		}
		return null;
	}

	public String $attr(Element element, String attr) {
		if (element == null) {
			return null;
		}
		return element.attr(attr);
	}

	public String $attr(String selector, String attr) {
		Element element = $element(selector);
		if (element == null) {
			return null;
		}
		return element.attr(attr);
	}

	public String $href(Element href, String attr) {
		if (href == null) {
			return null;
		}
		return href.absUrl(attr);
	}

	public String $href(Element href, String... attrs) {
		if (href == null) {
			return null;
		}
		for (String attr : attrs) {
			String value = $href(href, attr);
			if (StringUtils.isNotEmpty(value)) {
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
		if (img == null) {
			return null;
		}
		return img.absUrl(attr);
	}

	public String $image(Element img, String... attrs) {
		if (img == null) {
			return null;
		}
		for (String attr : attrs) {
			String value = $image(img, attr);
			if (StringUtils.isNotEmpty(value)) {
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

	private boolean isTable(String content) {
		if (!StringUtils.contains(content, "</html>")) {
			String rege = "<\\s*(thead|tbody|tr|td|th)[\\s\\S]+";
			Pattern pattern = Pattern.compile(rege);
			Matcher matcher = pattern.matcher(content);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
}
