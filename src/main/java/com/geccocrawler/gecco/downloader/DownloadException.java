package com.geccocrawler.gecco.downloader;

public class DownloadException extends Exception {

	private static final long serialVersionUID = 4102345334913558607L;

	public DownloadException(Throwable cause) {
		super(cause);
	}

	public DownloadException(String message) {
		super(message);
	}

}
