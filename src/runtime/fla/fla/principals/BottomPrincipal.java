package fla.principals;

import java.util.Collections;
import java.util.Set;

import fla.util.ActsForQuery;

public final class BottomPrincipal extends Principal {
  public static final BottomPrincipal INSTANCE = new BottomPrincipal();

  private BottomPrincipal() {
  }

  @Override
  public int hashCode() {
    // Randomly generated.
    return -1186605908;
  }

  @Override
  public boolean equals(Principal p) {
    return p instanceof BottomPrincipal;
  }

  @Override
  public String toString() {
    return "⊥";
  }

  @Override
  public Principal confidentiality() {
    return this;
  }

  @Override
  public Principal integrity() {
    return this;
  }

  @Override
  public Principal project(Principal projection) {
    return this;
  }

  @Override
  Principal project(TopPrincipal projection) {
    return this;
  }

  @Override
  Principal project(PrimitivePrincipal projection) {
    return this;
  }

  @Override
  Principal project(ConfPrincipal projection) {
    return this;
  }

  @Override
  Principal project(IntegPrincipal projection) {
    return this;
  }

  @Override
  Principal project(OwnedPrincipal projection) {
    return this;
  }

  @Override
  Principal project(ConjunctivePrincipal projection) {
    return this;
  }

  @Override
  Principal project(DisjunctivePrincipal projection) {
    return this;
  }

  @Override
  public Principal owner(Principal owner) {
    return this;
  }

  @Override
  Principal owner(TopPrincipal owner) {
    return this;
  }

  @Override
  Principal owner(PrimitivePrincipal owner) {
    return this;
  }

  @Override
  Principal owner(ConfPrincipal owner) {
    return this;
  }

  @Override
  Principal owner(IntegPrincipal owner) {
    return this;
  }

  @Override
  Principal owner(OwnedPrincipal owner) {
    return this;
  }

  @Override
  Principal owner(ConjunctivePrincipal owner) {
    return this;
  }

  @Override
  Principal owner(DisjunctivePrincipal owner) {
    return this;
  }

  @Override
  Set<PrimitivePrincipal> componentPrimitivePrincipals() {
    return Collections.emptySet();
  }

  @Override
  final Set<Delegation<?, ?>> usableDelegations(ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    // Only (non-top, non-bottom) primitive principals store delegations.
    return Collections.emptySet();
  }
}
