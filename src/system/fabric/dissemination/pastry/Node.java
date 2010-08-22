package fabric.dissemination.pastry;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Properties;

import fabric.common.util.Pair;

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

    // Load values from dissemConfig into the parameters for the Pastry
    // environment.
    Parameters params = env.getParameters();
    for (Pair<String, String> property : new Pair[] {
        new Pair<String, String>("bootstrap", "localhost:13373"),
        new Pair<String, String>("firewall_test_policy", "never"),
        new Pair<String, String>("nat_search_policy", "never"),
        new Pair<String, String>("pastry_socket_allow_loopback", "true"),
        new Pair<String, String>("replication_interval", "300000"),
        new Pair<String, String>("aggregation_interval", "600000") }) {
      String key = property.first;
      String value =
          dissemConfig.getProperty("fabric.dissemination.pastry." + key);
      if (value == null) value = property.second;
      params.setString(key, value);
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
    synchronized (node) {
      while (!node.isReady() && !node.joinFailed()) {
        try {
          node.wait(500);
        } catch (InterruptedException e) {
        }
      }

      if (node.joinFailed()) {
        throw new IOException("Network error: "
            + "Could not join Pastry network. (" + node.joinFailedReason()
            + ")");
      }
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
