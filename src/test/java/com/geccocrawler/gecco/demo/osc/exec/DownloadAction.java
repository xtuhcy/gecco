package com.geccocrawler.gecco.demo.osc.exec;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class DownloadAction extends Action {

	private String url;
	private String localPath;
	private CloseableHttpClient httpClient;

	{
		RequestConfig clientConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
		PoolingHttpClientConnectionManager syncConnectionManager = new PoolingHttpClientConnectionManager();
		syncConnectionManager.setMaxTotal(1000);
		syncConnectionManager.setDefaultMaxPerRoute(10);
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(clientConfig)
				.setConnectionManager(syncConnectionManager).build();
	}

	public DownloadAction(ActionQueue queue, String url, String localPath) {
		super(queue);
		this.url = url;
		this.localPath = localPath;
	}

	@Override
	protected void execute() {
		HttpRequestBase request = new HttpGet(url);
		try {
			HttpClientContext context = HttpClientContext.create();
			org.apache.http.HttpResponse response = httpClient.execute(request, context);
			FileUtils.copyInputStreamToFile(response.getEntity().getContent(), new File(localPath));
		} catch (Exception e) {
			Log.error("Download error,  " + e.toString());
		} finally {
			request.releaseConnection();
		}
	}

}
