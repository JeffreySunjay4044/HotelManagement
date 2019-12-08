package com.sun.dev.entities;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SensorSocket implements Runnable{

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
        { 
            try
            { 
                line = in.readUTF();
                // Expected input imei//state
                System.out.println(line); 

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
