package fla.principals;

import fla.Label;
import fla.util.ActsForQuery;
import fla.util.DelegationPair;
import fla.util.PolyInstantiableSet;

public class PrimitivePrincipal extends Principal {
  private final PolyInstantiableSet<DelegationPair> delegations;

  public PrimitivePrincipal() {
    this.delegations = new PolyInstantiableSet<>();
  }

  @Override
  public boolean equals(Principal p) {
    return this == p;
  }

  @Override
  public final ConfPrincipal confidentiality() {
    return new ConfPrincipal(this);
  }

  @Override
  public final IntegPrincipal integrity() {
    return new IntegPrincipal(this);
  }

  @Override
  public final Principal project(Principal projection) {
    return projection.owner(this);
  }

  @Override
  Principal project(TopPrincipal projection) {
    // Use the TopPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  final Principal project(PrimitivePrincipal projection) {
    // Let p = this, and q = projection.
    Principal p = this;
    Principal q = projection;

    // Have p:q, which is normal form.
    return new OwnedPrincipal(p, q);
  }

  @Override
  final Principal project(ConfPrincipal projection) {
    // Use the ConfPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  final Principal project(IntegPrincipal projection) {
    // Use the IntegPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  final Principal project(OwnedPrincipal projection) {
    // Use the OwnedPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  final Principal project(ConjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  final Principal project(DisjunctivePrincipal projection) {
    // Use the DisjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public final Principal owner(Principal owner) {
    return owner.project(this);
  }

  @Override
  Principal owner(TopPrincipal owner) {
    // Use the TopPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  final Principal owner(PrimitivePrincipal owner) {
    // Use the AbstractPrimitivePrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  final Principal owner(ConfPrincipal owner) {
    // Use the ConfPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  final Principal owner(IntegPrincipal owner) {
    // Use the IntegPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  final Principal owner(OwnedPrincipal owner) {
    // Use the OwnedPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  final Principal owner(ConjunctivePrincipal owner) {
    // Use the ConjunctivePrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  final Principal owner(DisjunctivePrincipal owner) {
    // Use the DisjunctivePrincipal projection implementation.
    return owner.project(this);
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
   * It is also assumed that {@code accessPolicy} has no integrity component.
   * <p>
   * A final assumption that is not explicitly checked is that this principal
   * acts for both {@code maxLabel} and {@code accessPolicy}. (Otherwise, we
   * have no business making this query to this principal!)
   * <p>
   * If {@code maxLabel} or {@code accessPolicy} is {@code null}, then a static
   * context is assumed (in which no dynamic delegations exists), and this
   * method returns false.
   *
   * @param granter the potential granter
   * @param superior the potential superior
   * @param maxLabel labels on delegations considered when satisfying this
   *          query will not exceed this label
   * @param accessPolicy the confidentiality level of the query. This should
   *          act for the confidentiality component of {@code maxLabel}
   */
  public final boolean delegatesTo(Principal granter, Principal superior,
      Label maxLabel, Principal accessPolicy) {
    return delegatesTo(new ActsForQuery<>(superior, granter, maxLabel,
        accessPolicy));
  }

  @Override
  final boolean delegatesTo(ActsForQuery<?, ?> query) {
    if (!query.useDynamicContext()) {
      // Static context. No dynamic delegations should be used.
      return false;
    }
  }
}
