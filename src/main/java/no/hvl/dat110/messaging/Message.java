package no.hvl.dat110.messaging;

import no.hvl.dat110.TODO;

import java.util.Arrays;

public class Message {

	// the up to 127 bytes of data (payload) that a message can hold
	private byte[] data;

	// construction a Message with the data provided
	public Message(byte[] data) {
        if (data == null || data.length > 127) {
            throw new IllegalArgumentException("Data must be non-null and up to 127 bytes long");
        }

		this.data = Arrays.copyOf(data, data.length);
	}

	public byte[] getData() {
		return this.data; 
	}

}
