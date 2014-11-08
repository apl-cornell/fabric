package fla.util;

import fla.Label;
import fla.principals.Principal;
import fla.principals.PrincipalUtil;

public class ActsForQuery {
  public final Principal superior;
  public final Principal inferior;
  public final Label maxUsableLabel;
  public final Principal accessPolicy;

  public ActsForQuery(Principal superior, Principal inferior,
      Label maxUsableLabel, Principal accessPolicy) {
    this.superior = superior;
    this.inferior = inferior;
    this.maxUsableLabel = maxUsableLabel;
    this.accessPolicy =
        accessPolicy == null ? null : accessPolicy.confidentiality();
  }

  public ActsForQuery superior(Principal superior) {
    return new ActsForQuery(superior, inferior, maxUsableLabel, accessPolicy);
  }

  public ActsForQuery inferior(Principal inferior) {
    return new ActsForQuery(superior, inferior, maxUsableLabel, accessPolicy);
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
    ActsForQuery that = (ActsForQuery) obj;

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
}
