package com.geccocrawler.gecco.monitor;

import java.lang.reflect.Method;

import com.geccocrawler.gecco.downloader.DownloadException;
import com.geccocrawler.gecco.downloader.DownloadServerException;
import com.geccocrawler.gecco.request.HttpRequest;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DownloadMointorIntercetor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if(method.getName().equals("download")) {
			System.out.println(Monitor.exceptionStatics());
			System.out.println(Monitor.serverErrorStatics());
			System.out.println(Monitor.successStatics());
			HttpRequest request = (HttpRequest)args[0];
			try {
				Object o = proxy.invokeSuper(obj, args);
				Monitor.incrSuccess(request.getUrl());
				return o;
			} catch(DownloadServerException ex) {
				Monitor.incrServerError(request.getUrl());
				throw ex;
			} catch(DownloadException ex) {
				Monitor.incrException(request.getUrl());
				throw ex;
			}
			
		} else {
			return proxy.invokeSuper(obj, args);
		}
	}
}
