package netperf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
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

      // Receive and ack pings on a single sub-socket.
      socket = server.accept();
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());

      byte[] pingIn = new byte[Config.PING_SIZE];
      byte[] pingOut = new byte[Config.PING_SIZE];
      rand.nextBytes(pingOut);

      for (int i = 0; i < Config.NUM_PINGS + Config.NUM_FLOOD_PINGS; i++) {
        in.readFully(pingIn);
        out.write(pingOut);
        out.flush();
      }

      socket.close();

      // Receive and ack pings, each on its own sub-socket.
      for (int i = 0; i < Config.NUM_PINGS + Config.NUM_FLOOD_PINGS; i++) {
        socket = server.accept();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        in.readFully(pingIn);
        out.write(pingOut);
        out.flush();

        socket.close();
      }

      throughputTestSingleSocket(server);
      throughputTestMultipleSockets(server);
    }
  }

  private static void throughputTestSingleSocket(SubServerSocket server)
      throws IOException {
    // Throughput test on single sub-socket.
    byte[] megabyte = new byte[1000 * 1000];
    long bytesReceived = 0;
    SubSocket socket = server.accept();
    DataInputStream in = new DataInputStream(socket.getInputStream());
    long start = System.nanoTime();
    try {
      while (true) {
        in.readFully(megabyte);
        bytesReceived += megabyte.length;
      }
    } catch (EOFException e) {
    } catch (IOException e) {
      e.printStackTrace();
    }
    long end = System.nanoTime();
    System.out.println(Config.formatBytes(bytesReceived) + " received in "
        + Config.formatTime(end - start) + " ("
        + Config.formatbps(8000000000.0 * bytesReceived / (end - start)) + ")");

    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void throughputTestMultipleSockets(SubServerSocket server) {
    // Throughput test with each megabyte on its own sub-socket.
    long bytesReceived = 0;
    long start = System.nanoTime();
    byte[] packet = new byte[Config.THROUGHPUT_PACKET_SIZE];
    try {
      while (true) {
        SubSocket socket = server.accept();
        DataInputStream in = new DataInputStream(socket.getInputStream());

        try {
          in.readFully(packet);
          bytesReceived += packet.length;
        } catch (EOFException e) {
          break;
        }

        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    long end = System.nanoTime();
    System.out.println(Config.formatBytes(bytesReceived) + " received in "
        + Config.formatTime(end - start) + " ("
        + Config.formatbps(8000000000.0 * bytesReceived / (end - start)) + ")");
  }

}
