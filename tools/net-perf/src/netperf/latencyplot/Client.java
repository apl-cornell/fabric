package netperf.latencyplot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import netperf.DNS;
import fabric.common.net.SubSocket;
import fabric.common.net.SubSocketFactory;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.NameService;

public class Client {

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: Client hostname");
      return;
    }

    final String host = args[0];
    final Random rand = new Random();
    Protocol protocol = new HandshakeUnauthenticated();
    NameService dns = new DNS();
    SubSocketFactory ssf = new SubSocketFactory(protocol, dns, null);

    // Set up a first sub-socket to flush out the initial setup overhead.
    SubSocket socket = ssf.createSocket(host);
    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    out.writeInt(0);
    out.flush();
    in.readInt();
    socket.close();

    socket = ssf.createSocket(host);
    in = new DataInputStream(socket.getInputStream());
    out = new DataOutputStream(socket.getOutputStream());

    int step = (int) Math.ceil(Config.MAX_PACKET_SIZE / 10000.0);
    for (int i = 1; i < Config.MAX_PACKET_SIZE; i += step) {
      byte[] ping = new byte[i];
      rand.nextBytes(ping);
      long start = System.nanoTime();
      out.write(ping);
      out.flush();
      in.readFully(ping);
      long end = System.nanoTime();
      System.out.println(i + "," + (end - start));
    }
  }

}
