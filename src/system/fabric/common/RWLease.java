package fabric.common;

import java.io.Serializable;

import fabric.lang.security.Principal;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * A lease that allows both reads and writes.
 */
public class RWLease extends Lease implements Serializable {

  /**
   * (Inlined) owner of the lease with read-write privileges.
   *
   * Store and onum for the owner's principal.
   */
  private final Store ownerStore;
  private final long ownerOnum;

  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   * @param ownerStore store of the worker principal which owns the lease.
   * @param ownerOnum onum of the worker principal which owns the lease.
   */
  public RWLease(long expiry, Store ownerStore, long ownerOnum) {
    super(expiry);
    this.ownerStore = ownerStore;
    this.ownerOnum = ownerOnum;
  }

  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   * @param owner the worker principal which owns the lease.
   */
  public RWLease(long expiry, Principal owner) {
    super(expiry);
    this.ownerStore = owner.$getStore();
    this.ownerOnum = owner.$getOnum();
  }

  /**
   * Constructor with no lessees.
   *
   * @param expiry expiry time, in milliseconds since the epoch.
   */
  public RWLease(long expiry) {
    super(expiry);
    this.ownerStore = null;
    this.ownerOnum = 0;
  }

  // Deserialization constructor.
  protected RWLease() {
    super(0);
    this.ownerStore = null;
    this.ownerOnum = 0;
  }

  /**
   * Check if the given principal owns the lease.
   *
   * @param p Principal to be checked.
   */
  public boolean ownedByPrincipal(Principal p) {
    if (ownerStore == null) return false;
    return ownerStore.equals(p.$getStore()) && ownerOnum == p.$getOnum();
  }

  /**
   * Check if the current worker owns the lease.
   */
  public boolean ownedByCurrentWorker() {
    return ownedByPrincipal(Worker.getWorker().getPrincipal());
  }

  /**
   * @return the owner store
   */
  public Store getOwnerStore() {
    return ownerStore;
  }

  /**
   * @return the owner store
   */
  public long getOwnerOnum() {
    return ownerOnum;
  }
}
