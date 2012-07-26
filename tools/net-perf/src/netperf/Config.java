package netperf;

import java.text.DecimalFormat;

public class Config {
  public static final int NUM_PINGS = 5;
  public static final int PING_SIZE = 64;
  public static final int PING_INTERVAL = 500;
  public static final int THROUGHPUT_TEST_LEN = 10;

  private static DecimalFormat zeroDForm = new DecimalFormat("##0");
  private static DecimalFormat oneDForm = new DecimalFormat("#0.0");
  private static DecimalFormat twoDForm = new DecimalFormat("0.00");
  private static DecimalFormat threeDForm = new DecimalFormat("0.000");

  public static String formatTimeMS(long ns) {
    double ms = ns / 1000000.0;
    String result = formatThreeSig(ms, "ms");
    if (result != null) return result;
    return zeroDForm.format(ms) + "ms";
  }

  private static String formatThreeSig(double ms, String unit) {
    if (ms < 1) return threeDForm.format(ms) + " " + unit;

    if (ms < 10) return twoDForm.format(ms) + " " + unit;

    if (ms < 100) return oneDForm.format(ms) + " " + unit;

    if (ms < 1000) return zeroDForm.format(ms) + " " + unit;

    return null;
  }

  public static String formatTime(long ns) {
    if (ns < 1000) return ns + "ns";

    double us = ns / 1000.0;
    String result = formatThreeSig(us, "μs");
    if (result != null) return result;

    double ms = us / 1000.0;
    result = formatThreeSig(us, "ms");
    if (result != null) return result;

    double s = ms / 1000.0;
    result = formatThreeSig(s, "s");
    if (result != null) return result;

    return zeroDForm.format(s) + "s";
  }

  public static String formatBytes(long bytes) {
    return formatSI(bytes) + "Bytes";
  }

  public static String formatbps(double bps) {
    return formatSI(bps) + "bits/s";
  }

  private static String formatSI(long base) {
    if (base < 1000) return base + "";
    return formatSI((double) base);
  }

  private static String formatSI(double base) {
    String result = formatThreeSig(base, "");
    if (result != null) return result;

    double k = base / 1000.0;
    result = formatThreeSig(k, "k");
    if (result != null) return result;

    double M = k / 1000.0;
    result = formatThreeSig(M, "M");
    if (result != null) return result;

    double G = M / 1000.0;
    result = formatThreeSig(G, "G");
    if (result != null) return result;

    double T = G / 1000.0;
    result = formatThreeSig(T, "T");
    if (result != null) return result;

    double P = T / 1000.0;
    result = formatThreeSig(P, "P");
    if (result != null) return result;

    double E = P / 1000.0;
    result = formatThreeSig(E, "E");
    if (result != null) return result;

    double Z = E / 1000.0;
    result = formatThreeSig(Z, "Z");
    if (result != null) return result;

    double Y = Z / 1000.0;
    result = formatThreeSig(Y, "Y");
    if (result != null) return result;

    return zeroDForm.format(Y) + "Y";
  }
}
