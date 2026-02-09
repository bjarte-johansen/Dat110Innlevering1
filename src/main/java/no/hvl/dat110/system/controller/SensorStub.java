package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.*;

public class SensorStub extends RPCLocalStub {

	public SensorStub(RPCClient rpcclient) {
		super(rpcclient);
	}


    /**
     * RPC implementation so client can call read method on the server.
     * This will marshall the parameter and make the RPC call to the server,
     * which will then unmarshall the parameter and call the read method on the server side.
     * @return the integer value read from the sensor on the server side
     */

	public int read() {
		// marshall parameter to read call (void parameter)
		byte[] request = RPCUtils.marshallVoid();

		// make remote procedure call for read
		byte[] response = rpcclient.call((byte)Common.READ_RPCID, request);

		// unmarshall the return value from the call (an integer)
		return RPCUtils.unmarshallInteger(response);
	}
}
