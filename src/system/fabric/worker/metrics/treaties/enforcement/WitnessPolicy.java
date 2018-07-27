package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import fabric.common.Logging;
import fabric.common.Threading;
import fabric.metrics.Metric;
import fabric.worker.Worker;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.TreatiesBoxRef;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.TransactionManager;

/**
 * Policy enforcing the treaty by directly monitoring the metric value.
 */
public class WitnessPolicy extends EnforcementPolicy {

  private final Multimap<TreatiesBoxRef, TreatyStatement> witnesses;

  public WitnessPolicy(Multimap<Metric, TreatyStatement> witnesses) {
    super(EnforcementPolicy.Kind.WITNESS);
    this.witnesses = HashMultimap.create();
    for (Map.Entry<Metric, TreatyStatement> witness : witnesses.entries()) {
      this.witnesses.put(new TreatiesBoxRef(witness.getKey().get$treatiesBox()),
          witness.getValue());
    }
  }

  public WitnessPolicy(DataInput in) throws IOException {
    super(EnforcementPolicy.Kind.WITNESS);
    this.witnesses = HashMultimap.create();
    int count = in.readInt();
    for (int i = 0; i < count; i++) {
      this.witnesses.put(new TreatiesBoxRef(in), TreatyStatement.read(in));
    }
  }

  @Override
  public long calculateExpiry(MetricTreaty treaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (Map.Entry<TreatiesBoxRef, TreatyStatement> witness : witnesses
        .entries()) {
      MetricTreaty witnessTreaty = witness.getKey().get().get$treatiesBox()
          .get$$treaties().get(witness.getValue());
      calculated = Math.min(calculated,
          witnessTreaty == null ? 0 : witnessTreaty.getExpiry());
    }
    return calculated;
  }

  @Override
  public long updatedExpiry(MetricTreaty oldTreaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (Map.Entry<TreatiesBoxRef, TreatyStatement> witness : witnesses
        .entries()) {
      MetricTreaty witnessTreaty = witness.getKey().get().get$treatiesBox()
          .get$$treaties().get(witness.getValue());
      calculated = Math.min(calculated,
          witnessTreaty == null ? 0 : witnessTreaty.getExpiry());
    }
    return calculated;
  }

  @Override
  protected void writePolicyData(DataOutput out) throws IOException {
    out.writeInt(witnesses.size());
    for (Map.Entry<TreatiesBoxRef, TreatyStatement> witness : witnesses
        .entries()) {
      witness.getKey().write(out);
      witness.getValue().write(out);
    }
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this || (obj instanceof WitnessPolicy
        && witnesses.equals(((WitnessPolicy) obj).witnesses));
  }

  @Override
  public int hashCode() {
    return EnforcementPolicy.Kind.WITNESS.ordinal() ^ witnesses.hashCode();
  }

  @Override
  public String toString() {
    return "enforced by " + witnesses;
  }

  @Override
  public void activate(StatsMap weakStats) {
    if (TransactionManager.getInstance().inTxn()) {
      for (Map.Entry<TreatiesBoxRef, TreatyStatement> witness : witnesses
          .entries()) {
        TreatiesBoxRef witnessMetric = witness.getKey();
        TreatyStatement witnessStatement = witness.getValue();
        if (witnessStatement instanceof ThresholdStatement) {
          witnessMetric.get().refreshThresholdTreaty(false,
              ((ThresholdStatement) witnessStatement).rate,
              ((ThresholdStatement) witnessStatement).base, weakStats);
        } else if (witnessStatement instanceof EqualityStatement) {
          witnessMetric.get().refreshEqualityTreaty(false,
              ((EqualityStatement) witnessStatement).value, weakStats);
        }
      }
    } else {
      Future<?> futures[] = new Future<?>[witnesses.size()];
      int i = 0;
      for (Map.Entry<TreatiesBoxRef, TreatyStatement> witness : witnesses
          .entries()) {
        final TreatiesBoxRef witnessMetric = witness.getKey();
        final TreatyStatement witnessStatement = witness.getValue();
        futures[i++] = Threading.getPool().submit(new Runnable() {
          @Override
          public void run() {
            if (witnessStatement instanceof ThresholdStatement) {
              ((Metric._Proxy) witnessMetric.get().$getProxy())
                  .refreshThresholdTreaty$remote(
                      Worker.getWorker().getWorker(witnessMetric.objStoreName),
                      null, false, ((ThresholdStatement) witnessStatement).rate,
                      ((ThresholdStatement) witnessStatement).base, weakStats);
            } else if (witnessStatement instanceof EqualityStatement) {
              ((Metric._Proxy) witnessMetric.get().$getProxy())
                  .refreshEqualityTreaty$remote(
                      Worker.getWorker().getWorker(witnessMetric.objStoreName),
                      null, false, ((EqualityStatement) witnessStatement).value,
                      weakStats);
            }
          }
        });
      }
      for (Future<?> f : futures) {
        try {
          f.get();
        } catch (ExecutionException e) {
          throw new InternalError(
              "Execution exception running witness activation!", e);
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }
    }
  }

  @Override
  public void apply(MetricTreaty t) {
    // Observe the witnesses
    for (Map.Entry<TreatiesBoxRef, TreatyStatement> witness : witnesses
        .entries()) {
      Metric m = witness.getKey().get();
      if (m == null) Logging.METRICS_LOGGER.log(Level.SEVERE,
          "A witness metric was null applying to {0}", t);
      MetricTreaty w =
          m.get$treatiesBox().get$$treaties().get(witness.getValue());
      if (w == null) Logging.METRICS_LOGGER.log(Level.SEVERE,
          "A witness treaty was null applying to {0}", t);
      w.addObserver(t.getMetric(), t.getId());
    }
  }

  @Override
  public void unapply(MetricTreaty t) {
    // TODO: make this async where applicable.
    // Stop observing the metric.
    for (Map.Entry<TreatiesBoxRef, TreatyStatement> witness : witnesses
        .entries()) {
      // Don't worry about missing witnesses, it's possible they were cleared
      // out anticipating this.
      Metric m = witness.getKey().get();
      if (m == null) continue;
      MetricTreaty w =
          m.get$treatiesBox().get$$treaties().get(witness.getValue());
      if (w == null) continue;
      w.removeObserver(t.getMetric(), t.getId());
    }
  }
}
