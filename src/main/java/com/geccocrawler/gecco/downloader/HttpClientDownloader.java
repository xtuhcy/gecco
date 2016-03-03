package com.geccocrawler.gecco.downloader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.geccocrawler.gecco.request.HttpPostRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.utils.UrlUtils;

/**
 * userAgent更换
 * proxy更换
 * 
 * @author huchengyi
 *
 */
public class HttpClientDownloader extends AbstractDownloader {
	
	private static Log log = LogFactory.getLog(HttpClientDownloader.class);
	
	private CloseableHttpClient httpClient;
	
	private long timeout;
	
	public HttpClientDownloader() {
		RequestConfig clientConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
		PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();
		syncConnectionManager.setMaxTotal(1000);
		syncConnectionManager.setDefaultMaxPerRoute(50);
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(clientConfig).setConnectionManager(syncConnectionManager).build();
	}

	@Override
	public HttpResponse download(HttpRequest request) throws DownloaderException {
		if(log.isDebugEnabled()) {
			log.debug("downloading..." + request.getUrl());
		}
		HttpRequestBase reqObj = null;
		if(request instanceof HttpPostRequest) {//post
			HttpPostRequest post = (HttpPostRequest)request;
			reqObj = new HttpPost(post.getUrl());
			//post fields
			List<NameValuePair> fields = new ArrayList<NameValuePair>();
			for(Map.Entry<String, Object> entry : post.getFields().entrySet()) {
				NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				fields.add(nvp);
			}
			try {
				HttpEntity entity = new UrlEncodedFormEntity(fields, "UTF-8");
				((HttpEntityEnclosingRequestBase) reqObj).setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {//get
			reqObj = new HttpGet(request.getUrl());
		}
		//header
		reqObj.addHeader("User-Agent", UserAgent.getUserAgent());
		for(Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
			reqObj.addHeader(entry.getKey(), entry.getValue());
		}
		//request config
		RequestConfig.Builder builder = RequestConfig.custom()
		.setConnectionRequestTimeout(((Long)timeout).intValue())
		.setSocketTimeout(((Long)timeout).intValue())
		.setConnectionRequestTimeout(((Long)timeout).intValue())
		.setRedirectsEnabled(false);
		//proxy
		HttpHost proxy = Proxys.getProxy();
		if(proxy != null) {
			builder.setProxy(proxy);
		}
		reqObj.setConfig(builder.build());
		//request and response
		try {
			 HttpClientContext context = HttpClientContext.create();  
			org.apache.http.HttpResponse response = httpClient.execute(reqObj, context);
			int status = response.getStatusLine().getStatusCode();
			HttpResponse resp = new HttpResponse();
			resp.setStatus(status);
			if(status == 302 || status == 301) {
				String redirectUrl = response.getFirstHeader("Location").getValue();
				resp.setContent(UrlUtils.relative2Absolute(request.getUrl(), redirectUrl));
			} else if(status == 200) {
				HttpEntity responseEntity = response.getEntity();
				resp.setRaw(responseEntity.getContent());
				String contentType = responseEntity.getContentType().getValue();
				resp.setContentType(contentType);
				String charset = getCharset(request.getCharset(), contentType);
				resp.setCharset(charset);
				String content = EntityUtils.toString(responseEntity, charset);
				/*Header ceHeader = responseEntity.getContentEncoding();
				if(ceHeader != null && ceHeader.getValue().equalsIgnoreCase("gzip")) {
					content = EntityUtils.toString(new GzipDecompressingEntity(responseEntity), charset);
				} else {
					content = EntityUtils.toString(responseEntity, charset);
				}*/
				resp.setContent(content);
			} else {
				throw new DownloaderException("ERROR : " + status);
			}
			return resp;
		} catch (Exception e) {
			throw new DownloaderException(e);
		} finally {
			reqObj.releaseConnection();
		}
	}
	
	@Override
	public void timeout(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public void shutdown() {
		try {
			httpClient.close();
		} catch (IOException e) {
			httpClient = null;
		}
	}

}
