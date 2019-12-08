package com.sun.dev.entities;

public abstract class Equipments {
	int unitsUsed;
	boolean currentState;
	
	public void powerOn() {
		this.currentState = true;
	}
	
	public void powerOff() {
		this.currentState = false;
	}
	
	public boolean getCurrentState() {
		return currentState;
	}
	
	public int getPowerUsed() {
		return this.unitsUsed;
	}
	
	@Override
	public String toString() {
		String result  = "";
		result += "\\n Current State  :" + currentState;
		
		return result;
		
	}
	
}
