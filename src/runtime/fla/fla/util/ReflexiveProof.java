package fla.util;

import fla.principals.Principal;

/**
 * Represents use of the reflexivity axiom.
 */
public final class ReflexiveProof<Superior extends Principal, Inferior extends Principal>
    extends ActsForProof<Superior, Inferior> {
  public ReflexiveProof(Superior p, Inferior q) {
    super(p, q);
  }
}
