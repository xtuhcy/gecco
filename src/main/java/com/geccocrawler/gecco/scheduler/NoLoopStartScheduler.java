package com.geccocrawler.gecco.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 不需要循环抓取的start队列
 *
 * @author huchengyi
 *
 */
public class NoLoopStartScheduler implements Scheduler {
	
	private ConcurrentLinkedQueue<HttpRequest> queue;
	private final AtomicBoolean firstSeedArrived;
	private final Object firstSeedLock;
	private volatile boolean shutdown;

	public NoLoopStartScheduler() {
		queue = new ConcurrentLinkedQueue<HttpRequest>();
		firstSeedArrived = new AtomicBoolean(false);
		firstSeedLock = new Object();
		shutdown = false;
	}

	@Override
	public HttpRequest out() {
		HttpRequest request = queue.poll();
		if (request != null) {
			return request;
		}
		awaitFirstSeed();
		return queue.poll();
	}

	@Override
	public void into(HttpRequest request) {
		if (request == null) {
			return;
		}
		queue.offer(request);
		if (firstSeedArrived.compareAndSet(false, true)) {
			notifyFirstSeed();
		}
	}

	/**
	 * 释放首次种子等待，防止在未注入初始请求前线程提前退出。
	 */
	private void awaitFirstSeed() {
		if (firstSeedArrived.get() || shutdown) {
			return;
		}
		synchronized (firstSeedLock) {
			while (!firstSeedArrived.get() && !shutdown) {
				try {
					firstSeedLock.wait(200L);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		}
	}

	private void notifyFirstSeed() {
		synchronized (firstSeedLock) {
			firstSeedLock.notifyAll();
		}
	}

	public void shutdown() {
		shutdown = true;
		notifyFirstSeed();
	}

}
