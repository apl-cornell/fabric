package fabric.dissemination;

import java.util.Properties;

import fabric.common.ObjectGroup;
import fabric.common.util.LongSet;
import fabric.worker.RemoteStore;
import fabric.worker.Worker;

/**
 * This simple FetchManger always goes directly to the store.
 */
public class DummyFetchManager implements FetchManager {

  private Cache cache;

  public DummyFetchManager(Worker worker, Properties dissemConfig) {
    this(worker, dissemConfig, new Cache());
  }

  public DummyFetchManager(Worker worker, Properties dissemConfig,
      Cache cache) {
    this.cache = cache;
  }

  @Override
  public ObjectGroup fetch(RemoteStore store, long onum) {
    Cache.Entry entry = cache.get(store, onum, true);
    if (entry == null) return null;
    return entry.objectGlob.decrypt();
  }

  @Override
  public void destroy() {
  }

  @Override
  public boolean updateCaches(RemoteStore store, LongSet onums,
      AbstractGlob<?> update) {
    return update.updateCache(cache, store, onums);
  }

}
