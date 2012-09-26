package fabric.lang.security;

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
   * Cache for positive label relabelling relationships. If (L1,L2) is in this
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

  public SecurityCache(SecurityCache parent) {
    this.parent = parent;

    this.actsFor = new HashMap<ActsForPair, ActsForProof>();
    this.notActsFor = new HashSet<ActsForPair>();
    this.actsForDependencies =
        new HashMap<DelegationPair, Set<ActsForPair>>();

    this.trueLabelRelabels = new HashSet<Pair<Label, Label>>();
    this.falseLabelRelabels = new HashSet<Pair<Label, Label>>();
    this.trueLabelRelabelsDependencies =
        new HashMap<DelegationPair, Set<Pair<Label, Label>>>();
    this.truePolicyRelabels =
        new HashMap<Pair<Policy, Policy>, Set<DelegationPair>>();
    this.falsePolicyRelabels = new HashSet<Pair<Policy, Policy>>();
    this.truePolicyRelabelsDependencies =
        new HashMap<DelegationPair, Set<Pair<Policy, Policy>>>();

    this.readerPolicies =
        new HashMap<Triple<Principal, Principal, Store>, ConfPolicy>();
    this.writerPolicies =
        new HashMap<Triple<Principal, Principal, Store>, IntegPolicy>();
    this.policyJoins =
        new HashMap<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>>();
    this.policyJoinDependencies =
        new HashMap<SecurityCache.DelegationPair, Set<Triple<Policy, Policy, Store>>>();
    this.policyMeets =
        new HashMap<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>>();
    this.policyMeetDependencies =
        new HashMap<SecurityCache.DelegationPair, Set<Triple<Policy, Policy, Store>>>();
    this.toLabelCache =
        new HashMap<Triple<ConfPolicy, IntegPolicy, Store>, Label>();
    this.labelJoins =
        new HashMap<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>>();
    this.labelMeets =
        new HashMap<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>>();
    this.labelJoinDependencies =
        new HashMap<SecurityCache.DelegationPair, Set<Triple<Label, Label, Store>>>();
    this.labelMeetDependencies =
        new HashMap<SecurityCache.DelegationPair, Set<Triple<Label, Label, Store>>>();

    copyStateFromParent();
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

    copyStateFromParent();
  }

  private void copyStateFromParent() {
    if (parent != null) {
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
  }

  private <T, U> void copyMapSet(Map<T, Set<U>> src, Map<T, Set<U>> dst) {
    for (Entry<T, Set<U>> entry : src.entrySet()) {
      dst.put(entry.getKey(), new HashSet<U>(entry.getValue()));
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

  void removeActsFor(ActsForPair pair) {
    actsFor.remove(pair);
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
      set = new HashSet<ActsForPair>();
      actsForDependencies.put(del, set);
    }
    set.add(pair);
  }

  Set<ActsForPair> getActsForDependencies(DelegationPair pair) {
    return actsForDependencies.get(pair);
  }

  Set<ActsForPair> removeActsForDependencies(DelegationPair pair) {
    return actsForDependencies.remove(pair);
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

  void removeTrueLabelRelabel(Pair<Label, Label> pair) {
    trueLabelRelabels.remove(pair);
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
      set = new HashSet<Pair<Label, Label>>();
      trueLabelRelabelsDependencies.put(del, set);
    }
    set.add(pair);
  }

  Set<Pair<Label, Label>> removeTrueLabelRelabelsDependencies(
      DelegationPair pair) {
    return trueLabelRelabelsDependencies.remove(pair);
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

  void removeTruePolicyRelabel(Pair<Policy, Policy> pair) {
    truePolicyRelabels.remove(pair);
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
      set = new HashSet<Pair<Policy, Policy>>();
      truePolicyRelabelsDependencies.put(del, set);
    }
    set.add(pair);
  }

  Set<Pair<Policy, Policy>> removeTruePolicyRelabelsDependencies(
      DelegationPair pair) {
    return truePolicyRelabelsDependencies.remove(pair);
  }

  ConfPolicy getReaderPolicy(Triple<Principal, Principal, Store> triple) {
    return readerPolicies.get(triple);
  }

  Set<Entry<Triple<Principal, Principal, Store>, ConfPolicy>> readerPolicyEntrySet() {
    return readerPolicies.entrySet();
  }

  void putReaderPolicy(Triple<Principal, Principal, Store> triple,
      ConfPolicy policy) {
    readerPolicies.put(triple, policy);
  }

  IntegPolicy getWriterPolicy(Triple<Principal, Principal, Store> triple) {
    return writerPolicies.get(triple);
  }

  Set<Entry<Triple<Principal, Principal, Store>, IntegPolicy>> writerPolicyEntrySet() {
    return writerPolicies.entrySet();
  }

  void putWriterPolicy(Triple<Principal, Principal, Store> triple,
      IntegPolicy policy) {
    writerPolicies.put(triple, policy);
  }

  Pair<Policy, Set<DelegationPair>> getPolicyJoin(
      Triple<Policy, Policy, Store> triple) {
    return policyJoins.get(triple);
  }

  Set<Entry<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>>> policyJoinEntrySet() {
    return policyJoins.entrySet();
  }

  void putPolicyJoin(Triple<Policy, Policy, Store> triple, Policy policy,
      Set<DelegationPair> deps) {
    policyJoins
    .put(triple, new Pair<Policy, Set<DelegationPair>>(policy, deps));

    // Record that this join depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addPolicyJoinDependency(del, triple);
    }
  }

  private void addPolicyJoinDependency(DelegationPair del,
      Triple<Policy, Policy, Store> triple) {
    Set<Triple<Policy, Policy, Store>> set = policyJoinDependencies.get(del);
    if (set == null) {
      set = new HashSet<Triple<Policy, Policy, Store>>();
      policyJoinDependencies.put(del, set);
    }
    set.add(triple);
  }

  void clearPolicyJoinDependencies() {
    policyJoinDependencies.clear();
  }

  Set<Triple<Policy, Policy, Store>> removePolicyJoinDependencies(
      DelegationPair pair) {
    return policyJoinDependencies.remove(pair);
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
    policyMeets
    .put(triple, new Pair<Policy, Set<DelegationPair>>(policy, deps));

    // Record that this meet depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addPolicyMeetDependency(del, triple);
    }
  }

  private void addPolicyMeetDependency(DelegationPair del,
      Triple<Policy, Policy, Store> triple) {
    Set<Triple<Policy, Policy, Store>> set = policyMeetDependencies.get(del);
    if (set == null) {
      set = new HashSet<Triple<Policy, Policy, Store>>();
      policyMeetDependencies.put(del, set);
    }
    set.add(triple);
  }

  void clearPolicyMeetDependencies() {
    policyMeetDependencies.clear();
  }

  Set<Triple<Policy, Policy, Store>> removePolicyMeetDependencies(
      DelegationPair pair) {
    return policyMeetDependencies.remove(pair);
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
    labelJoins.put(triple, new Pair<Label, Set<DelegationPair>>(label, deps));

    // Record that this join depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addLabelJoinDependency(del, triple);
    }
  }

  void clearLabelJoins() {
    labelJoins.clear();
  }

  void removeLabelJoin(Triple<Label, Label, Store> triple) {
    labelJoins.remove(triple);
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
    labelMeets.put(triple, new Pair<Label, Set<DelegationPair>>(label, deps));

    // Record that this meet depends on the given set of dependencies.
    for (DelegationPair del : deps) {
      addLabelMeetDependency(del, triple);
    }
  }

  void clearLabelMeets() {
    labelMeets.clear();
  }

  void removeLabelMeet(Triple<Label, Label, Store> triple) {
    labelMeets.remove(triple);
  }

  private void addLabelJoinDependency(DelegationPair del,
      Triple<Label, Label, Store> triple) {
    Set<Triple<Label, Label, Store>> set = labelJoinDependencies.get(del);
    if (set == null) {
      set = new HashSet<Triple<Label, Label, Store>>();
      labelJoinDependencies.put(del, set);
    }
    set.add(triple);
  }

  void clearLabelJoinDependencies() {
    labelJoinDependencies.clear();
  }

  Set<Triple<Label, Label, Store>> removeLabelJoinDependencies(
      DelegationPair pair) {
    return labelJoinDependencies.remove(pair);
  }

  void addLabelMeetDependency(DelegationPair del,
      Triple<Label, Label, Store> triple) {
    Set<Triple<Label, Label, Store>> set = labelMeetDependencies.get(del);
    if (set == null) {
      set = new HashSet<Triple<Label, Label, Store>>();
      labelMeetDependencies.put(del, set);
    }
    set.add(triple);
  }

  void clearLabelMeetDependencies() {
    labelMeetDependencies.clear();
  }

  Set<Triple<Label, Label, Store>> removeLabelMeetDependencies(
      DelegationPair pair) {
    return labelMeetDependencies.remove(pair);
  }

  static <T extends fabric.lang.Object, U> Triple<T, T, U> canonicalize(T x,
      T y, U z) {
    if (compare(x, y) <= 0) return new Triple<T, T, U>(x, y, z);
    return new Triple<T, T, U>(y, x, z);
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
