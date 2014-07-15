package fabric.common;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

/**
 * A table of <code>SSLSocketFactory</code>s.
 */
public class SSLSocketFactoryTable {
  private static Map<String, SSLSocketFactory> table = new HashMap<>();

  public static void register(String hostname, SSLSocketFactory factory) {
    table.put(hostname, factory);
  }

  public static SSLSocketFactory get(String hostname) {
    return table.get(hostname);
  }
}
