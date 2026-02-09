package no.hvl.dat110.rpc;

import utils.Debug;

public class RPCCommon {

	// RPCID for default stop method on the RPC server
	// no other RPC methods should use this 0 as RPC id
	public static final byte RPIDSTOP = 0;

    public static final byte RPID_READ_TEMP_INT = 1;
    public static final byte RPID_WRITE_TEMP_INT = 2;

    public static String getIdAsString(byte rpcid) {

        return switch (rpcid){
            case RPIDSTOP -> "RPIDSTOP";
    	    case RPID_READ_TEMP_INT -> "RPID_READ_TEMP_INT";
            case RPID_WRITE_TEMP_INT -> "RPID_WRITE_TEMP_INT";
            default -> "UNKNOWN RPCID";
    	};
    }

}
