package com.geccocrawler.gecco.downloader;

public class DownloadTimeoutException extends DownloadException {

	private static final long serialVersionUID = -5024670348287534079L;
	
	public DownloadTimeoutException(Throwable cause) {
		super(cause);
	}

	public DownloadTimeoutException(String message) {
		super(message);
	}
}
