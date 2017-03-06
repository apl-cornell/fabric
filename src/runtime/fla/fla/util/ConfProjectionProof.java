package fla.util;

import fla.principals.Principal;

/**
 * Represents use of the rule a ≽ b => a→ ≽ b→.
 */
public final class ConfProjectionProof extends
    ActsForProof<Principal, Principal> {
  /**
   * A proof of {@code superior.base()} ≽ {@code inferior.base()}.
   */
  public final ActsForProof<?, ?> proof;

  public ConfProjectionProof(ActsForProof<?, ?> proof) {
    super(proof.superior.confidentiality(), proof.inferior.confidentiality(),
        proof.label, proof.accessPolicy);
    this.proof = proof;
  }
}
