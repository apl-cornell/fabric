package fla.principals;

/**
 * Represents an ownership projection of a principal.
 */
public final class OwnedPrincipal extends NonPrimitivePrincipal {
  /**
   * The owner principal, whose authority is being projected.
   */
  protected final Principal owner;

  /**
   * The ownership projection applied in this principal.
   */
  protected final Principal projection;

  /**
   * @param owner the principal whose authority is being projected
   * @param projection the ownership projection being applied
   */
  OwnedPrincipal(Principal owner, Principal projection) {
    this.owner = owner;
    this.projection = projection;
  }

  /**
   * @return the owner principal, whose authority is projected
   */
  public Principal owner() {
    return owner;
  }

  /**
   * @return the ownership projection
   */
  public Principal projection() {
    return projection;
  }

  @Override
  public int hashCode() {
    return owner.hashCode() ^ projection.hashCode();
  }

  @Override
  public boolean equals(Principal p) {
    if (!(p instanceof OwnedPrincipal)) return false;
    OwnedPrincipal that = (OwnedPrincipal) p;
    return this.owner.equals(that.owner())
        && this.projection.equals(that.projection());
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
    // Use the TopPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal project(PrimitivePrincipal projection) {
    // Let p:q = this, and r = projection.
    Principal pq = this;
    Principal r = projection;

    // Have (p:q)r, which is normal form.
    return new OwnedPrincipal(pq, r);
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
    // Let p:q = this, and r:s = projection.
    Principal pq = this;
    Principal rs = projection;

    // Have (p:q):(r:s), which is normal form.
    return new OwnedPrincipal(pq, rs);
  }

  @Override
  public Principal project(ConjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal project(DisjunctivePrincipal projection) {
    // Use the DisjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal owner(Principal owner) {
    return owner.project(this);
  }

  @Override
  public Principal owner(TopPrincipal owner) {
    // Use the TopPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public Principal owner(PrimitivePrincipal owner) {
    // Let p:q = this, and r = owner.
    // Have r:(p:q), which is normal form.
    return new OwnedPrincipal(owner, this);
  }

  @Override
  public Principal owner(ConfPrincipal owner) {
    // Use the ConfPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public Principal owner(IntegPrincipal owner) {
    // Use the IntegPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public Principal owner(OwnedPrincipal owner) {
    // Use the OwnedPrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public Principal owner(ConjunctivePrincipal owner) {
    // Use the ConjunctivePrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  public Principal owner(DisjunctivePrincipal owner) {
    // Use the DisjunctivePrincipal projection implementation.
    return owner.project(this);
  }

  @Override
  Principal join(Principal conjunct) {
    if (conjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) conjunct).join(this);
    }

    // Conjunct is a primitive principal.
    // Let p:q = this, and r = conjunct.
    Principal r = conjunct;

    // Can assume r ⋡ p:q.
    // We have p:q ∧ r, which is normal.
    return new ConjunctivePrincipal(this, r);
  }

  @Override
  Principal join(ConfPrincipal conjunct) {
    // Use the ConfPrincipal implementation.
    return conjunct.join(this);
  }

  @Override
  Principal join(IntegPrincipal conjunct) {
    // Use the IntegPrincipal implementation.
    return conjunct.join(this);
  }

  @Override
  Principal join(OwnedPrincipal conjunct) {
    // Let p:q = this, and r:s = conjunct.
    Principal rs = conjunct;

    // Can assume p:q ⋡ r:s and r:s ⋡ p:q.
    // We have (p:q) ∧ (r:s), which is normal.
    return new ConjunctivePrincipal(this, rs);
  }

  @Override
  Principal join(ConjunctivePrincipal conjunct) {
    // Use the ConjunctivePrincipal implementation.
    return conjunct.join(this);
  }

  @Override
  Principal join(DisjunctivePrincipal conjunct) {
    // Use the DisjunctivePrincipal implementation.
    return conjunct.join(this);
  }

  @Override
  Principal meet(Principal disjunct) {
    if (disjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) disjunct).meet(this);
    }

    // Disjunct is a primitive principal.
    // Let p:q = this, and r = disjunct.
    Principal r = disjunct;

    // Can assume r ⋡ p:q.
    // We have p:q ∨ r, which is normal.
    return new DisjunctivePrincipal(this, r);
  }

  @Override
  Principal meet(ConfPrincipal disjunct) {
    // Use the ConfPrincipal implementation.
    return disjunct.meet(this);
  }

  @Override
  Principal meet(IntegPrincipal disjunct) {
    // Use the IntegPrincipal implementation.
    return disjunct.meet(this);
  }

  @Override
  Principal meet(OwnedPrincipal disjunct) {
    // Let p:q = this, and r:s = conjunct.
    Principal rs = disjunct;

    // Can assume p:q ⋡ r:s and r:s ⋡ p:q.
    // We have (p:q) ∨ (r:s), which is normal.
    return new DisjunctivePrincipal(this, rs);
  }

  @Override
  Principal meet(ConjunctivePrincipal disjunct) {
    // Use the ConjunctivePrincipal implementation.
    return disjunct.meet(this);
  }

  @Override
  Principal meet(DisjunctivePrincipal disjunct) {
    // Use the DisjunctivePrincipal implementation.
    return disjunct.meet(this);
  }
}
