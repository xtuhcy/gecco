package com.geccocrawler.gecco.demo.dynamic;

import com.alibaba.fastjson.JSONObject;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.dynamic.DynamicGecco;
import com.geccocrawler.gecco.request.HttpGetRequest;

/**
 * 京东全部商品的动态配置方式，这里没有任何Bean的定义
 * 只有两个allSortJsonPipeline，productListJsonPipeline的Pipeline的定义
 * 由原来的10个类简化为3个类
 * 
 * @author huchengyi
 *
 */
public class DynamicJD {

	public static void main(String[] args) {
		
		//对应原来的Category和HrefBean类
		Class<?> category = DynamicGecco.html()
		.stringField("parentName").csspath("dt a").text().build()
		.listField("categorys", 
				DynamicGecco.html()
				.stringField("url").csspath("a").href().build()
				.stringField("title").csspath("a").text().build()
				.register()).csspath("dd a").build()
		.register();
		
		//对应原来的AllSort类
		DynamicGecco.html()
		.gecco("http://www.jd.com/allSort.aspx", "consolePipeline", "allSortJsonPipeline")
		.requestField("request").request().build()
		.listField("mobile", category)
				.csspath(".category-items > div:nth-child(1) > div:nth-child(2) > div.mc > div.items > dl").build()
		.register();
		
		//对应ProductBrief类
		Class<?> productBrief = DynamicGecco.html()
		.stringField("code").csspath(".j-sku-item").attr("data-sku").build()
		.stringField("title").csspath(".p-name> a > em").text().build()
		.stringField("preview").csspath(".p-img > a > img").image("data-lazy-img", "src").build()
		.stringField("detailUrl").csspath(".p-name > a").href(true).build()
		.register();
		
		//对应ProductList类
		DynamicGecco.html()
		.gecco("http://list.jd.com/list.html?cat={cat}&delivery={delivery}&page={page}&JL={JL}&go=0", "consolePipeline", "productListJsonPipeline")
		.requestField("request").request().build()
		.intField("currPage").csspath("#J_topPage > span > b").text().build()
		.intField("totalPage").csspath("#J_topPage > span > i").text().build()
		.listField("details", productBrief).csspath("#plist .gl-item").build()
		.register();
		
		//对应JDad类
		Class<?> jdAd = DynamicGecco.json()
		.stringField("ad").jsonpath("$.ads[0].ad").build()
		.listField("ads", JSONObject.class).jsonpath("$.ads").build()
		.register();
		
		//对应JDPrice类
		Class<?> jdPrice = DynamicGecco.json()
		.stringField("code").jsonpath("$.id[0]").build()
		.floatField("price").jsonpath("$.p[0]").build()
		.floatField("srcPrice").jsonpath("$.m[0]").build()
		.register();
		
		//对应ProductDetail类
		DynamicGecco.html()
		.gecco("http://item.jd.com/{code}.html", "consolePipeline")
		.stringField("code").requestParameter().build()
		.stringField("title").csspath("#name > h1").text().build()
		.stringField("detail").csspath("#product-detail-2").build()
		.stringField("image").csspath("#spec-n1 img").image("d:/gecco/jd/img").build()
		.field("price", jdPrice).ajax("http://p.3.cn/prices/get?type=1&pdtk=&pdbp=0&skuid=J_{code}").build()
		.field("jdAd", jdAd).ajax("http://cd.jd.com/promotion/v2?skuId={code}&area=1_2805_2855_0&cat=737%2C794%2C798").build()
		.register();
		
		HttpGetRequest start = new HttpGetRequest("http://www.jd.com/allSort.aspx");
		start.setCharset("GBK");
		GeccoEngine.create()
		.classpath("com.geccocrawler.gecco.demo.dynamic")
		.start(start)
		.interval(2000)
		.run();

	}

}
