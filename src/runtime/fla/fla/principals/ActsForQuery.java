package fla.principals;

import java.util.HashSet;
import java.util.Set;

public final class ActsForQuery<Superior extends Principal, Inferior extends Principal> {
  /**
   * The principal being queried.
   */
  public final Principal receiver;
  public final Superior superior;
  public final Inferior inferior;
  public final Principal maxUsableLabel;
  public final Principal accessPolicy;

  /**
   * Memoized value for the lower bound of principals to which recursive queries
   * can be made.
   */
  private Principal recurseLowerBound;

  ActsForQuery(Principal receiver, Superior superior, Inferior inferior,
      Principal maxUsableLabel, Principal accessPolicy) {
    this.receiver = receiver;
    this.superior = superior;
    this.inferior = inferior;
    this.maxUsableLabel = maxUsableLabel;
    this.accessPolicy =
        accessPolicy == null ? null : accessPolicy.confidentiality();
  }

  /**
   * @return an ActsForQuery equivalent to the query {@code l1} ⊑ {@code l2},
   *          having label {@code maxUsableLabel} and access policy {@code
   *          accessPolicy}.
   */
  static ActsForQuery<Principal, Principal> flowsToQuery(Principal receiver,
      Principal l1, Principal l2, Principal maxUsableLabel,
      Principal accessPolicy) {
    // l1 ⊑ l2 is just l2→ ∧ l1← ≽ l1→ ∧ l2←.
    Principal superior =
        PrincipalUtil.conjunction(l2.confidentiality(), l1.integrity());
    Principal inferior =
        PrincipalUtil.conjunction(l1.confidentiality(), l2.integrity());
    return new ActsForQuery<>(receiver, superior, inferior, maxUsableLabel,
        accessPolicy);
  }

  ActsForQuery<Superior, Inferior> receiver(Principal receiver) {
    if (receiver == this.receiver) return this;
    return new ActsForQuery<>(receiver, superior, inferior, maxUsableLabel,
        accessPolicy);
  }

  <P extends Principal> ActsForQuery<P, Inferior> superior(P superior) {
    if (superior == this.superior) return (ActsForQuery<P, Inferior>) this;
    return new ActsForQuery<>(receiver, superior, inferior, maxUsableLabel,
        accessPolicy);
  }

  <P extends Principal> ActsForQuery<Superior, P> inferior(P inferior) {
    if (inferior == this.inferior) return (ActsForQuery<Superior, P>) this;
    return new ActsForQuery<>(receiver, superior, inferior, maxUsableLabel,
        accessPolicy);
  }

  /**
   * Meets the confidentiality projection of the given principal into the {@code
   * maxUsableLabel} and returns the result. This ensures that the given
   * principal is trusted with the confidentiality of the resulting query's
   * answer.
   */
  ActsForQuery<Superior, Inferior> meetLabel(Principal p) {
    // newMaxUsableLabel = maxUsableLabel ∨ (p→ ∧ ⊤←)
    Principal newMaxUsableLabel =
        PrincipalUtil.meet(maxUsableLabel, PrincipalUtil.join(
            p.confidentiality(), PrincipalUtil.top().integrity()));
    if (maxUsableLabel == newMaxUsableLabel) return this;
    return new ActsForQuery<>(receiver, superior, inferior, newMaxUsableLabel,
        accessPolicy);
  }

  @Override
  public int hashCode() {
    return receiver.hashCode()
        ^ superior.hashCode()
        ^ inferior.hashCode()
        ^ ((maxUsableLabel == null || accessPolicy == null) ? 0
            : (maxUsableLabel.hashCode() ^ accessPolicy.hashCode()));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ActsForQuery)) return false;
    ActsForQuery<?, ?> that = (ActsForQuery<?, ?>) obj;

    if (!PrincipalUtil.equals(this.receiver, that.receiver)) return false;
    if (!PrincipalUtil.equals(this.superior, that.superior)) return false;
    if (!PrincipalUtil.equals(this.inferior, that.inferior)) return false;

    boolean useDynamicContext = useDynamicContext();
    if (useDynamicContext != that.useDynamicContext()) return false;

    if (useDynamicContext) {
      return PrincipalUtil.equals(this.maxUsableLabel, that.maxUsableLabel)
          && PrincipalUtil.equals(this.accessPolicy, that.accessPolicy);
    }

    return true;
  }

  @Override
  public String toString() {
    return receiver + " -- " + superior + " ≽ " + inferior + " ({"
        + maxUsableLabel + "} @ (" + accessPolicy + "))";
  }

  boolean useDynamicContext() {
    return maxUsableLabel != null && accessPolicy != null;
  }

  /**
   * @return the lower bound (in the acts-for hierarchy) for principals to which
   *          recursive queries can be made
   */
  Principal recurseLowerBound() {
    if (recurseLowerBound != null) return recurseLowerBound;

    // The lower bound is {@code accessPolicy} ∧ {@code maxLabel}←.
    recurseLowerBound =
        PrincipalUtil.conjunction(accessPolicy, maxUsableLabel.integrity());
    return recurseLowerBound;
  }

  Set<NodePrincipal> componentPrimitivePrincipals() {
    Set<NodePrincipal> result = new HashSet<>();
    result.addAll(superior.componentNodePrincipals());
    result.addAll(inferior.componentNodePrincipals());
    result.addAll(maxUsableLabel.componentNodePrincipals());
    result.addAll(accessPolicy.componentNodePrincipals());
    return result;
  }

  /**
   * @return a robust version of this query. Strengthens the integrity component
   *          of {@code maxUsableLabel} to ensure the query is robust.
   */
  ActsForQuery<Superior, Inferior> makeRobust() {
    // The minimum integrity required for the query to be robust:
    // inferior← ∧ readersToWriters(inferior).
    Principal minInteg =
        PrincipalUtil.join(inferior.integrity(),
            PrincipalUtil.readersToWriters(inferior));
    Principal maxUsableLabel =
        PrincipalUtil.join(this.maxUsableLabel, minInteg);
    if (maxUsableLabel == this.maxUsableLabel) return this;
    return new ActsForQuery<>(receiver, superior, inferior, maxUsableLabel,
        accessPolicy);
  }
}
