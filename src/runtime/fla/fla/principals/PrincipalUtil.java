package fla.principals;

import java.util.Set;

public class PrincipalUtil {
  public static final Principal PUBLIC_TRUSTED = conjunction(bottom()
      .confidentiality(), top().integrity());
  public static final Principal SECRET_UNTRUSTED = conjunction(top()
      .confidentiality(), bottom().integrity());

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
   * See {@link fla.principals.NodePrincipal#delegatesTo(Principal,
   * Principal, Principal, Principal, Principal)}.
   */
  public static boolean delegatesTo(Principal receiver, NodePrincipal prover,
      Principal granter, Principal superior, Principal maxLabel,
      Principal accessPolicy) {
    return prover.delegatesTo(receiver, granter, superior, maxLabel,
        accessPolicy);
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

  public static Principal publicTrusted() {
    return PUBLIC_TRUSTED;
  }

  public static Principal secretUntrusted() {
    return SECRET_UNTRUSTED;
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

  /**
   * @return the readers-to-writers mapping of the given principal. This is
   *          defined as the confidentiality projection of the principal, viewed
   *          as an integrity projection (i.e., with the arrow "flipped").
   *          More precisely, if {@code p = a→ ∧ b←} is in normal form, then
   *          {@code readersToWriters(p)} is {@code a←}.
   */
  public static Principal readersToWriters(Principal p) {
    Principal confP = p.confidentiality();
    if (confP instanceof ConfPrincipal) {
      return ((ConfPrincipal) confP).base().integrity();
    }

    return confP.integrity();
  }

  /**
   * @return the information flow join of the given principals
   */
  public static Principal flowJoin(Principal p, Principal q) {
    Principal conf = PrincipalUtil.conjunction(p, q).confidentiality();
    Principal integ = PrincipalUtil.disjunction(p, q).integrity();
    return PrincipalUtil.conjunction(conf, integ);
  }

  /**
   * @return the information flow join of the given principals
   */
  public static Principal flowJoin(Principal p, Principal q, Principal... r) {
    Principal conf = PrincipalUtil.conjunction(p, q, r).confidentiality();
    Principal integ = PrincipalUtil.disjunction(p, q, r).integrity();
    return PrincipalUtil.conjunction(conf, integ);
  }

  /**
   * @return the information flow join of the given set of principals.
   *          If the set is empty, bottom is returned.
   */
  public static Principal flowJoin(Set<Principal> p) {
    Principal conf = PrincipalUtil.conjunction(p).confidentiality();
    Principal integ = PrincipalUtil.disjunction(p).integrity();
    return PrincipalUtil.conjunction(conf, integ);
  }

  /**
   * @return the information flow join of the given principals
   */
  public static Principal flowMeet(Principal p, Principal q) {
    Principal conf = PrincipalUtil.disjunction(p, q).confidentiality();
    Principal integ = PrincipalUtil.conjunction(p, q).integrity();
    return PrincipalUtil.conjunction(conf, integ);
  }

  /**
   * @return the information flow join of the given principals
   */
  public static Principal flowMeet(Principal p, Principal q, Principal... r) {
    Principal conf = PrincipalUtil.conjunction(p, q, r).confidentiality();
    Principal integ = PrincipalUtil.disjunction(p, q, r).integrity();
    return PrincipalUtil.conjunction(conf, integ);
  }

  /**
   * @return the information flow join of the given set of principals.
   *          If the set is empty, bottom is returned.
   */
  public static Principal flowMeet(Set<Principal> p) {
    Principal conf = PrincipalUtil.conjunction(p).confidentiality();
    Principal integ = PrincipalUtil.disjunction(p).integrity();
    return PrincipalUtil.conjunction(conf, integ);
  }

  /**
   * Add the delegation <code>superior</code> ≽ <code>inferior</code>
   * to <code>host</code>'s delegation set with label <code>label</code>
   */
  public static void addActsForDelegation(Principal host, Principal superior,
      Principal inferior, Principal label) {
    if (host instanceof NodePrincipal) {
      NodePrincipal node = (NodePrincipal) host;
      node.addDelegatesTo(inferior, superior, label);
    } else throw new Error("Host is not a NodePrincipal");
  }

  /**
   * Add the delegation <code>permissive</code> ⊑ <code>restrictive</code>
   * to <code>host</code>'s delegation set with label <code>label</code>
   */
  public static void addFlowsToDelegation(Principal host, Principal permissive,
      Principal restrictive, Principal label) {
    if (host instanceof NodePrincipal) {
      NodePrincipal node = (NodePrincipal) host;
      Principal inferior =
          conjunction(permissive.confidentiality(), restrictive.integrity());
      Principal superior =
          conjunction(restrictive.confidentiality(), permissive.integrity());
      node.addDelegatesTo(inferior, superior, label);
    } else throw new Error("Host is not a NodePrincipal");
  }

}
