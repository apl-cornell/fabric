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

public class DefaultNameService implements NameService {

  public enum PortType {
    WORKER {
      @Override
      int getPort(ConfigProperties p) {
        return p.workerPort;
      }
    },
    STORE {
      @Override
      int getPort(ConfigProperties p) {
        return p.storePort;
      }
    },
    ;

    abstract int getPort(ConfigProperties p);
  }

  private Map<String, SocketAddress> entries;
  private SocketAddress defaultAddr;

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
  public DefaultNameService(PortType portType) throws IOException {
    ConfigProperties defs = ConfigProperties.getDefaults();
    this.defaultAddr = new SocketAddress(null, portType.getPort(defs));

    //
    // load other properties files from the directory
    //
    entries = new HashMap<String, SocketAddress>();
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
        int port = portType.getPort(props);

        this.entries.put(name, new SocketAddress(InetAddress.getByName(host),
            port));
      }

    //
    // log entries
    //
    if (NAMING_LOGGER.isLoggable(Level.FINEST)) {
      // find length so output is pretty
      int size = 0;
      for (String name : entries.keySet())
        size = name.length() > size ? name.length() : size;

      // print nicely
      for (Map.Entry<String, SocketAddress> e : entries.entrySet())
        NAMING_LOGGER.finest(String.format("name service: %1$" + size
            + "s -> %2$s", e.getKey(), e.getValue()));
    }
  }

  @Override
  public SocketAddress localResolve(String name) {
    SocketAddress result = entries.get(name);
    if (result == null) result = defaultAddr;

    return result;
  }

  @Override
  public SocketAddress resolve(String name) {
    SocketAddress result = entries.get(name);
    if (result == null) result = defaultAddr;

    return result;
  }

}
