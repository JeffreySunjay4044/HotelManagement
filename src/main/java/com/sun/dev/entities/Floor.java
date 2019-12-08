package com.sun.dev.entities;

import java.lang.reflect.Field;
import java.util.Properties;

public class Floor {

	private Corridor[] mainCorridors;
	private Corridor[] subCorridors;
	private int floorNo;
	private int noOfMainCorridors = 0;
	private int noOfSubCorridors = 0;
	

	private void populateFromProps(Floor floor, Properties props) {
		Class<? extends Floor> callerClass = floor.getClass();
		String className = callerClass.getName();
		floor.noOfMainCorridors = Integer.parseInt(props.getProperty(className+"."+"noOfMainCorridors"));
		floor.noOfSubCorridors = Integer.parseInt(props.getProperty(className+"."+"noOfSubCorridors"));
	}

	public Floor(Properties props, int floorNo) {
		this.floorNo = floorNo;
		populateFromProps(this, props);
		mainCorridors = new MainCorridor[noOfMainCorridors]; 
		subCorridors = new SubCorridor[noOfSubCorridors]; 
		for(int i = 0; i < noOfMainCorridors; i++) {
			mainCorridors[i] = new MainCorridor(props, i);
		}
		for(int i = 0; i < noOfSubCorridors; i++) {
			subCorridors[i] = new SubCorridor(props, i);
		}
	}
	
	public Corridor[] getMainCorridors() {
		return mainCorridors;
	}

	public Corridor[] getSubCorridors() {
		return subCorridors;
	}

	public int getFloorNo() {
		return floorNo;
	}

	public int getNoOfMainCorridors() {
		return noOfMainCorridors;
	}

	public int getNoOfSubCorridors() {
		return noOfSubCorridors;
	}

	public  void turnOn() {
		for(int i = 0; i < noOfMainCorridors; i++) {
			mainCorridors[i].turnOn();
		}
		for(int i = 0; i < noOfSubCorridors; i++) {
			subCorridors[i].turnOn();
		}
	}
	
	public void turnOff() {
		for(int i = 0; i < noOfMainCorridors; i++) {
			mainCorridors[i].turnOff();
		}
		for(int i = 0; i < noOfSubCorridors; i++) {
			subCorridors[i].turnOff();
		}
	}
	
	@Override
	public String toString() {
		String result  = "";
		for(int i = 0; i < noOfMainCorridors; i++) {
			Corridor corridor = mainCorridors[i];
			result += "\\n Main Corridor No :"+ (i+1)  + corridor.toString();
		}
		for(int i = 0; i < noOfSubCorridors; i++) {
			Corridor corridor = subCorridors[i];
			result += "\\n Sub Corridor No :"+ (i+1)  + corridor.toString();
		}
		return result;
		
	}

}
