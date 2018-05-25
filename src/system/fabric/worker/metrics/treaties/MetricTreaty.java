package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.util.Pair;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.Observer;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.NoPolicy;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.TransactionManager;

/**
 * An inlineable representation of a treaty defined as a statement about a
 * metric.
 */
public class MetricTreaty implements Treaty<MetricTreaty> {

  /** The metric this treaty is over. */
  public final OidRef<Metric> metric;

  /**
   * The id of this treaty within the metric.  Used to reference treaties
   * through their associated metrics.
   */
  public final long id;

  /**
   * Has this treaty been activated since creation?
   */
  public final boolean activated;

  /** The asserted statement. */
  public final TreatyStatement statement;

  /** The observers of this treaty. */
  public final ImmutableObserverSet observers;

  /**
   * Maximum time in milliseconds before the currently advertised expiry that an
   * extension should be applied within.
   */
  public static long UPDATE_THRESHOLD = 1000;

  /** The policy being used to enforce this treaty. */
  private final EnforcementPolicy policy;

  /** The expiration time. */
  public final long expiry;

  public MetricTreaty(Metric metric, long id, TreatyStatement statement) {
    this.metric = new OidRef<>(metric);
    this.id = id;
    this.activated = false;
    this.statement = statement;
    this.observers = ImmutableObserverSet.emptySet();
    this.policy = NoPolicy.singleton;
    this.expiry = policy.calculateExpiry(this, StatsMap.emptyStats());
  }

  /**
   * Deserialization constructor.
   *
   * Metric is provided on the side to avoid unnecessary repeated references in
   * serialized TreatySet.
   */
  public MetricTreaty(OidRef<Metric> m, DataInput in) throws IOException {
    this.metric = m;
    this.id = in.readLong();
    this.activated = in.readBoolean();
    this.statement = TreatyStatement.read(in);
    this.observers = new ImmutableObserverSet(in);
    this.policy = EnforcementPolicy.read(in);
    this.expiry = in.readLong();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    // Don't serialize the metric, it'll be provided on deserialization.
    //this.metric.write(out);
    out.writeLong(id);
    out.writeBoolean(activated);
    this.statement.write(out);
    this.observers.write(out);
    this.policy.write(out);
    out.writeLong(this.expiry);
  }

  /**
   * Constructor for updating the observers, not affecting the expiry or policy.
   *
   * Updates the containing treaty set.
   */
  private MetricTreaty(MetricTreaty original, ImmutableObserverSet observers) {
    this.metric = original.metric;
    this.id = original.id;
    this.activated = original.activated;
    this.statement = original.statement;
    this.observers = observers;
    this.policy = original.policy;
    this.expiry = original.expiry;

    // Update containing treaty set.
    TreatySet origSet = this.getMetric().get$$treaties();
    TreatySet newSet = origSet.add(this);
    if (origSet != newSet && !origSet.equals(newSet))
      this.getMetric().set$$treaties(newSet);
  }

  /**
   * Constructor for updating the observers, affecting the policy and/or expiry.
   *
   * Updates the containing treaty set.
   */
  private MetricTreaty(MetricTreaty original, ImmutableObserverSet observers,
      EnforcementPolicy policy, long expiry) {
    this.metric = original.metric;
    this.id = original.id;
    this.activated = original.activated || !(policy instanceof NoPolicy);
    this.statement = original.statement;
    this.observers = observers;
    this.policy = policy;
    this.expiry = expiry;

    // Update containing treaty set.
    TreatySet origSet = this.getMetric().get$$treaties();
    TreatySet newSet = origSet.add(this);
    if (origSet != newSet && !origSet.equals(newSet))
      this.getMetric().set$$treaties(newSet);

    // TODO update observing to move to the new policy
  }

  /**
   * Constructor for the update step, changing expiry but not changing the
   * policy.
   *
   * Updates the containing treaty set.
   */
  private MetricTreaty(MetricTreaty original, long newExpiry) {
    this.metric = original.metric;
    this.id = original.id;
    this.activated = original.activated;
    this.statement = original.statement;
    this.observers = original.observers;
    this.policy = original.policy;
    this.expiry = newExpiry;

    // Update containing treaty set.
    TreatySet origSet = this.getMetric().get$$treaties();
    TreatySet newSet = origSet.add(this);
    if (origSet != newSet && !origSet.equals(newSet))
      this.getMetric().set$$treaties(newSet);
  }

  /**
   * Constructor for the update step, changing both the policy and expiry.
   *
   * Updates the containing treaty set.
   */
  private MetricTreaty(MetricTreaty original, EnforcementPolicy policy,
      long newExpiry) {
    this.metric = original.metric;
    this.id = original.id;
    this.activated = original.activated || !(policy instanceof NoPolicy);
    this.statement = original.statement;
    this.observers = original.observers;
    this.policy = policy;
    this.expiry = newExpiry;

    // Update containing treaty set.
    TreatySet origSet = this.getMetric().get$$treaties();
    TreatySet newSet = origSet.add(this);
    if (origSet != newSet && !origSet.equals(newSet))
      this.getMetric().set$$treaties(newSet);

    // TODO update observing to move to the new policy
  }

  @Override
  public Pair<MetricTreaty, ImmutableObserverSet> update(boolean asyncExtension,
      StatsMap weakStats) {
    long oldExpiry = this.expiry;
    long updatedCurExpiry = this.policy.updatedExpiry(this, weakStats);
    if (updatedCurExpiry != oldExpiry || !activated) {
      if (asyncExtension || updatedCurExpiry > System.currentTimeMillis()) {
        // Keep using the same policy if the policy still gives a good expiry or
        // we're only doing an async extension.
        MetricTreaty updatedTreaty = new MetricTreaty(this, updatedCurExpiry);
        Logging.METRICS_LOGGER.log(Level.FINEST,
            "UPDATING {0} TO {1} IN {2} {3}",
            new Object[] { this, updatedTreaty,
                TransactionManager.getInstance().getCurrentTid(),
                Thread.currentThread() });
        return new Pair<>(updatedTreaty,
            oldExpiry > updatedTreaty.expiry ? updatedTreaty.observers
                : ImmutableObserverSet.emptySet());
      } else {
        // Otherwise, try a different policy.
        EnforcementPolicy newPolicy =
            statement.getNewPolicy(getMetric(), weakStats);

        newPolicy.activate(weakStats);

        MetricTreaty updatedTreaty = new MetricTreaty(this, newPolicy,
            newPolicy.updatedExpiry(this, weakStats));
        Logging.METRICS_LOGGER.log(Level.FINEST,
            "UPDATING {0} TO {1} IN {2} {3}",
            new Object[] { this, updatedTreaty,
                TransactionManager.getInstance().getCurrentTid(),
                Thread.currentThread() });
        return new Pair<>(updatedTreaty,
            oldExpiry > updatedTreaty.expiry ? updatedTreaty.observers
                : ImmutableObserverSet.emptySet());
      }
    }
    // If nothing changed, don't update.
    return new Pair<>(this, ImmutableObserverSet.emptySet());
  }

  @Override
  public boolean valid() {
    Logging.METRICS_LOGGER.log(Level.FINEST,
        "CHECKING VALIDITY OF {0} IN {1} {2}",
        new Object[] { this, TransactionManager.getInstance().getCurrentTid(),
            Thread.currentThread() });
    boolean result = expiry > System.currentTimeMillis();
    if (result) TransactionManager.getInstance().registerExpiryUse(expiry);
    return result;
  }

  @Override
  public long getExpiry() {
    return expiry;
  }

  /**
   * @return the metric
   */
  public Metric getMetric() {
    return metric.get();
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public ImmutableObserverSet getObservers() {
    return observers;
  }

  /**
   * @return the policy
   */
  public EnforcementPolicy getPolicy() {
    return policy;
  }

  @Override
  public boolean isActive() {
    return activated;
  }

  @Override
  public boolean implies(MetricTreaty other) {
    return this.statement.implies(other.statement);
  }

  public boolean implies(Metric m, double rate, double base) {
    if (this.statement instanceof ThresholdStatement) {
      return getMetric().equals(m)
          && this.statement.implies(new ThresholdStatement(rate, base));
    }
    return false;
  }

  @Override
  public MetricTreaty addObserver(Observer obs) {
    if (observers.contains(obs)) return this;
    return new MetricTreaty(this, observers.remove(obs));
  }

  @Override
  public MetricTreaty addObserver(Observer obs, long id) {
    if (observers.contains(obs, id)) return this;
    return new MetricTreaty(this, observers.remove(obs, id));
  }

  @Override
  public MetricTreaty removeObserver(Observer obs) {
    if (!observers.contains(obs)) return this;
    ImmutableObserverSet updatedObservers = observers.remove(obs);
    // Stop enforcing if we're no longer being watched by anyone.
    if (updatedObservers.isEmpty())
      return new MetricTreaty(this, updatedObservers, NoPolicy.singleton, 0);
    return new MetricTreaty(this, updatedObservers);
  }

  @Override
  public MetricTreaty removeObserver(Observer obs, long id) {
    if (!observers.contains(obs, id)) return this;
    ImmutableObserverSet updatedObservers = observers.remove(obs, id);
    // Stop enforcing if we're no longer being watched by anyone.
    if (updatedObservers.isEmpty())
      return new MetricTreaty(this, updatedObservers, NoPolicy.singleton, 0);
    return new MetricTreaty(this, updatedObservers);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof MetricTreaty)) return false;
    MetricTreaty other = (MetricTreaty) obj;
    return metric.equals(other.metric) && id == other.id
        && activated == other.activated && statement.equals(other.statement)
        && observers.equals(other.observers) && policy.equals(other.policy)
        && expiry == other.expiry;
  }

  @Override
  public int hashCode() {
    return metric.hashCode() ^ Long.hashCode(id) ^ Boolean.hashCode(activated)
        ^ statement.hashCode() ^ observers.hashCode() ^ policy.hashCode()
        ^ Long.hashCode(expiry);
  }

  @Override
  public String toString() {
    return Long.toString(id) + "(" + activated + "): " + getMetric().toString()
        + " " + statement + " until " + expiry + " " + policy
        + " and observed by " + observers;
  }

  @Override
  public ImmutableMetricsVector getLeafSubjects() {
    if (getMetric() instanceof DerivedMetric) {
      return ((DerivedMetric) getMetric()).getLeafSubjects();
    } else if (getMetric() instanceof SampledMetric) {
      return ImmutableMetricsVector.createVector(new Metric[] { getMetric() });
    }
    throw new InternalError("Unknown metric type!");
  }
}
