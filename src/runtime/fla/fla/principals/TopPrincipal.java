package fla.principals;

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
  public Principal project(TopPrincipal projection) {
    // Rewrite ⊤:⊤ as ⊤.
    return this;
  }

  @Override
  public Principal project(PrimitivePrincipal projection) {
    // Let p = projection.
    Principal p = projection;

    // Have ⊤:p, which is normal form.
    return new OwnedPrincipal(this, p);
  }

  @Override
  public Principal project(ConfPrincipal projection) {
    // Use the ConfPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal project(IntegPrincipal projection) {
    // Use the IntegPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal project(OwnedPrincipal projection) {
    // Let p:q = projection.
    Principal pq = projection;

    // Have ⊤:(p:q), which is normal form.
    return new OwnedPrincipal(this, pq);
  }

  @Override
  public Principal project(ConjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal project(DisjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal owner(Principal owner) {
    // Rewrite owner:⊤ as owner.
    return owner;
  }

  @Override
  public Principal owner(TopPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  public Principal owner(PrimitivePrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  public Principal owner(ConfPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  public Principal owner(IntegPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  public Principal owner(OwnedPrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  public Principal owner(ConjunctivePrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }

  @Override
  public Principal owner(DisjunctivePrincipal owner) {
    // Use the generic implementation.
    return owner((Principal) owner);
  }
}
