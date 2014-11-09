package fla.principals;

import java.util.Collections;
import java.util.Set;

import fla.util.ActsForQuery;
import fla.util.DelegationPair;

public final class TopPrincipal extends Principal {
  public static final TopPrincipal INSTANCE = new TopPrincipal();

  private TopPrincipal() {
  }

  @Override
  public int hashCode() {
    // Randomly generated.
    return 594921698;
  }

  @Override
  public boolean equals(Principal p) {
    return p instanceof TopPrincipal;
  }

  @Override
  public ConfPrincipal confidentiality() {
    return new ConfPrincipal(this);
  }

  @Override
  public IntegPrincipal integrity() {
    return new IntegPrincipal(this);
  }

  @Override
  public Principal project(Principal projection) {
    return projection.owner(this);
  }

  @Override
  Principal project(TopPrincipal projection) {
    // Rewrite ⊤:⊤ as ⊤.
    return this;
  }

  @Override
  Principal project(PrimitivePrincipal projection) {
    // Let p = projection.
    Principal p = projection;

    // Have ⊤:p, which is normal form.
    return new OwnedPrincipal(this, p);
  }

  @Override
  Principal project(ConfPrincipal projection) {
    // Use the ConfPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  Principal project(IntegPrincipal projection) {
    // Use the IntegPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  Principal project(OwnedPrincipal projection) {
    // Let p:q = projection.
    Principal pq = projection;

    // Have ⊤:(p:q), which is normal form.
    return new OwnedPrincipal(this, pq);
  }

  @Override
  Principal project(ConjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  Principal project(DisjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal owner(Principal owner) {
    // Rewrite owner:⊤ as owner.
    return owner;
  }

  @Override
  Principal owner(TopPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  Principal owner(PrimitivePrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  Principal owner(ConfPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  Principal owner(IntegPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  Principal owner(OwnedPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  Principal owner(ConjunctivePrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  Principal owner(DisjunctivePrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  Set<PrimitivePrincipal> componentPrimitivePrincipals() {
    return Collections.emptySet();
  }

  @Override
  final Set<DelegationPair> usableDelegations(ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    // Only (non-top, non-bottom) primitive principals store delegations.
    return Collections.emptySet();
  }

  @Override
  Set<Principal> askablePrincipals(ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    // No component principals.
    return Collections.emptySet();
  }
}
