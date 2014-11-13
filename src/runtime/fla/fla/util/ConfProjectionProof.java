package fla.util;

import fla.principals.ConfPrincipal;

/**
 * Represents use of the rule a ≽ b => a→ ≽ b→.
 */
public final class ConfProjectionProof extends
    ActsForProof<ConfPrincipal, ConfPrincipal> {
  /**
   * A proof of {@code superior.base()} ≽ {@code inferior.base()}.
   */
  public final ActsForProof<?, ?> proof;

  public ConfProjectionProof(ActsForProof<?, ?> proof) {
    super((ConfPrincipal) proof.superior.confidentiality(),
        (ConfPrincipal) proof.inferior.confidentiality(), proof.label,
        proof.accessPolicy);
    this.proof = proof;
  }
}
