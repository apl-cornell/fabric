package fabric.common.net.naming;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import fabric.common.ConfigProperties;
import fabric.common.Logging;

public class DNS implements NameService {

  private final Map<PortType, Integer> defaultPorts;

  public DNS() {
    ConfigProperties defaults = ConfigProperties.getDefaults();
    this.defaultPorts = new HashMap<>();
    for (PortType portType : PortType.values()) {
      this.defaultPorts.put(portType, portType.getPort(defaults));
    }
  }

  @Override
  public SocketAddress resolve(String name, PortType portType)
      throws IOException {
    return new SocketAddress(InetAddress.getByName(name), getPort(name,
        portType));
  }

  @Override
  public SocketAddress localResolve(String name, PortType portType)
      throws IOException {
    return new SocketAddress(
        InetAddress.getByAddress(new byte[] { 0, 0, 0, 0 }), getPort(name,
            portType));
  }

  private int getPort(String name, PortType portType) {
    try {
      // Look up the port number in DNS.
      Hashtable<String, String> env = new Hashtable<>(1);
      env.put("java.naming.factory.initial",
          "com.sun.jndi.dns.DnsContextFactory");
      DirContext dirContext = new InitialDirContext(env);
      Attributes attrs = dirContext.getAttributes(name, new String[] { "TXT" });
      Attribute txt = attrs.get("TXT");
      if (txt != null) {
        NamingEnumeration<String> e = (NamingEnumeration<String>) txt.getAll();
        while (e.hasMore()) {
          String txtRecord = e.next();
          // Only look at TXT records that begin with '"fabric-worker: ' or similar.
          String header = "\"" + portType.dnsKey() + ": ";
          if (txtRecord.startsWith(header)) {
            // Strip off header and trailing quotation mark.
            String portNum = txtRecord.substring(header.length());
            portNum = portNum.substring(0, portNum.length() - 1);
            try {
              return Integer.parseInt(portNum);
            } catch (NumberFormatException ex) {
            }
          }
        }
      }
    } catch (NamingException e) {
      Logging.NAMING_LOGGER.log(Level.WARNING,
          "NamingException while looking up port info from DNS. Returning "
              + "default port number.", e);
    }

    return defaultPorts.get(portType);
  }
}
