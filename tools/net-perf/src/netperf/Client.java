package netperf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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

  private static DecimalFormat zeroDForm = new DecimalFormat("##0");
  private static DecimalFormat oneDForm = new DecimalFormat("#0.0");
  private static DecimalFormat twoDForm = new DecimalFormat("0.00");
  private static DecimalFormat threeDForm = new DecimalFormat("0.000");

  private static String formatTime(long ns) {
    double ms = ns / 1000000.0;

    if (ms < 1) return threeDForm.format(ms) + "ms";

    if (ms < 10) return twoDForm.format(ms) + "ms";

    if (ms < 100) return oneDForm.format(ms) + "ms";

    return zeroDForm.format(ms) + "ms";
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
    SubSocketFactory ssf = new SubSocketFactory(protocol, dns);

    // Send pings, all on the same sub-socket.
    System.out.println("Measuring latency for single sub-socket...");
    SubSocket socket = ssf.createSocket(host);
    DataInputStream in =
        new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    DataOutputStream out =
        new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

    byte[] ping = new byte[Config.PING_SIZE];
    long[] times = new long[Config.NUM_PINGS];

    for (int i = 0; i < Config.NUM_PINGS; i++) {
      rand.nextBytes(ping);

      if (i != 0) sleep();

      long start = System.nanoTime();
      out.write(ping);
      out.flush();
      in.readFully(ping);
      long end = System.nanoTime();

      times[i] = end - start;
      System.out.println(Config.PING_SIZE + " bytes from " + host + ": req="
          + (i + 1) + " time=" + formatTime(times[i]));
    }

    System.out.println();
    in.close();
    out.close();

    // Print stats.
    Arrays.sort(times);
    System.out.println("rtt min/avg/max = " + formatTime(times[0]) + "/"
        + formatTime(average(times)) + "/"
        + formatTime(times[Config.NUM_PINGS - 1]));
    System.out.println();

    // Send pings, each on its own sub-socket.
    System.out.println("Measuring latency with sub-socket set-up & tear-down "
        + "overhead...");
    for (int i = 0; i < Config.NUM_PINGS; i++) {
      rand.nextBytes(ping);

      if (i != 0) sleep();

      long start = System.nanoTime();

      socket = ssf.createSocket(host);
      in =
          new DataInputStream(new BufferedInputStream(socket.getInputStream()));
      out =
          new DataOutputStream(new BufferedOutputStream(
              socket.getOutputStream()));

      out.write(ping);
      out.flush();
      in.readFully(ping);

      in.close();
      out.close();

      long end = System.nanoTime();

      times[i] = end - start;
      System.out.println(Config.PING_SIZE + " bytes from " + host + ": req="
          + (i + 1) + " time=" + formatTime(times[i]));
    }

    System.out.println();

    // Print stats.
    Arrays.sort(times);
    System.out.println("rtt min/avg/max = " + formatTime(times[0]) + "/"
        + formatTime(average(times)) + "/"
        + formatTime(times[Config.NUM_PINGS - 1]));
    System.out.println();

    // Throughput test.
    System.out.println("Running throughput test (" + Config.THROUGHPUT_TEST_LEN
        + "s)...");
    throughputTest(ssf.createSocket(host));

    socket.close();
  }

  private static class Data {
    long bytesWritten = 0;
    boolean run = true;
  }

  private static void throughputTest(final SubSocket socket)
      throws IOException, InterruptedException {
    final DataInputStream in =
        new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    final DataOutputStream out =
        new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    final Data data = new Data();

    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          Random rand = new Random();
          byte[] megabyte = new byte[1000 * 1000];
          while (data.run) {
            rand.nextBytes(megabyte);
            out.write(megabyte);
            data.bytesWritten += megabyte.length;
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    thread.start();
    Thread.sleep(Config.THROUGHPUT_TEST_LEN * 1000);
    data.run = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    in.close();
    out.close();

    System.out.println(data.bytesWritten);
  }

}
