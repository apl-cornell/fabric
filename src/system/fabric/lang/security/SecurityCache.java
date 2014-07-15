package fabric.lang.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fabric.common.util.Pair;
import fabric.common.util.Triple;
import fabric.worker.Store;
import fabric.worker.transaction.AbstractSecurityCache;

/**
 * A per-transaction cache of acts-for relationships, relabelling judgements,
 * and label and policy objects (allowing us to intern Label, ConfPolicy, and
 * IntegPolicy objects). Because this a transaction-level cache, it is separate
 * from LabelCache, which is a top-level cache. This is kept in the
 * fabric.lang.security package to ensure that only security-related classes
 * can modify this cache.
 */
public final class SecurityCache extends AbstractSecurityCache {
  private final SecurityCache parent;
  private final LabelCache topLevelCache;

  // ///////////////////////////////////////////////////////////////////////////
  // Acts-for caches
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * Cache for acts-for relationships. Maps cached acts-for relationships to a
   * proof for the relationship.
   */
  private Map<ActsForPair, ActsForProof> actsFor;

  /**
   * Cache for acts-for dependencies. Maps delegation pairs to the set of cached
   * acts-for relationships that depend on the delegation.
   */
  private Map<DelegationPair, Set<ActsForPair>> actsForDependencies;

  /**
   * Cache for not-acts-for relationships.
   */
  private Set<ActsForPair> notActsFor;

  // ///////////////////////////////////////////////////////////////////////////
  // Relabelling caches
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * Cache for positive label-relabelling relationships. If (L1,L2) is in this
   * set, then L1 relabels to L2.
   */
  private Set<Pair<Label, Label>> trueLabelRelabels;

  /**
   * Cache for label relabelling dependencies. Maps delegation pairs to the set
   * of cached positive label relabelling relationships that depend on the
   * delegation.
   */
  private Map<DelegationPair, Set<Pair<Label, Label>>> trueLabelRelabelsDependencies;

  /**
   * Cache for positive policy relabelling relationships. If (P1,P2) is in this
   * set, then P1 relabels to P2.
   */
  private Map<Pair<Policy, Policy>, Set<DelegationPair>> truePolicyRelabels;

  /**
   * Cache for policy relabelling dependencies. Maps delegation pairs to the set
   * of cache politive policy relabelling relationships that depend on the
   * delegation.
   */
  private Map<DelegationPair, Set<Pair<Policy, Policy>>> truePolicyRelabelsDependencies;

  /**
   * Cache for negative label relabelling relationships. If (L1,L2) is in this
   * set, then L1 does not relabel to L2.
   */
  private Set<Pair<Label, Label>> falseLabelRelabels;

  /**
   * Cache for negative policy relabelling relationships. If (P1,P2) is in this
   * set, then P1 does not relabel to P2.
   */
  private Set<Pair<Policy, Policy>> falsePolicyRelabels;

  // ///////////////////////////////////////////////////////////////////////////
  // Policy & label caches
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * Cache for creating reader policies. If (P1,P2,S) is mapped to C, then C is
   * an object on store S representing the policy P1→P2.
   */
  private Map<Triple<Principal, Principal, Store>, ConfPolicy> readerPolicies;

  /**
   * Cache for creating writer policies. If (P1,P2,S) is mapped to C, then C is
   * an object on store S representing the policy P1←P2.
   */
  private Map<Triple<Principal, Principal, Store>, IntegPolicy> writerPolicies;

  /**
   * Cache for policy joins. If (P1,P2,S) is mapped to (C,D), then C is an
   * object on store S representing the policy P1 ⊔ P2, and was created using
   * the delegations in the (immutable) set D.
   */
  private Map<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>> policyJoins;

  /**
   * Cache for policy join dependencies. Maps delegation pairs to the components
   * of the joins that depend on the delegation. If a delegation D is mapped to
   * a set containing (P1,P2,S), then the entry for (P1,P2,S) in policyJoins
   * depends on D.
   */
  private Map<DelegationPair, Set<Triple<Policy, Policy, Store>>> policyJoinDependencies;

  /**
   * Cache for policy meets. If (P1,P2,S) is mapped to (C,D), then C is an
   * object on store S representing the policy P1 ⊓ P2, and was created using
   * the delegations in the (immutable) set D.
   */
  private Map<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>> policyMeets;

  /**
   * Cache for policy meet dependencies. Maps delegation pairs to the components
   * of the meets that depend on the delegation. If a delegation D is mapped to
   * a set containing (P1,P2,S), then the entry for (P1,P2,S) in policyMeets
   * depends on D.
   */
  private Map<DelegationPair, Set<Triple<Policy, Policy, Store>>> policyMeetDependencies;

  /**
   * Cache for creating labels. If (C,I,S) is mapped to L, then L is an object
   * on store S representing the label {C;I}.
   */
  private Map<Triple<ConfPolicy, IntegPolicy, Store>, Label> toLabelCache;

  /**
   * Cache for label joins. If (L1,L2,S) is mapped to (L3,D), then L3 is an
   * object on store S representing L1 ⊔ L2, and was created using the
   * delegations in the (immutable) set D.
   */
  private Map<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>> labelJoins;

  /**
   * Cache for label join dependencies. Maps delegation pairs to the components
   * of the joins that depend on the delegation. If a delegation D is mapped to
   * a set containing (L1,L2,S), then the entry for (L1,L2,S) in labelJoins
   * depends on D.
   */
  private Map<DelegationPair, Set<Triple<Label, Label, Store>>> labelJoinDependencies;

  /**
   * Cache for label meets. If (L1,L2,S) is mapped to (L3,D), then L3 is an
   * object on store S representing L1 ⊓ L2, and was created using the
   * delegations in the (immutable) set D.
   */
  private Map<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>> labelMeets;

  /**
   * Cache for label meet dependencies. Maps delegation pairs to the components
   * of the meets that depend on the delegation. If a delegation D is mapped to
   * a set containing (L1,L2,S), then the entry for (L1,L2,S) in labelMeets
   * depends on D.
   */
  private Map<DelegationPair, Set<Triple<Label, Label, Store>>> labelMeetDependencies;

  public SecurityCache(LabelCache topLevelCache) {
    this(topLevelCache, null);
  }

  public SecurityCache(SecurityCache parent) {
    this(parent.topLevelCache, parent);
    copyStateFromParent();
  }

  private SecurityCache(LabelCache topLevelCache, SecurityCache parent) {
    this.parent = parent;
    this.topLevelCache = topLevelCache;

    this.actsFor = new HashMap<>();
    this.notActsFor = new HashSet<>();
    this.actsForDependencies = new HashMap<>();

    this.trueLabelRelabels = new HashSet<>();
    this.falseLabelRelabels = new HashSet<>();
    this.trueLabelRelabelsDependencies = new HashMap<>();
    this.truePolicyRelabels = new HashMap<>();
    this.falsePolicyRelabels = new HashSet<>();
    this.truePolicyRelabelsDependencies = new HashMap<>();

    this.readerPolicies = new HashMap<>();
    this.writerPolicies = new HashMap<>();
    this.policyJoins = new HashMap<>();
    this.policyJoinDependencies = new HashMap<>();
    this.policyMeets = new HashMap<>();
    this.policyMeetDependencies = new HashMap<>();
    this.toLabelCache = new HashMap<>();
    this.labelJoins = new HashMap<>();
    this.labelMeets = new HashMap<>();
    this.labelJoinDependencies = new HashMap<>();
    this.labelMeetDependencies = new HashMap<>();
  }

  @Override
  protected void reset() {
    actsFor.clear();
    notActsFor.clear();
    actsForDependencies.clear();

    trueLabelRelabels.clear();
    falseLabelRelabels.clear();
    trueLabelRelabelsDependencies.clear();
    truePolicyRelabels.clear();
    falsePolicyRelabels.clear();
    truePolicyRelabelsDependencies.clear();

    readerPolicies.clear();
    writerPolicies.clear();
    policyJoins.clear();
    policyJoinDependencies.clear();
    policyMeets.clear();
    policyMeetDependencies.clear();
    toLabelCache.clear();
    labelJoins.clear();
    labelMeets.clear();
    labelJoinDependencies.clear();
    labelMeetDependencies.clear();

    if (parent != null) copyStateFromParent();
  }

  private void copyStateFromParent() {
    actsFor.putAll(parent.actsFor);
    notActsFor.addAll(parent.notActsFor);
    copyMapSet(parent.actsForDependencies, actsForDependencies);

    trueLabelRelabels.addAll(parent.trueLabelRelabels);
    falseLabelRelabels.addAll(parent.falseLabelRelabels);
    copyMapSet(parent.trueLabelRelabelsDependencies,
        trueLabelRelabelsDependencies);
    copyMapSet(parent.truePolicyRelabels, truePolicyRelabels);
    falsePolicyRelabels.addAll(parent.falsePolicyRelabels);
    copyMapSet(parent.truePolicyRelabelsDependencies,
        truePolicyRelabelsDependencies);

    readerPolicies.putAll(parent.readerPolicies);
    writerPolicies.putAll(parent.writerPolicies);
    policyJoins.putAll(parent.policyJoins);
    copyMapSet(parent.policyJoinDependencies, policyJoinDependencies);
    policyMeets.putAll(parent.policyMeets);
    copyMapSet(parent.policyMeetDependencies, policyMeetDependencies);
    toLabelCache.putAll(parent.toLabelCache);
    labelJoins.putAll(parent.labelJoins);
    copyMapSet(parent.labelJoinDependencies, labelJoinDependencies);
    labelMeets.putAll(parent.labelMeets);
    copyMapSet(parent.labelMeetDependencies, labelMeetDependencies);
  }

  private <T, U> void copyMapSet(Map<T, Set<U>> src, Map<T, Set<U>> dst) {
    for (Entry<T, Set<U>> entry : src.entrySet()) {
      dst.put(entry.getKey(), new HashSet<>(entry.getValue()));
    }
  }

  @Override
  protected void set(SecurityCache cache) {
    actsFor = cache.actsFor;
    notActsFor = cache.notActsFor;
    actsForDependencies = cache.actsForDependencies;

    trueLabelRelabels = cache.trueLabelRelabels;
    falseLabelRelabels = cache.falseLabelRelabels;
    trueLabelRelabelsDependencies = cache.trueLabelRelabelsDependencies;
    truePolicyRelabels = cache.truePolicyRelabels;
    falsePolicyRelabels = cache.falsePolicyRelabels;
    truePolicyRelabelsDependencies = cache.truePolicyRelabelsDependencies;

    readerPolicies = cache.readerPolicies;
    writerPolicies = cache.writerPolicies;
    policyJoins = cache.policyJoins;
    policyJoinDependencies = cache.policyJoinDependencies;
    policyMeets = cache.policyMeets;
    policyMeetDependencies = cache.policyMeetDependencies;
    toLabelCache = cache.toLabelCache;
    labelJoins = cache.labelJoins;
    labelMeets = cache.labelMeets;
    labelJoinDependencies = cache.labelJoinDependencies;
    labelMeetDependencies = cache.labelMeetDependencies;
  }

  @Override
  protected void mergeWithTopLevel() {
    // Sanity check: make sure we don't have a parent -- only top-level security
    // caches for top-level transactions should be merged with the top-level
    // cache.
    if (parent != null) throw new InternalError();

    topLevelCache.addAll(this);
  }

  void notifyRevokedDelegation(DelegationPair del) {
    {
      // Remove all positive acts-for relationships that depend on the
      // delegation.
      Set<ActsForPair> deps = actsForDependencies.remove(del);
      if (deps != null) {
        for (ActsForPair pair : deps) {
          actsFor.remove(pair);
        }
      }
    }
    {
      // Remove all positive label-relabelling relationships that depend on the
      // given delegation.
      Set<Pair<Label, Label>> deps = trueLabelRelabelsDependencies.remove(del);
      if (deps != null) {
        for (Pair<Label, Label> pair : deps) {
          trueLabelRelabels.remove(pair);
        }
      }
    }
    {
      // Remove all positive policy-relabelling relationships that depend on the
      // give delegation.
      Set<Pair<Policy, Policy>> deps =
          truePolicyRelabelsDependencies.remove(del);
      if (deps != null) {
        for (Pair<Policy, Policy> pair : deps) {
          truePolicyRelabels.remove(pair);
        }
      }
    }
    {
      // Remove all cached policy joins that depend on the given delegation.
      Set<Triple<Policy, Policy, Store>> deps =
          policyJoinDependencies.remove(del);
      if (deps != null) {
        for (Triple<Policy, Policy, Store> triple : deps) {
          policyJoins.remove(triple);
        }
      }
    }
    {
      // Remove all cached policy meets that depend on the given delegation.
      Set<Triple<Policy, Policy, Store>> deps =
          policyMeetDependencies.remove(del);
      if (deps != null) {
        for (Triple<Policy, Policy, Store> triple : deps) {
          policyMeets.remove(triple);
        }
      }
    }
    {
      // Remove all cached label joins that depend on the given delegation.
      Set<Triple<Label, Label, Store>> deps = labelJoinDependencies.remove(del);
      if (deps != null) {
        for (Triple<Label, Label, Store> triple : deps) {
          labelJoins.remove(triple);
        }
      }
    }
    {
      // Remove all cached label meets that depend on the given delegation.
      Set<Triple<Label, Label, Store>> deps = labelMeetDependencies.remove(del);
      if (deps != null) {
        for (Triple<Label, Label, Store> triple : deps) {
          labelMeets.remove(triple);
        }
      }
    }
  }

  // ///////////////////////////////////////////////////////////////////////////
  // Acts-for cache operations
  // ///////////////////////////////////////////////////////////////////////////

  boolean containsActsFor(ActsForPair pair) {
    return actsFor.containsKey(pair);
  }

  ActsForProof getActsFor(ActsForPair pair) {
    return actsFor.get(pair);
  }

  void putActsFor(ActsForPair pair, ActsForProof proof) {
    actsFor.put(pair, proof);
  }

  boolean containsNotActsFor(ActsForPair pair) {
    return notActsFor.contains(pair);
  }

  void addNotActsFor(ActsForPair pair) {
    notActsFor.add(pair);
  }

  void clearNotActsFor() {
    notActsFor.clear();
  }

  void addActsForDependency(DelegationPair del, ActsForPair pair) {
    Set<ActsForPair> set = actsForDependencies.get(del);
    if (set == null) {
      set = new HashSet<>();
      actsForDependencies.put(del, set);
    }
    set.add(pair);
  }

  Set<ActsForPair> getActsForDependencies(DelegationPair pair) {
    return actsForDependencies.get(pair);
  }

  // ///////////////////////////////////////////////////////////////////////////
  // Label cache operations
  // ///////////////////////////////////////////////////////////////////////////

  boolean containsTrueLabelRelabel(Pair<Label, Label> pair) {
    return trueLabelRelabels.contains(pair);
  }

  void addTrueLabelRelabel(Pair<Label, Label> pair, Set<DelegationPair> deps) {
    trueLabelRelabels.add(pair);

    // Record that this relabelling depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addTrueLabelRelabelsDependency(del, pair);
    }
  }

  boolean containsFalseLabelRelabel(Pair<Label, Label> pair) {
    return falseLabelRelabels.contains(pair);
  }

  void addFalseLabelRelabel(Pair<Label, Label> pair) {
    falseLabelRelabels.add(pair);
  }

  void clearFalseLabelRelabels() {
    falseLabelRelabels.clear();
  }

  private void addTrueLabelRelabelsDependency(DelegationPair del,
      Pair<Label, Label> pair) {
    Set<Pair<Label, Label>> set = trueLabelRelabelsDependencies.get(del);
    if (set == null) {
      set = new HashSet<>();
      trueLabelRelabelsDependencies.put(del, set);
    }
    set.add(pair);
  }

  boolean containsTruePolicyRelabel(Pair<Policy, Policy> pair) {
    return truePolicyRelabels.containsKey(pair);
  }

  Set<DelegationPair> getTruePolicyRelabels(Pair<Policy, Policy> pair) {
    return truePolicyRelabels.get(pair);
  }

  void putTruePolicyRelabels(Pair<Policy, Policy> pair, Set<DelegationPair> deps) {
    truePolicyRelabels.put(pair, deps);

    // Update the inverse map too.
    for (DelegationPair del : deps) {
      addTruePolicyRelabelsDependency(del, pair);
    }
  }

  boolean containsFalsePolicyRelabel(Pair<Policy, Policy> pair) {
    return falsePolicyRelabels.contains(pair);
  }

  void addFalsePolicyRelabel(Pair<Policy, Policy> pair) {
    falsePolicyRelabels.add(pair);
  }

  void clearFalsePolicyRelabels() {
    falsePolicyRelabels.clear();
  }

  private void addTruePolicyRelabelsDependency(DelegationPair del,
      Pair<Policy, Policy> pair) {
    Set<Pair<Policy, Policy>> set = truePolicyRelabelsDependencies.get(del);
    if (set == null) {
      set = new HashSet<>();
      truePolicyRelabelsDependencies.put(del, set);
    }
    set.add(pair);
  }

  /**
   * Given a triple (P1, P2, S), returns a cached object on store S that
   * represents the confidentiality policy P1→P2. Null is returned if no such
   * object is cached. The top-level cache is checked first before falling back
   * on the transaction-local cache.
   */
  ConfPolicy getReaderPolicy(Triple<Principal, Principal, Store> triple) {
    ConfPolicy result = topLevelCache.getReaderPolicy(triple);
    if (result == null) result = readerPolicies.get(triple);
    return result;
  }

  /**
   * Returns the reader-policy entry set for the transaction-local cache. In
   * each ((P1, P2, S), C) entry, C is an object on store S representing the
   * confidentiality policy P1→P2.
   */
  Set<Entry<Triple<Principal, Principal, Store>, ConfPolicy>> readerPolicyEntrySet() {
    return readerPolicies.entrySet();
  }

  /**
   * Adds a reader policy to the transaction-local cache.
   */
  void putReaderPolicy(Triple<Principal, Principal, Store> triple,
      ConfPolicy policy) {
    readerPolicies.put(triple, policy);
  }

  /**
   * Given a triple (P1, P2, S), returns a cached object on store S that
   * represents the integrity policy P1←P2. Null is returned if no such object
   * is cached. The top-level cache is checked first before falling back on the
   * transaction-local cache.
   */
  IntegPolicy getWriterPolicy(Triple<Principal, Principal, Store> triple) {
    IntegPolicy result = topLevelCache.getWriterPolicy(triple);
    if (result == null) result = writerPolicies.get(triple);
    return result;
  }

  /**
   * Returns the writer-policy entry set for the transaction-local cache. In
   * each ((P1, P2, S), I) entry, I is an object on store S representing the
   * integrity policy P1←P2.
   */
  Set<Entry<Triple<Principal, Principal, Store>, IntegPolicy>> writerPolicyEntrySet() {
    return writerPolicies.entrySet();
  }

  /**
   * Adds a writer policy to the transaction-local cache.
   */
  void putWriterPolicy(Triple<Principal, Principal, Store> triple,
      IntegPolicy policy) {
    writerPolicies.put(triple, policy);
  }

  /**
   * Given a triple (P1, P2, S), returns a cached object on store S that
   * represents the policy P1 ⊔ P2. Null is returned if no such object is
   * cached. The top-level cache is checked first before falling back on the
   * transaction-local cache.
   */
  Pair<Policy, Set<DelegationPair>> getPolicyJoin(
      Triple<Policy, Policy, Store> triple) {
    Policy result = topLevelCache.getPolicyJoin(triple);
    if (result == null) return policyJoins.get(triple);
    return new Pair<>(result, Collections.<DelegationPair> emptySet());
  }

  /**
   * Returns the policy-join entry set for the transaction-local cache. In each
   * ((P1, P2, S), (P3, D)) entry, P3 is an object on store S representing the
   * policy P1 ⊔ P2, and was constructed using the delegations in D.
   */
  Set<Entry<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>>> policyJoinEntrySet() {
    return policyJoins.entrySet();
  }

  /**
   * Adds a policy join to the transaction-local cache.
   */
  void putPolicyJoin(Triple<Policy, Policy, Store> triple, Policy policy,
      Set<DelegationPair> deps) {
    policyJoins.put(triple, new Pair<>(policy, deps));

    // Record that this join depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addPolicyJoinDependency(del, triple);
    }
  }

  /**
   * Records that the the policy-join entry corresponding to the given triple
   * depends on the given delegation.
   */
  private void addPolicyJoinDependency(DelegationPair del,
      Triple<Policy, Policy, Store> triple) {
    Set<Triple<Policy, Policy, Store>> set = policyJoinDependencies.get(del);
    if (set == null) {
      set = new HashSet<>();
      policyJoinDependencies.put(del, set);
    }
    set.add(triple);
  }

  Pair<Policy, Set<DelegationPair>> getPolicyMeet(
      Triple<Policy, Policy, Store> triple) {
    return policyMeets.get(triple);
  }

  Set<Entry<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>>> policyMeetEntrySet() {
    return policyMeets.entrySet();
  }

  void putPolicyMeet(Triple<Policy, Policy, Store> triple, Policy policy,
      Set<DelegationPair> deps) {
    policyMeets.put(triple, new Pair<>(policy, deps));

    // Record that this meet depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addPolicyMeetDependency(del, triple);
    }
  }

  private void addPolicyMeetDependency(DelegationPair del,
      Triple<Policy, Policy, Store> triple) {
    Set<Triple<Policy, Policy, Store>> set = policyMeetDependencies.get(del);
    if (set == null) {
      set = new HashSet<>();
      policyMeetDependencies.put(del, set);
    }
    set.add(triple);
  }

  Label getLabel(Triple<ConfPolicy, IntegPolicy, Store> triple) {
    return toLabelCache.get(triple);
  }

  Set<Entry<Triple<ConfPolicy, IntegPolicy, Store>, Label>> labelEntrySet() {
    return toLabelCache.entrySet();
  }

  void putLabel(Triple<ConfPolicy, IntegPolicy, Store> triple, Label label) {
    toLabelCache.put(triple, label);
  }

  Label getLabelJoin(Triple<Label, Label, Store> triple) {
    Pair<Label, ?> value = labelJoins.get(triple);
    return value == null ? null : value.first;
  }

  Set<Entry<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>>> labelJoinEntrySet() {
    return labelJoins.entrySet();
  }

  void putLabelJoin(Triple<Label, Label, Store> triple, Label label,
      Set<DelegationPair> deps) {
    labelJoins.put(triple, new Pair<>(label, deps));

    // Record that this join depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addLabelJoinDependency(del, triple);
    }
  }

  Label getLabelMeet(Triple<Label, Label, Store> triple) {
    Pair<Label, ?> value = labelMeets.get(triple);
    return value == null ? null : value.first;
  }

  Set<Entry<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>>> labelMeetEntrySet() {
    return labelMeets.entrySet();
  }

  void putLabelMeet(Triple<Label, Label, Store> triple, Label label,
      Set<DelegationPair> deps) {
    labelMeets.put(triple, new Pair<>(label, deps));

    // Record that this meet depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addLabelMeetDependency(del, triple);
    }
  }

  private void addLabelJoinDependency(DelegationPair del,
      Triple<Label, Label, Store> triple) {
    Set<Triple<Label, Label, Store>> set = labelJoinDependencies.get(del);
    if (set == null) {
      set = new HashSet<>();
      labelJoinDependencies.put(del, set);
    }
    set.add(triple);
  }

  void addLabelMeetDependency(DelegationPair del,
      Triple<Label, Label, Store> triple) {
    Set<Triple<Label, Label, Store>> set = labelMeetDependencies.get(del);
    if (set == null) {
      set = new HashSet<>();
      labelMeetDependencies.put(del, set);
    }
    set.add(triple);
  }

  static <T extends fabric.lang.Object, U> Triple<T, T, U> canonicalize(T x,
      T y, U z) {
    if (compare(x, y) <= 0) return new Triple<>(x, y, z);
    return new Triple<>(y, x, z);
  }

  private static int compare(fabric.lang.Object o1, fabric.lang.Object o2) {
    int storeCompare = o1.$getStore().name().compareTo(o2.$getStore().name());
    if (storeCompare != 0) return storeCompare;

    long onum1 = o1.$getOnum();
    long onum2 = o2.$getOnum();
    if (onum1 == onum2) return 0;
    if (onum1 < onum2) return -1;
    return 1;
  }

  /**
   * Ported from Jif's jif.lang.PrincipalUtil.
   */
  static abstract class PrincipalPair {
    final Principal p;
    final Principal q;

    PrincipalPair(Principal p, Principal q) {
      this.p = p;
      this.q = q;
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || !o.getClass().equals(this.getClass())) return false;

      PrincipalPair that = (PrincipalPair) o;
      return PrincipalUtil._Impl.equals(this.p, that.p);
      // Redundant; PrincipalUtil.equals does this already:
//           && PrincipalUtil._Impl.equals(this.q, that.q)
    }

    @Override
    public int hashCode() {
      return (p == null ? -4234 : p.hashCode())
          ^ (q == null ? 23 : q.hashCode());
    }

    @Override
    public String toString() {
      return p.name() + "-" + q.name();
    }
  }

  /**
   * Ported from Jif's jif.lang.PrincipalUtil.
   */
  static class ActsForPair extends PrincipalPair {
    ActsForPair(Principal superior, Principal inferior) {
      super(superior, inferior);
    }
  }

  /**
   * Ported from Jif's jif.lang.PrincipalUtil.
   */
  static class DelegationPair extends PrincipalPair {
    DelegationPair(Principal superior, Principal inferior) {
      super(superior, inferior);
    }
  }
}
