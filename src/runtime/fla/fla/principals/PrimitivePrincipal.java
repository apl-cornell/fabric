package fla.principals;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public abstract class PrimitivePrincipal extends Principal {

  /**
   *
   */
  public PrimitivePrincipal() {
    super();
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
  final Principal project(TopPrincipal projection) {
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
  protected final Principal project(OwnedPrincipal projection) {
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
  protected final Principal owner(TopPrincipal owner) {
    // Use the TopPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  final Principal owner(PrimitivePrincipal owner) {
    // Use the PrimitivePrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  protected final Principal owner(ConfPrincipal owner) {
    // Use the ConfPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  protected final Principal owner(IntegPrincipal owner) {
    // Use the IntegPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  protected final Principal owner(OwnedPrincipal owner) {
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

  @Override
  protected Set<NodePrincipal> componentNodePrincipals() {
    return Collections.emptySet();
  }

  @Override
  Map<Principal, Set<Delegation<?, ?>>> delegations() {
    // Only node principals store delegations.
    return Collections.emptyMap();
  }
}
