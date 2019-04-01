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
import fabric.common.util.LongKeyMap;
import fabric.common.util.Oid;
import fabric.common.util.OidKeyHashMap;
import fabric.metrics.treaties.Treaty;
import fabric.metrics.treaties.Treaty._Proxy;
import fabric.metrics.util.TreatiesBox;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
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
    int numStores = in.readInt();
    for (int i = 0; i < numStores; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int count = in.readInt();
      for (int j = 0; j < count; j++) {
        long onum = in.readLong();
        TreatyStatement stmt = TreatyStatement.read(in);
        Oid t = new Oid(s, onum);
        this.statementMap.put(stmt, t);
      }
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
    int numStores = in.readInt();
    for (int i = 0; i < numStores; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int count = in.readInt();
      for (int j = 0; j < count; j++) {
        long onum = in.readLong();
        TreatyStatement stmt = TreatyStatement.read(in);
        Oid t = new Oid(s, onum);
        this.statementMap.put(stmt, t);
      }
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
    // For a more compressed representation, we're going to serialize in the
    // reverse to avoid duplicated store names.
    OidKeyHashMap<TreatyStatement> reverse = new OidKeyHashMap<>();
    for (Map.Entry<TreatyStatement, Oid> entry : this.statementMap.entrySet()) {
      reverse.put(entry.getValue(), entry.getKey());
    }
    out.writeInt(reverse.storeSet().size());
    for (Store s : reverse.storeSet()) {
      out.writeUTF(s.name());
      out.writeInt(reverse.get(s).size());
      for (LongKeyMap.Entry<TreatyStatement> e : reverse.get(s).entrySet()) {
        out.writeLong(e.getKey());
        e.getValue().write(out);
      }
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
    return ((fabric.metrics.util.TreatiesBox._Impl) impl).$treaties;
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
    TreatySet resultSet = this;
    // Make sure we clobber an extension if this in-place update isn't an
    // extension.
    if (curLog != null && curBox.$writeLockHolder == curLog
        && boxHistory != null && this != getAssociatedTreaties(boxHistory)) {
      if (boxHistory != null && this == getAssociatedTreaties(boxHistory))
        throw new IllegalStateException(
            "Somehow modifying the history's treatyset...");
    } else {
      // TODO check that it's a proper garbage collection?
      resultSet = new TreatySet(this);
    }

    resultSet.statementMap.remove(treaty.get$predicate(), new Oid(treaty));
    if (TransactionManager.getInstance().inTxn()) {
      resultSet.owner.set$$treaties(resultSet);
      if (TransactionManager.usingPrefetching()) resultSet.owner
          .set$$associates(resultSet.owner.get$$associates().remove(treaty));
    } else {
      TreatySet resultSetCopy = resultSet;
      Worker.runInSubTransaction(new Code<Void>() {
        @Override
        public Void run() {
          resultSetCopy.owner.set$$treaties(resultSetCopy);
          if (TransactionManager.usingPrefetching())
            resultSetCopy.owner.set$$associates(
                resultSetCopy.owner.get$$associates().remove(treaty));
          return null;
        }
      });
    }
  }

  public Treaty get(TreatyStatement stmt) {
    Oid oid = statementMap.get(stmt);
    if (oid == null) return null;
    return new Treaty._Proxy(oid.store, oid.onum);
  }

  public Treaty create(TreatyStatement stmt, StatsMap statsMap) {
    // Returning a preexisting treaty, if there is one.
    if (statementMap.containsKey(stmt)) return get(stmt);

    // Make a new delta if this is the first time we've written it in the
    // current transaction.
    Log curLog = TransactionManager.getInstance().getCurrentLog();
    TreatiesBox._Impl curBox = (TreatiesBox._Impl) owner.fetch();
    TreatiesBox._Impl boxHistory = (TreatiesBox._Impl) owner.fetch().$history;
    TreatySet resultSet = this;
    if (curLog != null && curBox.$writeLockHolder == curLog
        && boxHistory != null && this != getAssociatedTreaties(boxHistory)) {
      if (boxHistory != null && this == getAssociatedTreaties(boxHistory))
        throw new IllegalStateException(
            "Somehow modifying the history's treatyset...");
    } else {
      resultSet = new TreatySet(this);
    }

    // Make the treaty
    Treaty newTreaty =
        Treaty._Impl.newTreaty(resultSet.owner.get$owner(), stmt, statsMap);

    resultSet.statementMap.put(stmt, new Oid(newTreaty));
    if (TransactionManager.getInstance().inTxn()) {
      resultSet.owner.set$$treaties(resultSet);
      if (TransactionManager.usingPrefetching()) resultSet.owner
          .set$$associates(resultSet.owner.get$$associates().add(newTreaty));
    } else {
      TreatySet resultSetCopy = resultSet;
      Worker.runInSubTransaction(new Code<Void>() {
        @Override
        public Void run() {
          resultSetCopy.owner.set$$treaties(resultSetCopy);
          if (TransactionManager.usingPrefetching())
            resultSetCopy.owner.set$$associates(
                resultSetCopy.owner.get$$associates().add(newTreaty));
          return null;
        }
      });
    }

    return newTreaty;
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

  public void flattenUpdates(TreatiesBox._Impl ownerImpl) {
    TreatiesBox._Impl historyObj = (TreatiesBox._Impl) ownerImpl.$history;
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
