package fla.util;

import fla.principals.OwnedPrincipal;
import fla.principals.Principal;

/**
 * Represents use of the rule a ≽ c and b ≽ d => a:b ≽ c:d.
 */
public final class OwnedPrincipalsProof1 extends
    ActsForProof<OwnedPrincipal, OwnedPrincipal> {
  /**
   * A proof of {@code superior.owner()} ≽ {@code inferior.owner()}.
   */
  public final ActsForProof<Principal, Principal> ownersProof;

  /**
   * A proof of {@code superior.projection()} ≽
   * {@code inferior.projection()}.
   */
  public final ActsForProof<Principal, Principal> projectionProof;

  public OwnedPrincipalsProof1(OwnedPrincipal superior,
      OwnedPrincipal inferior, ActsForProof<Principal, Principal> ownersProof,
      ActsForProof<Principal, Principal> projectionProof) {
    super(superior, inferior, ownersProof.label, ownersProof.accessPolicy);
    this.ownersProof = ownersProof;
    this.projectionProof = projectionProof;

    assertEquals(ownersProof.label, projectionProof.label);
  }
}
