package fla.util;

import fla.principals.Principal;

/**
 * Represents the use of an axiom that a principal has delegated authority to
 * another principal.
 */
public final class DelegatesProof<Superior extends Principal, Inferior extends Principal>
    extends ActsForProof<Superior, Inferior> {
  /**
   * The principal storing the delegation. This can be {@code null} to indicate
   * that the delegation is statically known.
   */
  public final Principal source;

  /**
   * Constructs a statically known delegation.
   */
  public DelegatesProof(ActsForQuery<Superior, Inferior> query) {
    this(null, query.inferior, query.superior, query.maxUsableLabel,
        query.accessPolicy);
  }

  /**
   * @param source the principal storing the delegation. This can be (@code
   *          null} to indicate that the delegation is statically known.
   */
  public DelegatesProof(Principal source, ActsForQuery<Superior, Inferior> query) {
    this(source, query.inferior, query.superior, query.maxUsableLabel,
        query.accessPolicy);
  }

  /**
   * @param source the principal storing the delegation. This can be (@code
   *          null} to indicate that the delegation is statically known.
   */
  public DelegatesProof(Principal source, Inferior inferior, Superior superior,
      Principal label, Principal accessPolicy) {
    super(superior, inferior, label, accessPolicy);
    this.source = source;
  }
}
