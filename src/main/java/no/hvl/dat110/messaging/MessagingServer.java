package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import no.hvl.dat110.TODO;

public class MessagingServer {

	// server-side socket for accepting incoming TCP connections
	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {
		try {
            System.out.println("Messaging server: starting, listening on port " + port);
			this.welcomeSocket = new ServerSocket(port);
		} catch (IOException ex) {
			System.out.println("Messaging server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// accept an incoming connection from a client
	public MessageConnection accept() {
        try {
            return new MessageConnection(welcomeSocket.accept());
        }catch(IOException ex) {
            System.out.println("Messaging server: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
	}

	public void stop() {
		if (welcomeSocket != null) {
			try {
                System.out.println("Messaging server: closing, listened on port " + welcomeSocket.getLocalPort());
				welcomeSocket.close();
			} catch (IOException ex) {
				System.out.println("Messaging server: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}
