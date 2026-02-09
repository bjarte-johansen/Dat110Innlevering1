package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}

    /**
    RPC implementation so client can call write method on the server.
    This will marshall the parameter and make the RPC call to the server,
    which will then unmarshall the parameter and call the write method on the server side.
    @param message the message to be displayed on the server side
     */
	public void write (String message) {
        //System.out.println("CALLING RPC Server: DisplayImpl :: DISPLAY:" + message);

        byte[] params = RPCUtils.marshallString(message);

        // no need to store the return value since the method is void
        rpcclient.call(RPCCommon.RPID_WRITE_TEMP_INT, params);
	}
}
