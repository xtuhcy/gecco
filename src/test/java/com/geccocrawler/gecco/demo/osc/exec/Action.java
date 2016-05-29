package com.geccocrawler.gecco.demo.osc.exec;

import org.apache.log4j.Logger;

public abstract class Action implements Runnable {

	protected final static Logger Log = Logger.getLogger(Action.class);

	private ActionQueue queue;
	protected Long createTime;

	public Action(ActionQueue queue) {
		this.createTime = System.currentTimeMillis();
		this.queue = queue;
	}

	public void run() {
		if (queue != null) {
			long start = System.currentTimeMillis();
			try {
				execute();
				long end = System.currentTimeMillis();
				long interval = end - start;
				long leftTime = end - createTime;
				if (interval > 1000) {
					Log.warn("execute action : " + this.toString()
							+ ", interval : " + interval + ", leftTime : "
							+ leftTime + ", size : " + queue.getQueue().size());
				}
			} catch (Exception e) {
				Log.error(
						"run action execute exception. action : "
								+ this.toString(), e);
			} finally {
				queue.dequeue(this);
			}
		}
	}

	protected abstract void execute();
}
