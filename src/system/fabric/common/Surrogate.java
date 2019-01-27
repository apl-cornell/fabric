package fabric.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableObjectSet;

/**
 * Encapsulates an inter-store pointer.
 */
public final class Surrogate extends _Impl {
  /**
   * The store for the object being pointed to.
   */
  public final Store store;

  /**
   * The onum for the object being pointed to.
   */
  public final long onum;

  public Surrogate(Store store, long onum, int version,
      ImmutableObjectSet associates, long expiry, Store labelStore,
      long labelOnum, Store accessPolicyStore, long accessPolicyOnum,
      ObjectInput serializedInput, Iterator<RefTypeEnum> refTypes,
      Iterator<Long> intraStoreRefs,
      Iterator<Pair<String, Long>> interStoreRefs)
      throws IOException, ClassNotFoundException {
    super(store, onum, version, associates, expiry, labelStore, labelOnum,
        accessPolicyStore, accessPolicyOnum, serializedInput, refTypes,
        intraStoreRefs, interStoreRefs);
    String storeName = serializedInput.readUTF();
    this.store = Worker.getWorker().getStore(storeName);
    this.onum = serializedInput.readLong();
  }

  @Override
  protected _Proxy $makeProxy() {
    throw new InternalError("Attempted to make proxy for a surrogate.");
  }

  @Override
  public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
      List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs) {
    // This should never be called. Surrogates are created in serialized form on
    // the store and should never be transmitted by the worker.
    throw new InternalError("Attempted to serialize a surrogate.");
  }
}
