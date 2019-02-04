package fabric.worker.metrics.treaties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import fabric.common.FastSerializable;
import fabric.common.Logging;
import fabric.common.util.DeltaMap;
import fabric.common.util.Oid;
import fabric.metrics.treaties.Treaty;
import fabric.metrics.treaties.Treaty._Proxy;
import fabric.metrics.util.TreatiesBox;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TransactionManager;

/**
 * Base class for a set of treaties (time limited logical statements) on a
 * Fabric object.
 */
public class TreatySet
    implements FastSerializable, Iterable<Treaty._Proxy>, Serializable {

  private TreatiesBox._Proxy owner;
  private Map<TreatyStatement, Oid> statementMap;

  /** @return a value to use for an empty vector */
  public static TreatySet emptySet(TreatiesBox owner) {
    return new TreatySet(owner);
  }

  private void readObject(ObjectInputStream in) throws IOException {
    Store ownerStore = Worker.getWorker().getStore(in.readUTF());
    this.owner = new TreatiesBox._Proxy(ownerStore, in.readLong());
    int size = in.readInt();
    this.statementMap = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      // Keys aren't serialized separately, it's already in the treaty.
      TreatyStatement stmt = TreatyStatement.read(in);
      Store s = Worker.getWorker().getStore(in.readUTF());
      long onum = in.readLong();
      Oid t = new Oid(s, onum);
      this.statementMap.put(stmt, t);
    }
  }

  private void readObjectNoData() {
    throw new InternalError("This shouldn't happen");
  }

  /** Deserializer */
  public static TreatySet read(DataInput in) throws IOException {
    return new TreatySet(in);
  }

  public TreatySet(DataInput in) throws IOException {
    Store ownerStore = Worker.getWorker().getStore(in.readUTF());
    this.owner = new TreatiesBox._Proxy(ownerStore, in.readLong());
    int size = in.readInt();
    this.statementMap = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      // Keys aren't serialized separately, it's already in the treaty.
      TreatyStatement stmt = TreatyStatement.read(in);
      Store s = Worker.getWorker().getStore(in.readUTF());
      long onum = in.readLong();
      Oid t = new Oid(s, onum);
      this.statementMap.put(stmt, t);
    }
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    write(out);
  }

  @Override
  public final void write(DataOutput out) throws IOException {
    out.writeUTF(owner.$getStore().name());
    out.writeLong(owner.$getOnum());
    out.writeInt(this.statementMap.size());
    // Don't bother writing out the keys, it's already included in the values.
    for (Map.Entry<TreatyStatement, Oid> entry : this.statementMap.entrySet()) {
      entry.getKey().write(out);
      out.writeUTF(entry.getValue().store.name());
      out.writeLong(entry.getValue().onum);
    }
  }

  public TreatySet(TreatiesBox owner) {
    this.owner = (TreatiesBox._Proxy) owner.$getProxy();
    this.statementMap = new HashMap<>();
  }

  public TreatySet(TreatySet original) {
    this.owner = original.owner;
    this.statementMap = new DeltaMap<>(original.statementMap);
  }

  /** @return the number of treaties. */
  public int size() {
    return statementMap.size();
  }

  @Override
  public Iterator<Treaty._Proxy> iterator() {
    return new Iterator<Treaty._Proxy>() {
      private Iterator<Oid> oidIter = statementMap.values().iterator();

      @Override
      public boolean hasNext() {
        return oidIter.hasNext();
      }

      @Override
      public _Proxy next() {
        Oid nextOid = oidIter.next();
        return new Treaty._Proxy(nextOid.store, nextOid.onum);
      }
    };
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj || (obj instanceof TreatySet
        && this.owner.equals(((TreatySet) obj).owner));
  }

  @Override
  public int hashCode() {
    return this.owner.hashCode() ^ this.statementMap.hashCode();
  }

  @Override
  public String toString() {
    // TODO: might want to avoid toString on treaty objects...
    return statementMap.toString();
  }

  // Dumb hack to allow for easy grab of an object's currently associated treaty
  // set (optimistic possibly) without invoking a read operation.
  // There's an assumption that this only gets used on things we already hold a
  // lock for.
  private static TreatySet getAssociatedTreaties(
      fabric.lang.Object._Impl impl) {
    return ((fabric.metrics.util.TreatiesBox._Impl) impl).get$treaties();
  }

  public void remove(Treaty treaty) {
    // Don't do anything if there's no actual change.
    if (!statementMap.containsValue(new Oid(treaty))) return;

    Logging.METRICS_LOGGER.log(Level.FINEST, "REMOVING TREATY {0} IN {1} {2}",
        new Object[] { treaty, TransactionManager.getInstance().getCurrentTid(),
            Thread.currentThread() });
    Log curLog = TransactionManager.getInstance().getCurrentLog();
    TreatiesBox._Impl curBox = (TreatiesBox._Impl) owner.fetch();
    TreatiesBox._Impl boxHistory = (TreatiesBox._Impl) owner.fetch().$history;
    if (curLog != null && curBox.$writeLockHolder == curLog
        && boxHistory != null && this != getAssociatedTreaties(boxHistory)) {
      if (boxHistory != null && this == getAssociatedTreaties(boxHistory))
        throw new IllegalStateException(
            "Somehow modifying the history's treatyset...");
      // Make sure we clobber an extension if this in-place update isn't an
      // extension.
      statementMap.remove(treaty.get$predicate(), new Oid(treaty));
      owner.set$treaties(this);
    } else {
      // TODO check that it's a proper garbage collection?
      TreatySet updated = new TreatySet(this);
      updated.statementMap.remove(treaty.get$predicate(), new Oid(treaty));
      owner.set$treaties(updated);
    }
  }

  public Treaty get(TreatyStatement stmt) {
    Oid oid = statementMap.get(stmt);
    if (oid == null) return null;
    return new Treaty._Proxy(oid.store, oid.onum);
  }

  public Treaty create(TreatyStatement stmt, StatsMap statsMap) {
    if (statementMap.containsKey(stmt)) return get(stmt);

    Log curLog = TransactionManager.getInstance().getCurrentLog();
    TreatiesBox._Impl curBox = (TreatiesBox._Impl) owner.fetch();
    TreatiesBox._Impl boxHistory = (TreatiesBox._Impl) owner.fetch().$history;
    if (curLog != null && curBox.$writeLockHolder == curLog
        && boxHistory != null && this != getAssociatedTreaties(boxHistory)) {
      if (boxHistory != null && this == getAssociatedTreaties(boxHistory))
        throw new IllegalStateException(
            "Somehow modifying the history's treatyset...");
      Treaty newTreaty =
          Treaty._Impl.newTreaty(owner.get$owner(), stmt, statsMap);
      statementMap.put(stmt, new Oid(newTreaty));
      owner.set$treaties(this);
      return newTreaty;
    } else {
      TreatySet updated = new TreatySet(this);
      Treaty newTreaty =
          Treaty._Impl.newTreaty(owner.get$owner(), stmt, statsMap);
      updated.statementMap.put(stmt, new Oid(newTreaty));
      owner.set$treaties(updated);
      return newTreaty;
    }
  }

  public Map<Store, Set<Long>> prefetch(Store triggeringStore) {
    Map<Store, Set<Long>> result = new HashMap<>();
    for (Treaty t : this) {
      for (Map.Entry<Store, Set<Long>> e : t.getObservers()
          .prefetch(triggeringStore).entrySet()) {
        if (result.containsKey(e.getKey()))
          result.get(e.getKey()).addAll(e.getValue());
        else result.put(e.getKey(), e.getValue());
      }
    }
    return result;
  }

  public void flattenUpdates() {
    TreatiesBox._Impl historyObj = (TreatiesBox._Impl) owner.fetch().$history;
    if (historyObj != null) {
      TreatySet historyMap = getAssociatedTreaties(historyObj);
      // Merge changes to statementMap
      while (statementMap != historyMap.statementMap
          && statementMap instanceof DeltaMap
          && ((DeltaMap<TreatyStatement, Oid>) statementMap).backingMap != historyMap.statementMap)
        statementMap =
            ((DeltaMap<TreatyStatement, Oid>) statementMap).commitChanges();
    } else {
      // Merge changes to statementMap
      while (statementMap instanceof DeltaMap)
        statementMap =
            ((DeltaMap<TreatyStatement, Oid>) statementMap).commitChanges();
    }
  }

  public TreatySet makeCopy() {
    TreatySet copy = new TreatySet(owner);
    copy.statementMap.putAll(statementMap);
    return copy;
  }
}
