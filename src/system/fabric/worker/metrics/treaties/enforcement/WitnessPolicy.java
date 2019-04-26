package fabric.worker.metrics.treaties.enforcement;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import fabric.common.Logging;
import fabric.common.Threading;
import fabric.metrics.Metric;
import fabric.metrics.treaties.Treaty;
import fabric.metrics.util.TreatiesBox;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.TransactionManager;

/**
 * Policy enforcing the treaty by relying on subtreaties.
 */
public class WitnessPolicy extends EnforcementPolicy implements Serializable {

  /**
   * Utility factory method since we can't expose constructors for signatures.
   */
  public static WitnessPolicy create(
      Multimap<Metric, TreatyStatement> witnesses) {
    return new WitnessPolicy(witnesses);
  }

  private TreeMultimap<TreatiesBox._Proxy, TreatyStatement> witnesses;

  public WitnessPolicy(Multimap<Metric, TreatyStatement> witnesses) {
    this.witnesses = TreeMultimap.create(new Comparator<TreatiesBox._Proxy>() {
      @Override
      public int compare(TreatiesBox._Proxy a, TreatiesBox._Proxy b) {
        int storeComp = a.$getStore().name().compareTo(b.$getStore().name());
        if (storeComp != 0) return storeComp;
        return Long.compare(a.$getOnum(), b.$getOnum());
      }
    }, new Comparator<TreatyStatement>() {
      @Override
      public int compare(TreatyStatement a, TreatyStatement b) {
        if (a instanceof ThresholdStatement) {
          if (b instanceof ThresholdStatement) {
            ThresholdStatement aT = (ThresholdStatement) a;
            ThresholdStatement bT = (ThresholdStatement) b;
            int rateComp = Double.compare(aT.rate(), bT.rate());
            if (rateComp != 0) return rateComp;
            return Double.compare(aT.base(), bT.base());
          } else {
            return 1;
          }
        } else {
          if (b instanceof ThresholdStatement) {
            EqualityStatement aT = (EqualityStatement) a;
            EqualityStatement bT = (EqualityStatement) b;
            return Double.compare(aT.value(), bT.value());
          } else {
            return -1;
          }
        }
      }
    });
    for (Map.Entry<Metric, TreatyStatement> witness : witnesses.entries()) {
      this.witnesses.put(
          (TreatiesBox._Proxy) witness.getKey().get$treatiesBox().$getProxy(),
          witness.getValue());
    }
  }

  public WitnessPolicy(DataInput in) throws IOException {
    this.witnesses = TreeMultimap.create(new Comparator<TreatiesBox._Proxy>() {
      @Override
      public int compare(TreatiesBox._Proxy a, TreatiesBox._Proxy b) {
        int storeComp = a.$getStore().name().compareTo(b.$getStore().name());
        if (storeComp != 0) return storeComp;
        return Long.compare(a.$getOnum(), b.$getOnum());
      }
    }, new Comparator<TreatyStatement>() {
      @Override
      public int compare(TreatyStatement a, TreatyStatement b) {
        if (a instanceof ThresholdStatement) {
          if (b instanceof ThresholdStatement) {
            ThresholdStatement aT = (ThresholdStatement) a;
            ThresholdStatement bT = (ThresholdStatement) b;
            int rateComp = Double.compare(aT.rate(), bT.rate());
            if (rateComp != 0) return rateComp;
            return Double.compare(aT.base(), bT.base());
          } else {
            return 1;
          }
        } else {
          if (b instanceof ThresholdStatement) {
            EqualityStatement aT = (EqualityStatement) a;
            EqualityStatement bT = (EqualityStatement) b;
            return Double.compare(aT.value(), bT.value());
          } else {
            return -1;
          }
        }
      }
    });
    int count = in.readInt();
    for (int i = 0; i < count; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      this.witnesses.put(new TreatiesBox._Proxy(s, in.readLong()),
          TreatyStatement.read(in));
    }
  }

  @Override
  public long calculateExpiry(Treaty treaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> witness : witnesses
        .entries()) {
      Treaty witnessTreaty = witness.getKey().get(witness.getValue());
      calculated = Math.min(calculated,
          witnessTreaty == null ? 0 : witnessTreaty.get$$expiry());
    }
    return calculated;
  }

  @Override
  public long updatedExpiry(Treaty oldTreaty, StatsMap weakStats) {
    long calculated = Long.MAX_VALUE;
    for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> witness : witnesses
        .entries()) {
      Treaty witnessTreaty = witness.getKey().get(witness.getValue());
      calculated = Math.min(calculated,
          witnessTreaty == null ? 0 : witnessTreaty.get$$expiry());
    }
    return calculated;
  }

  @Override
  protected void writePolicyData(DataOutput out) throws IOException {
    out.writeInt(witnesses.size());
    for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> witness : witnesses
        .entries()) {
      out.writeUTF(witness.getKey().$getStore().name());
      out.writeLong(witness.getKey().$getOnum());
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
      for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> witness : witnesses
          .entries()) {
        TreatiesBox._Proxy witnessMetric = witness.getKey();
        TreatyStatement witnessStatement = witness.getValue();
        if (witnessStatement instanceof ThresholdStatement) {
          witnessMetric.get$owner().createThresholdTreaty(
              ((ThresholdStatement) witnessStatement).rate(),
              ((ThresholdStatement) witnessStatement).base(), 0, weakStats);
        } else if (witnessStatement instanceof EqualityStatement) {
          witnessMetric.get$owner().createEqualityTreaty(
              ((EqualityStatement) witnessStatement).value(), weakStats);
        }
        // Small optimization to give up once we know this policy isn't going
        // anywhere.
        Treaty w = witnessMetric.get(witness.getValue());
        if (w == null || w.invalid()) break;
      }
    } else {
      Future<?> futures[] = new Future<?>[witnesses.size()];
      int i = 0;
      for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> witness : witnesses
          .entries()) {
        final TreatiesBox._Proxy witnessMetric = witness.getKey();
        final TreatyStatement witnessStatement = witness.getValue();
        futures[i++] = Threading.getPool().submit(new Runnable() {
          @Override
          public void run() {
            if (witnessStatement instanceof ThresholdStatement) {
              ((Metric._Proxy) witnessMetric.get$owner().$getProxy())
                  .createThresholdTreaty$remote(
                      Worker.getWorker()
                          .getWorker(witnessMetric.$getStore().name()),
                      null, ((ThresholdStatement) witnessStatement).rate(),
                      ((ThresholdStatement) witnessStatement).base(), 0,
                      weakStats);
            } else if (witnessStatement instanceof EqualityStatement) {
              ((Metric._Proxy) witnessMetric.get$owner().$getProxy())
                  .createEqualityTreaty$remote(
                      Worker.getWorker()
                          .getWorker(witnessMetric.$getStore().name()),
                      null, ((EqualityStatement) witnessStatement).value(),
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
  public void apply(Treaty t) {
    // Observe the witnesses
    for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> witness : witnesses
        .entries()) {
      Treaty w = witness.getKey().get(witness.getValue());
      if (w == null) Logging.METRICS_LOGGER.log(Level.SEVERE,
          "A witness treaty was null applying to {0} for {2} {1}",
          new Object[] { t, witness.getValue(), witness.getKey() });
      w.addObserver(t);
      if (TransactionManager.usingPrefetching())
        t.set$$associates(t.get$$associates().add(w));
    }
  }

  @Override
  public void unapply(Treaty t) {
    // TODO: make this async where applicable.
    // Stop observing the metric.
    boolean wasGood = t.get$$expiry() == 0;
    for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> witness : witnesses
        .entries()) {
      // Don't worry about missing witnesses, it's possible they were cleared
      // out anticipating this.
      Treaty w = witness.getKey().get(witness.getValue());
      if (w == null) continue;
      w.removeObserver(t);
      if (t.get$$expiry() != 0 && wasGood) {
        TransactionManager.getInstance().stats.addMsg("Wat? " + w + " " + t);
        wasGood = false;
      }
      if (TransactionManager.usingPrefetching())
        t.set$$associates(t.get$$associates().remove(w));
    }
  }

  @Override
  public void shiftPolicies(Treaty t, EnforcementPolicy newPolicy) {
    if (newPolicy instanceof WitnessPolicy) {
      WitnessPolicy nextPol = (WitnessPolicy) newPolicy;
      // Only add and remove nonoverlapping witnesses
      Set<Map.Entry<TreatiesBox._Proxy, TreatyStatement>> toBeRemoved =
          new TreeSet<>(
              new Comparator<Map.Entry<TreatiesBox._Proxy, TreatyStatement>>() {
                @Override
                public int compare(
                    Map.Entry<TreatiesBox._Proxy, TreatyStatement> a,
                    Map.Entry<TreatiesBox._Proxy, TreatyStatement> b) {
                  // Try keys
                  TreatiesBox._Proxy a1 = a.getKey();
                  TreatiesBox._Proxy b1 = b.getKey();
                  int storeComp =
                      a1.$getStore().name().compareTo(b1.$getStore().name());
                  if (storeComp != 0) return storeComp;
                  int onumComp = Long.compare(a1.$getOnum(), b1.$getOnum());
                  if (onumComp != 0) return onumComp;

                  // Try values
                  TreatyStatement a2 = a.getValue();
                  TreatyStatement b2 = b.getValue();
                  if (a2 instanceof ThresholdStatement) {
                    if (b2 instanceof ThresholdStatement) {
                      ThresholdStatement aT = (ThresholdStatement) a2;
                      ThresholdStatement bT = (ThresholdStatement) b2;
                      int rateComp = Double.compare(aT.rate(), bT.rate());
                      if (rateComp != 0) return rateComp;
                      return Double.compare(aT.base(), bT.base());
                    } else {
                      return 1;
                    }
                  } else {
                    if (b2 instanceof ThresholdStatement) {
                      EqualityStatement aT = (EqualityStatement) a2;
                      EqualityStatement bT = (EqualityStatement) b2;
                      return Double.compare(aT.value(), bT.value());
                    } else {
                      return -1;
                    }
                  }
                }
              });
      toBeRemoved.addAll(witnesses.entries());
      toBeRemoved.removeAll(nextPol.witnesses.entries());
      for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> e : toBeRemoved) {
        Treaty w = e.getKey().get(e.getValue());
        if (w == null) continue;
        w.removeObserver(t);
        if (TransactionManager.usingPrefetching())
          t.set$$associates(t.get$$associates().remove(w));
      }

      Set<Map.Entry<TreatiesBox._Proxy, TreatyStatement>> toBeAdded =
          new TreeSet<>(
              new Comparator<Map.Entry<TreatiesBox._Proxy, TreatyStatement>>() {
                @Override
                public int compare(
                    Map.Entry<TreatiesBox._Proxy, TreatyStatement> a,
                    Map.Entry<TreatiesBox._Proxy, TreatyStatement> b) {
                  // Try keys
                  TreatiesBox._Proxy a1 = a.getKey();
                  TreatiesBox._Proxy b1 = b.getKey();
                  int storeComp =
                      a1.$getStore().name().compareTo(b1.$getStore().name());
                  if (storeComp != 0) return storeComp;
                  int onumComp = Long.compare(a1.$getOnum(), b1.$getOnum());
                  if (onumComp != 0) return onumComp;

                  // Try values
                  TreatyStatement a2 = a.getValue();
                  TreatyStatement b2 = b.getValue();
                  if (a2 instanceof ThresholdStatement) {
                    if (b2 instanceof ThresholdStatement) {
                      ThresholdStatement aT = (ThresholdStatement) a2;
                      ThresholdStatement bT = (ThresholdStatement) b2;
                      int rateComp = Double.compare(aT.rate(), bT.rate());
                      if (rateComp != 0) return rateComp;
                      return Double.compare(aT.base(), bT.base());
                    } else {
                      return 1;
                    }
                  } else {
                    if (b2 instanceof ThresholdStatement) {
                      EqualityStatement aT = (EqualityStatement) a2;
                      EqualityStatement bT = (EqualityStatement) b2;
                      return Double.compare(aT.value(), bT.value());
                    } else {
                      return -1;
                    }
                  }
                }
              });
      toBeAdded.addAll(nextPol.witnesses.entries());
      toBeAdded.removeAll(witnesses.entries());
      for (Map.Entry<TreatiesBox._Proxy, TreatyStatement> e : toBeAdded) {
        Treaty w = e.getKey().get(e.getValue());
        if (w == null) Logging.METRICS_LOGGER.log(Level.SEVERE,
            "A witness treaty was null applying to {0} for {2} {1}",
            new Object[] { t, e.getValue(), e.getKey() });
        w.addObserver(t);
        if (TransactionManager.usingPrefetching())
          t.set$$associates(t.get$$associates().add(w));
      }
    } else {
      // Do the normal thing.
      unapply(t);
      newPolicy.apply(t);
    }
  }

  @Override
  protected void writeKind(DataOutput out) throws IOException {
    out.writeByte(Kind.WITNESS.ordinal());
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    writePolicyData(out);
  }

  private void readObject(java.io.ObjectInputStream in) throws IOException {
    this.witnesses = TreeMultimap.create(new Comparator<TreatiesBox._Proxy>() {
      @Override
      public int compare(TreatiesBox._Proxy a, TreatiesBox._Proxy b) {
        int storeComp = a.$getStore().name().compareTo(b.$getStore().name());
        if (storeComp != 0) return storeComp;
        return Long.compare(a.$getOnum(), b.$getOnum());
      }
    }, new Comparator<TreatyStatement>() {
      @Override
      public int compare(TreatyStatement a, TreatyStatement b) {
        if (a instanceof ThresholdStatement) {
          if (b instanceof ThresholdStatement) {
            ThresholdStatement aT = (ThresholdStatement) a;
            ThresholdStatement bT = (ThresholdStatement) b;
            int rateComp = Double.compare(aT.rate(), bT.rate());
            if (rateComp != 0) return rateComp;
            return Double.compare(aT.base(), bT.base());
          } else {
            return 1;
          }
        } else {
          if (b instanceof ThresholdStatement) {
            EqualityStatement aT = (EqualityStatement) a;
            EqualityStatement bT = (EqualityStatement) b;
            return Double.compare(aT.value(), bT.value());
          } else {
            return -1;
          }
        }
      }
    });
    int count = in.readInt();
    for (int i = 0; i < count; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      this.witnesses.put(new TreatiesBox._Proxy(s, in.readLong()),
          TreatyStatement.read(in));
    }
  }

  private void readObjectNoData() {
    // This really shouldn't occur...
    this.witnesses = TreeMultimap.create(new Comparator<TreatiesBox._Proxy>() {
      @Override
      public int compare(TreatiesBox._Proxy a, TreatiesBox._Proxy b) {
        int storeComp = a.$getStore().name().compareTo(b.$getStore().name());
        if (storeComp != 0) return storeComp;
        return Long.compare(a.$getOnum(), b.$getOnum());
      }
    }, new Comparator<TreatyStatement>() {
      @Override
      public int compare(TreatyStatement a, TreatyStatement b) {
        if (a instanceof ThresholdStatement) {
          if (b instanceof ThresholdStatement) {
            ThresholdStatement aT = (ThresholdStatement) a;
            ThresholdStatement bT = (ThresholdStatement) b;
            int rateComp = Double.compare(aT.rate(), bT.rate());
            if (rateComp != 0) return rateComp;
            return Double.compare(aT.base(), bT.base());
          } else {
            return 1;
          }
        } else {
          if (b instanceof ThresholdStatement) {
            EqualityStatement aT = (EqualityStatement) a;
            EqualityStatement bT = (EqualityStatement) b;
            return Double.compare(aT.value(), bT.value());
          } else {
            return -1;
          }
        }
      }
    });
  }
}
