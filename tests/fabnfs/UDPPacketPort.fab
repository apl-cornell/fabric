package fabnfs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

// Create the UDP port and get packets from it
class UDPPacketPort extends java.lang.Object  {
    byte buf[];
    int  bufLen;

    DatagramSocket input;
    DatagramSocket output;
    int portNumber;

    // debugging flag, shared amoug all instances of this class
    static boolean debug = false;
    
    UDPPacketPort(int port) {
	portNumber = port;
	InitializePort();
	
	// create the buffer to hold incoming packet data
	bufLen = 10240; // make sure it is big enough for an 8K write packet
	buf = null;
    };

    public void InitializePort() {
	try {
	    if (portNumber > 0)
		input = new DatagramSocket(portNumber);
	    else
		input = new DatagramSocket(); // let system assign port
	} catch(SocketException s) {
	    System.out.print("Couldn't create UDP input socket for port "
			     + portNumber + "\n");
	    return;
	};
    };

    public int Port() {
	return input.getLocalPort();
    };
    
    // Collect the next packet from the network
    public DatagramPacket GetPacket(byte [] buf) {
	DatagramPacket packet;
	try {
	    if (debug) 
		System.out.print("Waiting on socket for packet\n");
	    packet = new DatagramPacket(buf, buf.length);
	    input.receive(packet);
	} catch(IOException e) {
	    System.out.print("Couldn't get packet off of socket.\n");
	    return null;
	}
	if (debug) 
	    System.out.print("Got a packet size " + packet.getLength() +
			     "\n");

	return packet;
    };

    public void SendPacket(InetAddress dest, int port, XDRPacket p) {
	try {
	    DatagramPacket dp = new DatagramPacket(p.Data(), p.Length(),
						   dest, port);
	    input.send(dp);
	} catch (IOException e) {
	    System.err.print("Couldn't send reply back to " + dest + "\n");
	}
    };
};
