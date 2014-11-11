package fla.principals;

import java.util.Set;

public class PrincipalUtil {
  /**
   * Determines whether {@code superior} ≽ {@code inferior} in an empty
   * (i.e., static) context.
   */
  public static boolean staticallyActsFor(Principal superior, Principal inferior) {
    return inferior.actsFor(superior, inferior, null, null);
  }

  /**
   * Determines whether two principals are equivalent in an empty (i.e., static)
   * context. Principals are equivalent if they act for each other.
   */
  public static boolean staticallyEquivalent(Principal p, Principal q) {
    return staticallyActsFor(p, q) && staticallyActsFor(q, p);
  }

  /**
   * Asks {@code prover} whether it can find a proof for "{@code superior} ≽
   * {@code inferior}". See {@link fla.principals.Principal#actsFor(Principal,
   * Principal, Principal, Principal)}.
   */
  public static boolean actsFor(Principal prover, Principal superior,
      Principal inferior, Principal maxUsableLabel, Principal accessPolicy) {
    return prover.actsFor(superior, inferior, maxUsableLabel, accessPolicy);
  }

  /**
   * Asks {@code prover} whether it can find a proof for "{@code inferior} ⊑
   * {@code superior}". See {@link fla.principals.Principal#actsFor(Principal,
   * Principal, Principal, Principal)}.
   */
  public static boolean flowsTo(Principal prover, Principal inferior,
      Principal superior, Principal maxUsableLabel, Principal accessPolicy) {
    return prover.flowsTo(inferior, superior, maxUsableLabel, accessPolicy);
  }

  /**
   * Asks {@code prover} whether it can find the (direct) delegation "{@code
   * superior} ≽ {@code granter}" whose label does not exceed {@code maxLabel}.
   * See {@link fla.principals.PrimitivePrincipal#delegatesTo(Principal,
   * Principal, Principal, Principal)}.
   */
  public static boolean delegatesTo(PrimitivePrincipal prover,
      Principal granter, Principal superior, Principal maxLabel,
      Principal accessPolicy) {
    return prover.delegatesTo(granter, superior, maxLabel, accessPolicy);
  }

  /**
   * Determines whether two principals are equal. Principals are equal if they
   * are the same object, or if they mutually agree that they are equal.
   */
  public static boolean equals(Principal p, Principal q) {
    return p == q || p.equals(q) && q.equals(p);
  }

  /**
   * Asks {@code prover} whether it can find a proof for the equivalence of two
   * principals. Principals are equivalent if they act for each other. See
   * {@link fla.principals.Principal#actsFor(Principal, Principal, Principal,
   * Principal)} for documentation on {@code maxUsableLabel} and {@code
   * accessPolicy}.
   */
  public static boolean equivalent(Principal prover, Principal p, Principal q,
      Principal maxUsableLabel, Principal accessPolicy) {
    return actsFor(prover, p, q, maxUsableLabel, accessPolicy)
        && actsFor(prover, q, p, maxUsableLabel, accessPolicy);
  }

  /**
   * @return the bottom principal
   */
  public static BottomPrincipal bottom() {
    return BottomPrincipal.INSTANCE;
  }

  /**
   * @return the top principal
   */
  public static TopPrincipal top() {
    return TopPrincipal.INSTANCE;
  }

  /**
   * @return the confidentiality projection of the given principal
   */
  public static Principal confidentiality(Principal p) {
    return p.confidentiality();
  }

  /**
   * @return the integrity projection of the given principal
   */
  public static Principal integrity(Principal p) {
    return p.integrity();
  }

  /**
   * @return the ownership projection {@code p:q}
   */
  public static Principal project(Principal p, Principal q) {
    return p.project(q);
  }

  /**
   * @return the conjunction (join) of the given principals
   */
  public static Principal conjunction(Principal p, Principal q) {
    if (staticallyActsFor(p, q)) return p;
    if (staticallyActsFor(q, p)) return q;

    if (p instanceof NonPrimitivePrincipal)
      return ((NonPrimitivePrincipal) p).join(q);

    if (q instanceof NonPrimitivePrincipal)
      return ((NonPrimitivePrincipal) q).join(p);

    // Both arguments are primitive principals.
    return new ConjunctivePrincipal(p, q);
  }

  /**
   * @return the conjunction (join) of the given principals
   */
  public static Principal conjunction(Principal p, Principal q, Principal... r) {
    Principal result = conjunction(p, q);
    for (Principal r_i : r)
      result = conjunction(result, r_i);
    return result;
  }

  /**
   * @return the conjunction (join) of the given set of principals.
   *          If the set is empty, bottom is returned.
   */
  public static Principal conjunction(Set<Principal> p) {
    Principal result = bottom();
    for (Principal p_i : p) {
      result = join(result, p_i);
    }
    return result;
  }

  /**
   * @return the conjunction (join) of the given principals
   */
  public static Principal join(Principal p, Principal q) {
    return conjunction(p, q);
  }

  /**
   * @return the conjunction (join) of the given principals
   */
  public static Principal join(Principal p, Principal q, Principal... r) {
    return conjunction(p, q, r);
  }

  /**
   * @return the conjunction (join) of the given set of principals.
   *          If the set is empty, bottom is returned.
   */
  public static Principal join(Set<Principal> p) {
    return conjunction(p);
  }

  /**
   * @return the disjunction (meet) of the given principals
   */
  public static Principal disjunction(Principal p, Principal q) {
    if (staticallyActsFor(p, q)) return q;
    if (staticallyActsFor(q, p)) return p;

    if (p instanceof NonPrimitivePrincipal)
      return ((NonPrimitivePrincipal) p).meet(q);

    if (q instanceof NonPrimitivePrincipal)
      return ((NonPrimitivePrincipal) q).meet(p);

    // Both arguments are primitive principals.
    return new DisjunctivePrincipal(p, q);
  }

  /**
   * @return the disjunction (meet) of the given principals
   */
  public static Principal disjunction(Principal p, Principal q, Principal... r) {
    Principal result = disjunction(p, q);
    for (Principal r_i : r)
      result = disjunction(result, r_i);
    return result;
  }

  /**
   * @return the disjunction (meet) of the given set of principals.
   *          If the set is empty, top is returned.
   */
  public static Principal disjunction(Set<Principal> p) {
    Principal result = top();
    for (Principal p_i : p) {
      result = meet(result, p_i);
    }
    return result;
  }

  /**
   * @return the disjunction (meet) of the given principals
   */
  public static Principal meet(Principal p, Principal q) {
    return disjunction(p, q);
  }

  /**
   * @return the disjunction (meet) of the given principals
   */
  public static Principal meet(Principal p, Principal q, Principal... r) {
    return disjunction(p, q, r);
  }

  /**
   * @return the disjunction (meet) of the given set of principals.
   *          If the set is empty, bottom is returned.
   */
  public static Principal meet(Set<Principal> p) {
    return disjunction(p);
  }
}
