package fabric.net;

import fabric.worker.RemoteCore;
import fabric.worker.remote.RemoteWorker;
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
  private static final Map<String, Integer> workerPorts;
  
  static {
    aliases = new HashMap<String, String>();
    aliases.put("core00", "core00.systems.cs.cornell.edu");
    
    workerPorts = new HashMap<String, Integer>();
    workerPorts.put("worker0", 3374);
    workerPorts.put("worker1", 3375);
    workerPorts.put("dalek.systems.cs.cornell.edu", 3374);
    workerPorts.put("gaia.systems.cs.cornell.edu", 3386);
    workerPorts.put("bob", 3387);    
    workerPorts.put("alice", 3388);    
    workerPorts.put("calendarapp", 3389);
    workerPorts.put("trantor.u.cs.cornell.edu", 3390);    
  }
  
  /**
   * This saves our fingers during testing.
   */
  static public String resolveAlias(String name) {
    String result = aliases.get(name);
    return result == null ? name : result;
  }
  
  /**
   * Maps from worker names to their port numbers.
   */
  static int resolveWorkerPort(String name) {
    if (!workerPorts.containsKey(name)) return 3373;
    return workerPorts.get(name);
  }
  
  /**
   * Returns a list of node addresses for the given host.
   */
  public Pair<List<InetSocketAddress>, Principal> lookup(
      RemoteNode host) throws UnknownHostException {
    if (host instanceof RemoteCore) return lookup((RemoteCore) host);
    if (host instanceof RemoteWorker) return lookup((RemoteWorker) host);
    throw new InternalError();
  }
  
  /**
   * Returns a list of worker node addresses for the given worker.
   */
  public Pair<List<InetSocketAddress>, Principal> lookup(
      RemoteWorker worker) throws UnknownHostException {
    return lookup(worker, resolveWorkerPort(worker.name()));
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
