package netperf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import fabric.common.net.SubSocket;
import fabric.common.net.SubSocketFactory;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.naming.NameService;

public class Client {

  private static void sleep() throws InterruptedException {
    if (Config.PING_INTERVAL > 0) Thread.sleep(Config.PING_INTERVAL);
  }

  private static long average(long[] data) {
    long sum = 0;
    for (long x : data)
      sum += x;
    return sum / data.length;
  }

  public static void main(String[] args) throws IOException,
      InterruptedException {
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

    // Send pings with interval between each, all on the same sub-socket.
    System.out.println("Measuring latency for single sub-socket with "
        + Config.PING_INTERVAL + " ms between requests...");
    socket = ssf.createSocket(host);
    in = new DataInputStream(socket.getInputStream());
    out = new DataOutputStream(socket.getOutputStream());

    byte[] ping = new byte[Config.PING_SIZE];
    long[] times = new long[Config.NUM_PINGS];

    int tenth = (int) Math.ceil(Config.NUM_PINGS / 10.0);
    for (int i = 0; i < Config.NUM_PINGS; i++) {
      rand.nextBytes(ping);

      if (i != 0) sleep();

      long start = System.nanoTime();
      out.write(ping);
      out.flush();
      in.readFully(ping);
      long end = System.nanoTime();

      times[i] = end - start;
      if (i % tenth == 0)
        System.out.println(Config.PING_SIZE + " bytes from " + host + ": req="
            + (i + 1) + " time=" + Config.formatTimeMS(times[i]));
    }

    System.out.println();

    // Print stats.
    Arrays.sort(times);
    System.out.println("rtt min/avg/max = " + Config.formatTimeMS(times[0])
        + "/" + Config.formatTimeMS(average(times)) + "/"
        + Config.formatTimeMS(times[Config.NUM_PINGS - 1]));
    System.out.println();

    // Flood pings, all on the same sub-socket.
    System.out.println("Measuring latency for single sub-socket while "
        + "flooding requests...");

    times = new long[Config.NUM_FLOOD_PINGS];
    for (int i = 0; i < Config.NUM_FLOOD_PINGS; i++) {
      rand.nextBytes(ping);

      long start = System.nanoTime();
      out.write(ping);
      out.flush();
      in.readFully(ping);
      long end = System.nanoTime();

      times[i] = end - start;
    }

    System.out.println();
    socket.close();

    // Print stats.
    Arrays.sort(times);
    System.out.println("rtt min/avg/max = " + Config.formatTimeMS(times[0])
        + "/" + Config.formatTimeMS(average(times)) + "/"
        + Config.formatTimeMS(times[Config.NUM_FLOOD_PINGS - 1]));
    System.out.println();

    // Send pings with interval between each, each on its own sub-socket.
    System.out.println("Measuring latency with sub-socket set-up & tear-down "
        + "overhead...");
    times = new long[Config.NUM_PINGS];
    for (int i = 0; i < Config.NUM_PINGS; i++) {
      rand.nextBytes(ping);

      if (i != 0) sleep();

      long start = System.nanoTime();

      socket = ssf.createSocket(host);
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());

      out.write(ping);
      out.flush();
      in.readFully(ping);

      socket.close();

      long end = System.nanoTime();

      times[i] = end - start;
      if (i % tenth == 0)
        System.out.println(Config.PING_SIZE + " bytes from " + host + ": req="
            + (i + 1) + " time=" + Config.formatTimeMS(times[i]));
    }

    System.out.println();

    // Print stats.
    Arrays.sort(times);
    System.out.println("rtt min/avg/max = " + Config.formatTimeMS(times[0])
        + "/" + Config.formatTimeMS(average(times)) + "/"
        + Config.formatTimeMS(times[Config.NUM_PINGS - 1]));
    System.out.println();

    // Flood pings, each on its own sub-socket.
    System.out.println("Measuring latency with sub-socket set-up & tear-down "
        + "overhead while flooding requests...");
    times = new long[Config.NUM_FLOOD_PINGS];
    for (int i = 0; i < Config.NUM_FLOOD_PINGS; i++) {
      rand.nextBytes(ping);

      long start = System.nanoTime();

      socket = ssf.createSocket(host);
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());

      out.write(ping);
      out.flush();
      in.readFully(ping);

      socket.close();

      long end = System.nanoTime();

      times[i] = end - start;
    }

    System.out.println();

    // Print stats.
    Arrays.sort(times);
    System.out.println("rtt min/avg/max = " + Config.formatTimeMS(times[0])
        + "/" + Config.formatTimeMS(average(times)) + "/"
        + Config.formatTimeMS(times[Config.NUM_FLOOD_PINGS - 1]));
    System.out.println();

    // Throughput test on single sub-socket.
    throughputTest(ssf.createSocket(host));

    // Throughput test with each packet on its own sub-socket.
    throughputTest(ssf, host);
  }

  private static class Data {
    long start = 0;
    long end = 0;
    long bytesSent = 0;
    boolean run = true;
  }

  private static void throughputTest(final SubSocket socket)
      throws IOException, InterruptedException {
    System.out.println("Running throughput test on single sub-socket ("
        + Config.THROUGHPUT_TEST_LEN + "s)...");

    final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    final Data data = new Data();

    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          Random rand = new Random();
          byte[] packet = new byte[Config.THROUGHPUT_PACKET_SIZE];
          data.start = System.nanoTime();
          while (data.run) {
            rand.nextBytes(packet);
            out.write(packet);
            data.bytesSent += packet.length;
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            out.flush();
          } catch (IOException e) {
          }
          data.end = System.nanoTime();
        }
      }
    };

    thread.start();
    Thread.sleep(Config.THROUGHPUT_TEST_LEN * 1000);
    data.run = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    socket.close();

    System.out.println(Config.formatBytes(data.bytesSent)
        + " sent in "
        + Config.formatTime(data.end - data.start)
        + " ("
        + Config.formatbps(8000000000.0 * data.bytesSent
            / (data.end - data.start)) + ")");
  }

  private static void throughputTest(final SubSocketFactory factory,
      final String host) throws InterruptedException {
    System.out.println("Running throughput test with socket set-up & "
        + "tear-down overhead (" + Config.THROUGHPUT_TEST_LEN + "s)...");

    final Data data = new Data();

    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          Random rand = new Random();
          byte[] packet = new byte[Config.THROUGHPUT_PACKET_SIZE];
          data.start = System.nanoTime();
          while (data.run) {
            final SubSocket socket = factory.createSocket(host);
            final DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());
            rand.nextBytes(packet);
            out.write(packet);
            out.flush();
            socket.close();
            data.bytesSent += packet.length;
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          data.end = System.nanoTime();
        }
      }
    };

    thread.start();
    Thread.sleep(Config.THROUGHPUT_TEST_LEN * 1000);
    data.run = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      // Signal server to stop.
      SubSocket socket = factory.createSocket(host);
      socket.close();
    } catch (IOException e) {
    }

    System.out.println(Config.formatBytes(data.bytesSent)
        + " sent in "
        + Config.formatTime(data.end - data.start)
        + " ("
        + Config.formatbps(8000000000.0 * data.bytesSent
            / (data.end - data.start)) + ")");
  }

}
