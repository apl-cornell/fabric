package fabric.common;

import fabric.common.util.Oid;
import fabric.lang.security.Principal;
import fabric.worker.Worker;

/**
 * A lease that allows both reads and writes.
 */
public class RWLease extends Lease {

  /**
   * Owner of the lease with read-write privileges.
   */
  private final Oid owner;
  
  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   * @param lessees Set of RemoteWorker nodes which should be contacted on an
   * in-term write prepare.
   */
  public RWLease(long expiry, Oid owner) {
    super(expiry);
    this.owner = owner;
  }
  
  /**
   * Constructor with no lessees.
   *
   * @param expiry expiry time, in milliseconds since the epoch.
   */
  public RWLease(long expiry) {
    super(expiry);
    this.owner = null;
  }

  // Deserialization constructor.
  protected RWLease() {
    super(0);
    this.owner = null;
  }

  /**
   * Check if the given principal owns the lease.
   *
   * @param p Principal to be checked.
   */
  public boolean ownedByPrincipal(Principal p) {
    return owner.equals(new Oid(p));
  }

  /**
   * Check if the current worker owns the lease.
   */
  public boolean ownedByCurrentWorker() {
    return ownedByPrincipal(Worker.getWorker().getPrincipal());
  }

  /**
   * @return the owner
   */
  public Oid getOwner() {
    return owner;
  }
}
