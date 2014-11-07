package fla.principals;

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
  public Principal project(TopPrincipal projection) {
    return this;
  }

  @Override
  public Principal project(PrimitivePrincipal projection) {
    return this;
  }

  @Override
  public Principal project(ConfPrincipal projection) {
    return this;
  }

  @Override
  public Principal project(IntegPrincipal projection) {
    return this;
  }

  @Override
  public Principal project(OwnedPrincipal projection) {
    return this;
  }

  @Override
  public Principal project(ConjunctivePrincipal projection) {
    return this;
  }

  @Override
  public Principal project(DisjunctivePrincipal projection) {
    return this;
  }

  @Override
  public Principal owner(Principal owner) {
    return this;
  }

  @Override
  public Principal owner(TopPrincipal owner) {
    return this;
  }

  @Override
  public Principal owner(PrimitivePrincipal owner) {
    return this;
  }

  @Override
  public Principal owner(ConfPrincipal owner) {
    return this;
  }

  @Override
  public Principal owner(IntegPrincipal owner) {
    return this;
  }

  @Override
  public Principal owner(OwnedPrincipal owner) {
    return this;
  }

  @Override
  public Principal owner(ConjunctivePrincipal owner) {
    return this;
  }

  @Override
  public Principal owner(DisjunctivePrincipal owner) {
    return this;
  }

}
