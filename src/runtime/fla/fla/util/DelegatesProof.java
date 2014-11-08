package fla.util;

import fla.principals.Principal;

/**
 * Represents the use of an axiom that a principal has delegated authority to
 * another principal.
 */
public final class DelegatesProof extends ActsForProof {
  public DelegatesProof(Principal inferior, Principal superior) {
    super(superior, inferior);
  }
}
