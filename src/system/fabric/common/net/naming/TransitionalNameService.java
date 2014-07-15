package fabric.common.net.naming;

import static fabric.common.Logging.NAMING_LOGGER;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import fabric.common.ConfigProperties;
import fabric.common.Resources;
import fabric.common.util.Pair;

public class TransitionalNameService implements NameService {

  private final Map<Pair<String, PortType>, SocketAddress> entries;
  private final DNS dns;

  /**
   * Loads the entries in the name service from properties files in a directory.
   * Scans the provided directory looking for files named *.properties. Creates
   * a name service entry for each file found, with the name given by the
   * filename (e.g foo for foo.properties), and address given by the PortType
   * argument
   *
   * @param portType
   *          the type of port number to read.
   */
  public TransitionalNameService() throws IOException {
    dns = new DNS();

    //
    // load other properties files from the directory
    //
    entries = new HashMap<>();
    File directory = Resources.getFile("etc", "config");
    for (File f : directory.listFiles())
      if (f.getName().endsWith(".properties")) {

        //
        // get the properties
        //
        String name = f.getName();
        name = name.substring(0, name.lastIndexOf('.'));

        ConfigProperties props = new ConfigProperties(name);

        String host = props.hostname;

        for (PortType portType : PortType.values()) {
          int port = portType.getPort(props);
          this.entries.put(new Pair<>(name, portType), new SocketAddress(
              InetAddress.getByName(host), port));
        }
      }

    //
    // log entries
    //
    if (NAMING_LOGGER.isLoggable(Level.FINEST)) {
      // find length so output is pretty
      int size = 0;
      for (Pair<String, PortType> name : entries.keySet()) {
        int length = name.toString().length();
        size = length > size ? length : size;
      }

      // print nicely
      for (Map.Entry<Pair<String, PortType>, SocketAddress> e : entries
          .entrySet())
        NAMING_LOGGER.finest(String.format("name service: %1$" + size
            + "s -> %2$s", e.getKey(), e.getValue()));
    }
  }

  @Override
  public SocketAddress localResolve(String name, PortType portType)
      throws IOException {
    SocketAddress result = entries.get(new Pair<>(name, portType));
    if (result != null) {
      // If the result is a local loopback address, return the any address.
      // Kinda hacky, but makes things easier to test.
      if (result.getAddress().isLoopbackAddress()) {
        return new SocketAddress(InetAddress.getByAddress(new byte[] { 0, 0, 0,
            0 }), result.getPort());
      }

      return result;
    }

    // Fall back on DNS.
    return dns.localResolve(name, portType);
  }

  @Override
  public SocketAddress resolve(String name, PortType portType)
      throws IOException {
    SocketAddress result = entries.get(new Pair<>(name, portType));
    if (result == null) result = dns.resolve(name, portType);

    return result;
  }

}
