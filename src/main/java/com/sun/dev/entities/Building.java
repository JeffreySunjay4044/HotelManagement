package com.sun.dev.entities;

import java.util.Properties;

public class Building {

	private Floor[] floors ;
	private int noOfFloors ;
	
	public Building(Properties props) {
		Class<? extends Building> callerClass =  this.getClass();
		String className = callerClass.getName();
		System.out.println(className);
		this.noOfFloors = Integer.parseInt(props.getProperty(className+"."+"noOfFloors"));
		this.floors = new Floor[noOfFloors];
		for(int i = 0; i <  noOfFloors; i++) {
			this.floors[i] = new Floor(props, i);
		}
	}
	
	public Floor[] getFloors() {
		return floors;
	}

	public int getNoOfFloors() {
		return noOfFloors;
	}

	public  void turnOn() {
		for(int i = 0; i <  noOfFloors; i++) {
			this.floors[i].turnOn();
		}
	}
	
	public  void turnOff() {
		for(int i = 0; i <  noOfFloors; i++) {
			this.floors[i].turnOff();
		}
	}
	
	@Override
	public String toString() {
		String result  = "";
		for(int i = 0; i < floors.length; i++) {
			Floor floor = floors[i];
			String variedResult = floor.toString();
			result += "\\n  Floor No :"+ (i+1)  + variedResult.toString();
		}
		return result;
		
	}

}
