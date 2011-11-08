package fabric.dissemination.pastry;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import rice.environment.Environment;
import rice.environment.params.Parameters;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 * Represents a pastry node with a dissemination process. Creating an instance
 * of this class will set up a pastry node on the local machine, attempt to join
 * a pastry ring, and start a disseminator application associated with the
 * pastry node created.
 */
public class Node {
  // should be in params file?
  private static int DEFAULT_PORT = 13373;

  protected Environment env;
  protected SocketPastryNodeFactory pnf;
  protected PastryNode node;
  protected Disseminator disseminator;

  public Node(Properties dissemConfig) throws IOException {
    env = new Environment();

    // Some default environment values.
    Map<String, String> defaults = new HashMap<String, String>();
    defaults.put("bootstrap", "localhost:13373");
    defaults.put("firewall_test_policy", "never");
    defaults.put("nat_search_policy", "never");
    defaults.put("pastry_socket_allow_loopback", "true");
    defaults.put("replication_interval", "300000");
    defaults.put("aggregation_interval", "600000");
    defaults.put("pastry_protocol_periodicLeafSet_lease_period", "8000");
    
    // Load values from dissemConfig into the parameters for the Pastry
    // environment.
    Parameters params = env.getParameters();
    for (String key : dissemConfig.stringPropertyNames()) {
      if (!key.startsWith("fabric.dissemination.pastry.")) continue;
      
      String value = dissemConfig.getProperty(key);
      key = key.substring("fabric.dissemination.pastry.".length());
      params.setString(key, value);
      defaults.remove(key);
    }
    
    // Load defaults for any keys that weren't specified in dissemConfig.
    for (Map.Entry<String, String> entry : defaults.entrySet()) {
      params.setString(entry.getKey(), entry.getValue());
    }

    String bootstrap = params.getString("bootstrap");
    String[] parts = bootstrap.split(":");
    String bootHost = parts.length > 0 ? parts[0] : null;

    // convert "localhost" into actual host name. needed to work on windows
    if (bootHost.equals("localhost"))
      bootHost = InetAddress.getLocalHost().getHostName();
    int bootPort =
        parts.length == 2 ? Integer.parseInt(parts[1]) : DEFAULT_PORT;
    InetSocketAddress boot = new InetSocketAddress(bootHost, bootPort);

    int port = findFreePort(DEFAULT_PORT);
    NodeIdFactory idf = new RandomNodeIdFactory(env);
    pnf = new SocketPastryNodeFactory(idf, port, env);

    node = pnf.newNode(pnf.getNodeHandle(boot));
    waitForReady(); // waits until the pastry node is actually set up

    disseminator = new Disseminator(node);
  }

  /** Returns the disseminator application of this node. */
  public Disseminator disseminator() {
    return disseminator;
  }

  private void waitForReady() throws IOException {
    int spinCount = 0;
    synchronized (node) {
      while (!node.isReady() && !node.joinFailed()) {
        try {
          node.wait(500);
        } catch (InterruptedException e) {
        }
        if (++spinCount == 2) {
          System.out.println("Waiting for Pastry node to be ready.");
          System.out.println("If this takes too long, consider setting or "
              + "reducing the value of the");
          System.out.println("  fabric.dissemination.pastry.pastry_protocol_"
              + "periodicLeafSet_lease_period");
          System.out.println("configuration parameter.");
        }
        if (spinCount % 20 == 0) {
          System.out.println("Still waiting...");
        }
      }

      if (node.joinFailed()) {
        throw new IOException("Network error: "
            + "Could not join Pastry network. (" + node.joinFailedReason()
            + ")");
      }
    }
    
    if (spinCount >= 2) {
      System.out.println("Pastry node ready.");
    }
  }

  private int findFreePort(int port) {
    while (true) {
      try {
        ServerSocket sock = new ServerSocket();
        sock.bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
        sock.close();
        break;
      } catch (IOException e) {
        port++;
      }
    }

    return port;
  }

  /**
   * Shuts down and destroys this node.
   */
  public void destroy() {
    env.destroy();
  }

}
