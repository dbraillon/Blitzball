package com.dbraillon.blitzball.tools;

public class Reflex {

	private int reflex;
	private int counter;
	
	public Reflex(int reflex) {
		
		setReflex(reflex);
		setCounter(reflex);
	}
	
	public void increase() {
		
		if(getCounter() < getReflex())
			setCounter(getCounter() + 1);
	}
	
	public void increaseMax() {
		
		setCounter(getReflex());
	}
	
	public void reset() {
		
		setCounter(0);
	}
	
	public boolean isAware() {
		
		return getCounter() == getReflex();
	}
	
	
	// Getters and setters
	private int getReflex() {
		return reflex;
	}
	
	private void setReflex(int reflex) {
		this.reflex = reflex;
	}
	
	private int getCounter() {
		return counter;
	}
	
	private void setCounter(int counter) {
		this.counter = counter;
	}
}
