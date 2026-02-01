package no.hvl.dat110.messaging;


import java.net.Socket;

import no.hvl.dat110.TODO;
import utils.Debug;

public class MessagingClient {

	// name/IP address of the messaging server
	private String server;

	// server port on which the messaging server is listening
	private int port;
	
	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	// setup of a messaging connection to a messaging server
	public MessageConnection connect () {
        try{
            return new MessageConnection(new Socket(server, port));
        }catch(Exception ex) {
            Debug.println("Messaging client: " + ex.getMessage());
            ex.printStackTrace();
            throw new UnsupportedOperationException("Error occurred while attempting to connect to server");
        }
	}
}
