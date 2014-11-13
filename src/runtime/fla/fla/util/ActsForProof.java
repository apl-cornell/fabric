package fla.util;

import fla.principals.Principal;
import fla.principals.PrincipalUtil;

public abstract class ActsForProof<Superior extends Principal, Inferior extends Principal> {
  public final Superior superior;
  public final Inferior inferior;

  /**
   * The label on the proof.
   */
  public final Principal label;

  /**
   * The access policy for the query that this proof answers.
   */
  public final Principal accessPolicy;

  ActsForProof(Superior superior, Inferior inferior, Principal label,
      Principal accessPolicy) {
    this.superior = superior;
    this.inferior = inferior;
    this.label = label;
    this.accessPolicy = accessPolicy;
  }

  static void assertEquals(Principal p, Principal q) {
    if (!PrincipalUtil.equals(p, q)) {
      throw new InvalidProofException(p + " ≠ " + q);
    }
  }

  static class InvalidProofException extends RuntimeException {
    public InvalidProofException(String message) {
      super(message);
    }
  }
}
