package fabric.common;

import fabric.worker.remote.RemoteWorker;
import java.util.Collections;
import java.util.Set;

/**
 * A lease that allows both reads and writes.
 */
public class RWLease extends Lease {
  
  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   * @param lessees Set of RemoteWorker nodes which should be contacted on an
   * in-term write prepare.
   */
  public RWLease(long expiry, Set<RemoteWorker> lessees) {
    super(expiry, true, lessees);
  }
  
  /**
   * Constructor with no lessees.
   *
   * @param expiry expiry time, in milliseconds since the epoch.
   */
  public RWLease(long expiry) {
    super(expiry, true, Collections.<RemoteWorker>emptySet());
  }

  // Deserialization constructor.
  protected RWLease() {
    super(0, true, Collections.<RemoteWorker>emptySet());
  }
}
