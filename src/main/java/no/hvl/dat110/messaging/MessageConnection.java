package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import no.hvl.dat110.TODO;
import utils.Debug;


public class MessageConnection {

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
        Debug.printf("MessageConnection/send{\n");
        Debug.indent();

        try {
            // encapsulate data into a segment and write to the output stream
            byte[] data = message.getData();
            int n = data.length;

            if(n > 255)
                throw new IOException("Message too long");

            Debug.printf("MessageConnection/sending data: %s, len: %d\n", Arrays.toString(data), data.length);

            // check for message length exceeding 255 bytes
            outStream.writeByte(n);
            outStream.write(data, 0, data.length);
        }catch (IOException ex) {
            Debug.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }

        Debug.unindent();
        Debug.println("}");
		
		//throw new UnsupportedOperationException(TODO.method());
	}

	public Message receive() {
        Debug.printf("MessageConnection/receive{\n");
        Debug.indent();

		// read a segment from the input stream and decapsulate data into a Message
        try {
            Debug.println("- Read length byte ...");

            int n = inStream.readUnsignedByte(); // ← length byte
            Debug.println("- Read length: " + n);

            Debug.printf("- Attempt read %d bytes\n", n);

            byte[] data = new byte[n];
            inStream.readFully(data);

            Debug.printf("- (#info payload: %s)\n", Arrays.toString(data));

            Debug.unindent();
            Debug.printf("}\n");
            // create message
            return new Message(data);
        }catch (IOException ex) {
            Debug.println("Connection: " + ex.getMessage());
            ex.printStackTrace();
        }

        Debug.unindent();
        Debug.println("}");

        // return empty message in case of error
		return new Message(new byte[0]);
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