package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.messaging.SegmentUtils;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCLocalStub;
import no.hvl.dat110.rpc.RPCUtils;

import java.util.Arrays;

public class TestIntIntStub extends RPCLocalStub {
	
	public TestIntIntStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public int m(int x) {
				
		byte[] request = RPCUtils.marshallInteger(x);
		
		byte[] reply = rpcclient.call((byte)3, request);

		int xres = RPCUtils.unmarshallInteger(reply);

        System.out.printf("- TestIntIntStub: INPUT = %d, data: %s\n", request.length, Arrays.toString(request));
        System.out.printf("- TestIntIntStub: OUTPUT = %d, data: %s\n", reply.length, Arrays.toString(reply));
		
		return xres;
	}
}
