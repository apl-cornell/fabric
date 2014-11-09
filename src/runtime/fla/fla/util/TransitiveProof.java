package fla.util;

import fla.principals.Principal;

/**
 * Represents use of the transitivity rule.
 */
public class TransitiveProof<Superior extends Principal, Intermediate extends Principal, Inferior extends Principal>
    extends ActsForProof<Superior, Inferior> {
  public final ActsForProof<Superior, Intermediate> superiorToP;
  public final ActsForProof<Intermediate, Inferior> pToInferior;
  public final Intermediate p;

  public TransitiveProof(ActsForProof<Superior, Intermediate> superiorToP,
      Intermediate p, ActsForProof<Intermediate, Inferior> pToInferior) {
    super(superiorToP.superior, pToInferior.inferior);
    this.superiorToP = superiorToP;
    this.pToInferior = pToInferior;
    this.p = p;
  }
}
