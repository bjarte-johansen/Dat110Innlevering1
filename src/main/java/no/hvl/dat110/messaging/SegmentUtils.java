package no.hvl.dat110.messaging;

import java.util.Arrays;

public class SegmentUtils {
    public static String segmentToString(byte[] seg) {
        return "{segment: " + Arrays.toString(seg) + ", length: " + seg.length + "}";
    }
    public static String payloadToString(byte[] payload) {
        return "{payload: " + Arrays.toString(payload) + ", length: " + payload.length + "}";
    }

    public static void debugSegmentOrPayload(String method, byte[] data, boolean isSegment) {
        if (isSegment) {
            byte len = data[0];
            //byte[] payload = Arrays.copyOfRange(data, 1, data.length);
            System.out.println("print/segment." + method + "(), len-value:" + data[0] + ", payload: " + Arrays.toString(data));
        } else {
            byte[] payload = Arrays.copyOfRange(data, 0, data.length);
            System.out.println("print/data." + method + "(), len: -, payload: " + Arrays.toString(payload));
        }
    }

    public static void printRpcSegment(byte[] data) {
        byte len = data[0];
        byte rpcid = data[1];
        byte[] payload = Arrays.copyOfRange(data, 2, data.length);
        System.out.println("print/segment. rpcid: " + rpcid + ", len:" + len + ", data: " + Arrays.toString(payload));
    }

    public static void printRpcMessage(byte rpcid, byte[] data) {
        byte[] payload = Arrays.copyOfRange(data, 1, data.length);
        System.out.println("print/segment. rpcid: " + rpcid + ",  data: " + Arrays.toString(payload));
    }
}