package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;
import utils.Debug;

public class MessageUtils {
    public static final int LENGTH_BYTES = 1;

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

    /*
    protected static void encodeLength32(int len, byte[] data){
        data[0] = (byte)(len >> 24);
        data[1] = (byte)(len >> 16);
        data[2] = (byte)(len >> 8);
        data[3] = (byte)(len);
    }

    protected static int decodeLength32(byte[] data){
        return ((data[0] & 0xff) << 24)
            | ((data[1] & 0xff) << 16)
            | ((data[2] & 0xff) << 8)
            | (data[3] & 0xff);
    }
    */

	public static byte[] encapsulate(Message message) {
		byte[] data = message.getData();
        int len = data.length;
        byte[] segment;

        Debug.printf("MessageUtils/encapsulate");
        Debug.indent();
        Debug.printf("data length=%d, data=%s", len, Arrays.toString(data));

        if(data.length > 0) {
            segment = new byte[MessageUtils.SEGMENTSIZE];

            // TODO - START

            // encode length of data and copy data into segment
            segment[0] = (byte) len;
            System.arraycopy(data, 0, segment, MessageUtils.LENGTH_BYTES, len);
        }else{
            segment = new byte[0];
        }

        Debug.unindent();

        return segment;
	}

	public static Message decapsulate(byte[] segment) {
        Debug.printf("MessageUtils/decapsulate");
        Debug.indent();
        Debug.printf("segment length=%d, segment=%s\n", segment.length, Arrays.toString(segment));
        Debug.unindent();

        // decapsulate segment and put received payload data into a message
        if(segment == null || segment.length < 1) {
            return new Message(new byte[0]);
        }

        int len = segment[0];
        byte[] data = new byte[len];

        System.arraycopy(segment, 1, data, 0, len);
		return new Message(data);
	}
	
}
