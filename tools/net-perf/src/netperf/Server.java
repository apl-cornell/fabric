package netperf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

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
    SubServerSocketFactory sssf = new SubServerSocketFactory(protocol, dns);
    SubServerSocket server = sssf.createServerSocket("localhost");

    while (true) {
      // Receive and ack pings on a single sub-socket.
      SubSocket socket = server.accept();
      DataInputStream in =
          new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      DataOutputStream out =
          new DataOutputStream(new BufferedOutputStream(
              socket.getOutputStream()));

      byte[] pingIn = new byte[Config.PING_SIZE];
      byte[] pingOut = new byte[Config.PING_SIZE];
      rand.nextBytes(pingOut);

      for (int i = 0; i < Config.NUM_PINGS; i++) {
        in.readFully(pingIn);
        out.write(pingOut);
        out.flush();
      }

      in.close();
      out.close();

      // Receive and ack pings, each on its own sub-socket.
      for (int i = 0; i < Config.NUM_PINGS; i++) {
        socket = server.accept();
        in =
            new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
        out =
            new DataOutputStream(new BufferedOutputStream(
                socket.getOutputStream()));

        in.readFully(pingIn);
        out.write(pingOut);
        out.flush();

        in.close();
        out.close();
      }

      // Throughput test.
      byte[] megabyte = new byte[1000 * 1000];
      long bytesWritten = 0;
      socket = server.accept();
      in =
          new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      out =
          new DataOutputStream(new BufferedOutputStream(
              socket.getOutputStream()));
      try {
        while (true) {
          in.readFully(megabyte);
          bytesWritten += megabyte.length;
        }
      } catch (IOException e) {
      }
      System.out.println(bytesWritten);

      try {
        in.close();
      } catch (IOException e) {
      }

      try {
        out.close();
      } catch (IOException e) {
      }

      try {
        socket.close();
      } catch (IOException e) {
      }
    }
  }

}
