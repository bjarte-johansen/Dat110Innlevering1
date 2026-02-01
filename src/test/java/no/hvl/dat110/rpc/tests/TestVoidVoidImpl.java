package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.rpc.RPCRemoteImpl;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCServer;
import utils.Debug;

import java.util.Arrays;

public class TestVoidVoidImpl extends RPCRemoteImpl {

	public TestVoidVoidImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid,rpcserver);
	}
	
	public void m() {
		System.out.println("void m() executed");
	}
	
	public byte[] invoke(byte[] request) {
        Debug.printf("invoke TestVoidVoidImpl (%s)\n", Arrays.toString(request));
		RPCUtils.unmarshallVoid(request);
		
		m();
		
		byte[] reply = RPCUtils.marshallVoid();
		
		return reply;
	}
}
