package fla.principals;

import java.util.Collections;
import java.util.Set;

import fla.util.ActsForQuery;
import fla.util.DelegationPair;

/**
 * An abstract class for principals constructed from other principals through
 * join, meet, and projection operators.
 */
abstract class NonPrimitivePrincipal extends Principal {
  /**
   * @return the join of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal join(Principal p);

  /**
   * @return the join of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal join(ConfPrincipal p);

  /**
   * @return the join of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal join(IntegPrincipal p);

  /**
   * @return the join of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal join(OwnedPrincipal p);

  /**
   * @return the join of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal join(ConjunctivePrincipal p);

  /**
   * @return the join of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal join(DisjunctivePrincipal p);

  /**
   * @return the meet of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal meet(Principal p);

  /**
   * @return the meet of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal meet(ConfPrincipal p);

  /**
   * @return the meet of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal meet(IntegPrincipal p);

  /**
   * @return the meet of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal meet(OwnedPrincipal p);

  /**
   * @return the meet of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal meet(ConjunctivePrincipal p);

  /**
   * @return the meet of this principal with {@code p}. Callers should handle
   *          the case where {@code PrincipalUtil.staticallyActsFor(this, p)} or
   *          {@code PrincipalUtil.staticallyActsfor(p, this)}.
   */
  abstract Principal meet(DisjunctivePrincipal p);

  @Override
  final Set<DelegationPair> usableDelegations(ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    // Only primitive principals store delegations.
    return Collections.emptySet();
  }
}
