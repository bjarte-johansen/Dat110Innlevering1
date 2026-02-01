package no.hvl.dat110.rpc;

public class RPCCommon {

	// RPCID for default stop method on the RPC server
	// no other RPC methods should use this 0 as RPC id
	public static byte RPIDSTOP = 0;

    public static byte RPID_READ_TEMP_INT = 1;
    public static byte RPID_WRITE_TEMP_INT = 2;

    public static String getIdAsString(byte rpcid) {

    	switch(rpcid) {

    	case 0: return "RPIDSTOP";
    	case 1: return "RPID_READ_TEMP_INT";
    	case 2: return "RPID_WRITE_TEMP_INT";
    	default: return "UNKNOWN RPCID";

    	}

    }
}
