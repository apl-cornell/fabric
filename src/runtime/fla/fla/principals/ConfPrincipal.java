package fla.principals;

/**
 * Represents the confidentiality projection of a principal.
 */
public final class ConfPrincipal extends NonPrimitivePrincipal {
  /**
   * The principal whose authority is being projected.
   */
  protected final Principal base;

  /**
   * @param principal the principal whose authority is being projected
   */
  ConfPrincipal(Principal principal) {
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
    if (!(p instanceof ConfPrincipal)) return false;
    return base.equals(((ConfPrincipal) p).base());
  }

  @Override
  public ConfPrincipal confidentiality() {
    return this;
  }

  @Override
  public Principal integrity() {
    return PrincipalUtil.bottom();
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
    // Let p→ = this, and q = projection.
    Principal q = projection;

    // Have (p→):q, which is normal form.
    return new OwnedPrincipal(this, q);
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
    // Let p→ = this, and q:r = projection.
    Principal qr = projection;

    // Have (p→):(q:r), which is normal form.
    return new OwnedPrincipal(this, qr);
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
    // Let p→ = this, and q = owner.
    Principal p = base;
    Principal q = owner;

    // Rewrite q:(p→) as (q:p)→.
    return q.project(p).confidentiality();
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

  @Override
  Principal join(Principal conjunct) {
    if (conjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) conjunct).join(this);
    }

    // Conjunct is a primitive principal.
    // Let p→ = this, and q = conjunct.
    Principal p = base;
    Principal q = conjunct;

    // Can assume q ⋡ p→.
    // Rewrite p→ ∧ q as (p ∧ q)→ ∧ q←.
    return new ConjunctivePrincipal(PrincipalUtil.conjunction(p, q)
        .confidentiality(), q.integrity());
  }

  @Override
  Principal join(ConfPrincipal conjunct) {
    // Let p→ = this, and q→ = conjunct.
    Principal p = base;
    Principal q = conjunct.base();

    // Can assume p→ ⋡ q→ and q→ ⋡ p→.
    // Rewrite p→ ∧ q→ as (p ∧ q)→.
    return PrincipalUtil.conjunction(p, q).confidentiality();
  }

  @Override
  Principal join(IntegPrincipal conjunct) {
    // Let p→ = this, and q← = conjunct.
    Principal p = base;
    Principal q = conjunct.base();

    // If p=q, then rewrite p→ ∧ q← as p.
    if (PrincipalUtil.equals(p, q)) return p;

    // Have p→ ∧ q←, which is normal form.
    return new ConjunctivePrincipal(this, conjunct);
  }

  @Override
  Principal join(OwnedPrincipal conjunct) {
    // Let p→ = this, and q:r = conjunct.
    Principal p = base;
    Principal qr = conjunct;

    // Can assume q:r ⋡ p→.
    // Rewrite p→ ∧ q:r as (p ∧ q:r)→ ∧ q:r←.
    return new ConjunctivePrincipal(PrincipalUtil.conjunction(p, qr)
        .confidentiality(), qr.integrity());
  }

  @Override
  Principal join(ConjunctivePrincipal conjunct) {
    // Let p→ = this, and q∧r = conjunct.
    Principal p = base;
    Principal qr = conjunct;

    // Can assume q∧r ⋡ p→.
    // Rewrite p→ ∧ (q∧r) as (p∧(q∧r))→ ∧ (q∧r)←.
    return new ConjunctivePrincipal(PrincipalUtil.conjunction(p, qr)
        .confidentiality(), qr.integrity());
  }

  @Override
  Principal join(DisjunctivePrincipal conjunct) {
    // Let p→ = this, and q∨r = conjunct.
    Principal p = base;
    Principal qr = conjunct;

    // Can assume q∨r ⋡ p→.
    // Rewrite p→ ∧ (q∨r) as (p∧(q∨r))→ ∧ (q∨r)←.
    return new ConjunctivePrincipal(PrincipalUtil.conjunction(p, qr)
        .confidentiality(), qr.integrity());
  }

  @Override
  Principal meet(Principal disjunct) {
    if (disjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) disjunct).join(this);
    }

    // Disjunct is a primitive principal.
    // Let p→ = this, and q = disjunct.
    Principal p = base;
    Principal q = disjunct;

    // Can assume q ⋡ p→.
    // Rewrite p→ ∨ q as (p∨q)→.
    return PrincipalUtil.disjunction(p, q).confidentiality();
  }

  @Override
  Principal meet(ConfPrincipal disjunct) {
    // Let p→ = this, and q→ = disjunct.
    Principal p = base;
    Principal q = disjunct.base();

    // Can assume q→ ⋡ p→ and p→ ⋡ q→.
    // Rewrite p→ ∨ q→ as (p∨q)→.
    return PrincipalUtil.disjunction(p, q).confidentiality();
  }

  @Override
  Principal meet(IntegPrincipal disjunct) {
    // Let p→ = this, and q← = disjunct.
    // Rewrite p→ ∨ q← as ⊥.
    return PrincipalUtil.bottom();
  }

  @Override
  Principal meet(OwnedPrincipal disjunct) {
    // Let p→ = this, and q:r = disjunct.
    Principal p = base;
    Principal qr = disjunct;

    // Can assume q:r ⋡ p→.
    // Rewrite p→ ∨ q:r as (p ∨ q:r)→.
    return PrincipalUtil.disjunction(p, qr).confidentiality();
  }

  @Override
  Principal meet(ConjunctivePrincipal disjunct) {
    // Let p→ = this, and q∧r = disjunct.
    Principal p = base;
    Principal qr = disjunct;

    // Can assume q∧r ⋡ p→.
    // Rewrite p→ ∨ (q∧r) as (p∨(q∧r))→.
    return PrincipalUtil.disjunction(p, qr).confidentiality();
  }

  @Override
  Principal meet(DisjunctivePrincipal disjunct) {
    // Let p→ = this, and q∨r = disjunct.
    Principal p = base;
    Principal qr = disjunct;

    // Can assume q∨r ⋡ p→.
    // Rewrite p→ ∨ (q∨r) as (p∨(q∨r))→.
    return PrincipalUtil.disjunction(p, qr).confidentiality();
  }
}
