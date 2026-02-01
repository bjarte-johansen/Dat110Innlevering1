package no.hvl.dat110.rpc;

import java.util.Arrays;
import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageUtils;
import no.hvl.dat110.messaging.MessagingServer;
import no.hvl.dat110.system.controller.Common;
import utils.Debug;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;
	
	// hashmap to register RPC methods which are required to extend RPCRemoteImpl
	// the key in the hashmap is the RPC identifier of the method
	private HashMap<Byte,RPCRemoteImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {
		
		// the stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP,this);
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
			
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {
	    
		   byte rpcid = 0;
		   Message requestmsg, replymsg;
           byte[] responseData;
		   
		   // - receive a Message containing an RPC request
		   // - extract the identifier for the RPC method to be invoked from the RPC request
		   // - extract the method's parameter by decapsulating using the RPCUtils
		   // - lookup the method to be invoked
		   // - invoke the method and pass the param
		   // - encapsulate return value 
		   // - send back the message containing the RPC reply

            Debug.println("attempting to receive message");

            // wait until read
            requestmsg = connection.receive();
            byte[] requestData = requestmsg.getData();

            Debug.println("received request, " + Arrays.toString(requestData));

            rpcid = requestData[0];
            byte[] param = RPCUtils.decapsulate(requestData);

            // handle & send response
            RPCRemoteImpl service = services.get(rpcid);
            if (service != null) {
                responseData = service.invoke(param);
                replymsg = new Message(responseData);
            } else {
                // Handle unknown RPC ID
                responseData = RPCUtils.marshallInteger(-1); // Example error code
                replymsg = new Message(responseData);
            }
            connection.send(replymsg);


			// stop the server if it was stop methods that was called
		   if (rpcid == RPCCommon.RPIDSTOP) {
			   stop = true;
		   }
		}
	
	}
	
	// used by server side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
        services.put(rpcid, impl);
	}
	
	public void stop() {

		if (connection != null) {
			connection.close();
		} else {
            Debug.println("RPCServer.stop - connection was null");
		}
		
		if (msgserver != null) {
			msgserver.stop();
		} else {
            Debug.println("RPCServer.stop - msgserver was null");
		}
		
	}
}
