package fla.principals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A conjunction of a set of principals.
 */
public final class ConjunctivePrincipal extends NonPrimitivePrincipal {

  protected final Set<Principal> conjuncts;

  ConjunctivePrincipal(Principal p, Principal q) {
    this(set(p, q), false);
  }

  ConjunctivePrincipal(Set<Principal> conjuncts) {
    this(conjuncts, true);
  }

  private ConjunctivePrincipal(Set<Principal> conjuncts, boolean copyNeeded) {
    this.conjuncts =
        Collections.unmodifiableSet(copyNeeded ? new HashSet<>(conjuncts)
            : conjuncts);
  }

  /**
   * @return a {@code Set} containing {@code p} and {@code q}
   */
  private static Set<Principal> set(Principal p, Principal q) {
    Set<Principal> result = new HashSet<>();
    result.add(p);
    result.add(q);
    return result;
  }

  public Set<Principal> conjuncts() {
    return conjuncts;
  }

  @Override
  public int hashCode() {
    return conjuncts.hashCode();
  }

  @Override
  public boolean equals(Principal p) {
    if (!(p instanceof ConjunctivePrincipal)) return false;
    return conjuncts.equals(((ConjunctivePrincipal) p).conjuncts());
  }

  /**
   * @return true iff this conjunctive principal has two conjuncts: a
   *          confidentiality projection and an integrity projection.
   */
  private boolean isTopLevelNormalForm() {
    if (conjuncts.size() != 2) return false;
    Principal conjunct;
    {
      Iterator<Principal> it = conjuncts.iterator();
      conjunct = it.next();
    }

    // Since we get to assume that this principal is part of a normal form, it
    // is sufficient to check whether one of the conjuncts is a confidentiality
    // or integrity projection.
    return conjunct instanceof ConfPrincipal
        || conjunct instanceof IntegPrincipal;
  }

  @Override
  public ConfPrincipal confidentiality() {
    if (!isTopLevelNormalForm()) return new ConfPrincipal(this);

    // Just return the confidentiality component.
    for (Principal conjunct : conjuncts) {
      if (conjunct instanceof ConfPrincipal) {
        return (ConfPrincipal) conjunct;
      }
    }

    // Shouldn't reach here.
    throw new Error("Invariant violation -- principal not in normal form: "
        + this);
  }

  @Override
  public IntegPrincipal integrity() {
    if (!isTopLevelNormalForm()) return new IntegPrincipal(this);

    // Just return the integrity component.
    for (Principal conjunct : conjuncts) {
      if (conjunct instanceof IntegPrincipal) {
        return (IntegPrincipal) conjunct;
      }
    }

    // Shouldn't reach here.
    throw new Error("Invariant violation -- principal not in normal form: "
        + this);
  }

  @Override
  public Principal project(Principal projection) {
    // Distribute projection to all conjuncts.
    // Let ⋀p_i = this, and q = projection.
    Set<Principal> p = conjuncts;
    Principal q = projection;

    // Rewrite (⋀p_i):q as ⋀(p_i:q).
    Set<Principal> conjuncts = new HashSet<>(p.size());
    for (Principal p_i : p) {
      conjuncts.add(p_i.project(q));
    }

    return PrincipalUtil.join(conjuncts);
  }

  @Override
  public Principal project(TopPrincipal projection) {
    // Use the TopPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  public Principal project(PrimitivePrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  public Principal project(ConfPrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  public Principal project(IntegPrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  public Principal project(OwnedPrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  public Principal project(ConjunctivePrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  public Principal project(DisjunctivePrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  public Principal owner(Principal owner) {
    // Let ⋀p_i = this, and q = owner.
    Set<Principal> p = conjuncts;
    Principal q = owner;

    // Rewrite q:(⋀p_i) as ⋀(q:p_i).
    Set<Principal> conjuncts = new HashSet<>(p.size());
    for (Principal p_i : p) {
      conjuncts.add(q.owner(p_i));
    }

    return PrincipalUtil.join(conjuncts);
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
    // Let ⋀p_i = this, and q = conjunct.
    Principal q = conjunct;

    // Can assume ⋀p_i ⋡ q. So ∀i, p_i ⋡ q.
    // Join in the new conjunct, while omitting any existing conjuncts that
    // the new conjunct statically acts for.
    // i.e., rewrite ⋀p_i ∧ q as ⋀{p_i | q ⋡ p_i} ∧ q.
    // This is terribly inefficient. Maybe we shouldn't be so eager about doing
    // this.
    return joinAndMinimize(q);
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
    // Let ⋀p_i = this, and q:r = conjunct.
    Principal qr = conjunct;

    // Can assume ⋀p_i ⋡ q:r. So ∀i, p_i ⋡ q:r.
    // Join in the new conjunct, while omitting any existing conjuncts that
    // the new conjunct statically acts for.
    // i.e., rewrite (⋀p_i) ∧ q:r as ⋀{p_i | q:r ⋡ p_i} ∧ q:r.
    // This is terribly inefficient. Maybe we shouldn't be so eager about doing
    // this.
    return joinAndMinimize(qr);
  }

  @Override
  Principal join(ConjunctivePrincipal conjunct) {
    Principal result = this;

    // Attempt to minimize the number of conjuncts. This is terribly inefficient
    // (quadratic). Maybe we shouldn't be so eager about doing this.
    for (Principal p : conjunct.conjuncts()) {
      result = PrincipalUtil.join(result, p);
    }
    return result;
  }

  @Override
  Principal join(DisjunctivePrincipal conjunct) {
    // Let ⋀p_i = this, and q∨r = conjunct.
    Principal qr = conjunct;

    // Can assume ⋀p_i ⋡ (q∨r). So ∀i, p_i ⋡ q∨r.
    // Join in the new conjunct, while omitting any existing conjuncts that
    // the new conjunct statically acts for.
    // i.e., rewrite (⋀p_i) ∧ q∨r as ⋀{p_i | q ⋡ p_i} ∧ q∨r.
    // This is terribly inefficient. Maybe we shouldn't be so eager about doing
    // this.
    return joinAndMinimize(qr);
  }

  /**
   * Joins in the principal {@code q}, while omitting from the result any
   * existing conjuncts that {@code q} statically acts for.
   * i.e., rewrites (⋀p_i) ∧ q as ⋀{p_i | q ⋡ p_i} ∧ q.
   */
  private Principal joinAndMinimize(Principal q) {
    Set<Principal> p = conjuncts;
    Set<Principal> newConjuncts = new HashSet<>(p.size() + 1);
    newConjuncts.add(q);
    for (Principal p_i : p) {
      if (!PrincipalUtil.staticallyActsFor(q, p_i)) {
        newConjuncts.add(p_i);
      }
    }

    if (newConjuncts.size() == 1) return q;
    return new ConjunctivePrincipal(newConjuncts, false);
  }

  @Override
  Principal meet(Principal disjunct) {
    if (disjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) disjunct).join(this);
    }

    // Disjunct is a primitive principal.
    // Let ⋀p_i = this, and q = disjunct.
    Principal q = disjunct;

    // Rewrite ⋀p_i ∨ q as ⋀{p_i ∨ q}.
    return meetAndRewrite(q);
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
    // Let ⋀p_i = this, and q:r = disjunct.
    Principal qr = disjunct;

    // Rewrite ⋀p_i ∨ q:r as ⋀{p_i ∨ q:r}.
    return meetAndRewrite(qr);
  }

  @Override
  Principal meet(ConjunctivePrincipal disjunct) {
    // Let ⋀p_i = this, and q∧r = disjunct.
    Principal qr = disjunct;

    // Rewrite ⋀p_i ∨ (q∧r) as ⋀{p_i ∨ (q∧r)}.
    return meetAndRewrite(qr);
  }

  @Override
  Principal meet(DisjunctivePrincipal disjunct) {
    // Let ⋀p_i = this, and q∨r = disjunct.
    Principal qr = disjunct;

    // Rewrite ⋀p_i ∨ q∨r as ⋀{p_i ∨ q∨r}.
    return meetAndRewrite(qr);
  }

  /**
   * Meets in the principal {@code q}, while rewriting to Join Normal Form.
   * i.e., rewrites (⋀p_i) ∨ q as ⋀{p_i ∨ q}.
   */
  private Principal meetAndRewrite(Principal q) {
    Set<Principal> p = conjuncts;
    Set<Principal> newConjuncts = new HashSet<>(p.size());
    for (Principal p_i : p) {
      newConjuncts.add(PrincipalUtil.meet(p_i, q));
    }
    return PrincipalUtil.join(newConjuncts);
  }
}