package com.sun.dev.entities;

public class Light extends Equipments {
	
	public Light(int totalPower){
		super();
		this.unitsUsed = totalPower;
		this.currentState = false;
	}
}
