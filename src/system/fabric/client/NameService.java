package fabric.client;

import fabric.common.Pair;

import java.net.*;
import java.util.*;
import java.security.Principal;
import javax.security.auth.x500.X500Principal;

/**
 * This class represents a name service that maps core hostnames to socket
 * addresses and X500 principals.
 */
public class NameService {
  /**
   * Returns a list of core node addresses for the given core.
   */
  public Pair<List<InetSocketAddress>, Principal> lookupCore(RemoteCore core)
      throws UnknownHostException {
    // Look up the core's hostname in DNS.
    InetAddress[] ipAddrs;
    try {
      ipAddrs = InetAddress.getAllByName(core.name);
    } catch (UnknownHostException e) {
      // XXX If hostname not found, use localhost.
      ipAddrs = InetAddress.getAllByName("localhost");
    }

    List<InetSocketAddress> socketAddrs =
        new ArrayList<InetSocketAddress>(ipAddrs.length);
    for (InetAddress ip : ipAddrs) {System.out.println(ip);
      // XXX Obtain port number from DNS too?
      socketAddrs.add(new InetSocketAddress(ip, 3372));
    }

    // XXX Need to obtain X500 principal from DNS too.
    return new Pair<List<InetSocketAddress>, Principal>(socketAddrs,
        new X500Principal("cn=" + core.name
            + ",ou=Fabric,o=Cornell University,l=Ithaca,st=NY,c=US"));
  }
}
