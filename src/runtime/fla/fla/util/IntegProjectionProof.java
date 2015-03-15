package fla.util;

import fla.principals.Principal;

/**
 * Represents use of the rule a ≽ b => a← ≽ b←.
 */
public final class IntegProjectionProof extends
ActsForProof<Principal, Principal> {
  /**
   * A proof of {@code superior.base()} ≽ {@code inferior.base()}.
   */
  public final ActsForProof<?, ?> proof;

  public IntegProjectionProof(ActsForProof<?, ?> proof) {
    super(proof.superior.integrity(), proof.inferior.integrity(), proof.label,
        proof.accessPolicy);
    this.proof = proof;
  }
}
