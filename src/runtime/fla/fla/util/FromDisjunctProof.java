package fla.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fla.principals.DisjunctivePrincipal;
import fla.principals.Principal;

/**
 * Represents use of the rule a ≽ c and b ≽ c => a ∨ b ≽ c.
 */
public final class FromDisjunctProof<Inferior extends Principal> extends
ActsForProof<DisjunctivePrincipal, Inferior> {
  /**
   * Maps disjuncts in {@code superior} to proofs that the disjunct ≽
   * {@code inferior}.
   */
  public final Map<Principal, ActsForProof<Principal, Inferior>> disjunctProofs;

  /**
   * @param disjunctProofs maps disjuncts in {@code superior} to proofs that the
   *         disjunct ≽ {@code inferior}
   */
  public FromDisjunctProof(DisjunctivePrincipal superior, Inferior inferior,
      Map<Principal, ActsForProof<Principal, Inferior>> disjunctProofs) {
    super(superior, inferior, value(disjunctProofs).label,
        value(disjunctProofs).accessPolicy);
    this.disjunctProofs =
        Collections.unmodifiableMap(new HashMap<>(disjunctProofs));

    // Ensure the labels and access policies for all subproofs match.
    for (ActsForProof<Principal, Inferior> subproof : disjunctProofs.values()) {
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
