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
  public DelegatesProof(Inferior inferior, Superior superior) {
    this(null, inferior, superior);
  }

  /**
   * @param source the principal storing the delegation. This can be
   *          {@code null} to indicate that the delegation is statically known.
   */
  public DelegatesProof(Principal source, Inferior inferior, Superior superior) {
    super(superior, inferior);
    this.source = source;
  }
}
