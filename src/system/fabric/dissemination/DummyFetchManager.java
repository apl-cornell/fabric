package fabric.dissemination;

import java.util.Properties;

import fabric.common.ObjectGroup;
import fabric.common.WarrantyRefreshGroup;
import fabric.common.util.Pair;
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

  public DummyFetchManager(Worker worker, Properties dissemConfig, Cache cache) {
    this.cache = cache;
  }

  @Override
  public Pair<ObjectGroup, WarrantyRefreshGroup> fetch(RemoteStore store,
      long onum) {
    return cache.get(store, onum, true).decrypt();
  }

  @Override
  public void destroy() {
  }

  @Override
  public boolean updateCaches(RemoteStore store, long onum,
      AbstractGlob<?> update) {
    return update.updateCache(cache, store, onum);
  }

}
