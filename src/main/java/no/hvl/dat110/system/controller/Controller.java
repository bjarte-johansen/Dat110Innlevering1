package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.SegmentUtils;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;
import no.hvl.dat110.rpc.RPCCommon;
import no.hvl.dat110.system.display.DisplayDevice;
import no.hvl.dat110.system.sensor.SensorImpl;
import utils.IndentedOut;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) {

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

        int temperature = sensorImpl.read();

        displayImpl.write( String.valueOf(temperature) );

		// TODO - END
		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
