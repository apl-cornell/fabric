package fabric.lang.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
  // Label caches
  // ///////////////////////////////////////////////////////////////////////////

  private Set<Pair> trueLabelRelabels;
  private Set<Pair> falseLabelRelabels;
  private Map<DelegationPair, Set<Pair>> trueLabelRelabelsDependencies;
  private Map<Pair, Set<DelegationPair>> truePolicyRelabels;
  private Set<Pair> falsePolicyRelabels;
  private Map<DelegationPair, Set<Pair>> truePolicyRelabelsDependencies;
  private Map<Pair, Label> labelJoins;
  private Map<Pair, Label> labelMeets;
  private Map<DelegationPair, Set<Pair>> labelJoinDependencies;
  private Map<DelegationPair, Set<Pair>> labelMeetDependencies;

  public SecurityCache(SecurityCache parent) {
    this.parent = parent;

    if (parent == null) {
      this.actsFor = new HashMap<ActsForPair, ActsForProof>();
      this.notActsFor = new HashSet<ActsForPair>();
      this.actsForDependencies =
          new HashMap<DelegationPair, Set<ActsForPair>>();

      this.trueLabelRelabels = new HashSet<Pair>();
      this.falseLabelRelabels = new HashSet<Pair>();
      this.trueLabelRelabelsDependencies =
          new HashMap<DelegationPair, Set<Pair>>();
      this.truePolicyRelabels = new HashMap<Pair, Set<DelegationPair>>();
      this.falsePolicyRelabels = new HashSet<Pair>();
      this.truePolicyRelabelsDependencies =
          new HashMap<DelegationPair, Set<Pair>>();
      this.labelJoins = new HashMap<Pair, Label>();
      this.labelMeets = new HashMap<Pair, Label>();
      this.labelJoinDependencies = new HashMap<DelegationPair, Set<Pair>>();
      this.labelMeetDependencies = new HashMap<DelegationPair, Set<Pair>>();
      return;
    }

    this.actsFor = new HashMap<ActsForPair, ActsForProof>(parent.actsFor);
    this.notActsFor = new HashSet<ActsForPair>(parent.notActsFor);
    this.actsForDependencies =
        new HashMap<DelegationPair, Set<ActsForPair>>(
            parent.actsForDependencies);

    this.trueLabelRelabels = new HashSet<Pair>(parent.trueLabelRelabels);
    this.falseLabelRelabels = new HashSet<Pair>(parent.falseLabelRelabels);
    this.trueLabelRelabelsDependencies =
        new HashMap<DelegationPair, Set<Pair>>(
            parent.trueLabelRelabelsDependencies);
    this.truePolicyRelabels =
        new HashMap<Pair, Set<DelegationPair>>(parent.truePolicyRelabels);
    this.falsePolicyRelabels = new HashSet<Pair>(parent.falsePolicyRelabels);
    this.truePolicyRelabelsDependencies =
        new HashMap<DelegationPair, Set<Pair>>(
            parent.truePolicyRelabelsDependencies);
    this.labelJoins = new HashMap<Pair, Label>(parent.labelJoins);
    this.labelMeets = new HashMap<Pair, Label>(parent.labelMeets);
    this.labelJoinDependencies =
        new HashMap<DelegationPair, Set<Pair>>(parent.labelJoinDependencies);
    this.labelMeetDependencies =
        new HashMap<DelegationPair, Set<Pair>>(parent.labelMeetDependencies);
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
      actsForDependencies.putAll(parent.actsForDependencies);

      trueLabelRelabels.addAll(parent.trueLabelRelabels);
      falseLabelRelabels.addAll(parent.falseLabelRelabels);
      trueLabelRelabelsDependencies
          .putAll(parent.trueLabelRelabelsDependencies);
      truePolicyRelabels.putAll(parent.truePolicyRelabels);
      falsePolicyRelabels.addAll(parent.falsePolicyRelabels);
      truePolicyRelabelsDependencies
          .putAll(parent.truePolicyRelabelsDependencies);
      labelJoins.putAll(parent.labelJoins);
      labelMeets.putAll(parent.labelMeets);
      labelJoinDependencies.putAll(parent.labelJoinDependencies);
      labelMeetDependencies.putAll(parent.labelMeetDependencies);
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

  boolean containsTrueLabelRelabel(Pair pair) {
    return trueLabelRelabels.contains(pair);
  }
  
  void addTrueLabelRelabel(Pair pair) {
    trueLabelRelabels.add(pair);
  }
  
  void removeTrueLabelRelabel(Pair pair) {
    trueLabelRelabels.remove(pair);
  }

  boolean containsFalseLabelRelabel(Pair pair) {
    return falseLabelRelabels.contains(pair);
  }
  
  void addFalseLabelRelabel(Pair pair) {
    falseLabelRelabels.add(pair);
  }

  void clearFalseLabelRelabels() {
    falseLabelRelabels.clear();
  }
  
  void addTrueLabelRelabelsDependency(DelegationPair del, Pair pair) {
    Set<Pair> set = trueLabelRelabelsDependencies.get(del);
    if (set == null) {
      set = new HashSet<Pair>();
      trueLabelRelabelsDependencies.put(del, set);
    }
    set.add(pair);
  }

  Set<Pair> removeTrueLabelRelabelsDependencies(DelegationPair pair) {
    return trueLabelRelabelsDependencies.remove(pair);
  }
  
  boolean containsTruePolicyRelabel(Pair pair) {
    return truePolicyRelabels.containsKey(pair);
  }
  
  Set<DelegationPair> getTruePolicyRelabels(Pair pair) {
    return truePolicyRelabels.get(pair);
  }
  
  void putTruePolicyRelabels(Pair pair, Set<DelegationPair> deps) {
    truePolicyRelabels.put(pair, deps);
  }
  
  void removeTruePolicyRelabel(Pair pair) {
    truePolicyRelabels.remove(pair);
  }
  
  boolean containsFalsePolicyRelabel(Pair pair) {
    return falsePolicyRelabels.contains(pair);
  }
  
  void addFalsePolicyRelabel(Pair pair) {
    falsePolicyRelabels.add(pair);
  }

  void clearFalsePolicyRelabels() {
    falsePolicyRelabels.clear();
  }
  
  void addTruePolicyRelabelsDependency(DelegationPair del, Pair pair) {
    Set<Pair> set = truePolicyRelabelsDependencies.get(del);
    if (set == null) {
      set = new HashSet<Pair>();
      truePolicyRelabelsDependencies.put(del, set);
    }
    set.add(pair);
  }
  
  Set<Pair> removeTruePolicyRelabelsDependencies(DelegationPair pair) {
    return truePolicyRelabelsDependencies.remove(pair);
  }
  
  Label getLabelJoin(Pair pair) {
    return labelJoins.get(pair);
  }
  
  void putLabelJoin(Pair pair, Label label) {
    labelJoins.put(pair, label);
  }

  void clearLabelJoins() {
    labelJoins.clear();
  }
  
  void removeLabelJoin(Pair pair) {
    labelJoins.remove(pair);
  }
  
  Label getLabelMeet(Pair pair) {
    return labelMeets.get(pair);
  }
  
  void putLabelMeet(Pair pair, Label label) {
    labelMeets.put(pair, label);
  }

  void clearLabelMeets() {
    labelMeets.clear();
  }
  
  void removeLabelMeet(Pair pair) {
    labelMeets.remove(pair);
  }
  
  void addLabelJoinDependency(DelegationPair del, Pair pair) {
    Set<Pair> set = labelJoinDependencies.get(del);
    if (set == null) {
      set = new HashSet<Pair>();
      labelJoinDependencies.put(del, set);
    }
    set.add(pair);
  }

  void clearLabelJoinDependencies() {
    labelJoinDependencies.clear();
  }
  
  Set<Pair> removeLabelJoinDependencies(DelegationPair pair) {
    return labelJoinDependencies.remove(pair);
  }
  
  void addLabelMeetDependency(DelegationPair del, Pair pair) {
    Set<Pair> set = labelMeetDependencies.get(del);
    if (set == null) {
      set = new HashSet<Pair>();
      labelMeetDependencies.put(del, set);
    }
    set.add(pair);
  }

  void clearLabelMeetDependencies() {
    labelMeetDependencies.clear();
  }
  
  Set<Pair> removeLabelMeetDependencies(DelegationPair pair) {
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
      return PrincipalUtil._Impl.equals(this.p, that.p)
          && PrincipalUtil._Impl.equals(this.q, that.q);
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

  /**
   * Internal representation of a pair of objects, used for the caches. Ported
   * from Jif's jif.lang.LabelUtil.
   */
  static class Pair {
    final Object left; // must be non null
    final Object right; // must be non null

    public Pair(Object left, Object right) {
      this.left = left;
      this.right = right;
    }

    @Override
    public int hashCode() {
      return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Pair) {
        Pair that = (Pair) o;
        return (this.left == that.left || this.left.equals(that.left))
            && (this.right == that.right || this.right.equals(that.right));
      }
      return false;
    }

    @Override
    public String toString() {
      return left + "-" + right;
    }
  }
}
