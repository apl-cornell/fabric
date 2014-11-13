package fla.util;

import fla.principals.Principal;

/**
 * Represents use of the reflexivity axiom.
 */
public final class ReflexiveProof<Superior extends Principal, Inferior extends Principal>
    extends ActsForProof<Superior, Inferior> {
  public ReflexiveProof(ActsForQuery<Superior, Inferior> query) {
    super(query.superior, query.inferior, query.maxUsableLabel,
        query.accessPolicy);
  }
}
