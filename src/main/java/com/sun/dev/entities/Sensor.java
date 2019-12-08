package com.sun.dev.entities;

public class Sensor {

	String imei;
	int floorNo;
	String corridorType ;
	
	public Sensor(String imei, int floorNo, String corridorType) {
		this.imei = imei;
		this.floorNo = floorNo;
		this.corridorType = corridorType;
	}
	
}
