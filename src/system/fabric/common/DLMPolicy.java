package fabric.common;

import java.security.Principal;
import java.util.*;

/**
 * An instance of this class represents a policy in the decentralised label
 * model.
 */
public final class DLMPolicy implements Policy {
  private Map<Principal, Set<Principal>> confidentiality;
  private Map<Principal, Set<Principal>> integrity;
  private ACLPolicy effectivePolicy;

  /**
   * Creates a default DLMPolicy. Under the default policy, no protection is
   * provided.
   */
  public DLMPolicy() {
    this.effectivePolicy = ACLPolicy.DEFAULT;
  }

  /**
   * Creates a policy under which the given principal is the sole reader and
   * writer.
   */
  public DLMPolicy(Principal p) {
    this.confidentiality = new HashMap<Principal, Set<Principal>>();
    this.confidentiality.put(p, new HashSet<Principal>());
    this.integrity = new HashMap<Principal, Set<Principal>>();
    this.integrity.put(p, new HashSet<Principal>());
    this.effectivePolicy = new ACLPolicy(p);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.common.Policy#canRead(java.security.Principal)
   */
  public boolean canRead(Principal p) {
    return effectivePolicy.canRead(p);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.common.Policy#canWrite(java.security.Principal)
   */
  public boolean canWrite(Principal p) {
    return effectivePolicy.canWrite(p);
  }
}
