package com.geccocrawler.gecco.monitor;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 下载统计指标
 * 
 * @author huchengyi
 *
 */
public class DownloadStatistics implements Serializable {
	
	private static final long serialVersionUID = 5441547994721879109L;

	/**
	 * 成功下载次数
	 */
	private AtomicLong success;
	
	/**
	 * 下载异常次数
	 */
	private AtomicLong exception;
	
	/**
	 * 服务器错误次数
	 */
	private AtomicLong serverError;

	public DownloadStatistics() {
		success = new AtomicLong(0);
		exception = new AtomicLong(0);
		serverError = new AtomicLong(0);
	}
	
	public long getSuccess() {
		return success.get();
	}

	public long incrSuccess() {
		return this.success.incrementAndGet();
	}

	public long getException() {
		return exception.get();
	}

	public long incrException() {
		return this.exception.incrementAndGet();
	}

	public long getServerError() {
		return serverError.get();
	}

	public long incrServerError() {
		return this.serverError.incrementAndGet();
	}

	@Override
	public String toString() {
		return "[success=" + success + ", exception=" + exception + ", serverError=" + serverError + "]";
	}

}
