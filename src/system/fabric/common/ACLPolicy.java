package fabric.common;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * An instance of this class represents a policy implemented as an access
 * control list.
 */
public final class ACLPolicy implements Policy {
  // The set of allowed readers. If null, then everyone is allowed to read.
  private Set<Principal> readers;

  // The set of allowed writers. If null, then everyone is allowed to write.
  private Set<Principal> writers;
  
  public static final ACLPolicy DEFAULT = new ACLPolicy();

  /**
   * Creates a default ACLPolicy. Under the default policy, no protection is
   * provided.
   */
  private ACLPolicy() {}

  /**
   * Creates a policy under which the given principal is the sole reader and
   * writer.
   */
  public ACLPolicy(Principal p) {
    this.readers = new HashSet<Principal>();
    this.readers.add(p);
    this.writers = new HashSet<Principal>();
    this.writers.add(p);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.common.Policy#canRead(java.security.Principal)
   */
  public boolean canRead(Principal p) {
    return readers == null || readers.contains(p);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.common.Policy#canWrite(java.security.Principal)
   */
  public boolean canWrite(Principal p) {
    return writers == null || writers.contains(p);
  }

}
