package no.hvl.dat110.system.display;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;
import no.hvl.dat110.system.sensor.SensorDevice;
import no.hvl.dat110.system.sensor.SensorImpl;


public class DisplayDevice {
	
	public static void main(String[] args) {

		System.out.println("Display server starting ...");

        run();

        System.out.println("Display server stopping ...");
	}

    private static void run() {
        RPCServer sensorServer = new RPCServer(Common.DISPLAYPORT);
        DisplayImpl impl = new DisplayImpl((byte)Common.WRITE_RPCID, sensorServer);
        sensorServer.run();
        sensorServer.stop();
    }
}
