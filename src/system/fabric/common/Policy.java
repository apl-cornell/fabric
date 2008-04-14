package fabric.common;

import java.security.Principal;

/**
 * This represents the security (confidentiality, integrity, availability) and
 * persistence policy for a Fabric object.
 */
public interface Policy {
  // TODO add availability, persistence
  
  /**
   * Determines whether a principal is permitted to read data protected by this
   * policy.
   * 
   * @return Returns true iff the principal is a permitted reader under the
   *         confidentiality policy.
   */
  boolean canRead(Principal p);

  /**
   * Determines whether a principal is permitted to write data protected by this
   * policy.
   * 
   * @return Returns true iff the principal is a permitted writer under the
   *         integrity policy.
   */
  boolean canWrite(Principal p);
}
