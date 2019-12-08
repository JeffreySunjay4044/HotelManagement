package com.sun.dev;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.sun.dev.entities.Building;

public class Executor {
	
	public static Building inputBuilding ;
	
	
	public static void main(String[] args) throws IOException, SchedulerException {
			InputStream input = new FileInputStream("/Users/jeffreysunjay/eclipse-workspace/HotelManagement/src/main/resources/config.properties") ;
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);            
            inputBuilding = new Building(prop);
            
            SensorSocket socketServer = new SensorSocket(8080);
            Thread newThread  = new Thread(socketServer);
            newThread.start();      
            
            JobDetail job1 = JobBuilder.newJob(MyJob.class)
                    .withIdentity("job1", "group1").build();
 
            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 18 * * * ?"))
                    .build();
             
            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1, trigger1);
            
            JobDetail job2 = JobBuilder.newJob(ShutdownJob.class)
                    .withIdentity("job2", "group2").build();
 
            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger2", "group2")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 6 * * * ?"))
                    .build();
             
            Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
            scheduler2.start();
            scheduler2.scheduleJob(job2, trigger2);
	}
	
	
	public static class MyJob implements Job{

	      @Override
	      public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	    	  // turn on all lights
	    	  inputBuilding.turnOn();
	      }

	    }
	
	public static class ShutdownJob implements Job{

	      @Override
	      public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	    	  // turn on all lights
	    	  inputBuilding.turnOff();
	      }

	    }
	
	
	public static class SensorSocket implements Runnable{

		 //initialize socket and input stream 
	    private Socket          socket   = null; 
	    private ServerSocket    server   = null; 
	    private DataInputStream in       =  null;
	  
	    // constructor with port 
	    public SensorSocket(int port) 
	    { 
	        // starts server and waits for a connection 
	        try
	        { 
	            server = new ServerSocket(port); 
	            System.out.println("Server started"); 
	  
	            System.out.println("Waiting for a client ..."); 
	   
	        } 
	        catch(IOException i) 
	        { 
	            System.out.println(i); 
	        } 
	    }

		@Override
		public void run() {
			try {
			socket = server.accept(); 
			  
	        // takes input from the client socket 
	        in = new DataInputStream( 
	            new BufferedInputStream(socket.getInputStream())); 

	        String line = ""; 

	        // reads message from client until "Over" is sent 
	        while (!line.equals("ExitAllFinalCommand")) 
	        { 	                System.out.println(" again"); 

	            try
	            { 
	                line = in.readUTF();	                
	    	        	System.out.println("line again"); 
	    	        

	                // Expected input imei//state let us assume input as floor//no//mc//no//ON
	                if(line.length() > 0) {
	                	String[] lineResult = line.split("//");
	                	int floorNo = Integer.parseInt(lineResult[1]);
                		boolean state = lineResult[4] == "ON" ? true : false;
	                	if(lineResult[2] == "mc") {
	                		int mainCorridor = Integer.parseInt(lineResult[3]);
	                		if(state) {
		                		inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].turnOnOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].getLights());
		                		inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].turnOffOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].getAirConditioners());
	                		}else {
		                		inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].turnOffOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].getLights());
		                		inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].turnOnOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[mainCorridor].getAirConditioners());

	                		}
	                	}else {
	                		int subCorridor = Integer.parseInt(lineResult[3]);
	                		if(state) {
		                		inputBuilding.getFloors()[floorNo].getSubCorridors()[subCorridor].turnOnOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[subCorridor].getLights());
		                		inputBuilding.getFloors()[floorNo].getSubCorridors()[subCorridor].turnOffOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[subCorridor].getAirConditioners());
	                		}else {
		                		inputBuilding.getFloors()[floorNo].getSubCorridors()[subCorridor].turnOffOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[subCorridor].getLights());
		                		inputBuilding.getFloors()[floorNo].getSubCorridors()[subCorridor].turnOnOne(inputBuilding.getFloors()[floorNo].getMainCorridors()[subCorridor].getAirConditioners());

	                		}

	                	}
	                    System.out.println(line); 

		                System.out.println(inputBuilding.toString()); 
		                System.out.println("!!!!@@@@!!!!"); 
	                }
	         



	            } 
	            catch(IOException i) 
	            { 
	                System.out.println(i); 
	            } 
	        } 
	        System.out.println("Closing connection"); 

	        // close connection 
	        socket.close(); 
	        in.close();	
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
	}
	

}
