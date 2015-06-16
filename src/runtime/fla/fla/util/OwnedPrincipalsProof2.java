package fla.util;

import fla.principals.OwnedPrincipal;
import fla.principals.Principal;

/**
 * Represents use of the rule a ≽ c and b ≽ c:d => a:b ≽ c:d.
 */
public final class OwnedPrincipalsProof2<Inferior extends Principal> extends
    ActsForProof<OwnedPrincipal, Inferior> {
  /**
   * A proof of {@code superior.owner()} ≽ {@code inferior.owner()}.
   */
  public final ActsForProof<Principal, Principal> ownersProof;

  /**
   * A proof of {@code superior.projection()} ≽ {@code inferior}.
   */
  public final ActsForProof<Principal, Inferior> projectionProof;

  public OwnedPrincipalsProof2(OwnedPrincipal superior, Inferior inferior,
      ActsForProof<Principal, Principal> ownersProof,
      ActsForProof<Principal, Inferior> projectionProof) {
    super(superior, inferior, ownersProof.label, ownersProof.accessPolicy);
    this.ownersProof = ownersProof;
    this.projectionProof = projectionProof;

    assertEquals(ownersProof.label, projectionProof.label);
  }
}
