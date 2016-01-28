package com.geccocrawler.gecco.downloader;

public class DownloaderException extends Exception {

	private static final long serialVersionUID = 4102345334913558607L;

	public DownloaderException(Throwable cause) {
		super(cause);
	}

	public DownloaderException(String message) {
		super(message);
	}

}
