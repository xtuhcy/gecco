package com.geccocrawler.gecco.demo.osc.exec;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;


public class AbstractActionQueue implements ActionQueue {

	protected final static Logger LOG = Logger.getLogger(AbstractActionQueue.class);

	private Queue<Action> queue;
	private Executors executor;

	public AbstractActionQueue(Executors executor) {
		super();
		this.queue = new LinkedList<Action>();
		this.executor = executor;
	}

	public ActionQueue getActionQueue() {
		return this;
	}

	public void enqueue(Action action) {
		boolean canExecute = false;
		synchronized (queue) {
			queue.add(action);
			int s = queue.size();
			if (s == 1) {
				canExecute = true;
			} else if (s > 1000) {
				LOG.warn(action.toString() + " queue size : " + s);
			}
		}

		if (canExecute) {
			executor.execute(action);
		}
	}

	public void dequeue(Action action) {
		Action nextAction = null;
		synchronized (queue) {
			if (queue.size() == 0) {
				LOG.error("queue.size() is 0.");
			}
			Action temp = queue.remove();
			if (temp != action) {
				LOG.error("action queue error. temp " + temp.toString() + ", action : " + action.toString());
			}
			if (queue.size() != 0) {
				nextAction = queue.peek();
			}
		}
		if (nextAction != null) {
			executor.execute(nextAction);
		}
	}

	public void clear() {
		synchronized (queue) {
			queue.clear();
		}
	}
	public Queue<Action> getQueue() {
		return this.queue;
	}

}
