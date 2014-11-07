package fla.principals;

import fla.Label;
import fla.util.DelegationPair;
import fla.util.PolyInstantiableSet;

public abstract class Principal implements Label {
  private final PolyInstantiableSet<DelegationPair> delegations;

  Principal() {
    this.delegations = new PolyInstantiableSet<>();
  }

  /**
   * Asks this principal whether it can find the (direct) delegation "{@code
   * superior} actsfor {@code granter}" whose label does not exceed {@code
   * maxLabel}.
   * <p>
   * When making recursive calls, any principals receiving those calls must act
   * for {@code accessPolicy} and the integrity projection of {@code maxLabel}.
   * It is assumed that {@code accessPolicy} acts for the confidentiality
   * component of {@code maxLabel}. As such, no explicit checks will be made to
   * ensure that principals receiving recursive calls will act for this
   * component (by the above assumption, such a check would be subsumed by the
   * check against {@code accessPolicy}).
   * <p>
   * A final assumption that is not explicitly checked is that this principal
   * acts for both {@code maxLabel} and {@code accessPolicy}. (Otherwise, we
   * have no business making this query to this principal!)
   *
   * @param granter the potential granter
   * @param superior the potential superior
   * @param maxLabel labels on delegations considered when satisfying this
   *          query will not exceed this label
   * @param accessPolicy the confidentiality level of the query. This should
   *          act for the confidentiality component of {@code maxLabel}
   */
  public final boolean delegatesTo(Principal granter, Principal superior,
      Label maxLabel, ConfPrincipal accessPolicy) {

  }

  /**
   * Asks this principal whether it can find a proof for "{@code superior}
   * actsfor {@code inferior}". Labels on the delegations used in this proof
   * may not exceed {@code maxUsableLabel}.
   * <p>
   * When making recursive calls, any principals receiving those calls must act
   * for {@code accessPolicy} and the integrity projection of {@code
   * maxUsableLabel}. It is assumed that {@code accessPolicy} acts for the
   * confidentiality component of {@code maxUsableLabel}. As such, no explicit
   * checks will be made to ensure that principals receiving recursive calls
   * will act for this component (by the above assumption, such a check would
   * be subsumed by the check against {@code accessPolicy}).
   * <p>
   * A final assumption that is not explicitly checked is that this principal
   * acts for both {@code maxUsableLabel} and {@code accessPolicy}. (Otherwise,
   * we have no business making this query to this principal!)
   *
   * @param superior the potential superior
   * @param inferior the potential inferior
   * @param maxUsableLabel labels on delegations used in the proof will not
   *          exceed this label
   * @param accessPolicy the confidentiality level of the query. This should
   *          act for the confidentiality component of {@code maxUsableLabel}
   */
  public final boolean actsFor(Principal superior, Principal inferior,
      Label maxUsableLabel, ConfPrincipal accessPolicy) {

  }

  /**
   * Stores a new delegation with this principal.
   *
   * @param granter the principal granting privileges
   * @param superior the principal receiving new privileges
   * @param label the label on the new delegation
   */
  public final void addDelegatesTo(Principal granter, Principal superior,
      Label label) {
    delegations.add(label, new DelegationPair(granter, superior));
  }

  /**
   * @return the confidentiality projection of this principal. The returned
   *          principal will be in normal form if this principal is.
   */
  public abstract Principal confidentiality();

  /**
   * @return the integrity projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal integrity();

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(Principal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(TopPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(PrimitivePrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(ConfPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(IntegPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(OwnedPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(ConjunctivePrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(DisjunctivePrincipal projection);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(Principal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(TopPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(PrimitivePrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(ConfPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(IntegPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(OwnedPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(ConjunctivePrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(DisjunctivePrincipal owner);

  /**
   * @return {@code true} iff the given object is a syntactically equivalent
   *          principal.
   */
  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof Principal) return equals((Principal) obj);
    return false;
  }

  /**
   * @return {@code true} iff the given principal is syntactically equivalent to
   *          this one.
   */
  public abstract boolean equals(Principal p);
}
