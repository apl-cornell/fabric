package fla.principals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jif.lang.FromDisjunctProof;
import jif.lang.ToConjunctProof;
import fla.Label;
import fla.util.ActsForProof;
import fla.util.ActsForQuery;
import fla.util.DelegatesProof;
import fla.util.DelegationPair;
import fla.util.PolyInstantiableSet;
import fla.util.ReflexiveProof;
import fla.util.TransitiveProof;

public abstract class Principal implements Label {
  private final PolyInstantiableSet<DelegationPair> delegations;

  Principal() {
    this.delegations = new PolyInstantiableSet<>();
  }

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
  public abstract Principal project(TopPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(PrimitivePrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(ConfPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(IntegPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(OwnedPrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(ConjunctivePrincipal projection);

  /**
   * @return an ownership projection of this principal. The returned principal
   *          will be in normal form if this principal is.
   */
  public abstract Principal project(DisjunctivePrincipal projection);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(Principal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(TopPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(PrimitivePrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(ConfPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(IntegPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(OwnedPrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(ConjunctivePrincipal owner);

  /**
   * @return the ownership projection owner:this. The returned principal will be
   *          in normal form if this principal is.
   */
  public abstract Principal owner(DisjunctivePrincipal owner);

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
   * Asks this principal whether it can find the (direct) delegation "{@code
   * superior} actsfor {@code granter}" whose label does not exceed {@code
   * maxLabel}.
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
   *          query will not exceed this label
   * @param accessPolicy the confidentiality level of the query. This should
   *          act for the confidentiality component of {@code maxLabel}
   */
  public final boolean delegatesTo(Principal granter, Principal superior,
      Label maxLabel, Principal accessPolicy) {
    return delegatesTo(new ActsForQuery(superior, granter, maxLabel,
        accessPolicy));
  }

  /**
   * Asks this principal whether it can find the (direct) delegation "{@code
   * query.superior} actsfor {@code query.inferior}" whose label does not exceed
   * {@code query.maxLabel}.
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
  private final boolean delegatesTo(ActsForQuery query) {
    if (!query.useDynamicContext()) {
      // Static context. No dynamic delegations should be used.
      return false;
    }
  }

  /**
   * Asks this principal whether it can find a proof for "{@code superior}
   * actsfor {@code inferior}". Labels on the delegations used in this proof
   * may not exceed {@code maxUsableLabel}.
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
   * @param maxUsableLabel labels on delegations used in the proof will not
   *          exceed this label
   * @param accessPolicy the confidentiality level of the query. This should
   *          act for the confidentiality component of {@code maxUsableLabel}
   */
  public final boolean actsFor(Principal superior, Principal inferior,
      Label maxUsableLabel, Principal accessPolicy) {
    return actsForProof(new ActsForQuery(superior, inferior, maxUsableLabel,
        accessPolicy)) != null;
  }

  /**
   * Asks this principal for a proof of "{@code query.superior} actsfor {@code
   * query.inferior}". Labels on the delegations used in this proof may
   * not exceed {@code query.maxUsableLabel}.
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
  private final ActsForProof actsForProof(ActsForQuery query) {
    if (delegatesTo(query)) {
      return new DelegatesProof(query.inferior, query.superior);
    }

    if (PrincipalUtil.equals(query.inferior, query.superior)) {
      return new ReflexiveProof(query.inferior, query.superior);
    }

    // Try searching.
    // TODO Verify proof?
    return findActsForProof(query, new ProofSearchState());
  }

  /**
   * Searches for an ActsForProof.
   *
   * @param searchState records the goals that we are in the middle of
   *          attempting
   */
  private final ActsForProof findActsForProof(ActsForQuery query,
      ProofSearchState searchState) {
    // Try the dumb things first.
    if (query.inferior instanceof BottomPrincipal
        || query.superior instanceof TopPrincipal) {
      return new DelegatesProof(query.inferior, query.superior);
    }

    if (PrincipalUtil.equals(query.inferior, query.superior)) {
      return new ReflexiveProof(query.inferior, query.superior);
    }

    // Check the search state.
    if (searchState.contains(query)) {
      // Already on the goal stack. Prevent an infinite recursion.
      return null;
    }

    // Push the query onto the search-state stack.
    searchState = new ProofSearchState(searchState, query);

    // Attempt to use the rule:
    //   a ≽ c or b ≽ c => a ∧ b ≽ c
    if (query.superior instanceof ConjunctivePrincipal) {
      ConjunctivePrincipal superior = (ConjunctivePrincipal) query.superior;
      for (Principal witness : superior.conjuncts()) {
        ActsForProof proof =
            findActsForProof(query.superior(witness), searchState);
        if (proof != null) {
          // Have a proof of witness ≽ query.inferior.
          DelegatesProof step = new DelegatesProof(witness, this);
          return new TransitiveProof(step, witness, proof);
        }
      }
    }

    // Attempt to use the rule:
    //   a ≽ c and b ≽ c => a ∨ b ≽ c
    if (query.superior instanceof DisjunctivePrincipal) {
      DisjunctivePrincipal superior = (DisjunctivePrincipal) query.superior;
      Map<Principal, ActsForProof> proofs;
      boolean success = true;
      for (Principal p : superior.disjuncts()) {
        ActsForProof proof = findActsForProof(query.superior(p), searchState);
        if (proof == null) {
          success = false;
          break;
        }
        proofs.put(p, proof);
      }

      if (success) {
        return new FromDisjunctProof(query.superior, query.inferior, proofs);
      }
    }

    // Attempt to use the rule:
    //   a ≽ b and a ≽ c => a ≽ b ∧ c
    if (query.inferior instanceof ConjunctivePrincipal) {
      ConjunctivePrincipal inferior = (ConjunctivePrincipal) query.inferior;
      Map<Principal, ActsForProof> proofs;
      boolean success = true;
      for (Principal p : inferior.conjuncts()) {
        ActsForProof proof = findActsForProof(query.inferior(p), searchState);
        if (proof == null) {
          success = false;
          break;
        }
        proofs.put(p, proof);
      }

      if (success) {
        return new ToConjunctProof(query.superior, query.inferior, proofs);
      }
    }

    // Attempt to use the rule:
    //   a ≽ b or a ≽ c => a ≽ b ∨ c
    if (query.inferior instanceof DisjunctivePrincipal) {
      DisjunctivePrincipal inferior = (DisjunctivePrincipal) query.inferior;
      for (Principal witness : inferior.disjuncts()) {
        ActsForProof proof =
            findActsForProof(query.inferior(witness), searchState);
        if (proof != null) {
          // Have a proof of query.superior ≽ witness.
          DelegatesProof step = new DelegatesProof(this, witness);
          return new TransitiveProof(proof, witness, step);
        }
      }
    }

    if (query.inferior instanceof ConfPrincipal) {
      ConfPrincipal inferior = (ConfPrincipal) query.inferior;

      // Attempt to use the rule:
      //   a ≽ b => a ≽ b→
      ActsForProof proof =
          findActsForProof(query.inferior(inferior.base()), searchState);
      if (proof != null) {
        // Have a proof of query.superior ≽ inferior.base().
        DelegatesProof step = new DelegatesProof(inferior, inferior.base());
        return new TransitiveProof(proof, inferior.base(), step);
      }

      if (query.superior instanceof ConfPrincipal) {
        ConfPrincipal superior = (ConfPrincipal) query.superior;

        // Attempt to use the rule:
        //   a ≽ b => a→ ≽ b→
        proof =
            findActsForProof(
                query.superior(superior.base()).inferior(inferior.base()),
                searchState);
        if (proof != null) {
          // Have a proof of superior.base ≽ inferior.base.
          return new ConfProjectionProof(proof);
        }
      }
    }

    if (query.inferior instanceof IntegPrincipal) {
      IntegPrincipal inferior = (IntegPrincipal) query.inferior;

      // Attempt to use the rule:
      //   a ≽ b => a ≽ b←
      ActsForProof proof =
          findActsForProof(query.inferior(inferior.base()), searchState);
      if (proof != null) {
        // Have a proof of query.superior ≽ inferior.base().
        DelegatesProof step = new DelegatesProof(inferior, inferior.base());
        return new TransitiveProof(proof, inferior.base(), step);
      }

      if (query.superior instanceof IntegPrincipal) {
        IntegPrincipal superior = (IntegPrincipal) query.superior;

        // Attempt to use the rule:
        //   a ≽ b => a← ≽ b←
        proof =
            findActsForProof(
                query.superior(superior.base()).inferior(inferior.base()),
                searchState);
        if (proof != null) {
          // Have a proof of superior.base ≽ inferior.base.
          return new IntegProjectionProof(proof);
        }
      }
    }

    // Attempt to use the rule:
    //   a ∨ b ≽ c => a:b ≽ c
    if (query.superior instanceof OwnedPrincipal) {
      OwnedPrincipal superior = (OwnedPrincipal) query.superior;
      Principal a = superior.owner();
      Principal b = superior.projection();
      ActsForProof proof =
          findActsForProof(query.superior(PrincipalUtil.disjunction(a, b)),
              searchState);
      if (proof != null) {
        return new MeetToOwnerProof(superior, proof);
      }

      // Attempt to use the rule:
      //   a ≽ c and a∨b ≽ c∨d => a:b ≽ c:d
      if (query.inferior instanceof OwnedPrincipal) {
        OwnedPrincipal inferior = (OwnedPrincipal) query.inferior;
        Principal c = inferior.owner();
        Principal d = inferior.projection();
        ActsForProof ownersProof =
            findActsForProof(query.superior(a).inferior(c), searchState);
        if (ownersProof != null) {
          ActsForProof projectionProof =
              findActsForProof(query.superior(PrincipalUtil.disjunction(a, b))
                  .inferior(PrincipalUtil.disjunction(c, d)), searchState);
          if (projectionProof != null) {
            return new OwnedPrincipalsProof(query.superior, query.inferior,
                ownersProof, projectionProof);
          }
        }
      }
    }

    // Attempt to use the rule:
    //   a ≽ b => a ≽ b:c
    if (query.inferior instanceof OwnedPrincipal) {
      OwnedPrincipal inferior = (OwnedPrincipal) query.inferior;
      Principal b = inferior.owner();
      ActsForProof proof = findActsForProof(query.inferior(b), searchState);
      if (proof != null) {
        // Have a proof of query.superior ≽ inferior.owner().
        DelegatesProof step = new DelegatesProof(inferior, inferior.owner());
        return new TransitiveProof(proof, inferior.owner(), step);
      }
    }

    if (query.useDynamicContext()) {
      // Attempt to use transitivity.
      // For each usable delegation p ≽ q, try to show:
      //   query.superior ≽ p and q ≽ query.inferior.
      XXX();

      // Forward query to other nodes.
      XXX();
    }

    // Proof failed.
    return null;
  }

  private static final class ProofSearchState {
    private List<ActsForQuery> goalStack;

    public ProofSearchState() {
      goalStack = Collections.emptyList();
    }

    private ProofSearchState(ProofSearchState state, ActsForQuery query) {
      List<ActsForQuery> stack = new ArrayList<>(state.goalStack.size() + 1);
      this.goalStack = Collections.unmodifiableList(stack);
      stack.addAll(state.goalStack);
      stack.add(query);
    }

    public boolean contains(ActsForQuery query) {
      return goalStack.contains(query);
    }
  }

  /**
   * Stores a new delegation with this principal.
   *
   * @param granter the principal granting privileges
   * @param superior the principal receiving new privileges
   * @param label the label on the new delegation
   */
  public final void addDelegatesTo(Principal granter, Principal superior,
      Label label) {
    delegations.add(label, new DelegationPair(granter, superior));
  }
}
