package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import no.hvl.dat110.TODO;
import utils.Debug;


public class MessageConnection {
    public boolean quiet = true;

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection



	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {
        if(!quiet) Debug.beginBlock("MessageConnection/send");

        try {
            // encapsulate data into a segment and write to the output stream
            byte[] data = message.getData();
            int n = data.length;

            if(n > 255)
                throw new IOException("Message too long");

            if(!quiet) Debug.println(SegmentUtils.payloadToString(data));

            // check for message length exceeding 255 bytes
            outStream.writeByte(n);
            outStream.write(data, 0, data.length);
        }catch (IOException ex) {
            Debug.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }

        if(!quiet) Debug.endBlock();
	}

	public Message receive() {
        if(!quiet) Debug.beginBlock("MessageConnection::receive");
        byte[] data = null;

		// read a segment from the input stream and decapsulate data into a Message
        try {
            if(!quiet) Debug.println("- Read segment length status");
            int n = inStream.readUnsignedByte(); // ← length byte

            if(!quiet) Debug.printf("- Try read payload (%d bytes)\n", n);

            data = new byte[n];
            inStream.readFully(data);

            if(!quiet) Debug.printf("- OK: %s\n", SegmentUtils.payloadToString(data));
        }catch (IOException ex) {
            Debug.println("Connection: " + ex.getMessage());
            ex.printStackTrace();

            data = new byte[0];
        }

        if(!quiet) Debug.endBlock();

        // return empty message in case of error
        return new Message(data);
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}