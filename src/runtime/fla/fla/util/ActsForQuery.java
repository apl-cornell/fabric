package fla.util;

import fla.principals.Principal;
import fla.principals.PrincipalUtil;

public class ActsForQuery<Superior extends Principal, Inferior extends Principal> {
  public final Superior superior;
  public final Inferior inferior;
  public final Principal maxUsableLabel;
  public final Principal accessPolicy;

  /**
   * Memoized value for the lower bound of principals to which recursive queries
   * can be made.
   */
  private Principal recurseLowerBound;

  public ActsForQuery(Superior superior, Inferior inferior,
      Principal maxUsableLabel, Principal accessPolicy) {
    this.superior = superior;
    this.inferior = inferior;
    this.maxUsableLabel = maxUsableLabel;
    this.accessPolicy =
        accessPolicy == null ? null : accessPolicy.confidentiality();
  }

  public <P extends Principal> ActsForQuery<P, Inferior> superior(P superior) {
    return new ActsForQuery<>(superior, inferior, maxUsableLabel, accessPolicy);
  }

  public <P extends Principal> ActsForQuery<Superior, P> inferior(P inferior) {
    return new ActsForQuery<>(superior, inferior, maxUsableLabel, accessPolicy);
  }

  @Override
  public int hashCode() {
    return superior.hashCode()
        ^ inferior.hashCode()
        ^ ((maxUsableLabel == null || accessPolicy == null) ? 0
            : (maxUsableLabel.hashCode() ^ accessPolicy.hashCode()));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ActsForQuery)) return false;
    ActsForQuery<?, ?> that = (ActsForQuery<?, ?>) obj;

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

  public boolean useDynamicContext() {
    return maxUsableLabel != null && accessPolicy != null;
  }

  /**
   * @return the lower bound (in the acts-for hierarchy) for principals to which
   *          recursive queries can be made
   */
  public Principal recurseLowerBound() {
    if (recurseLowerBound != null) return recurseLowerBound;

    // The lower bound is {@code accessPolicy} ∧ {@code maxLabel}←.
    recurseLowerBound =
        PrincipalUtil.conjunction(accessPolicy, maxUsableLabel.integrity());
    return recurseLowerBound;
  }
}
