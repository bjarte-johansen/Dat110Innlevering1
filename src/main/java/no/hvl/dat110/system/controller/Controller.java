package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.SegmentUtils;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;
import no.hvl.dat110.rpc.RPCCommon;
import no.hvl.dat110.system.display.DisplayDevice;
import no.hvl.dat110.system.sensor.SensorImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Controller  {
    static volatile boolean running = true;
	
	private static int N = 5;
	
	public static void main (String[] args) throws Exception{

        //System.setOut(new IndentedOut(System.out));

		DisplayStub display;
		SensorStub sensor;
		
		RPCClient displayclient,sensorclient;
		
		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
		displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);
        sensorclient.connect();
        displayclient.connect();
		
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);
				
		// TODO - START
		
		// create local display and sensor stub objects
		// connect to sensor and display RPC servers - using the RPCClients
		// read value from sensor using RPC and write to display using RPC

        SensorStub sensorImpl = new SensorStub(sensorclient);
        DisplayStub displayImpl = new DisplayStub(displayclient);


        int UPDATE_INTERVAL_MS = 1000;


        // read from sensor and write to display
        int temp = sensorImpl.read();
        displayImpl.write(String.valueOf(temp));


		// TODO - END
		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
