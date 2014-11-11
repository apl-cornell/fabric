package fla.principals;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fla.util.ActsForProof;
import fla.util.ActsForQuery;
import fla.util.ConfProjectionProof;
import fla.util.DelegatesProof;
import fla.util.DelegationPair;
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
  abstract Set<PrimitivePrincipal> componentPrimitivePrincipals();

  /**
   * Obtains a set of delegations that are stored at this principal and can be
   * used to answer the given query.
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
  abstract Set<DelegationPair> usableDelegations(ActsForQuery<?, ?> query,
      ProofSearchState searchState);

  /**
   * Obtains a set of principals to whom the given query can be forwarded. A
   * query can be forwarded to any PrimitivePrincipal {@code p} that:
   * <ul>
   * <li>appears as a component of this principal (if this is a {@code
   * NonPrimitivePrincipal}); or</li>
   * <li>appears as a component of a <i>usable</i> delegation stored at this
   * principal;</li>
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
  abstract Set<PrimitivePrincipal> askablePrincipals(ActsForQuery<?, ?> query,
      ProofSearchState searchState);

  /**
   * Determines whether the given principal {@code p} can be asked the given
   * {@code query}. The principal can be asked if {@code p} acts for {@code
   * query.accessPolicy} and the integrity projection of {@code query.maxLabel}.
   *
   * @param searchState the state of the proof search being made
   */
  private final boolean isAskable(Principal p, ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    // See if p ≽ query.recurseLowerBound().
    query = query.inferior(query.recurseLowerBound()).superior(p);
    return findActsForProof(query, searchState) != null;
  }

  /**
   * Removes from the given set all principals that cannot be asked the given
   * query.
   *
   * @param searchState the state of the proof search being made
   *
   * @return the object {@code set}, with all unaskable principals having been
   *          removed
   */
  final Set<PrimitivePrincipal> removeUnaskablePrincipals(
      Set<PrimitivePrincipal> set, ActsForQuery<?, ?> query,
      ProofSearchState searchState) {
    for (Iterator<PrimitivePrincipal> it = set.iterator(); it.hasNext();) {
      PrimitivePrincipal p = it.next();

      // Assume p passes muster if it has participated in the search so far.
      if (searchState.hasParticipant(p)) continue;

      if (!isAskable(p, query, searchState)) it.remove();
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
    for (DelegationPair delegation : usableDelegations(query,
        new ProofSearchState())) {
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
    return actsForProof(new ActsForQuery<>(superior, inferior, maxUsableLabel,
        accessPolicy)) != null;
  }

  public final boolean flowsTo(Principal inferior, Principal superior,
      Principal maxUsableLabel, Principal accessPolicy) {
    return actsForProof(ActsForQuery.flowsToQuery(inferior, superior,
        maxUsableLabel, accessPolicy)) != null;
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
    return findActsForProof(query, new ProofSearchState());
  }

  /**
   * Searches for an ActsForProof.
   *
   * @param searchState records the goals that we are in the middle of
   *          attempting
   */
  final <Superior extends Principal, Inferior extends Principal> ActsForProof<Superior, Inferior> findActsForProof(
      ActsForQuery<Superior, Inferior> query, final ProofSearchState searchState) {
    // Try the dumb things first.
    if (query.inferior instanceof BottomPrincipal
        || query.superior instanceof TopPrincipal) {
      return new DelegatesProof<>(query.inferior, query.superior);
    }

    if (PrincipalUtil.equals(query.inferior, query.superior)) {
      return new ReflexiveProof<>(query.superior, query.inferior);
    }

    // Check the search state.
    if (searchState.contains(query)) {
      // Already on the goal stack. Prevent an infinite recursion.
      return null;
    }

    // Push the query onto the search-state stack.
    final ProofSearchState origSearchState = searchState;
    final ProofSearchState newSearchState =
        new ProofSearchState(searchState, query);

    // Attempt to use the rule:
    //   a ≽ c or b ≽ c => a ∧ b ≽ c
    if (query.superior instanceof ConjunctivePrincipal) {
      final ConjunctivePrincipal superior =
          (ConjunctivePrincipal) query.superior;
      for (Principal witness : superior.conjuncts()) {
        ActsForProof<Principal, Inferior> proof =
            findActsForProof(query.superior(witness), newSearchState);
        if (proof != null) {
          // Have a proof of witness ≽ query.inferior.
          DelegatesProof<Superior, Principal> step =
              new DelegatesProof<>(witness, query.superior);
          return new TransitiveProof<>(step, witness, proof);
        }
      }
    }

    // Attempt to use the rule:
    //   a ≽ c and b ≽ c => a ∨ b ≽ c
    if (query.superior instanceof DisjunctivePrincipal) {
      final DisjunctivePrincipal superior =
          (DisjunctivePrincipal) query.superior;
      Map<Principal, ActsForProof<Principal, Inferior>> proofs =
          new HashMap<>(superior.disjuncts().size());
      boolean success = true;
      for (Principal p : superior.disjuncts()) {
        ActsForProof<Principal, Inferior> proof =
            findActsForProof(query.superior(p), newSearchState);
        if (proof == null) {
          success = false;
          break;
        }
        proofs.put(p, proof);
      }

      if (success) {
        return (ActsForProof<Superior, Inferior>) new FromDisjunctProof<>(
            superior, query.inferior, proofs);
      }
    }

    // Attempt to use the rule:
    //   a ≽ b and a ≽ c => a ≽ b ∧ c
    if (query.inferior instanceof ConjunctivePrincipal) {
      final ConjunctivePrincipal inferior =
          (ConjunctivePrincipal) query.inferior;
      Map<Principal, ActsForProof<Superior, Principal>> proofs =
          new HashMap<>(inferior.conjuncts.size());
      boolean success = true;
      for (Principal p : inferior.conjuncts()) {
        ActsForProof<Superior, Principal> proof =
            findActsForProof(query.inferior(p), newSearchState);
        if (proof == null) {
          success = false;
          break;
        }
        proofs.put(p, proof);
      }

      if (success) {
        return (ActsForProof<Superior, Inferior>) new ToConjunctProof<>(
            query.superior, inferior, proofs);
      }
    }

    // Attempt to use the rule:
    //   a ≽ b or a ≽ c => a ≽ b ∨ c
    if (query.inferior instanceof DisjunctivePrincipal) {
      DisjunctivePrincipal inferior = (DisjunctivePrincipal) query.inferior;
      for (Principal witness : inferior.disjuncts()) {
        ActsForProof<Superior, Principal> proof =
            findActsForProof(query.inferior(witness), newSearchState);
        if (proof != null) {
          // Have a proof of query.superior ≽ witness.
          DelegatesProof<Principal, Inferior> step =
              new DelegatesProof<>(query.inferior, witness);
          return new TransitiveProof<>(proof, witness, step);
        }
      }
    }

    if (query.inferior instanceof ConfPrincipal) {
      ConfPrincipal inferior = (ConfPrincipal) query.inferior;
      {
        // Attempt to use the rule:
        //   a ≽ b => a ≽ b→
        ActsForProof<Superior, Principal> proof =
            findActsForProof(query.inferior(inferior.base()), newSearchState);
        if (proof != null) {
          // Have a proof of query.superior ≽ inferior.base().
          DelegatesProof<Principal, Inferior> step =
              new DelegatesProof<>(query.inferior, inferior.base());
          return new TransitiveProof<>(proof, inferior.base(), step);
        }
      }

      if (query.superior instanceof ConfPrincipal) {
        ConfPrincipal superior = (ConfPrincipal) query.superior;

        // Attempt to use the rule:
        //   a ≽ b => a→ ≽ b→
        ActsForProof<Principal, Principal> proof =
            findActsForProof(
                query.superior(superior.base()).inferior(inferior.base()),
                newSearchState);
        if (proof != null) {
          // Have a proof of superior.base() ≽ inferior.base().
          return (ActsForProof<Superior, Inferior>) new ConfProjectionProof(
              proof);
        }
      }
    }

    if (query.inferior instanceof IntegPrincipal) {
      IntegPrincipal inferior = (IntegPrincipal) query.inferior;
      {
        // Attempt to use the rule:
        //   a ≽ b => a ≽ b←
        ActsForProof<Superior, Principal> proof =
            findActsForProof(query.inferior(inferior.base()), newSearchState);
        if (proof != null) {
          // Have a proof of query.superior ≽ inferior.base().
          DelegatesProof<Principal, Inferior> step =
              new DelegatesProof<>(query.inferior, inferior.base());
          return new TransitiveProof<>(proof, inferior.base(), step);
        }
      }

      if (query.superior instanceof IntegPrincipal) {
        IntegPrincipal superior = (IntegPrincipal) query.superior;

        // Attempt to use the rule:
        //   a ≽ b => a← ≽ b←
        ActsForProof<Principal, Principal> proof =
            findActsForProof(
                query.superior(superior.base()).inferior(inferior.base()),
                newSearchState);
        if (proof != null) {
          // Have a proof of superior.base() ≽ inferior.base().
          return (ActsForProof<Superior, Inferior>) new IntegProjectionProof(
              proof);
        }
      }
    }

    // Attempt to use the rule:
    //   a ∨ b ≽ c => a:b ≽ c
    if (query.superior instanceof OwnedPrincipal) {
      OwnedPrincipal superior = (OwnedPrincipal) query.superior;
      Principal a = superior.owner();
      Principal b = superior.projection();
      ActsForProof<Principal, Inferior> proof =
          findActsForProof(query.superior(PrincipalUtil.disjunction(a, b)),
              newSearchState);
      if (proof != null) {
        return (ActsForProof<Superior, Inferior>) new MeetToOwnerProof<>(
            superior, proof);
      }

      // Attempt to use the rule:
      //   a ≽ c and a∨b ≽ c∨d => a:b ≽ c:d
      if (query.inferior instanceof OwnedPrincipal) {
        OwnedPrincipal inferior = (OwnedPrincipal) query.inferior;
        Principal c = inferior.owner();
        Principal d = inferior.projection();
        ActsForProof<Principal, Principal> ownersProof =
            findActsForProof(query.superior(a).inferior(c), newSearchState);
        if (ownersProof != null) {
          ActsForProof<Principal, Principal> projectionProof =
              findActsForProof(query.superior(PrincipalUtil.disjunction(a, b))
                  .inferior(PrincipalUtil.disjunction(c, d)), newSearchState);
          if (projectionProof != null) {
            return (ActsForProof<Superior, Inferior>) new OwnedPrincipalsProof(
                superior, inferior, ownersProof, projectionProof);
          }
        }
      }
    }

    // Attempt to use the rule:
    //   a ≽ b => a ≽ b:c
    if (query.inferior instanceof OwnedPrincipal) {
      OwnedPrincipal inferior = (OwnedPrincipal) query.inferior;
      Principal b = inferior.owner();
      ActsForProof<Superior, Principal> proof =
          findActsForProof(query.inferior(b), newSearchState);
      if (proof != null) {
        // Have a proof of query.superior ≽ inferior.owner().
        DelegatesProof<Principal, Inferior> step =
            new DelegatesProof<>(query.inferior, inferior.owner());
        return new TransitiveProof<>(proof, inferior.owner(), step);
      }
    }

    // Attempt to use transitivity.
    // For each usable delegation p ≽ q, try to show either:
    //   query.superior = p and q ≽ query.inferior
    // or:
    //   query.superior ≽ p and q = query.inferior.
    for (DelegationPair delegation : usableDelegations(query, newSearchState)) {
      // Let p = delegation.superior and q = delegation.inferior.
      final Principal p = delegation.superior;
      final Principal q = delegation.inferior;

      if (PrincipalUtil.equals(query.superior, p)) {
        // Have query.superior = p. Show q ≽ query.inferior.
        ActsForProof<Principal, Inferior> step =
            findActsForProof(query.superior(q), newSearchState);
        if (step != null) {
          // Proof successful.
          return new TransitiveProof<>(new DelegatesProof<>(this, q,
              query.superior), q, step);
        }
      }

      if (PrincipalUtil.equals(q, query.inferior)) {
        // Have q = query.inferior. Show query.superior ≽ p.
        ActsForProof<Superior, Principal> step =
            findActsForProof(query.inferior(p), newSearchState);
        if (step != null) {
          // Proof successful.
          return new TransitiveProof<>(step, p, new DelegatesProof<>(this,
              query.inferior, p));
        }
      }
    }

    // Forward query to other nodes. First, figure out who to ask.
    Set<PrimitivePrincipal> askable =
        new HashSet<>(askablePrincipals(query, newSearchState));
    askable.addAll(origSearchState.allParticipants);
    askable.removeAll(origSearchState.principalsAsked);

    // Add the set of askable nodes to the existing search state.
    final ProofSearchState recurseSearchState =
        new ProofSearchState(origSearchState, askable);

    // Ask the other nodes.
    for (Principal callee : askable) {
      ActsForProof<Superior, Inferior> result =
          callee.findActsForProof(query, recurseSearchState);
      if (result != null) return result;
    }

    // Proof failed.
    return null;
  }

  final class ProofSearchState {
    private final Set<ActsForQuery<?, ?>> goals;

    /**
     * The set of principals who have participated in the search so far.
     */
    private final Set<PrimitivePrincipal> allParticipants;

    /**
     * The set of principals who have already been asked about the most recent
     * goal on the stack.
     */
    private final Set<Principal> principalsAsked;

    public ProofSearchState() {
      goals = Collections.emptySet();
      if (Principal.this instanceof PrimitivePrincipal) {
        allParticipants =
            Collections.singleton((PrimitivePrincipal) Principal.this);
      } else {
        allParticipants = Collections.emptySet();
      }
      principalsAsked = Collections.emptySet();
    }

    /**
     * Constructs a new search state with the given query pushed onto the query
     * stack.
     */
    private ProofSearchState(ProofSearchState state, ActsForQuery<?, ?> query) {
      Set<ActsForQuery<?, ?>> goals = new HashSet<>(state.goals.size() + 1);
      this.goals = Collections.unmodifiableSet(goals);
      goals.addAll(state.goals);
      goals.add(query);

      this.allParticipants = state.allParticipants;

      this.principalsAsked = Collections.emptySet();
    }

    /**
     * Constructs a new search state with the given set of principals added to
     * the set of principals asked.
     */
    private ProofSearchState(ProofSearchState state,
        Set<PrimitivePrincipal> newPrincipalsAsked) {
      this.goals = state.goals;

      Set<PrimitivePrincipal> allParticipants =
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
    }

    public boolean contains(ActsForQuery<?, ?> query) {
      return goals.contains(query);
    }

    public boolean hasParticipant(Principal p) {
      return allParticipants.contains(p);
    }

    @Override
    public String toString() {
      StringBuffer result = new StringBuffer();
      result.append("goals = [\n");
      for (ActsForQuery<?, ?> goal : goals) {
        result.append("  " + goal + ",\n");
      }
      result.append("]\n");

      result.append("allParticipants = " + allParticipants + "\n");

      result.append("prinicpalsAsked = " + principalsAsked);

      return result.toString();
    }
  }

}
