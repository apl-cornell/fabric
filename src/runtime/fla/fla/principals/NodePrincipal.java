package fla.principals;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NodePrincipal extends PrimitivePrincipal {
  /**
   * Maps labels L to delegation pairs labelled with L.
   */
  private final Map<Principal, Set<Delegation<?, ?>>> delegations;

  public final String name;

  /**
   * @param name a name for this principal, for {@code toString()} purposes.
   */
  public NodePrincipal(String name) {
    this.delegations = new HashMap<>();
    this.name = name;
  }

  @Override
  final Map<Principal, Set<Delegation<?, ?>>> delegations() {
    return delegations;
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Stores a new delegation with this principal.
   *
   * @param inferior the principal granting privileges
   * @param superior the principal receiving new privileges
   * @param label the label on the new delegation
   */
  public final void addDelegatesTo(Principal inferior, Principal superior,
      Principal label) {
    Set<Delegation<?, ?>> entry = delegations.get(label);
    if (entry == null) {
      entry = new HashSet<>();
      delegations.put(label, entry);
    }

    entry.add(new Delegation<>(this, inferior, superior, label));
  }

  /**
   * Asks this principal whether it can find the (direct) delegation "{@code
   * superior} ≽ {@code granter}" whose label flows to {@code maxLabel}.
   * <p>
   * When making recursive calls, any principals receiving those calls must act
   * for {@code accessPolicy} and the integrity projection of {@code maxLabel}.
   * It is assumed that {@code accessPolicy} acts for the confidentiality
   * component of {@code maxLabel}. As such, no explicit checks will be made to
   * ensure that principals receiving recursive calls will act for this
   * component (by the above assumption, such a check would be subsumed by the
   * check against {@code accessPolicy}).
   * <p>
   * It is also assumed that {@code accessPolicy} has no integrity component.
   * <p>
   * A final assumption that is not explicitly checked is that this principal
   * acts for both {@code maxLabel} and {@code accessPolicy}. (Otherwise, we
   * have no business making this query to this principal!)
   * <p>
   * If {@code maxLabel} or {@code accessPolicy} is {@code null}, then a static
   * context is assumed (in which no dynamic delegations exists), and this
   * method returns false.
   *
   * @param granter the potential granter
   * @param superior the potential superior
   * @param maxLabel labels on delegations considered when satisfying this
   *          query must flow to this label
   * @param accessPolicy the confidentiality level of the query. This should
   *          act for the confidentiality component of {@code maxLabel}
   */
  public final boolean delegatesTo(Principal receiver, Principal granter,
      Principal superior, Principal maxLabel, Principal accessPolicy) {
    return delegatesTo(new ActsForQuery<>(receiver, superior, granter,
        maxLabel, accessPolicy));
  }

  @Override
  protected Set<NodePrincipal> componentNodePrincipals() {
    return Collections.singleton(this);
  }
}
