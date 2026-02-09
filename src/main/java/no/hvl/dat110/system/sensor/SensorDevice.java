package no.hvl.dat110.system.sensor;

import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;

public class SensorDevice {

	public static void main(String[] args) {

		System.out.println("Sensor server starting ...");
		
        run();
		
		System.out.println("Sensor server stopping ...");
	}

    public static void run(){
        RPCServer sensorServer = new RPCServer(Common.SENSORPORT);
        SensorImpl sensor = new SensorImpl((byte)Common.READ_RPCID, sensorServer);
        sensorServer.run();
        sensorServer.stop();
    }

}
