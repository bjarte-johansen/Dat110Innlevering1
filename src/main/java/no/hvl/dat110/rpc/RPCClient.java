package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

import java.util.Arrays;

import utils.Debug;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {
        // connect by creating the underlying messaging connection
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
        Debug.printf("# rpclient/call started");
        Debug.indent(1);

        // encapsulate the rpcid and param in an RPC message
        byte[] rpcMessage = RPCUtils.encapsulate(rpcid, param);

        Debug.println();
        Debug.printf("%s (is \"%s\")\n", Arrays.toString(rpcMessage), RPCCommon.getIdAsString(rpcid));

        connection.send(new Message(rpcMessage));

        // block & wait for a reply
        Debug.println("- waiting for reply");
        Message reply = connection.receive();
        Debug.println("- received reply");

        // dont allow this ..
        if(reply.getData().length == 0) {
            throw new IllegalStateException("RPC CALL reply.getData().length was zero");
        }

        Debug.indent();
        Debug.printf("data: %s, len: %d\n", Arrays.toString(reply.getData()), reply.getData().length);
        Debug.printf("decapsulated: " + Arrays.toString(RPCUtils.decapsulate(reply.getData())) + "\n");
        Debug.unindent();

        Debug.indent(-1);
        Debug.printf("# rpcclient/call end");

        return RPCUtils.decapsulate(reply.getData());
	}

}
