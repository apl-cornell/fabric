package fla.principals;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fabric.common.exceptions.InternalError;
import fla.util.ActsForProof;
import fla.util.ConfProjectionProof;
import fla.util.DelegatesProof;
import fla.util.FromDisjunctProof;
import fla.util.IntegProjectionProof;
import fla.util.MeetToOwnerProof;
import fla.util.OwnedPrincipalsProof;
import fla.util.ReflexiveProof;
import fla.util.ToConjunctProof;
import fla.util.TransitiveProof;

public abstract class Principal {
  /**
   * @return the confidentiality projection of this principal. The returned
   *          principal will be in normal form if this principal is.
   */
  public abstract Principal confidentiality();

  /**
   * @return the integrity projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal integrity();

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(Principal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  abstract Principal project(TopPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  abstract Principal project(PrimitivePrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  abstract Principal project(ConfPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  abstract Principal project(IntegPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  abstract Principal project(OwnedPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  abstract Principal project(ConjunctivePrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  abstract Principal project(DisjunctivePrincipal projection);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(Principal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  abstract Principal owner(TopPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  abstract Principal owner(PrimitivePrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  abstract Principal owner(ConfPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  abstract Principal owner(IntegPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  abstract Principal owner(OwnedPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  abstract Principal owner(ConjunctivePrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  abstract Principal owner(DisjunctivePrincipal owner);

  /**
   * @return {@code true} iff the given object is a syntactically equivalent
   *          principal.
   */
  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof Principal) return equals((Principal) obj);
    return false;
  }

  /**
   * @return {@code true} iff the given principal is syntactically equivalent to
   *          this one.
   */
  public abstract boolean equals(Principal p);

  /**
   * @return the set of {@code PrimitivePrincipal}s that appear as components of
   *          this principal.
   */
  protected abstract Set<NodePrincipal> componentNodePrincipals();

  /**
   * @return the delegation set stored at this principal, represented as a map
   *          from labels to delegations at that label. If this principal
   *          supports the storage of delegations, then modifications to the
   *          returned map should reflect modifications to the stored
   *          delegations. Otherwise, an empty unmodifiable map should be
   *          returned.
   */
  abstract Map<Principal, Set<Delegation<?, ?>>> delegations();

  /**
   * Obtains a set of delegations that are stored at this principal and can be
   * used to answer the given query. Each delegation is mapped to a proof
   * showing that it can be used.
   * <p>
   * A delegation may be used to answer a query if the label on the delegation
   * flows to {@code query.maxLabel}.
   * <p>
   * If {@code query.maxUsableLabel} or {@code query.accessPolicy} is {@code
   * null}, then a static context is assumed (in which no dynamic delegations
   * exists), and this method returns an empty set.
   *
   * @param searchState the state of the proof search being made
   */
  private final Map<Delegation<?, ?>, ActsForProof<?, ?>> usableDelegations(
      ActsForQuery<?, ?> query, ProofSearchState searchState) {
    if (!query.useDynamicContext()) {
      // Static context. No dynamic delegations should be used.
      return Collections.emptyMap();
    }

    Map<Delegation<?, ?>, ActsForProof<?, ?>> result = new HashMap<>();

    for (Map.Entry<Principal, Set<Delegation<?, ?>>> entry : delegations()
        .entrySet()) {
      Principal delegationLabel = entry.getKey();
      Principal queryLabel = query.maxUsableLabel;

      // Can use delegations if we can robustly show
      //   delegationLabel ⊑ queryLabel
      //     with queryLabel
      //     at (query.accessPolicy ∧ delegationLabel→).
      //
      // The confidentiality of the answer to this subquery is the same as that
      // of the answer to the top-level query:
      //   queryLabel→.
      //
      // To ensure the integrity of the answer to the top-level query, the
      // integrity of the answer to this subquery acts for queryLabel←.
      //
      // To ensure the robustness of this subquery, the integrity of its answer
      // may be higher; this is encapsulated in the "makeRobust()" call below.
      //
      // The access policy on this subquery ensures the confidentiality of both
      // the top-level query and the delegation.
      ActsForQuery<?, ?> subquery =
          ActsForQuery.flowsToQuery(
              this,
              delegationLabel,
              queryLabel,
              queryLabel,
              PrincipalUtil.conjunction(query.accessPolicy,
                  delegationLabel.confidentiality())).makeRobust();
      ProofSearchResult<?, ?> usabilityProofResult =
          actsForProof(this, subquery, searchState);
      switch (usabilityProofResult.type) {
      case SUCCESS:
        for (Delegation<?, ?> delegation : entry.getValue()) {
          result.put(delegation, usabilityProofResult.proof);
        }
        break;

      case PRUNED:
        // Can make progress if the subquery can.
        searchState.progressCondition
            .or(usabilityProofResult.progressCondition);
        break;

      case FAILED:
        break;
      }
    }

    return result;
  }

  /**
   * Obtains a set of principals to whom the given query can be forwarded. A
   * query can be forwarded to any PrimitivePrincipal {@code p} that
   * <ul>
   * <li>appears as a component of this principal (if this is a {@code
   * NonPrimitivePrincipal}),</li>
   * <li>appears as a component of the superior or inferior parts of the
   * query,</li>
   * <li>appears as a component of a <i>usable</i> delegation stored at this
   * principal, or</li>
   * <li>appears in the search state as a participant in the query,</li>
   * </ul>
   * such that {@code p} acts for {@code query.accessPolicy} and the
   * integrity projection of {@code query.maxLabel}.
   * <p>
   * If {@code query.maxUsableLabel} or {@code query.accessPolicy} is {@code
   * null}, then a static context is assumed (in which no dynamic delegations
   * exists, so forwarding of queries is not useful), and this method returns an
   * empty set.
   *
   * @param searchState the state of the proof search being made
   */
  private final Set<NodePrincipal> askablePrincipals(ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    if (!query.useDynamicContext()) {
      // Static context. No dynamic delegations should be used.
      return Collections.emptySet();
    }

    // Construct an unfiltered set of candidates.
    // Start with all participants in the search state.
    Set<NodePrincipal> unfilteredResult =
        new HashSet<>(searchState.allParticipants);

    // Add primitive principals that appear as a component of this principal.
    unfilteredResult.addAll(componentNodePrincipals());

    // Add primitive principals that appears as a component of the query.
    unfilteredResult.addAll(query.componentPrimitivePrincipals());

    // Add components of usable delegations.
    for (Delegation<?, ?> delegation : usableDelegations(query, searchState)
        .keySet()) {
      // XXX Dropping usability proofs. Should use them to prove correctness of the return value.
      unfilteredResult.addAll(delegation.componentPrimitivePrincipals());
    }

    // Filter.
    return removeUnaskablePrincipals(unfilteredResult, query, searchState);
  }

  /**
   * Removes from the given set all principals that cannot be asked the given
   * query. A principal can be asked if it acts for {@code query.accessPolicy}
   * and the integrity projection of {@code query.maxLabel}.
   *
   * @param searchState the state of the proof search being made
   *
   * @return the object {@code set}, with all unaskable principals having been
   *          removed
   */
  private final Set<NodePrincipal> removeUnaskablePrincipals(
      Set<NodePrincipal> set, ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    for (Iterator<NodePrincipal> it = set.iterator(); it.hasNext();) {
      PrimitivePrincipal p = it.next();

      // See if p ≽ query.recurseLowerBound(), robustly.
      ActsForQuery<?, ?> subquery =
          query.inferior(query.recurseLowerBound()).superior(p).makeRobust();
      ProofSearchResult<?, ?> result =
          actsForProof(this, subquery, searchState);
      switch (result.type) {
      case SUCCESS:
        break;

      case PRUNED:
        // Can make progress if the subquery can.
        searchState.progressCondition.or(result.progressCondition);

        //$FALL-THROUGH$
      case FAILED:
        // Unable to prove the askability of p. Remove.
        it.remove();
        break;
      }
    }

    return set;
  }

  /**
   * Asks this principal whether it can find the (direct) delegation "{@code
   * query.superior} ≽ {@code query.inferior}" whose label flows to {@code
   * query.maxLabel}.
   * <p>
   * When making recursive calls, any principals receiving those calls must act
   * for {@code query.accessPolicy} and the integrity projection of {@code
   * query.maxLabel}. It is assumed that {@code query.accessPolicy} acts for the
   * confidentiality component of {@code query.maxLabel}. As such, no explicit
   * checks will be made to ensure that principals receiving recursive calls
   * will act for this component (by the above assumption, such a check would be
   * subsumed by the check against {@code query.accessPolicy}).
   * <p>
   * It is also assumed that {@code query.accessPolicy} has no integrity
   * component.
   * <p>
   * A final assumption that is not explicitly checked is that this principal
   * acts for both {@code query.maxLabel} and {@code query.accessPolicy}.
   * (Otherwise, we have no business making this query to this principal!)
   * <p>
   * If {@code query.maxUsableLabel} or {@code query.accessPolicy} is {@code
   * null}, then a static context is assumed (in which no dynamic delegations
   * exists), and this method returns false.
   */
  final boolean delegatesTo(ActsForQuery<?, ?> query) {
    for (Delegation<?, ?> delegation : usableDelegations(query,
        new ProofSearchState()).keySet()) {
      if (PrincipalUtil.equals(query.inferior, delegation.inferior)
          && PrincipalUtil.equals(query.superior, delegation.superior))
        return true;
    }

    return false;
  }

  /**
   * Asks this principal whether it can find a proof for "{@code superior} ≽
   * {@code inferior}". Labels on the delegations used in this proof must flow
   * to {@code maxUsableLabel}.
   * <p>
   * When making recursive calls, any principals receiving those calls must act
   * for {@code accessPolicy} and the integrity projection of {@code
   * maxUsableLabel}. It is assumed that {@code accessPolicy} acts for the
   * confidentiality component of {@code maxUsableLabel}. As such, no explicit
   * checks will be made to ensure that principals receiving recursive calls
   * will act for this component (by the above assumption, such a check would
   * be subsumed by the check against {@code accessPolicy}).
   * <p>
   * It is also assumed that {@code accessPolicy} has no integrity component.
   * <p>
   * A final assumption that is not explicitly checked is that this principal
   * acts for both {@code maxUsableLabel} and {@code accessPolicy}. (Otherwise,
   * we have no business making this query to this principal!)
   * <p>
   * If {@code maxUsableLabel} or {@code accessPolicy} is {@code null}, then a
   * static context is assumed, and dynamic delegations will be ignored.
   *
   * @param superior the potential superior
   * @param inferior the potential inferior
   * @param maxUsableLabel labels on delegations used in the proof must flow to
   *          this label
   * @param accessPolicy the confidentiality level of the query. This should
   *          act for the confidentiality component of {@code maxUsableLabel}
   */
  public final boolean actsFor(Principal superior, Principal inferior,
      Principal maxUsableLabel, Principal accessPolicy) {
    return actsForProof(new ActsForQuery<>(this, superior, inferior,
        maxUsableLabel, accessPolicy)) != null;
  }

  public final boolean flowsTo(Principal inferior, Principal superior,
      Principal maxUsableLabel, Principal accessPolicy) {
    return actsForProof(ActsForQuery.flowsToQuery(this, inferior, superior,
        maxUsableLabel, accessPolicy)) != null;
  }

  /**
   * Revokes delegations of the form <code>superior ≽ inferior {L}</code>, where
   * {@code label ⊑ L}, at all principals reachable from this one.
   *
   * @param inferior the principal whose delegations are being revoked
   * @param superior the principal whose privileges are being revoked
   * @param label the label on the revocation. Labels on revoked delegations
   *          must flow to this label.
   */
  public final void removeDelegatesTo(Principal inferior, Principal superior,
      Principal label) {
    removeDelegatesTo(this, new RevocationRequest(inferior, superior, label),
        Collections.singleton(this));
  }

  /**
   * Recursively calls {@link Principal#removeDelegatesTo(Principal,
   * RevocationRequest, Set)} on those elements of {@code candidates} for which
   * it is safe to do so.
   *
   * @param req the revocation request
   * @param candidates the set of candidates on which to recurse
   * @param visited the set of principals already called so far
   * @param delegationLabel the label on the delegations from which the
   *          candidate set was derived. This can be {@code null} if the
   *          candidate set was not derived from any delegations, in which case
   *          this will be treated as ⊤←.
   * @return the set of candidates to which recursive calls were made. In
   *          principle, this will have label {@code req.label ∧
   *          delegationLabel→ ∧ (readersToWriters(req.label) ∨
   *          readersToWriters(delegationLabel))}.
   *
   */
  private final Set<NodePrincipal> recurseRemoveDelegatesTo(
      RevocationRequest req, Set<NodePrincipal> candidates,
      Set<Principal> visited, Principal delegationLabel) {
    if (delegationLabel == null) {
      // Default to bottom confidentiality, top integrity.
      delegationLabel = PrincipalUtil.top().integrity();
    }

    // Remove all candidates that we can't safely visit.
    for (Iterator<NodePrincipal> it = candidates.iterator(); it.hasNext();) {
      PrimitivePrincipal candidate = it.next();
      {
        // We can recurse only if we can robustly show:
        //   candidate ≽ req.label→
        //     with (req.label ∧ delegationLabel→)
        //     at req.label→ ∧ delegationLabel→.
        //
        // Because the confidentiality of the recursive calls will be
        //   req.label→ ∧ delegationLabel→,
        // this the confidentiality of the query result.
        //
        // To ensure the integrity of the revocation request, the integrity of
        // the query result acts for req.label←.
        //
        // To ensure the robustness of this query, the integrity of its result
        // may be higher; this is encapsulated in the "makeRobust()" call below.
        // NB: this is why, for completeness, we should not combine this query
        // with the one below.
        //
        // The access policy ensures the confidentiality of both the revocation
        // request and the delegation.
        Principal superior = candidate;
        Principal inferior = req.label.confidentiality();
        Principal label =
            PrincipalUtil.conjunction(req.label,
                delegationLabel.confidentiality());
        Principal accessPolicy =
            PrincipalUtil.conjunction(req.label.confidentiality(),
                delegationLabel.confidentiality());
        ActsForQuery<Principal, Principal> query =
            new ActsForQuery<>(this, superior, inferior, label, accessPolicy)
                .makeRobust();
        if (actsForProof(query) == null) {
          // Can't recurse to this candidate.
          it.remove();
          continue;
        }
      }

      {
        // We can recurse only if we can show
        //   candidate ≽ delegationLabel→
        //     with (req.label ∧ delegationLabel→)
        //     at req.label→ ∧ delegationLabel→.
        //
        // Because the confidentiality of the recursive calls will be
        //   req.label→ ∧ delegationLabel→,
        // this the confidentiality of the query result.
        //
        // To ensure the integrity of the revocation request, the integrity of
        // the query result acts for req.label←.
        //
        // To ensure robustness of this query, the integrity its result may be
        // higher; this is encapsulated in the "makeRobust()" call below.
        // NB: this is why, for completeness, we should not combine this query
        // with the one above.
        //
        // The access policy ensures the confidentiality of both the revocation
        // request and the delegation.
        Principal superior = candidate;
        Principal inferior = delegationLabel.confidentiality();
        Principal label =
            PrincipalUtil.conjunction(req.label,
                delegationLabel.confidentiality());
        Principal accessPolicy =
            PrincipalUtil.conjunction(req.label.confidentiality(),
                delegationLabel.confidentiality());
        ActsForQuery<Principal, Principal> query =
            new ActsForQuery<>(this, superior, inferior, label, accessPolicy)
                .makeRobust();
        if (actsForProof(query) == null) {
          // Can't recurse to this candidate.
          it.remove();
          continue;
        }
      }
    }

    // The contents of candidates has label
    //   req.label ∧ delegationLabel→,
    // which every element of candidates now acts for, so it is safe to include
    // it as part of the visited set for recursive calls made to those
    // candidates.
    visited = new HashSet<>(visited);
    visited.addAll(candidates);
    visited = Collections.unmodifiableSet(visited);

    // Actually recurse. Recursive calls will have label
    //   req.label ∧ delegationLabel→
    for (Principal p : candidates) {
      p.removeDelegatesTo(this, req.joinLabel(delegationLabel), visited);
    }

    return candidates;
  }

  /**
   * Revokes delegations matching the given request, at all principals reachable
   * from this one.
   *
   * @param visited a set of principals that have already received the request,
   *          including {@code this}
   */
  private final void removeDelegatesTo(Principal caller, RevocationRequest req,
      Set<Principal> visited) {
    // If this is a remote call, ensure the caller acts for the request label's
    // integrity.
    if (caller != this) {
      req = req.meetIntegrity(caller);
    }

    // Recurse into other principals that we haven't visited yet.
    // First, consider components of this principal and of the request itself.
    {
      Set<NodePrincipal> candidates = new HashSet<>();
      candidates.addAll(componentNodePrincipals());
      candidates.addAll(req.componentPrimitivePrincipals());
      candidates.removeAll(visited);

      // The set of candidates we actually recurse to will have label
      //   {req.label ∧ readersToWriters(req.label)},
      // so it is safe to include it as part of the visited set for all
      // recursive calls we make.
      visited = new HashSet<>(visited);
      visited.addAll(recurseRemoveDelegatesTo(req, candidates, visited, null));
      visited = Collections.unmodifiableSet(visited);
    }

    // Now, consider recursing into components of any delegations we store.
    for (Map.Entry<Principal, Set<Delegation<?, ?>>> entry : delegations()
        .entrySet()) {
      Principal label = entry.getKey();
      Set<Delegation<?, ?>> delegations = entry.getValue();

      // Get a set of candidates for recursing into.
      Set<NodePrincipal> candidates = new HashSet<>();
      for (Delegation<?, ?> delegation : delegations) {
        candidates.addAll(delegation.componentPrimitivePrincipals());
      }
      candidates.removeAll(visited);

      // Recurse.
      recurseRemoveDelegatesTo(req, candidates, visited, label);
    }

    // Remove any local delegations that match.
    for (Iterator<Map.Entry<Principal, Set<Delegation<?, ?>>>> entryIt =
        delegations().entrySet().iterator(); entryIt.hasNext();) {
      Map.Entry<Principal, Set<Delegation<?, ?>>> entry = entryIt.next();
      Principal label = entry.getKey();

      // Delegations at this level can match only if we can robustly show:
      //   req.label ⊑ label
      //     with req.label
      //     at req.label→ ∧ label→.
      //
      // Because the effect of removal will be visible at req.label→, this is
      // the confidentiality of the query result.
      //
      // To ensure the integrity of the revocation request, the integrity of the
      // query result acts for req.label←.
      //
      // For robustness, the integrity of this query's result may be higher;
      // this is encapsulated in the "makeRobust()" call below.
      //
      // The access policy ensures the confidentiality of both the revocation
      // request and the delegation.
      Principal inferior = req.label;
      Principal superior = label;
      Principal queryLabel = req.label;
      Principal accessPolicy =
          PrincipalUtil.join(label.confidentiality(),
              req.label.confidentiality());
      if (actsForProof(ActsForQuery.flowsToQuery(this, inferior, superior,
          queryLabel, accessPolicy).makeRobust()) == null) {
        continue;
      }

      Set<Delegation<?, ?>> delegations = entry.getValue();
      for (Iterator<Delegation<?, ?>> it = delegations.iterator(); it.hasNext();) {
        Delegation<?, ?> delegation = it.next();
        if (!PrincipalUtil.equals(delegation.inferior, req.inferior)) continue;
        if (!PrincipalUtil.equals(delegation.superior, req.superior)) continue;
        it.remove();
      }

      if (delegations.isEmpty()) entryIt.remove();
    }
  }

  /**
   * Asks this principal for a proof of "{@code query.superior} ≽ {@code
   * query.inferior}". Labels on the delegations used in this proof must flow to
   * {@code query.maxUsableLabel}.
   * <p>
   * When making recursive calls, any principals receiving those calls must act
   * for {@code query.accessPolicy} and the integrity projection of {@code
   * query.maxUsableLabel}. It is assumed that {@code query.accessPolicy} acts
   * for the confidentiality component of {@code query.maxUsableLabel}. As such,
   * no explicit checks will be made to ensure that principals receiving
   * recursive calls will act for this component (by the above assumption, such
   * a check would be subsumed by the check against {@code query.accessPolicy}).
   * <p>
   * It is also assumed that {@code accessPolicy} has no integrity component.
   * <p>
   * A final assumption that is not explicitly checked is that this principal
   * acts for both {@code query.maxUsableLabel} and {@code query.accessPolicy}.
   * (Otherwise, we have no business making this query to this principal!)
   * <p>
   * If {@code query.maxUsableLabel} or {@code query.accessPolicy} is {@code
   * null}, then a static context is assumed, and dynamic delegations will be
   * ignored.
   */
  private final <Superior extends Principal, Inferior extends Principal> ActsForProof<Superior, Inferior> actsForProof(
      ActsForQuery<Superior, Inferior> query) {
    // TODO Verify proof?
    return actsForProof(this, query, new ProofSearchState()).proof;
  }

  /**
   * Answers the query while trying to use the cache in the search state. On a
   * cache miss, a proof search is made using {@code findActsForProof()}, and
   * the result is added to the cache.
   *
   * @param caller the principal making this call
   * @param searchState records the goals that we are in the middle of
   *          attempting, and any cached intermediate results
   */
  private final <Superior extends Principal, Inferior extends Principal> ProofSearchResult<Superior, Inferior> actsForProof(
      Principal caller, ActsForQuery<Superior, Inferior> query,
      ProofSearchState searchState) {
    // If this is a remote call, ensure the caller is trusted with the
    // confidentiality of the query's answer.
    if (caller != this) {
      query = query.meetLabel(caller);
    }

    // Check the cache.
    ProofSearchResult<Superior, Inferior> cachedResult =
        searchState.cacheLookup(query);

    if (cachedResult != null) {
      // Cache hit.
      return cachedResult;
    }

    // Cache miss. Search for a proof.
    ProofSearchResult<Superior, Inferior> result =
        findActsForProof(caller, query, searchState);

    searchState.cache(query, result);
    return result;
  }

  /**
   * Searches for an ActsForProof, assuming a cache miss. Callers are
   * responsible for caching the results.
   *
   * @param caller the principal making this call
   * @param searchState records the goals that we are in the middle of
   *          attempting, and any cached intermediate results
   * @param query the query to satisfy. It is assumed that the caller acts for
   *          the label in the query.
   */
  private final <Superior extends Principal, Inferior extends Principal> ProofSearchResult<Superior, Inferior> findActsForProof(
      Principal caller, ActsForQuery<Superior, Inferior> query,
      ProofSearchState searchState) {
    final boolean forwarded = caller != this;

    // If this is a forwarded query, just skip to the part that uses
    // delegations directly. The caller should have done the rest already.
    if (!forwarded) {
      // Try the dumb things first.
      if (query.inferior instanceof BottomPrincipal
          || query.superior instanceof TopPrincipal) {
        return new ProofSearchResult<>(new DelegatesProof<>(query));
      }

      if (PrincipalUtil.equals(query.inferior, query.superior)) {
        return new ProofSearchResult<>(new ReflexiveProof<>(query));
      }
    }

    // Add the query to the search state.
    searchState = new ProofSearchState(searchState, query);

    // See if we can satisfy the query directly with a delegation.
    for (Map.Entry<Delegation<?, ?>, ActsForProof<?, ?>> entry : usableDelegations(
        query, searchState).entrySet()) {
      final Delegation<?, ?> delegation = entry.getKey();
      final ActsForProof<?, ?> usabilityProof = entry.getValue();
      if (PrincipalUtil.equals(query.superior, delegation.superior)
          && PrincipalUtil.equals(query.inferior, delegation.inferior)) {
        return new ProofSearchResult<>(
            (ActsForProof<Superior, Inferior>) new DelegatesProof<>(delegation,
                query, usabilityProof));
      }
    }

    // If this is a forwarded query, just skip to the next part that uses
    // delegations directly. The caller should have done the rest already.
    if (!forwarded) {
      // Attempt to use the rule:
      //   a ≽ c or b ≽ c => a ∧ b ≽ c
      if (query.superior instanceof ConjunctivePrincipal) {
        final ConjunctivePrincipal superior =
            (ConjunctivePrincipal) query.superior;
        for (Principal witness : superior.conjuncts()) {
          ProofSearchResult<Principal, Inferior> result =
              actsForProof(this, query.superior(witness), searchState);
          switch (result.type) {
          case SUCCESS:
            // Have a proof of witness ≽ query.inferior.
            // Construct a proof for query.superior ≽ witness.
            DelegatesProof<Superior, Principal> step =
            new DelegatesProof<>(query.inferior(witness));
            return new ProofSearchResult<>(new TransitiveProof<>(step, witness,
                result.proof));

          case PRUNED:
            // Can make progress if the subquery can.
            searchState.progressCondition.or(result.progressCondition);
            break;

          case FAILED:
            break;
          }
        }
      }

      // Attempt to use the rule:
      //   a ≽ c and b ≽ c => a ∨ b ≽ c
      if (query.superior instanceof DisjunctivePrincipal) {
        final DisjunctivePrincipal superior =
            (DisjunctivePrincipal) query.superior;

        // Maps subgoals to their proofs.
        Map<Principal, ActsForProof<Principal, Inferior>> proofs =
            new HashMap<>(superior.disjuncts().size());

        // Collects conditions for making further progress on this rule, should
        // the search be pruned.
        ProgressCondition ruleConditions = null;

        boolean success = true;
        L: for (Principal p : superior.disjuncts()) {
          ProofSearchResult<Principal, Inferior> result =
              actsForProof(this, query.superior(p), searchState);
          switch (result.type) {
          case SUCCESS:
            proofs.put(p, result.proof);
            break;

          case FAILED:
            success = false;
            ruleConditions = null;
            break L;

          case PRUNED:
            success = false;
            ruleConditions =
                ProgressCondition.and(ruleConditions, result.progressCondition);
            break;
          }
        }

        if (success) {
          return new ProofSearchResult<>(
              (ActsForProof<Superior, Inferior>) new FromDisjunctProof<>(
                  superior, query.inferior, proofs));
        }

        searchState.progressCondition.or(ruleConditions);
      }

      // Attempt to use the rule:
      //   a ≽ b and a ≽ c => a ≽ b ∧ c
      if (query.inferior instanceof ConjunctivePrincipal) {
        final ConjunctivePrincipal inferior =
            (ConjunctivePrincipal) query.inferior;

        // Maps subgoals to their proofs.
        Map<Principal, ActsForProof<Superior, Principal>> proofs =
            new HashMap<>(inferior.conjuncts().size());

        // Collects conditions for making further progress on this rule, should
        // the search be pruned.
        ProgressCondition ruleConditions = null;

        boolean success = true;
        L: for (Principal p : inferior.conjuncts()) {
          ProofSearchResult<Superior, Principal> result =
              actsForProof(this, query.inferior(p), searchState);
          switch (result.type) {
          case SUCCESS:
            proofs.put(p, result.proof);
            break;

          case FAILED:
            success = false;
            ruleConditions = null;
            break L;

          case PRUNED:
            success = false;
            ruleConditions =
                ProgressCondition.and(ruleConditions, result.progressCondition);
            break;
          }
        }

        if (success) {
          return new ProofSearchResult<>(
              (ActsForProof<Superior, Inferior>) new ToConjunctProof<>(
                  query.superior, inferior, proofs));
        }

        searchState.progressCondition.or(ruleConditions);
      }

      // Attempt to use the rule:
      //   a ≽ b or a ≽ c => a ≽ b ∨ c
      if (query.inferior instanceof DisjunctivePrincipal) {
        DisjunctivePrincipal inferior = (DisjunctivePrincipal) query.inferior;
        for (Principal witness : inferior.disjuncts()) {
          ProofSearchResult<Superior, Principal> result =
              actsForProof(this, query.inferior(witness), searchState);
          switch (result.type) {
          case SUCCESS:
            // Have a proof of query.superior ≽ witness.
            // Construct a proof for witness ≽ query.inferior.
            DelegatesProof<Principal, Inferior> step =
            new DelegatesProof<>(query.superior(witness));
            return new ProofSearchResult<>(new TransitiveProof<>(result.proof,
                witness, step));

          case PRUNED:
            // Can make progress if the subquery can.
            searchState.progressCondition.or(result.progressCondition);
            break;

          case FAILED:
            break;
          }
        }
      }

      if (query.inferior instanceof ConfPrincipal) {
        ConfPrincipal inferior = (ConfPrincipal) query.inferior;
        {
          // Attempt to use the rule:
          //   a ≽ b => a ≽ b→
          ProofSearchResult<Superior, Principal> result =
              actsForProof(this, query.inferior(inferior.base()), searchState);
          switch (result.type) {
          case SUCCESS:
            // Have a proof of query.superior ≽ inferior.base().
            // Construct a proof for inferior.base() ≽ query.inferior.
            DelegatesProof<Principal, Inferior> step =
            new DelegatesProof<>(query.superior(inferior.base()));
            return new ProofSearchResult<>(new TransitiveProof<>(result.proof,
                inferior.base(), step));

          case PRUNED:
            // Can make progress if the subquery can.
            searchState.progressCondition.or(result.progressCondition);
            break;

          case FAILED:
            break;
          }
        }

        if (query.superior instanceof ConfPrincipal) {
          ConfPrincipal superior = (ConfPrincipal) query.superior;

          // Attempt to use the rule:
          //   a ≽ b => a→ ≽ b→
          ProofSearchResult<Principal, Principal> result =
              actsForProof(this,
                  query.superior(superior.base()).inferior(inferior.base()),
                  searchState);
          switch (result.type) {
          case SUCCESS:
            // Have a proof of superior.base() ≽ inferior.base().
            return new ProofSearchResult<>(
                (ActsForProof<Superior, Inferior>) new ConfProjectionProof(
                    result.proof));

          case PRUNED:
            // Can make progress if the subquery can.
            searchState.progressCondition.or(result.progressCondition);
            break;

          case FAILED:
            break;
          }
        }
      }

      if (query.inferior instanceof IntegPrincipal) {
        IntegPrincipal inferior = (IntegPrincipal) query.inferior;
        {
          // Attempt to use the rule:
          //   a ≽ b => a ≽ b←
          ProofSearchResult<Superior, Principal> result =
              actsForProof(this, query.inferior(inferior.base()), searchState);
          switch (result.type) {
          case SUCCESS:
            // Have a proof of query.superior ≽ inferior.base().
            // Construct a proof for inferior.base() ≽query.inferior.
            DelegatesProof<Principal, Inferior> step =
                new DelegatesProof<>(query.superior(inferior.base()));
            return new ProofSearchResult<>(new TransitiveProof<>(result.proof,
                inferior.base(), step));

          case PRUNED:
            // Can make progress if the subquery can.
            searchState.progressCondition.or(result.progressCondition);
            break;

          case FAILED:
            break;
          }
        }

        if (query.superior instanceof IntegPrincipal) {
          IntegPrincipal superior = (IntegPrincipal) query.superior;

          // Attempt to use the rule:
          //   a ≽ b => a← ≽ b←
          ProofSearchResult<Principal, Principal> result =
              actsForProof(this,
                  query.superior(superior.base()).inferior(inferior.base()),
                  searchState);
          switch (result.type) {
          case SUCCESS:
            // Have a proof of superior.base() ≽ inferior.base().
            return new ProofSearchResult<>(
                (ActsForProof<Superior, Inferior>) new IntegProjectionProof(
                    result.proof));

          case PRUNED:
            // Can make progress if the subquery can.
            searchState.progressCondition.or(result.progressCondition);
            break;

          case FAILED:
            break;
          }
        }
      }

      // Attempt to use the rule:
      //   a ∨ b ≽ c => a:b ≽ c
      if (query.superior instanceof OwnedPrincipal) {
        OwnedPrincipal superior = (OwnedPrincipal) query.superior;
        Principal a = superior.owner();
        Principal b = superior.projection();
        ProofSearchResult<Principal, Inferior> result =
            actsForProof(this, query.superior(PrincipalUtil.disjunction(a, b)),
                searchState);
        switch (result.type) {
        case SUCCESS:
          return new ProofSearchResult<>(
              (ActsForProof<Superior, Inferior>) new MeetToOwnerProof<>(
                  superior, result.proof));

        case PRUNED:
          // Can make progress if the subquery can.
          searchState.progressCondition.or(result.progressCondition);
          break;

        case FAILED:
          break;
        }

        // Attempt to use the rule:
        //   a ≽ c and a∨b ≽ c∨d => a:b ≽ c:d
        if (query.inferior instanceof OwnedPrincipal) {
          OwnedPrincipal inferior = (OwnedPrincipal) query.inferior;
          Principal c = inferior.owner();
          Principal d = inferior.projection();

          // Collects conditions for making further progress on this rule,
          // should the search be pruned.
          ProgressCondition ruleConditions = null;

          ProofSearchResult<Principal, Principal> ownersProofResult =
              actsForProof(this, query.superior(a).inferior(c), searchState);
          switch (ownersProofResult.type) {
          case SUCCESS:
            break;

          case PRUNED:
            ruleConditions =
                new ProgressCondition(ownersProofResult.progressCondition,
                    ProgressCondition.Mutability.MUTABLE);
            break;

          case FAILED:
            break;
          }

          if (!ownersProofResult.failed()) {
            ProofSearchResult<Principal, Principal> projectionProofResult =
                actsForProof(
                    this,
                    query.superior(PrincipalUtil.disjunction(a, b)).inferior(
                        PrincipalUtil.disjunction(c, d)), searchState);
            switch (projectionProofResult.type) {
            case SUCCESS:
              if (ownersProofResult.succeeded()) {
                return new ProofSearchResult<>(
                    (ActsForProof<Superior, Inferior>) new OwnedPrincipalsProof(
                        superior, inferior, ownersProofResult.proof,
                        projectionProofResult.proof));
              }

              break;

            case PRUNED:
              // Take the conjunction of the conditions.
              ruleConditions =
              ProgressCondition.and(ruleConditions,
                  projectionProofResult.progressCondition);
              break;

            case FAILED:
              ruleConditions = null;
              break;
            }
          }

          searchState.progressCondition.or(ruleConditions);
        }
      }

      // Attempt to use the rule:
      //   a ≽ b => a ≽ b:c
      if (query.inferior instanceof OwnedPrincipal) {
        OwnedPrincipal inferior = (OwnedPrincipal) query.inferior;
        Principal b = inferior.owner();
        ProofSearchResult<Superior, Principal> result =
            actsForProof(this, query.inferior(b), searchState);
        switch (result.type) {
        case SUCCESS:
          // Have a proof of query.superior ≽ inferior.owner().
          // Construct a proof for inferior.owner() ≽ query.inferior.
          DelegatesProof<Principal, Inferior> step =
          new DelegatesProof<>(query.superior(inferior.owner()));
          return new ProofSearchResult<>(new TransitiveProof<>(result.proof,
              inferior.owner(), step));

        case PRUNED:
          // Can make progress if the subquery can.
          searchState.progressCondition.or(result.progressCondition);
          break;

        case FAILED:
          break;
        }
      }
    }

    // Attempt to use transitivity.
    // For each usable delegation p ≽ q, try to show either:
    //   query.superior = p and q ≽ query.inferior
    // or:
    //   query.superior ≽ p and q = query.inferior.
    for (Map.Entry<Delegation<?, ?>, ActsForProof<?, ?>> entry : usableDelegations(
        query, searchState).entrySet()) {
      final Delegation<?, ?> delegation = entry.getKey();
      final ActsForProof<?, ?> usabilityProof = entry.getValue();

      // Let p = delegation.superior and q = delegation.inferior.
      final Principal p = delegation.superior;
      final Principal q = delegation.inferior;

      if (PrincipalUtil.equals(query.superior, p)) {
        // Have query.superior = p. Show q ≽ query.inferior.
        ProofSearchResult<Principal, Inferior> step2Result =
            actsForProof(this, query.superior(q), searchState);
        switch (step2Result.type) {
        case SUCCESS:
          // Proof successful.
          ActsForProof<Superior, Principal> step1 =
              new DelegatesProof<>(
                  (Delegation<Principal, Superior>) delegation, query,
                  usabilityProof);
          return new ProofSearchResult<>(new TransitiveProof<>(step1, q,
              step2Result.proof));

        case PRUNED:
          // Can make progress if the subquery can.
          searchState.progressCondition.or(step2Result.progressCondition);
          break;

        case FAILED:
          break;
        }
      }

      if (PrincipalUtil.equals(q, query.inferior)) {
        // Have q = query.inferior. Show query.superior ≽ p.
        ProofSearchResult<Superior, Principal> step1Result =
            actsForProof(this, query.inferior(p), searchState);
        switch (step1Result.type) {
        case SUCCESS:
          // Proof successful.
          ActsForProof<Principal, Inferior> step2 =
              new DelegatesProof<>(
                  (Delegation<Inferior, Principal>) delegation, query,
                  usabilityProof);
          return new ProofSearchResult<>(new TransitiveProof<>(
              step1Result.proof, p, step2));

        case PRUNED:
          // Can make progress if the subquery can.
          searchState.progressCondition.or(step1Result.progressCondition);
          break;

        case FAILED:
          break;
        }
      }
    }

    // Forward query to other nodes. First, figure out who to ask.
    Set<NodePrincipal> askable =
        new HashSet<>(askablePrincipals(query, searchState));
    askable.removeAll(searchState.principalsAsked);

    // Add the set of askable nodes to the existing search state.
    searchState = new ProofSearchState(searchState, askable);

    // Ask the other nodes.
    for (Principal callee : askable) {
      ProofSearchResult<Superior, Inferior> result =
          callee.actsForProof(this, query.receiver(callee), searchState);
      switch (result.type) {
      case SUCCESS:
        // Proof successful.
        return new ProofSearchResult<>(result.proof);

      case PRUNED:
        // Can make progress if the subquery can.
        searchState.progressCondition.or(result.progressCondition);
        break;

      case FAILED:
        break;
      }
    }

    // No proof found.
    if (searchState.progressCondition.isFalse()) {
      // No further progress can be made.
      return ProofSearchResult.FAILED();
    }

    // Search was pruned.
    return ProofSearchResult.pruned(query, searchState.progressCondition);
  }

  /**
   * A request to revoke all reachable delegations of {@code inferior}'s
   * authority to {@code superior}, where {@code label} flows to the
   * delegation's label.
   */
  private final class RevocationRequest {
    /**
     * The principal whose delegations are to be revoked.
     */
    final Principal inferior;

    /**
     * The principal whose privileges are to be revoked.
     */
    final Principal superior;

    /**
     * The label on the request.
     */
    final Principal label;

    RevocationRequest(Principal inferior, Principal superior, Principal label) {
      this.inferior = inferior;
      this.superior = superior;
      this.label = label;
    }

    /**
     * Joins the confidentiality projection of the given principal into the
     * {@code label} of this request and returns the result. This ensures that
     * the given principal's confidentiality is protected by the resulting
     * request.
     */
    RevocationRequest joinLabel(Principal p) {
      Principal newLabel = PrincipalUtil.join(label, p.confidentiality());
      if (label == newLabel) return this;
      return new RevocationRequest(inferior, superior, newLabel);
    }

    /**
     * Meets the given principal's integrity into the {@code label} and returns
     * the resulting {@code RevocationRequest}. This ensures that the given
     * principal is trusted with the integrity of the request.
     */
    RevocationRequest meetIntegrity(Principal p) {
      // newLabel = label ∨ (⊤→ ∧ p←).
      Principal newLabel =
          PrincipalUtil.meet(
              label,
              PrincipalUtil.join(PrincipalUtil.top().confidentiality(),
                  p.integrity()));
      if (PrincipalUtil.equals(label, newLabel)) return this;
      return new RevocationRequest(inferior, superior, newLabel);
    }

    Set<NodePrincipal> componentPrimitivePrincipals() {
      Set<NodePrincipal> result = new HashSet<>();
      result.addAll(inferior.componentNodePrincipals());
      result.addAll(superior.componentNodePrincipals());
      result.addAll(label.componentNodePrincipals());
      return result;
    }
  }

  /**
   * There are three kinds of results:
   * <ol>
   * <li>SUCCESS - proof found</li>
   * <li>FAILED - a complete search was made, and no proof was</li>
   * <li>PRUNED - a partial search was made, and no proof found. There are
   * queries currently on the goal stack that need to be resolved before further
   * progress can be made.</li>
   * </ol>
   */
  private static final class ProofSearchResult<Superior extends Principal, Inferior extends Principal> {
    enum Type {
      SUCCESS, PRUNED, FAILED
    }

    private static final ProofSearchResult<Principal, Principal> FAILED =
        new ProofSearchResult<>(Type.FAILED);

    final Type type;
    final ActsForProof<Superior, Inferior> proof;

    /**
     * Represents the conditions that need to be met before further progress can
     * be made.
     */
    final ProgressCondition progressCondition;

    private ProofSearchResult(Type type) {
      this.type = type;
      this.proof = null;
      this.progressCondition = null;
    }

    /**
     * Constructs a ProofSearchResult indicating that a proof was found.
     */
    ProofSearchResult(ActsForProof<Superior, Inferior> proof) {
      this.type = Type.SUCCESS;
      this.proof = proof;
      this.progressCondition = null;
    }

    /**
     * Constructs a ProofSearchResult indicating that the search was pruned.
     */
    ProofSearchResult(ProgressCondition progressCondition) {
      this.type = Type.PRUNED;
      this.proof = null;
      this.progressCondition =
          new ProgressCondition(progressCondition,
              ProgressCondition.Mutability.IMMUTABLE);
    }

    /**
     * Constructs a ProofSearchResult indicating that the search for the given
     * query was pruned.
     *
     * The given progress condition will be sanitized to avoid self-cycles: any
     * instances of the given query in the condition will be considered
     * unsatisfiable, and the condition will be rewritten to reflect this. If
     * this results in the condition being unsatisfiable, then the constructed
     * ProofSearchResult will be {@code FAILED}.
     */
    static <Superior extends Principal, Inferior extends Principal> ProofSearchResult<Superior, Inferior> pruned(
        ActsForQuery<Superior, Inferior> query,
        ProgressCondition progressCondition) {
      // Disjuncts in the progress condition that mention the query itself are
      // not satisfiable.
      progressCondition =
          new ProgressCondition(progressCondition,
              ProgressCondition.Mutability.MUTABLE);
      if (progressCondition.notifyFailure(query)) {
        // Progress condition not satisfiable.
        return FAILED();
      }

      return new ProofSearchResult<>(progressCondition);
    }

    @Override
    public String toString() {
      StringBuffer result = new StringBuffer(type.toString());
      if (type == Type.PRUNED) {
        result.append("\n");
        result.append(progressCondition.toString(2));
      }

      return result.toString();
    }

    /**
     * @return true iff this represents a successful proof
     */
    boolean succeeded() {
      return type == Type.SUCCESS;
    }

    /**
     * @return true iff this represents a failed proof
     */
    boolean failed() {
      return type == Type.FAILED;
    }

    /**
     * @return a ProofSearchResult indicating that no proof was found, despite
     *          an exhaustive search
     */
    static <Superior extends Principal, Inferior extends Principal> ProofSearchResult<Superior, Inferior> FAILED() {
      return (ProofSearchResult<Superior, Inferior>) FAILED;
    }
  }

  /**
   * Represents the condition that needs to be met before further progress can
   * be made on a query. An empty progress condition is one that can never be
   * satisfied. A progress condition containing an empty disjunct is one that is
   * always satisfied. {@code null} is used to represent a neutral progress
   * condition (i.e., one that is the identity with respect to conjunction and
   * disjunction).
   */
  private static final class ProgressCondition {
    static enum Mutability {
      MUTABLE, IMMUTABLE
    }

    /**
     * A set of sets of queries that are currently on the goal stack. If all
     * queries in one of these sets are resolved positively (i.e., answered
     * "yes"), then further progress can be made.
     */
    final Set<Set<ActsForQuery<?, ?>>> disjuncts;

    /**
     * Creates a new empty but mutable progress condition.
     */
    ProgressCondition() {
      disjuncts = new HashSet<>();
    }

    /**
     * Creates a new singleton progress condition.
     */
    ProgressCondition(ActsForQuery<?, ?> condition, Mutability mutability) {
      final boolean immutable = mutability == Mutability.IMMUTABLE;

      if (immutable) {
        disjuncts =
            Collections.singleton(Collections
                .<ActsForQuery<?, ?>> singleton(condition));
        return;
      }

      disjuncts = new HashSet<>();
      Set<ActsForQuery<?, ?>> disjunct = new HashSet<>();
      disjunct.add(condition);
      disjuncts.add(disjunct);
    }

    /**
     * Copy constructor.
     */
    ProgressCondition(ProgressCondition old, Mutability mutability) {
      final boolean immutable = mutability == Mutability.IMMUTABLE;

      Set<Set<ActsForQuery<?, ?>>> disjuncts =
          new HashSet<>(old.disjuncts.size());
      for (Set<ActsForQuery<?, ?>> oldDisjunct : old.disjuncts) {
        Set<ActsForQuery<?, ?>> newDisjunct = new HashSet<>(oldDisjunct);
        if (immutable) newDisjunct = Collections.unmodifiableSet(newDisjunct);
        disjuncts.add(newDisjunct);
      }

      if (immutable) disjuncts = Collections.unmodifiableSet(disjuncts);
      this.disjuncts = disjuncts;
    }

    /**
     * @return true iff this condition can never be satisfied.
     */
    boolean isFalse() {
      return disjuncts.isEmpty();
    }

    /**
     * Joins the given {@code ProgressCondition} into this one by taking a
     * disjunction.
     */
    void or(ProgressCondition other) {
      if (other == null) return;
      disjuncts.addAll(other.disjuncts);
    }

    /**
     * @return the conjunction of {@code p1} and {@code p2} in a new mutable
     *          ProgressCondition. If one of {@code p1} or {@code p2} is {@code
     *          null}, then a copy of the other is returned. If both are {@code
     *          null}, then {@code null} is returned.
     */
    static ProgressCondition and(ProgressCondition p1, ProgressCondition p2) {
      if (p1 == null) {
        if (p2 == null) return null;
        return new ProgressCondition(p2, Mutability.MUTABLE);
      }

      if (p2 == null) return new ProgressCondition(p1, Mutability.MUTABLE);

      ProgressCondition result = new ProgressCondition();
      for (Set<ActsForQuery<?, ?>> thisDisjunct : p1.disjuncts) {
        for (Set<ActsForQuery<?, ?>> thatDisjunct : p2.disjuncts) {
          Set<ActsForQuery<?, ?>> newDisjunct =
              new HashSet<>(thisDisjunct.size() + thatDisjunct.size());
          newDisjunct.addAll(thisDisjunct);
          newDisjunct.addAll(thatDisjunct);
          result.disjuncts.add(newDisjunct);
        }
      }

      return result;
    }

    /**
     * Updates this progress condition to reflect that the given query had a
     * successful proof.
     *
     * @return true if this condition is satisfied as a consequent of the given
     *          successful query
     */
    boolean notifySuccess(ActsForQuery<?, ?> query) {
      boolean result = false;

      // We have to rebuild the entire data structure to ensure the hash tables'
      // consistency as we mutate their contents.
      Set<Set<ActsForQuery<?, ?>>> newDisjuncts =
          new HashSet<>(disjuncts.size());

      // Go through our disjuncts and remove the query. If any disjunct becomes
      // empty as a result, then the condition is satisfied.
      for (Set<ActsForQuery<?, ?>> disjunct : disjuncts) {
        Set<ActsForQuery<?, ?>> newDisjunct = new HashSet<>(disjunct);
        newDisjunct.remove(query);
        newDisjuncts.add(newDisjunct);

        // An empty disjunct represents a satisfied condition.
        result |= newDisjunct.isEmpty();
      }

      disjuncts.clear();
      disjuncts.addAll(newDisjuncts);
      return result;
    }

    /**
     * Updates this progress condition to reflect that the given query had a
     * failed proof.
     *
     * @return true if this condition becomes unsatisfiable as a consequent of
     *          the given unsuccessful proof.
     */
    boolean notifyFailure(ActsForQuery<?, ?> query) {
      return notifyFailures(Collections.<ActsForQuery<?, ?>> singleton(query));
    }

    /**
     * Updates this progress condition to reflect that the given queries had
     * failed proofs.
     *
     * @return true if this condition becomes unsatisfiable as a consequent of
     *          the given unsuccessful proofs.
     */
    boolean notifyFailures(Set<ActsForQuery<?, ?>> queries) {
      // Go through our disjuncts and remove those that mention any of the given
      // queries. If the condition becomes empty as a result, then the condition
      // is unsatisfiable.
      for (Iterator<Set<ActsForQuery<?, ?>>> it = disjuncts.iterator(); it
          .hasNext();) {
        Set<ActsForQuery<?, ?>> disjunct = it.next();

        // Determine whether disjunct and queries have an empty intersection.
        boolean emptyIntersection = true;
        Set<ActsForQuery<?, ?>> smaller, larger;
        if (queries.size() < disjunct.size()) {
          smaller = queries;
          larger = disjunct;
        } else {
          smaller = disjunct;
          larger = queries;
        }
        for (ActsForQuery<?, ?> query : smaller) {
          if (larger.contains(query)) {
            emptyIntersection = false;
            break;
          }
        }

        if (!emptyIntersection) {
          // The disjunct contains some failed query, and is unsatisfiable as a
          // result. Remove it from the set of disjuncts.
          it.remove();
        }
      }

      return disjuncts.isEmpty();
    }

    /**
     * Logically replaces all instances of the given query with the given
     * progress condition.
     */
    void substitute(ActsForQuery<?, ?> query,
        ProgressCondition progressCondition) {
      // Rebuild the entire data structure.
      Set<Set<ActsForQuery<?, ?>>> newDisjuncts =
          new HashSet<>(disjuncts.size());

      // Go through our disjuncts and replace the query. As we do so, rewrite
      // to maintain disjunctive normal form.
      boolean changesMade = false;
      for (Set<ActsForQuery<?, ?>> disjunct : disjuncts) {
        if (!disjunct.contains(query)) {
          newDisjuncts.add(disjunct);
          continue;
        }

        // Let a∧b = disjunct.
        // Let b = query.
        // Let c1∨c2 = progressCondition.
        //
        // Want to produce a∧(c1∨c2). To maintain normal form, rewrite to
        // a∧c1 ∨ a∧c2.

        ActsForQuery<?, ?> b = query;
        Set<ActsForQuery<?, ?>> a = new HashSet<>(disjunct);
        a.remove(b);

        for (Set<ActsForQuery<?, ?>> c : progressCondition.disjuncts) {
          Set<ActsForQuery<?, ?>> newDisjunct =
              new HashSet<>(a.size() + c.size());
          newDisjunct.addAll(a);
          newDisjunct.addAll(c);
          newDisjuncts.add(newDisjunct);
        }

        changesMade = true;
      }

      if (!changesMade) {
        // Nothing changed.
        return;
      }

      disjuncts.clear();
      disjuncts.addAll(newDisjuncts);
    }

    @Override
    public String toString() {
      return toString(0);
    }

    private String toString(int indent) {
      StringBuffer result = new StringBuffer();
      boolean firstDisjunct = true;
      for (Set<ActsForQuery<?, ?>> disjunct : disjuncts) {
        for (int i = 0; i < indent; i++)
          result.append(" ");
        result.append((firstDisjunct ? "" : ") or ") + "(\n");
        for (ActsForQuery<?, ?> conjunct : disjunct) {
          for (int i = 0; i < indent; i++)
            result.append(" ");
          result.append("  " + conjunct + "\n");
        }
        firstDisjunct = false;
      }
      for (int i = 0; i < indent; i++)
        result.append(" ");
      if (firstDisjunct) {
        result.append("FALSE\n");
      } else {
        result.append(")\n");
      }

      return result.toString();
    }
  }

  private final class ProofSearchState {
    /**
     * The most recent goal on the stack.
     */
    private final ActsForQuery<?, ?> curGoal;

    /**
     * The search state for the parent goal.
     */
    private final ProofSearchState parent;

    /**
     * The set of principals who have participated in the search so far.
     */
    private final Set<NodePrincipal> allParticipants;

    /**
     * The set of principals who have already been asked about the most recent
     * goal on the stack.
     */
    private final Set<Principal> principalsAsked;

    /**
     * Accumulates conditions under which further progress can be made on the
     * current query, in case the search for the current query is pruned.
     */
    private final ProgressCondition progressCondition;

    /**
     * The acts-for cache. Maps queries to the query's receiver's proof of the
     * query's truth.
     */
    private final Map<ActsForQuery<?, ?>, ActsForProof<?, ?>> actsForCache;

    /**
     * The not-acts-for cache. A set of queries, where the query's receiver
     * principal has previously returned a negative answer to the query.
     */
    private final Set<ActsForQuery<?, ?>> notActsForCache;

    /**
     * The cache of pruned proof searches. When a cycle in the proof tree is
     * found, the members of that cycle are entered into this cache. Once a
     * member of a cycle is definitively resolved, that member should be removed
     * from this cache.
     */
    private final PrunedSearchCache prunedSearchCache;

    /**
     * A cache of pruned proof searches. Maps queries to the condition that
     * needs to be met before further progress can be made on the query.
     */
    private final class PrunedSearchCache {
      /**
       * Maps queries to the condition that needs to be met before further
       * progress can be made on the query.
       */
      final Map<ActsForQuery<?, ?>, ProgressCondition> entries;

      PrunedSearchCache() {
        entries = new HashMap<>();
      }

      <Superior extends Principal, Inferior extends Principal> ProofSearchResult<Superior, Inferior> get(
          ActsForQuery<Superior, Inferior> query) {
        ProgressCondition progressCondition = entries.get(query);
        if (progressCondition == null) return null;
        return new ProofSearchResult<>(progressCondition);
      }

      /**
       * Adds a placeholder entry for the given query.
       */
      void addPlaceholder(ActsForQuery<?, ?> query) {
        ProgressCondition progressCondition =
            new ProgressCondition(query, ProgressCondition.Mutability.MUTABLE);
        entries.put(query, progressCondition);
      }

      /**
       * Updates the cache to reflect that the given query had a successful
       * proof.
       */
      void notifySuccess(ActsForQuery<?, ?> query) {
        entries.remove(query);
        for (Iterator<ProgressCondition> it = entries.values().iterator(); it
            .hasNext();) {
          ProgressCondition condition = it.next();
          if (condition.notifySuccess(query)) {
            // Condition satisfied -- can make more progress on query. Evict
            // from cache.
            it.remove();
          }
        }
      }

      /**
       * Updates the cache to reflect that the proof search for the given query
       * was pruned, and that further progress can be made under the given
       * condition.
       *
       * @return a set of queries that fail as a consequent of the given pruned
       *          query. This can contain the pruned query itself. This can
       *          happen, for example, if the query is its own progress
       *          condition.
       */
      Set<ActsForQuery<?, ?>> notifyPruned(ActsForQuery<?, ?> query,
          ProgressCondition progressCondition) {
        progressCondition =
            new ProgressCondition(progressCondition,
                ProgressCondition.Mutability.MUTABLE);

        entries.put(query, progressCondition);

        // In all cached conditions, replace all instances of the query with its
        // progress condition.
        for (ProgressCondition value : entries.values()) {
          value.substitute(query, progressCondition);
        }

        return Collections.emptySet();
      }

      /**
       * Updates the cache to reflect that the given query had a failed,
       * unpruned proof.
       *
       * @return a set of queries that fail as a consequent of the given failed
       *          query
       */
      Set<ActsForQuery<?, ?>> notifyFailure(ActsForQuery<?, ?> query) {
        entries.remove(query);

        Set<ActsForQuery<?, ?>> result = new HashSet<>();
        Set<ActsForQuery<?, ?>> toProcess = new HashSet<>();
        toProcess.add(query);

        // Loop until we get a fixed point.
        while (!toProcess.isEmpty()) {
          Set<ActsForQuery<?, ?>> processing = toProcess;
          toProcess = new HashSet<>();

          for (Iterator<Map.Entry<ActsForQuery<?, ?>, ProgressCondition>> it =
              entries.entrySet().iterator(); it.hasNext();) {
            Map.Entry<ActsForQuery<?, ?>, ProgressCondition> entry = it.next();

            if (entry.getValue().notifyFailures(processing)) {
              // Condition not satisfiable -- impossible to make more progress on
              // query. Evict from cache and report to caller.
              it.remove();

              ActsForQuery<?, ?> newFailure = entry.getKey();
              result.add(newFailure);
              toProcess.add(newFailure);
            }
          }
        }

        return result;
      }

      @Override
      public String toString() {
        return entries.toString();
      }
    }

    public ProofSearchState() {
      this.curGoal = null;
      this.parent = null;
      this.progressCondition = new ProgressCondition();

      if (Principal.this instanceof NodePrincipal) {
        allParticipants = Collections.singleton((NodePrincipal) Principal.this);
      } else {
        allParticipants = Collections.emptySet();
      }
      principalsAsked = Collections.emptySet();

      this.actsForCache = new HashMap<>();
      this.notActsForCache = new HashSet<>();
      this.prunedSearchCache = new PrunedSearchCache();
    }

    /**
     * Constructs a new search state with the given query pushed onto the query
     * stack, and with {@link ProofSearchState#principalsAsked} containing just
     * {@link Principal}{@code .this}.
     */
    private ProofSearchState(ProofSearchState state, ActsForQuery<?, ?> query) {
      // Sanity check.
      if (query.receiver != Principal.this) {
        throw new InternalError("Inconsistency: " + query.receiver + " != "
            + Principal.this);
      }

      this.curGoal = query;
      this.parent = state;
      this.progressCondition = new ProgressCondition();

      this.allParticipants = state.allParticipants;

      this.principalsAsked = Collections.singleton(Principal.this);

      this.actsForCache = state.actsForCache;
      this.notActsForCache = state.notActsForCache;
      this.prunedSearchCache = state.prunedSearchCache;

      // Add a placeholder to the cache in case the search cycles back here.
      prunedSearchCache.addPlaceholder(query);
    }

    /**
     * Constructs a new search state with the given set of principals added to
     * the set of principals asked.
     */
    private ProofSearchState(ProofSearchState state,
        Set<NodePrincipal> newPrincipalsAsked) {
      // Sanity check.
      if (state.curGoal.receiver != Principal.this) {
        throw new InternalError("Inconsistency: " + state.curGoal.receiver
            + " != " + Principal.this);
      }

      this.curGoal = state.curGoal;
      this.parent = state.parent;
      this.progressCondition = state.progressCondition;

      Set<NodePrincipal> allParticipants =
          new HashSet<>(state.allParticipants.size()
              + newPrincipalsAsked.size());
      this.allParticipants = Collections.unmodifiableSet(allParticipants);
      allParticipants.addAll(state.allParticipants);
      allParticipants.addAll(newPrincipalsAsked);

      Set<Principal> principalsAsked =
          new HashSet<>(state.principalsAsked.size()
              + newPrincipalsAsked.size());
      this.principalsAsked = Collections.unmodifiableSet(principalsAsked);

      principalsAsked.addAll(state.principalsAsked);
      principalsAsked.addAll(newPrincipalsAsked);

      this.actsForCache = state.actsForCache;
      this.notActsForCache = state.notActsForCache;
      this.prunedSearchCache = state.prunedSearchCache;
    }

    private <Superior extends Principal, Inferior extends Principal> void cache(
        ActsForQuery<Superior, Inferior> query,
        ProofSearchResult<Superior, Inferior> result) {
      switch (result.type) {
      case SUCCESS:
        actsForCache.put(query, result.proof);
        prunedSearchCache.notifySuccess(query);
        return;

      case PRUNED: {
        Set<ActsForQuery<?, ?>> newNotActsFors =
            prunedSearchCache.notifyPruned(query, result.progressCondition);

        // Update the not-acts-for cache with the new entries.
        notActsForCache.addAll(newNotActsFors);

        return;
      }

      case FAILED:
        notActsForCache.add(query);
        Set<ActsForQuery<?, ?>> newNotActsFors =
            prunedSearchCache.notifyFailure(query);

        // Update the not-acts-for cache with the new entries.
        notActsForCache.addAll(newNotActsFors);

        return;
      }

      throw new InternalError("Unknown acts-for-proof result type: "
          + result.type);
    }

    /**
     * @return a ProofSearchResult for cache hits, and null for cache misses
     */
    private <Superior extends Principal, Inferior extends Principal> ProofSearchResult<Superior, Inferior> cacheLookup(
        ActsForQuery<Superior, Inferior> query) {
      ActsForProof<?, ?> proof = actsForCache.get(query);
      if (proof != null) {
        return new ProofSearchResult<>((ActsForProof<Superior, Inferior>) proof);
      }

      if (notActsForCache.contains(query)) return ProofSearchResult.FAILED();

      return prunedSearchCache.get(query);
    }

    @Override
    public String toString() {
      StringBuffer result = new StringBuffer();
      result.append("goals = [\n");
      for (ProofSearchState curState = this; curState != null; curState =
          curState.parent) {
        result.insert(10, "  " + curState.curGoal + ",\n");
      }
      result.append("]\n");

      result.append("allParticipants = " + allParticipants + "\n");

      result.append("prinicpalsAsked = " + principalsAsked + "\n");

      result.append("progressCondition = " + progressCondition + "\n");

      result.append("actsForCache = {\n");
      for (ActsForQuery<?, ?> cacheEntry : actsForCache.keySet()) {
        result.append("  " + cacheEntry + "\n");
      }
      result.append("}\n");

      result.append("notActsForCache = {\n");
      for (ActsForQuery<?, ?> cacheEntry : notActsForCache) {
        result.append("  " + cacheEntry + "\n");
      }
      result.append("}\n");

      result.append("prunedSearchCache = {\n");
      for (Map.Entry<ActsForQuery<?, ?>, ProgressCondition> entry : prunedSearchCache.entries
          .entrySet()) {
        result.append("  " + entry.getKey() + " ->\n");
        result.append(entry.getValue().toString(4));
      }
      result.append("}\n");

      return result.toString();
    }
  }
}
