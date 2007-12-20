package fabric.client;

import fabric.common.Pair;

import java.net.*;
import java.util.*;
import java.security.Principal;
import javax.security.auth.x500.X500Principal;

/**
 * This class represents a name service that binds core IDs to core node
 * addresses.
 */
public class NameService {
  /**
   * Returns a list of core node addresses for the given core.
   */
  public List<Pair<InetSocketAddress, Principal>> lookupCore(RemoteCore core) {
    // TODO implement a real name service.
    return Collections.singletonList(new Pair<InetSocketAddress, Principal>(
        new InetSocketAddress("localhost", 3372), new X500Principal(
            "cn=core0,ou=Fabric,o=Cornell University,l=Ithaca,st=NY,c=US")));
  }
}
