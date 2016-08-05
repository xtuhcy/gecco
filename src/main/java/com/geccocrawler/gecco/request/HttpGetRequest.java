package com.geccocrawler.gecco.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpGetRequest extends AbstractHttpRequest {

	private static final long serialVersionUID = 6105458424891960971L;

	public HttpGetRequest() {
		super();
	}

	public HttpGetRequest(String url) {
		super(url);
	}

	public static HttpGetRequest fromJson(JSONObject request) {
		return (HttpGetRequest)JSON.toJavaObject(request, HttpGetRequest.class);
	}
}
