package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public void write (String message) {
		// implement marshalling, call and unmarshalling for write RPC method

        //System.out.println("CALLING RPC Server: DisplayImpl :: DISPLAY:" + message);

        byte[] params = RPCUtils.marshallString(message);

        byte[] result = rpcclient.call(RPCCommon.RPID_WRITE_TEMP_INT, params);
	}
}
