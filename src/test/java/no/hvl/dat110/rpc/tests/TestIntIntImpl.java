package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.rpc.RPCRemoteImpl;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCServer;

import java.util.Arrays;

public class TestIntIntImpl extends RPCRemoteImpl {

	public TestIntIntImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid,rpcserver);
	}
	
	public byte[] invoke(byte[] request) {

        if(request.length != 4) {
            System.out.println("TestIntIntImpl: invalid request length: " + request.length);
        }

        //byte[] payload = RPCUtils.decapsulate(request);
        System.out.println("TestIntIntImpl: request raw = " + Arrays.toString(request));

		int x = RPCUtils.unmarshallInteger(request);
		
		int resx = m(x);
		
		byte[] reply = RPCUtils.marshallInteger(resx);
		
		return reply;
	}
	
	public int m(int x) {
		System.out.println("int m("+x+") executed");
		return x;
	} 
}
