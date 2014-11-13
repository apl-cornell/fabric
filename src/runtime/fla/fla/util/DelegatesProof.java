package fla.util;

import fla.principals.Delegation;
import fla.principals.Principal;

/**
 * Represents the use of an axiom that a principal has delegated authority to
 * another principal.
 */
public final class DelegatesProof<Superior extends Principal, Inferior extends Principal>
    extends ActsForProof<Superior, Inferior> {
  /**
   * The dynamic delegation from {@code inferior} to {@code superior}. This can
   * be {@code null} to indicate that the delegation is statically known.
   */
  public final Delegation<Inferior, Superior> delegation;

  /**
   * A proof showing that the delegation can be used in this proof.
   */
  public final ActsForProof<?, ?> usabilityProof;

  /**
   * Constructs proof of a statically known delegation from the given query's
   * inferior to its superior.
   */
  public DelegatesProof(ActsForQuery<Superior, Inferior> query) {
    super(query.superior, query.inferior, query.maxUsableLabel,
        query.accessPolicy);
    this.delegation = null;
    this.usabilityProof = null;
  }

  /**
   * Constructs a proof that uses the given dynamic delegation to satisfy the
   * given query.
   *
   * @param delegation the dynamic delegation being used
   * @param usabilityProof a proof showing that the given delegation can be used
   *          as part of the proof of the given query
   */
  public DelegatesProof(Delegation<Inferior, Superior> delegation,
      ActsForQuery<?, ?> query, ActsForProof<?, ?> usabilityProof) {
    super(delegation.superior, delegation.inferior, query.maxUsableLabel,
        query.accessPolicy);
    this.delegation = delegation;
    this.usabilityProof = usabilityProof;
  }
}
