package fabric.lang.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fabric.common.util.Pair;
import fabric.worker.Store;
import fabric.worker.transaction.AbstractSecurityCache;

/**
 * A cache of acts-for relationships and relabelling judgements. This is kept in
 * the fabric.lang.security package to ensure that only security-related classes
 * can modify this cache.
 */
public final class SecurityCache extends AbstractSecurityCache {
  private final SecurityCache parent;

  // ///////////////////////////////////////////////////////////////////////////
  // Acts-for caches
  // ///////////////////////////////////////////////////////////////////////////

  /**
   * Cache for acts-for relationships. Ported from Jif's jif.lang.PrincipalUtil.
   */
  private Map<ActsForPair, ActsForProof> actsFor;

  /**
   * Cache for not-acts-for relationships. Ported from Jif's
   * jif.lang.PrincipalUtil.
   */
  private Set<ActsForPair> notActsFor;

  /**
   * Cache for acts-for dependencies. Ported from Jif's jif.lang.PrincipalUtil.
   */
  private Map<DelegationPair, Set<ActsForPair>> actsForDependencies;

  // ///////////////////////////////////////////////////////////////////////////
  // Relabelling caches
  // ///////////////////////////////////////////////////////////////////////////

  private Set<Pair<Label, Label>> trueLabelRelabels;
  private Set<Pair<Label, Label>> falseLabelRelabels;
  private Map<DelegationPair, Set<Pair<Label, Label>>> trueLabelRelabelsDependencies;
  private Map<Pair<Label, Label>, Set<DelegationPair>> truePolicyRelabels;
  private Set<Pair<Label, Label>> falsePolicyRelabels;
  private Map<DelegationPair, Set<Pair<Label, Label>>> truePolicyRelabelsDependencies;

  // ///////////////////////////////////////////////////////////////////////////
  // Label caches
  // ///////////////////////////////////////////////////////////////////////////

  private Map<Pair<Label, Label>, Map<Store, Label>> labelJoins;
  private Map<DelegationPair, Set<Pair<Label, Label>>> labelJoinDependencies;
  private Map<Pair<Label, Label>, Map<Store, Label>> labelMeets;
  private Map<DelegationPair, Set<Pair<Label, Label>>> labelMeetDependencies;

  public SecurityCache(SecurityCache parent) {
    this.parent = parent;

    if (parent == null) {
      this.actsFor = new HashMap<ActsForPair, ActsForProof>();
      this.notActsFor = new HashSet<ActsForPair>();
      this.actsForDependencies =
          new HashMap<DelegationPair, Set<ActsForPair>>();

      this.trueLabelRelabels = new HashSet<Pair<Label, Label>>();
      this.falseLabelRelabels = new HashSet<Pair<Label, Label>>();
      this.trueLabelRelabelsDependencies =
          new HashMap<DelegationPair, Set<Pair<Label, Label>>>();
      this.truePolicyRelabels =
          new HashMap<Pair<Label, Label>, Set<DelegationPair>>();
      this.falsePolicyRelabels = new HashSet<Pair<Label, Label>>();
      this.truePolicyRelabelsDependencies =
          new HashMap<DelegationPair, Set<Pair<Label, Label>>>();

      this.labelJoins = new HashMap<Pair<Label, Label>, Map<Store, Label>>();
      this.labelMeets = new HashMap<Pair<Label, Label>, Map<Store, Label>>();
      this.labelJoinDependencies =
          new HashMap<DelegationPair, Set<Pair<Label, Label>>>();
      this.labelMeetDependencies =
          new HashMap<DelegationPair, Set<Pair<Label, Label>>>();
      return;
    }

    this.actsFor = new HashMap<ActsForPair, ActsForProof>(parent.actsFor);
    this.notActsFor = new HashSet<ActsForPair>(parent.notActsFor);
    this.actsForDependencies = copyMapSet(parent.actsForDependencies);

    this.trueLabelRelabels =
        new HashSet<Pair<Label, Label>>(parent.trueLabelRelabels);
    this.falseLabelRelabels =
        new HashSet<Pair<Label, Label>>(parent.falseLabelRelabels);
    this.trueLabelRelabelsDependencies =
        copyMapSet(parent.trueLabelRelabelsDependencies);
    this.truePolicyRelabels = copyMapSet(parent.truePolicyRelabels);
    this.falsePolicyRelabels =
        new HashSet<Pair<Label, Label>>(parent.falsePolicyRelabels);
    this.truePolicyRelabelsDependencies =
        copyMapSet(parent.truePolicyRelabelsDependencies);

    this.labelJoins = copyMapMap(parent.labelJoins);
    this.labelMeets = copyMapMap(parent.labelMeets);
    this.labelJoinDependencies = copyMapSet(parent.labelJoinDependencies);
    this.labelMeetDependencies = copyMapSet(parent.labelMeetDependencies);
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

    labelJoins.clear();
    labelMeets.clear();
    labelJoinDependencies.clear();
    labelMeetDependencies.clear();

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

      copyMapMap(parent.labelJoins, labelJoins);
      copyMapMap(parent.labelMeets, labelMeets);
      copyMapSet(parent.labelJoinDependencies, labelJoinDependencies);
      copyMapSet(parent.labelMeetDependencies, labelMeetDependencies);
    }
  }

  private <T, U> Map<T, Set<U>> copyMapSet(Map<T, Set<U>> src) {
    Map<T, Set<U>> result = new HashMap<T, Set<U>>();
    copyMapSet(src, result);
    return result;
  }

  private <T, U, V> Map<T, Map<U, V>> copyMapMap(Map<T, Map<U, V>> src) {
    Map<T, Map<U, V>> result = new HashMap<T, Map<U, V>>();
    copyMapMap(src, result);
    return result;
  }

  private <T, U> void copyMapSet(Map<T, Set<U>> src, Map<T, Set<U>> dst) {
    for (Entry<T, Set<U>> entry : src.entrySet()) {
      dst.put(entry.getKey(), new HashSet<U>(entry.getValue()));
    }
  }

  private <T, U, V> void copyMapMap(Map<T, Map<U, V>> src, Map<T, Map<U, V>> dst) {
    for (Entry<T, Map<U, V>> entry : src.entrySet()) {
      dst.put(entry.getKey(), new HashMap<U, V>(entry.getValue()));
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

  void addTrueLabelRelabel(Pair<Label, Label> pair) {
    trueLabelRelabels.add(pair);
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

  void addTrueLabelRelabelsDependency(DelegationPair del,
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

  boolean containsTruePolicyRelabel(Pair<Label, Label> pair) {
    return truePolicyRelabels.containsKey(pair);
  }

  Set<DelegationPair> getTruePolicyRelabels(Pair<Label, Label> pair) {
    return truePolicyRelabels.get(pair);
  }

  void putTruePolicyRelabels(Pair<Label, Label> pair, Set<DelegationPair> deps) {
    truePolicyRelabels.put(pair, deps);
  }

  void removeTruePolicyRelabel(Pair<Label, Label> pair) {
    truePolicyRelabels.remove(pair);
  }

  boolean containsFalsePolicyRelabel(Pair<Label, Label> pair) {
    return falsePolicyRelabels.contains(pair);
  }

  void addFalsePolicyRelabel(Pair<Label, Label> pair) {
    falsePolicyRelabels.add(pair);
  }

  void clearFalsePolicyRelabels() {
    falsePolicyRelabels.clear();
  }

  void addTruePolicyRelabelsDependency(DelegationPair del,
      Pair<Label, Label> pair) {
    Set<Pair<Label, Label>> set = truePolicyRelabelsDependencies.get(del);
    if (set == null) {
      set = new HashSet<Pair<Label, Label>>();
      truePolicyRelabelsDependencies.put(del, set);
    }
    set.add(pair);
  }

  Set<Pair<Label, Label>> removeTruePolicyRelabelsDependencies(
      DelegationPair pair) {
    return truePolicyRelabelsDependencies.remove(pair);
  }

  private <T, U, V> V get(Map<T, Map<U, V>> map, T key1, U key2) {
    Map<U, V> result = map.get(key1);
    if (result == null) return null;
    return result.get(key2);
  }

  private <T, U, V> V put(Map<T, Map<U, V>> map, T key1, U key2, V val) {
    Map<U, V> submap = map.get(key1);
    if (submap == null) {
      submap = new HashMap<U, V>();
      map.put(key1, submap);
    }

    return submap.put(key2, val);
  }

  Label getLabelJoin(Pair<Label, Label> pair, Store store) {
    return get(labelJoins, pair, store);
  }

  void putLabelJoin(Pair<Label, Label> pair, Store store, Label label) {
    put(labelJoins, pair, store, label);
  }

  void clearLabelJoins() {
    labelJoins.clear();
  }

  void removeLabelJoin(Pair<Label, Label> pair) {
    labelJoins.remove(pair);
  }

  Label getLabelMeet(Pair<Label, Label> pair, Store store) {
    return get(labelMeets, pair, store);
  }

  void putLabelMeet(Pair<Label, Label> pair, Store store, Label label) {
    put(labelMeets, pair, store, label);
  }

  void clearLabelMeets() {
    labelMeets.clear();
  }

  void removeLabelMeet(Pair<Label, Label> pair) {
    labelMeets.remove(pair);
  }

  void addLabelJoinDependency(DelegationPair del, Pair<Label, Label> pair) {
    Set<Pair<Label, Label>> set = labelJoinDependencies.get(del);
    if (set == null) {
      set = new HashSet<Pair<Label, Label>>();
      labelJoinDependencies.put(del, set);
    }
    set.add(pair);
  }

  void clearLabelJoinDependencies() {
    labelJoinDependencies.clear();
  }

  Set<Pair<Label, Label>> removeLabelJoinDependencies(DelegationPair pair) {
    return labelJoinDependencies.remove(pair);
  }

  void addLabelMeetDependency(DelegationPair del, Pair<Label, Label> pair) {
    Set<Pair<Label, Label>> set = labelMeetDependencies.get(del);
    if (set == null) {
      set = new HashSet<Pair<Label, Label>>();
      labelMeetDependencies.put(del, set);
    }
    set.add(pair);
  }

  void clearLabelMeetDependencies() {
    labelMeetDependencies.clear();
  }

  Set<Pair<Label, Label>> removeLabelMeetDependencies(DelegationPair pair) {
    return labelMeetDependencies.remove(pair);
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
