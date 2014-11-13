package fla.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fla.principals.ConjunctivePrincipal;
import fla.principals.Principal;

/**
 * Represents use of the rule a ≽ b and a ≽ c => a ≽ b ∧ c.
 */
public class ToConjunctProof<Superior extends Principal> extends
ActsForProof<Superior, ConjunctivePrincipal> {
  /**
   * Maps conjuncts in {@code inferior} to proofs that {@code superior} ≽
   * the conjunct.
   */
  public final Map<Principal, ActsForProof<Superior, Principal>> conjunctProofs;

  /**
   * @param conjunctProofs maps conjuncts in {@code superior} to proofs that
   *        {@code superior} ≽ the conjunct
   */
  public ToConjunctProof(Superior superior, ConjunctivePrincipal inferior,
      Map<Principal, ActsForProof<Superior, Principal>> conjunctProofs) {
    super(superior, inferior, value(conjunctProofs).label,
        value(conjunctProofs).accessPolicy);
    this.conjunctProofs =
        Collections.unmodifiableMap(new HashMap<>(conjunctProofs));

    // Ensure the labels and access policies for all subproofs match.
    for (ActsForProof<Superior, Principal> subproof : conjunctProofs.values()) {
      assertEquals(label, subproof.label);
      assertEquals(accessPolicy, subproof.accessPolicy);
    }
  }

  /**
   * Returns a value in the map.
   */
  private static <Superior extends Principal, Inferior extends Principal> ActsForProof<Superior, Inferior> value(
      Map<Principal, ActsForProof<Superior, Inferior>> map) {
    return map.values().iterator().next();
  }
}
