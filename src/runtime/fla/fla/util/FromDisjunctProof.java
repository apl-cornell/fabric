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
    super(superior, inferior);
    this.disjunctProofs =
        Collections.unmodifiableMap(new HashMap<>(disjunctProofs));
  }
}
