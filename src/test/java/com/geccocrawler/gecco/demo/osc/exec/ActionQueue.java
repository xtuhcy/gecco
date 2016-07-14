package com.geccocrawler.gecco.demo.osc.exec;

import java.util.Queue;

public interface ActionQueue {

	public ActionQueue getActionQueue();

	public void enqueue(Action action);

	public void dequeue(Action action);

	public void clear();

	public Queue<Action> getQueue();
}
