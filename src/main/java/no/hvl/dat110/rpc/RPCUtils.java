package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.SegmentUtils;
import utils.Logger;

public class RPCUtils {
    public static final boolean LOG_CALLS = false;

    static public String rpcMessageToString(byte[] rpcmsg) {
        if(rpcmsg == null || rpcmsg.length == 0) return "";

        byte rpcid = rpcmsg[0];
        byte[] payload = Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);

        return String.format("{rpcid: %d, payload: %s}", rpcid, byteArrayToString(payload));
    }
    static public String byteArrayToString(byte[] payload) {
        if(payload == null || payload.length == 0) return "";

        return String.format("{bytes: %s}", Arrays.toString(payload));
    }
    static public String payloadToString(byte[] payload) {
        if(payload == null || payload.length == 0) return "";

        return String.format("{payload: %s}", byteArrayToString(payload));
    }

    ///////////////

    static String byteArrayToHexString(byte[] a) {
        if(a == null || a.length == 0) return "";

        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

	public static byte[] encapsulate(byte rpcid, byte[] payload) {
        if(payload == null){
            throw new IllegalArgumentException("Payload cannot be null");
        }

        // slower but very nice and neat
        byte[] rpcmsg = ByteBuffer.allocate(1 + payload.length)
            .put(rpcid)
            .put(payload)
            .array();

/*
		// faster but more errorprone
		// Encapsulate the rpcid and payload in a byte array according to the RPC message syntax / format
        // set rpcid as the first byte of the rpc message
        // copy the payload into the rpc message starting from index 1
        byte[] rpcmsg =  new byte[payload.length + 1];
        rpcmsg[0] = rpcid;
        System.arraycopy(payload, 0, rpcmsg, 1, payload.length);
 */

        // debug
        if(LOG_CALLS) Logger.logf("rpcutils/encapsulate/output, %s", rpcMessageToString(rpcmsg));

        // return result
		return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
        if (rpcmsg == null || rpcmsg.length < 1) {
            throw new IllegalArgumentException("Invalid RPC message, rpcmsg was null or too short");
        }

        if(LOG_CALLS) Logger.logf("rpcutils/decapsulate/input, %s\n", rpcMessageToString(rpcmsg) + ")");

        // extract payload
        byte[] payload = Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);

        // log
        if(LOG_CALLS) Logger.logf("rpcutils/decapsulate/output, %s", payloadToString(payload));

        // return result
        return payload;
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
	}


    /*
    void marshalling / unmarshalling
     */
	public static byte[] marshallVoid() {
		return new byte[0];
	}
	
	public static void unmarshallVoid(byte[] data) {
        if(data.length != 0) {
            throw new IllegalArgumentException("Data for unmarshalling void must be of length 0");
        }
	}




    /*
    boolean marshalling / unmarshalling
     */
	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {
		return (b) ? new byte[] {1} : new byte[] {0};
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {
        if(data == null || data.length != 1) {
            throw new IllegalArgumentException("Data for unmarshalling boolean must be of length 1");
        }
		return (data[0] > 0);
		
	}


    /*
    integer marshalling / unmarshalling
     */

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		return ByteBuffer
            .allocate(4)
            .order(ByteOrder.BIG_ENDIAN)
            .putInt(x)
            .array();
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		return ByteBuffer
            .wrap(data)
            .order(ByteOrder.BIG_ENDIAN)
            .getInt();

	}
}