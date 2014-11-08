package fla.util;

import fla.principals.Principal;

/**
 * Represents use of the transitivity rule.
 */
public class TransitiveProof extends ActsForProof {
  public final ActsForProof superiorToP;
  public final ActsForProof pToInferior;
  public final Principal p;

  public TransitiveProof(ActsForProof superiorToP, Principal p,
      ActsForProof pToInferior) {
    super(superiorToP.superior, pToInferior.inferior);
    this.superiorToP = superiorToP;
    this.pToInferior = pToInferior;
    this.p = p;
  }
}
