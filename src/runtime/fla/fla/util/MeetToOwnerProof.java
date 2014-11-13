package fla.util;

import fla.principals.OwnedPrincipal;
import fla.principals.Principal;

/**
 * Represents use of the rule a ∨ b ≽ c => a:b ≽ c.
 */
public final class MeetToOwnerProof<Meet extends Principal, Inferior extends Principal>
    extends ActsForProof<OwnedPrincipal, Inferior> {
  /**
   * A proof of {@code superior.owner() ∨ superior.projection()} ≽
   * {@code inferior}.
   */
  public final ActsForProof<Meet, Inferior> proof;

  public MeetToOwnerProof(OwnedPrincipal superior,
      ActsForProof<Meet, Inferior> proof) {
    super(superior, proof.inferior, proof.label, proof.accessPolicy);
    this.proof = proof;
  }
}
