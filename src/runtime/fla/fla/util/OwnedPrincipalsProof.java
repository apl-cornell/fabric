package fla.util;

import fla.principals.OwnedPrincipal;
import fla.principals.Principal;

/**
 * Represents use of the rule a ≽ c and a∨b ≽ c∨d => a:b ≽ c:d.
 */
public final class OwnedPrincipalsProof extends
    ActsForProof<OwnedPrincipal, OwnedPrincipal> {
  /**
   * A proof of {@code superior.owner()} ≽ {@code inferior.owner()}.
   */
  public final ActsForProof<Principal, Principal> ownersProof;

  /**
   * A proof of {@code superior.owner()} ∨ {@code superior.projection()} ≽
   * {@code inferior.owner()} ∨ {@code inferior.projection()}.
   */
  public final ActsForProof<Principal, Principal> projectionProof;

  public OwnedPrincipalsProof(OwnedPrincipal superior, OwnedPrincipal inferior,
      ActsForProof<Principal, Principal> ownersProof,
      ActsForProof<Principal, Principal> projectionProof) {
    super(superior, inferior);
    this.ownersProof = ownersProof;
    this.projectionProof = projectionProof;
  }
}
