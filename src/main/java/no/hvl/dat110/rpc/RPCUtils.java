package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import no.hvl.dat110.TODO;

public class RPCUtils {
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

        System.out.println("\tRPCUtils.encapsulate: rpcid=" + rpcid + " payloadlength=" + payload.length + ", payload: " + Arrays.toString(payload));

		// Encapsulate the rpcid and payload in a byte array according to the RPC message syntax / format
        byte[] rpcmsg =  new byte[1 + payload.length];
        rpcmsg[0] = rpcid;
        System.arraycopy(payload, 0, rpcmsg, 1, payload.length);
		return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
        if (rpcmsg == null || rpcmsg.length < 1) {
            throw new IllegalArgumentException("Invalid RPC message, rpcmsg was null or too short");
        }

        byte rpcid;
        byte[] payload;

        rpcid = rpcmsg[0];
        payload = Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);

        System.out.println(
            "\tRPCUtils.decapsulate: rpcid=" + rpcid +
                " payloadlength=" + payload.length +
                " payload=" + Arrays.toString(payload)
        );

		// Decapsulate the rpcid and payload in a byte array according to the RPC message syntax
        return payload;
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
		return ByteBuffer
            .allocate(str.length())
            .put(StandardCharsets.UTF_8.encode(str))
            .array();
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
		return StandardCharsets.UTF_8
            .decode(ByteBuffer
            .wrap(data))
            .toString();
	}
	
	public static byte[] marshallVoid() {
		return new byte[0];
	}
	
	public static void unmarshallVoid(byte[] data) {
        // debug: should we check for null data?

        if(data.length != 0) {
            throw new IllegalArgumentException("Data for unmarshalling void must be of length 0");
        }

        // this doesnt even make sense lol
	}

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

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		return ByteBuffer
            .allocate(4)
            .putInt(x)
            .array();
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		return ByteBuffer
            .wrap(data)
            .getInt();

	}
}
