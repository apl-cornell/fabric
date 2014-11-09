package fla.util;

import fla.principals.Principal;

/**
 * Represents the use of an axiom that a principal has delegated authority to
 * another principal.
 */
public final class DelegatesProof<Superior extends Principal, Inferior extends Principal>
    extends ActsForProof<Superior, Inferior> {
  public DelegatesProof(Inferior inferior, Superior superior) {
    super(superior, inferior);
  }
}
