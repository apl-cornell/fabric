package fla.principals;

/**
 * Represents the integrity projection of a principal.
 */
public final class IntegPrincipal extends NonPrimitivePrincipal {
  /**
   * The principal whose authority is being projected.
   */
  protected final Principal base;

  /**
   * @param principal the principal whose authority is being projected
   */
  IntegPrincipal(Principal principal) {
    this.base = principal;
  }

  /**
   * @return the principal whose authority is being projected
   */
  public Principal base() {
    return base;
  }

  @Override
  public int hashCode() {
    return base.hashCode();
  }

  @Override
  public boolean equals(Principal p) {
    if (!(p instanceof IntegPrincipal)) return false;
    return base.equals(((IntegPrincipal) p).base());
  }

  @Override
  public Principal confidentiality() {
    return PrincipalUtil.bottom().confidentiality();
  }

  @Override
  public IntegPrincipal integrity() {
    return this;
  }

  @Override
  public Principal project(Principal projection) {
    return projection.owner(this);
  }

  @Override
  Principal project(TopPrincipal projection) {
    // Use the TopPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  Principal project(PrimitivePrincipal projection) {
    // Let p← = this, and q = projection.
    Principal q = projection;

    // Have (p←):q, which is normal form.
    return new OwnedPrincipal(this, q);
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
    // Let p← = this, and q:r = projection.
    Principal qr = projection;

    // Have (p←):(q:r), which is normal form.
    return new OwnedPrincipal(this, qr);
  }

  @Override
  Principal project(ConjunctivePrincipal projection) {
    // Use the ConjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  Principal project(DisjunctivePrincipal projection) {
    // Use the DisjunctivePrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal owner(Principal owner) {
    // Let p← = this, and q = owner.
    Principal p = base;
    Principal q = owner;

    // Rewrite q:(p←) as (q:p)←.
    return q.project(p).integrity();
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
  Principal join(Principal conjunct) {
    if (conjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) conjunct).join(this);
    }

    // Conjunct is a primitive principal.
    // Let p← = this, and q = conjunct.
    Principal p = base;
    Principal q = conjunct;

    // Can assume q ⋡ p←.
    // Rewrite p← ∧ q as q→ ∧ (p ∧ q)←.
    return new ConjunctivePrincipal(q.confidentiality(), PrincipalUtil
        .conjunction(p, q).integrity());
  }

  @Override
  Principal join(ConfPrincipal conjunct) {
    // Use the ConfPrincipal implementation.
    return conjunct.join(this);
  }

  @Override
  Principal join(IntegPrincipal conjunct) {
    // Let p← = this, and q← = conjunct.
    Principal p = base;
    Principal q = conjunct.base();

    // Can assume p← ⋡ q← and q← ⋡ p←.
    // Rewrite p← ∧ q← as (p ∧ q)←.
    return PrincipalUtil.conjunction(p, q).integrity();
  }

  @Override
  Principal join(OwnedPrincipal conjunct) {
    // Let p← = this, and q:r = conjunct.
    Principal p = base;
    Principal qr = conjunct;

    // Can assume q:r ⋡ p←.
    // Rewrite p← ∧ q:r as q:r→ ∧ (p ∧ q:r)←.
    return new ConjunctivePrincipal(qr.confidentiality(), PrincipalUtil
        .conjunction(p, qr).integrity());
  }

  @Override
  Principal join(ConjunctivePrincipal conjunct) {
    // Let p← = this, and q∧r = conjunct.
    Principal p = base;
    Principal qr = conjunct;

    // Can assume q∧r ⋡ p←.
    // Rewrite p← ∧ (q∧r) as (q∧r)→ ∧ (p∧(q∧r))←.
    return new ConjunctivePrincipal(qr.confidentiality(), PrincipalUtil
        .conjunction(p, qr).integrity());
  }

  @Override
  Principal join(DisjunctivePrincipal conjunct) {
    // Let p← = this, and q∨r = conjunct.
    Principal p = base;
    Principal qr = conjunct;

    // Can assume q∨r ⋡ p←.
    // Rewrite p← ∧ (q∨r) as (q∨r)→ ∧ (p∧(q∨r))←.
    return new ConjunctivePrincipal(qr.confidentiality(), PrincipalUtil
        .conjunction(p, qr).integrity());
  }

  @Override
  Principal meet(Principal disjunct) {
    if (disjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) disjunct).join(this);
    }

    // Disjunct is a primitive principal.
    // Let p← = this, and q = disjunct.
    Principal p = base;
    Principal q = disjunct;

    // Can assume q ⋡ p←.
    // Rewrite p← ∨ q as (p∨q)←.
    return PrincipalUtil.disjunction(p, q).integrity();
  }

  @Override
  Principal meet(ConfPrincipal disjunct) {
    // Use the ConfPrinicpal implementation.
    return disjunct.meet(this);
  }

  @Override
  Principal meet(IntegPrincipal disjunct) {
    // Let p← = this, and q→ = disjunct.
    Principal p = base;
    Principal q = disjunct.base();

    // Can assume p← ⋡ q← and q← ⋡ p←.
    // Rewrite p← ∨ q← as (p∨q)←.
    return PrincipalUtil.disjunction(p, q).integrity();
  }

  @Override
  Principal meet(OwnedPrincipal disjunct) {
    // Let p← = this, and q:r = disjunct.
    Principal p = base;
    Principal qr = disjunct;

    // Can assume q:r ⋡ p←.
    // Rewrite p← ∨ q:r as (p ∨ q:r)←.
    return PrincipalUtil.disjunction(p, qr).integrity();
  }

  @Override
  Principal meet(ConjunctivePrincipal disjunct) {
    // Let p← = this, and q∧r = disjunct.
    Principal p = base;
    Principal qr = disjunct;

    // Can assume q∧r ⋡ p←.
    // Rewrite p← ∨ (q∧r) as (p∨(q∧r))←.
    return PrincipalUtil.disjunction(p, qr).integrity();
  }

  @Override
  Principal meet(DisjunctivePrincipal disjunct) {
    // Let p← = this, and q∨r = disjunct.
    Principal p = base;
    Principal qr = disjunct;

    // Can assume q∨r ⋡ p←.
    // Rewrite p← ∨ (q∨r) as (p∨(q∨r))←.
    return PrincipalUtil.disjunction(p, qr).integrity();
  }
}
