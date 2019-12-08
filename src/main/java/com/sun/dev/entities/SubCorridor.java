package com.sun.dev.entities;

import java.util.Properties;

public class SubCorridor extends Corridor {

	public SubCorridor(Properties props, int corridorNo) {
		super(corridorNo);		
		Class<? extends SubCorridor> callerClass =  this.getClass();
		String className = callerClass.getName();
		int noOfACs = Integer.parseInt(props.getProperty(className+"."+"noOfACs"));
		int noOfLights = Integer.parseInt(props.getProperty(className+"."+"noOfLights"));
		int acUnits = Integer.parseInt(props.getProperty(className+ "."+ "acUnits"));
		int lightUnits = Integer.parseInt(props.getProperty(className+ "."+ "lightUnits"));
		this.airConditioners = new AC[noOfACs];
		this.lights = new Light[noOfLights];
		for(int i = 0; i < noOfACs; i++) {
			this.airConditioners[i] = new AC(acUnits);
		}
		for(int i = 0; i < noOfLights; i++) {
			this.lights[i] = new Light(lightUnits);
		}	}
	
}
