package fla.principals;

public class PrimitivePrincipal extends Principal {
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
  public Principal project(TopPrincipal projection) {
    // Use the TopPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public final Principal project(PrimitivePrincipal projection) {
    // Let p = this, and q = projection.
    Principal p = this;
    Principal q = projection;

    // Have p:q, which is normal form.
    return new OwnedPrincipal(p, q);
  }

  @Override
  public final Principal project(ConfPrincipal projection) {
    // Use the ConfPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public final Principal project(IntegPrincipal projection) {
    // Use the IntegPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public final Principal project(OwnedPrincipal projection) {
    // Use the OwnedPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public final Principal project(ConjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public final Principal project(DisjunctivePrincipal projection) {
    // Use the DisjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public final Principal owner(Principal owner) {
    return owner.project(this);
  }

  @Override
  public Principal owner(TopPrincipal owner) {
    // Use the TopPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public final Principal owner(PrimitivePrincipal owner) {
    // Use the AbstractPrimitivePrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public final Principal owner(ConfPrincipal owner) {
    // Use the ConfPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public final Principal owner(IntegPrincipal owner) {
    // Use the IntegPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public final Principal owner(OwnedPrincipal owner) {
    // Use the OwnedPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public final Principal owner(ConjunctivePrincipal owner) {
    // Use the ConjunctivePrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public final Principal owner(DisjunctivePrincipal owner) {
    // Use the DisjunctivePrincipal projection implementation.
    return owner.project(this);
  }
}
