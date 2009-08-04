package fabric.client;

import fabric.client.remote.RemoteClient;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;

import java.net.*;
import java.util.*;
import java.security.Principal;
import javax.security.auth.x500.X500Principal;

/**
 * This class represents a name service that maps core hostnames to socket
 * addresses and X500 principals.
 */
public class NameService {
  private static final Map<String, String> aliases;
  private static final Map<String, Integer> clientPorts;
  
  static {
    aliases = new HashMap<String, String>();
    aliases.put("core00", "core00.systems.cs.cornell.edu");
    
    clientPorts = new HashMap<String, Integer>();
    clientPorts.put("client0", 3374);
    clientPorts.put("client1", 3375);
    clientPorts.put("dalek.systems.cs.cornell.edu", 3374);
    clientPorts.put("gaia.systems.cs.cornell.edu", 3386);
    clientPorts.put("bob", 3387);    
    clientPorts.put("alice", 3388);    
    clientPorts.put("calendarapp", 3389);
    clientPorts.put("trantor.u.cs.cornell.edu", 3390);    
  }
  
  /**
   * This saves our fingers during testing.
   */
  static String resolveAlias(String name) {
    String result = aliases.get(name);
    return result == null ? name : result;
  }
  
  /**
   * Maps from client names to their port numbers.
   */
  static int resolveClientPort(String name) {
    if (!clientPorts.containsKey(name)) return 3373;
    return clientPorts.get(name);
  }
  
  /**
   * Returns a list of node addresses for the given host.
   */
  public Pair<List<InetSocketAddress>, Principal> lookup(
      RemoteNode host) throws UnknownHostException {
    if (host instanceof RemoteCore) return lookup((RemoteCore) host);
    if (host instanceof RemoteClient) return lookup((RemoteClient) host);
    throw new InternalError();
  }
  
  /**
   * Returns a list of client node addresses for the given client.
   */
  public Pair<List<InetSocketAddress>, Principal> lookup(
      RemoteClient client) throws UnknownHostException {
    return lookup(client, resolveClientPort(client.name()));
  }
  
  /**
   * Returns a list of core node addresses for the given core.
   */
  public Pair<List<InetSocketAddress>, Principal> lookup(RemoteCore core)
      throws UnknownHostException {
    return lookup(core, 3372);
  }
  
  /**
   * Returns a list of node addresses for the given host.
   */
  private Pair<List<InetSocketAddress>, Principal> lookup(RemoteNode host,
      int port) throws UnknownHostException {
    // Look up the hostname in DNS.
    InetAddress[] ipAddrs;
    try {
      ipAddrs = InetAddress.getAllByName(host.name());
    } catch (UnknownHostException e) {
      // XXX If hostname not found, use localhost.
      ipAddrs = InetAddress.getAllByName("localhost");
    }

    List<InetSocketAddress> socketAddrs =
        new ArrayList<InetSocketAddress>(ipAddrs.length);
    for (InetAddress ip : ipAddrs) {
      // XXX Obtain port number from DNS too?
      socketAddrs.add(new InetSocketAddress(ip, port));
    }

    // XXX Need to obtain X500 principal from DNS too.
    return new Pair<List<InetSocketAddress>, Principal>(socketAddrs,
        new X500Principal("cn=" + host.name()
            + ",ou=Fabric,o=Cornell University,l=Ithaca,st=NY,c=US"));
  }
}
