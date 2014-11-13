package fla.util;

import fla.principals.IntegPrincipal;

/**
 * Represents use of the rule a ≽ b => a← ≽ b←.
 */
public final class IntegProjectionProof extends
    ActsForProof<IntegPrincipal, IntegPrincipal> {
  /**
   * A proof of {@code superior.base()} ≽ {@code inferior.base()}.
   */
  public final ActsForProof<?, ?> proof;

  public IntegProjectionProof(ActsForProof<?, ?> proof) {
    super((IntegPrincipal) proof.superior.integrity(),
        (IntegPrincipal) proof.inferior.integrity(), proof.label,
        proof.accessPolicy);
    this.proof = proof;
  }
}
