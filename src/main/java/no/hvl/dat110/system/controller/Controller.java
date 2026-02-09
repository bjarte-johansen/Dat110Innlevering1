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
    private static int UPDATE_INTERVAL_MS = 1000;

	
	public static void main (String[] args) throws Exception{
		DisplayStub display;
		SensorStub sensor;
		
		RPCClient displayClient;
        RPCClient sensorClient;
		
		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
        displayClient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
		sensorClient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);

        sensorClient.connect();
        displayClient.connect();
		
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayClient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorClient);
				
		// TODO - START
		
		// create local display and sensor stub objects
		// connect to sensor and display RPC servers - using the RPCClients
		// read value from sensor using RPC and write to display using RPC

        SensorStub sensorImpl = new SensorStub(sensorClient);
        DisplayStub displayImpl = new DisplayStub(displayClient);

        for(int i = 0; i < N; i++) {
            int temp = sensorImpl.read();
            displayImpl.write(String.valueOf(temp));
            Thread.sleep(UPDATE_INTERVAL_MS);
        }
/*
        // read from sensor and write to display
        int temp = sensorImpl.read();
        displayImpl.write(String.valueOf(temp));
*/

		stopdisplay.stop();
		stopsensor.stop();

        displayClient.disconnect();
        sensorClient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
