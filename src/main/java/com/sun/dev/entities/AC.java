package com.sun.dev.entities;

public class AC extends Equipments{

	public AC(int totalPower){
		super();
		this.unitsUsed = totalPower;
		this.currentState = false;
	}
}
