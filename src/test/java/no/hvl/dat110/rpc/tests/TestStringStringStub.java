package no.hvl.dat110.rpc.tests;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCLocalStub;
import no.hvl.dat110.rpc.RPCUtils;

public class TestStringStringStub extends RPCLocalStub {

	public TestStringStringStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public String m(String str) {
		
		byte[] request = RPCUtils.marshallString(str);
        System.out.printf("input: \"%s\"\n", str);
        System.out.printf("marshalled: \"%s\"\n", str);
		
		byte[] reply = rpcclient.call((byte) 2, request);
		
		String strres = RPCUtils.unmarshallString(reply);
        System.out.printf("unmarshalled: \"%s\"\n", strres);
		
		return strres;
	}
}
