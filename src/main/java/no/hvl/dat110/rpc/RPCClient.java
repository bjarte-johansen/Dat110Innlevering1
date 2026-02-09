package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

import java.util.Arrays;

import utils.Debug;
import utils.Logger;

public class RPCClient {
    public static final boolean RPCCLIENT_DEBUG = false;

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {
        // connect by creating the underlying messaging connection
        System.out.println("RPC client: connecting to server " + msgclient);
        connection = msgclient.connect();
	}
	
	public void disconnect() {
		// disconnect by closing the underlying messaging connection
		connection.close();
	}

	/*
	 Make a remote call om the method on the RPC server by sending an RPC request message and receive an RPC reply message

	 rpcid is the identifier on the server side of the method to be called
	 param is the marshalled parameter of the method to be called
	 */

	public byte[] call(byte rpcid, byte[] param) {
        if(RPCCLIENT_DEBUG && Logger.DEBUG) {
            Debug.println("-".repeat(20));
            Debug.beginBlock("# rpclient/call started");
        }

        // encapsulate the rpcid and param in an RPC message
        byte[] rpcMessage = RPCUtils.encapsulate(rpcid, param);

        if(RPCCLIENT_DEBUG && Logger.DEBUG) {
            Debug.printf("Sent:{\n");
            Debug.printf("\tRPCID: %d (%s)\n", rpcid, RPCCommon.getIdAsString(rpcid));
            Debug.printf("\tPARAMS: %s\n", Arrays.toString(rpcMessage));
            Debug.println("}");
        }

        // send the RPC message as a Message to the server
        connection.send(new Message(rpcMessage));

        // block & wait for a reply
        Message reply = connection.receive();

        if(RPCCLIENT_DEBUG && Logger.DEBUG) {
            Debug.println("Received:{");
            Debug.printf("\tDATA: %s\n", Arrays.toString(reply.getData()));
            Debug.println("}");
        }

        if(RPCCLIENT_DEBUG && Logger.DEBUG) {
            Debug.endBlock();
            Debug.println("-".repeat(20));
        }

        // return the data from the RPC reply message
        return reply.getData();
	}

}
