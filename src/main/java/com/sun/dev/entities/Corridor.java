package com.sun.dev.entities;

public abstract class Corridor {
	
	Equipments[] airConditioners;
	Equipments[] lights;
	int corridorNo;
	Sensor motionSensor;
	
	public Corridor(int corridorNo) {
		this.corridorNo = corridorNo;
	}
	
	public Equipments[] getAirConditioners() {
		return airConditioners;
	}
	public Equipments[] getLights() {
		return lights;
	}
	
	public void turnOn() {
		for(int i = 0; i < lights.length; i++) {
			lights[i].currentState = true;
		}
	}
	
	public void turnOff() {
		for(int i = 0; i < lights.length; i++) {
			lights[i].currentState = false;
		}
	}
	
	public boolean turnOffOne(Equipments[] equipment) {
		for(int i = 0; i < equipment.length; i++) {
			if(equipment[i].currentState == true) {
				equipment[i].currentState = false;
				i = equipment.length;
				return true;
			}
		}
		return false;
	}
	
	public boolean turnOnOne(Equipments[] equipments) {
		for(int i = 0; i < equipments.length; i++) {
			if(equipments[i].currentState == false) {
				equipments[i].currentState = true;
				i = equipments.length;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String result  = "";
		for(int i = 0; i < airConditioners.length; i++) {
			Equipments ac = airConditioners[i];
			result += "\\n AC No :"+ (i+1)  + ac.toString();
		}
		for(int i = 0; i < lights.length; i++) {
			Equipments light = lights[i];
			result += "\\n light  No :"+ (i+1)  + light.toString();
		}
		return result;
		
	}
}
