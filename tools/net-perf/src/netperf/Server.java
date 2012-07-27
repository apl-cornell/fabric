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
      // Receive an initial connection to flush out the initial setup overhead.
      SubSocket socket = server.accept();
      DataInputStream in =
          new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      DataOutputStream out =
          new DataOutputStream(new BufferedOutputStream(
              socket.getOutputStream()));
      in.readInt();
      out.writeInt(0);
      out.flush();
      in.close();
      out.close();

      // Receive and ack pings on a single sub-socket.
      socket = server.accept();
      in =
          new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      out =
          new DataOutputStream(new BufferedOutputStream(
              socket.getOutputStream()));

      byte[] pingIn = new byte[Config.PING_SIZE];
      byte[] pingOut = new byte[Config.PING_SIZE];
      rand.nextBytes(pingOut);

      for (int i = 0; i < Config.NUM_PINGS + Config.NUM_FLOOD_PINGS; i++) {
        in.readFully(pingIn);
        out.write(pingOut);
        out.flush();
      }

      in.close();
      out.close();

      // Receive and ack pings, each on its own sub-socket.
      for (int i = 0; i < Config.NUM_PINGS + Config.NUM_FLOOD_PINGS; i++) {
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

      throughputTestSingleSocket(server);
//      throughputTestMultipleSockets(server);

      try {
        socket.close();
      } catch (IOException e) {
      }
    }
  }

  private static void throughputTestSingleSocket(SubServerSocket server)
      throws IOException {
    // Throughput test on single sub-socket.
    byte[] megabyte = new byte[1000 * 1000];
    long bytesReceived = 0;
    SubSocket socket = server.accept();
    DataInputStream in =
        new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    DataOutputStream out =
        new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    long start = System.nanoTime();
    try {
      while (true) {
        in.readFully(megabyte);
        bytesReceived += megabyte.length;
      }
    } catch (IOException e) {
    }
    long end = System.nanoTime();
    System.out.println(Config.formatBytes(bytesReceived) + " received in "
        + Config.formatTime(end - start) + " ("
        + Config.formatbps(8000000000.0 * bytesReceived / (end - start)) + ")");

    try {
      in.close();
    } catch (IOException e) {
    }

    try {
      out.close();
    } catch (IOException e) {
    }
  }

  @SuppressWarnings("unused")
  private static void throughputTestMultipleSockets(SubServerSocket server) {
    // Throughput test with each megabyte on its own sub-socket.
    long bytesReceived = 0;
    long start = System.nanoTime();
    byte[] megabyte = new byte[1000 * 1000];
    try {
      while (true) {
        SubSocket socket = server.accept();
        DataInputStream in =
            new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
        DataOutputStream out =
            new DataOutputStream(new BufferedOutputStream(
                socket.getOutputStream()));
        in.readFully(megabyte);
        bytesReceived += megabyte.length;

        try {
          in.close();
        } catch (IOException e) {
        }

        try {
          out.close();
        } catch (IOException e) {
        }
      }
    } catch (IOException e) {
    }
    long end = System.nanoTime();
    System.out.println(Config.formatBytes(bytesReceived) + " received in "
        + Config.formatTime(end - start) + " ("
        + Config.formatbps(8000000000.0 * bytesReceived / (end - start)) + ")");
  }

}
