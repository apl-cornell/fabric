package netperf.latencyplot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import netperf.DNS;
import fabric.common.net.SubServerSocket;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.net.SubSocket;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.NameService;

public class Server {

  public static void main(String[] args) throws IOException {
    final Random rand = new Random();
    Protocol protocol = new HandshakeUnauthenticated();
    NameService dns = new DNS();
    SubServerSocketFactory sssf =
        new SubServerSocketFactory(protocol, dns, null);
    SubServerSocket server = sssf.createServerSocket("localhost");

    while (true) {
      // Receive an initial connection to flush out the initial setup overhead.
      SubSocket socket = server.accept();
      DataInputStream in = new DataInputStream(socket.getInputStream());
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());
      in.readInt();
      out.writeInt(0);
      out.flush();
      socket.close();

      socket = server.accept();
      in =
          new DataInputStream(socket.getInputStream());
      out =
          new DataOutputStream(
              socket.getOutputStream());
      byte[] pingOut = new byte[Config.MAX_PACKET_SIZE];
      rand.nextBytes(pingOut);

      int step = (int) Math.ceil(Config.MAX_PACKET_SIZE / 10000.0);
      for (int i = 1; i < Config.MAX_PACKET_SIZE; i += step) {
        byte[] pingIn = new byte[i];
        in.readFully(pingIn);
        out.write(pingOut, 0, i);
        out.flush();
      }
    }
  }
}
