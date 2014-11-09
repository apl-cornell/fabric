package fla.principals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A disjunction of a set of principals.
 */
public class DisjunctivePrincipal extends NonPrimitivePrincipal {

  protected final Set<Principal> disjuncts;

  DisjunctivePrincipal(Principal p, Principal q) {
    this(set(p, q), false);
  }

  DisjunctivePrincipal(Set<Principal> disjuncts) {
    this(disjuncts, true);
  }

  private DisjunctivePrincipal(Set<Principal> disjuncts, boolean copyNeeded) {
    this.disjuncts =
        Collections.unmodifiableSet(copyNeeded ? new HashSet<>(disjuncts)
            : disjuncts);
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

  public Set<Principal> disjuncts() {
    return disjuncts;
  }

  @Override
  public int hashCode() {
    return disjuncts.hashCode();
  }

  @Override
  public boolean equals(Principal p) {
    if (!(p instanceof DisjunctivePrincipal)) return false;
    return disjuncts.equals(((DisjunctivePrincipal) p).disjuncts());
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
    // Distribute projection to all disjuncts.
    // Let ⋁p_i = this, and q = projection.
    Set<Principal> p = disjuncts;
    Principal q = projection;

    // Rewrite (⋁p_i):q as ⋁(p_i:q).
    Set<Principal> disjuncts = new HashSet<>(p.size());
    for (Principal p_i : p) {
      disjuncts.add(p_i.project(q));
    }

    return PrincipalUtil.meet(disjuncts);
  }

  @Override
  Principal project(TopPrincipal projection) {
    // Use the TopPrincipal owner implementation.
    return projection.owner(this);
  }

  @Override
  Principal project(PrimitivePrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  Principal project(ConfPrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  Principal project(IntegPrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  Principal project(OwnedPrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  Principal project(ConjunctivePrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  Principal project(DisjunctivePrincipal projection) {
    // Use the generic implementation.
    return project((Principal) projection);
  }

  @Override
  public Principal owner(Principal owner) {
    // Let ⋁p_i = this, and q = owner.
    Set<Principal> p = disjuncts;
    Principal q = owner;

    // Rewrite q:(⋁p_i) as ⋁(q:p_i).
    Set<Principal> disjuncts = new HashSet<>(p.size());
    for (Principal p_i : p) {
      disjuncts.add(q.owner(p_i));
    }

    return PrincipalUtil.meet(disjuncts);
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
    // Let ⋁p_i = this, and q = conjunct.
    Principal q = conjunct;

    // Can assume q ⋡ ⋁p_i.
    // Have (⋁p_i) ∧ q, which is normal form.
    return new ConjunctivePrincipal(this, q);
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
    // Let ⋁p_i = this, and q:r = conjunct.
    Principal qr = conjunct;

    // Can assume q:r ⋡ ⋁p_i.
    // Have (⋁p_i) ∧ q:r, which is normal form.
    return new ConjunctivePrincipal(this, qr);
  }

  @Override
  Principal join(ConjunctivePrincipal conjunct) {
    // Use the ConjunctivePrincipal implementation.
    return conjunct.join(this);
  }

  @Override
  Principal join(DisjunctivePrincipal conjunct) {
    // Let ⋁p_i = this, and ⋁q_i = conjunct.
    // Have (⋁p_i) ∧ (⋁q_i), which is normal form.
    return new ConjunctivePrincipal(this, conjunct);
  }

  @Override
  Principal meet(Principal disjunct) {
    if (disjunct instanceof NonPrimitivePrincipal) {
      return ((NonPrimitivePrincipal) disjunct).meet(this);
    }

    // Conjunct is a primitive principal.
    // Let ⋁p_i = this, and q = disjunct.
    Principal q = disjunct;

    // Can assume q ⋡ ⋁p_i. So ∀i, q ⋡ p_i.
    // Meet in the new disjunct, while omitting any existing disjuncts that
    // statically act for the new disjunct.
    // i.e., rewrite (⋁p_i) ∨ q as ⋁{p_i | p_i ⋡ q} ∨ q.
    // This is terribly inefficient. Maybe we shouldn't be so eager about doing
    // this.
    return meetAndMinimize(q);
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
    // Let ⋁p_i = this, and q:r = disjunct.
    Principal qr = disjunct;

    // Can assume q:r ⋡ ⋁p_i. So ∀i, q:r ⋡ p_i.
    // Meet in the new disjunct, while omitting any existing disjuncts that
    // statically act for the new disjunct.
    // i.e., rewrite (⋁p_i) ∨ q:r as ⋁{p_i | p_i ⋡ q:r} ∨ q:r.
    // This is terribly inefficient. Maybe we shouldn't be so eager about doing
    // this.
    return meetAndMinimize(qr);
  }

  @Override
  Principal meet(ConjunctivePrincipal disjunct) {
    // Use the ConjunctivePrincipal implementation.
    return disjunct.meet(this);
  }

  @Override
  Principal meet(DisjunctivePrincipal disjunct) {
    Principal result = this;

    // Attempt to minimize the number of disjuncts. This is terribly inefficient
    // (quadratic). Maybe we shouldn't be so eager about doing this.
    for (Principal p : disjunct.disjuncts()) {
      result = PrincipalUtil.meet(result, p);
    }
    return result;
  }

  /**
   * Meets in the principal {@code q}, while omitting from the result any
   * existing disjuncts that statically act for {@code q}.
   * i.e., rewrites (⋁p_i) ∨ q as ⋁{p_i | p_i ⋡ q} ∨ q.
   */
  private Principal meetAndMinimize(Principal q) {
    Set<Principal> p = disjuncts;
    Set<Principal> newDisjuncts = new HashSet<>(p.size() + 1);
    newDisjuncts.add(q);
    for (Principal p_i : p) {
      if (!PrincipalUtil.staticallyActsFor(p_i, q)) {
        newDisjuncts.add(p_i);
      }
    }

    if (newDisjuncts.size() == 1) return q;
    return new DisjunctivePrincipal(newDisjuncts, false);
  }

  @Override
  Set<PrimitivePrincipal> componentPrimitivePrincipals() {
    Set<PrimitivePrincipal> result = new HashSet<>();
    for (Principal p : disjuncts) {
      result.addAll(p.componentPrimitivePrincipals());
    }
    return result;
  }
}
