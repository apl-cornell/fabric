package fabric.worker;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.common.util.OidKeyHashMap;
import fabric.lang.Object;

/**
 * A per-store cache for label and policy objects, allowing us to intern Label,
 * ConfPolicy, and IntegPolicy objects. This class is thread-safe.
 */
public class LabelCache {

  /**
   * Maps (OIDs of K1s) to (maps of (OIDs of K2s) to P proxies).
   */
  private static final class Cache<K1 extends Object, K2 extends Object, P extends Object> {
    private static final class EntrySoftRef<K1 extends Object, K2 extends Object, P extends Object>
    extends
    SoftReference<P> {
      final Cache<K1, K2, P> cache;
      final Object._Proxy key1;
      final Object._Proxy key2;

      private EntrySoftRef(Cache<K1, K2, P> cache, Object._Proxy key1,
          Object._Proxy key2, P value) {
        super(value);
        this.cache = cache;
        this.key1 = key1;
        this.key2 = key2;
      }
    }

    private static final ReferenceQueue<EntrySoftRef<?, ?, ?>> queue =
        new ReferenceQueue<EntrySoftRef<?, ?, ?>>();

    static {
      new Collector().start();
    }

    private final OidKeyHashMap<OidKeyHashMap<EntrySoftRef<K1, K2, P>>> entries;

    Cache() {
      this.entries =
          new OidKeyHashMap<OidKeyHashMap<EntrySoftRef<K1, K2, P>>>();
    }

    public synchronized P get(K1 key1, K2 key2) {
      OidKeyHashMap<EntrySoftRef<K1, K2, P>> submap = entries.get(key1);
      if (submap == null) return null;

      EntrySoftRef<K1, K2, P> entry = submap.get(key2);
      if (entry == null) return null;

      return entry.get();
    }

    public synchronized void put(K1 key1, K2 key2, P val) {
      OidKeyHashMap<EntrySoftRef<K1, K2, P>> submap = entries.get(key1);
      if (submap == null) {
        submap = new OidKeyHashMap<LabelCache.Cache.EntrySoftRef<K1, K2, P>>();
        entries.put(key1, submap);
      }

      Object._Proxy key1Proxy = key1 == null ? null : key1.$getProxy();
      Object._Proxy key2Proxy = key2 == null ? null : key2.$getProxy();

      EntrySoftRef<K1, K2, P> entry =
          new EntrySoftRef<K1, K2, P>(Cache.this, key1Proxy, key2Proxy, val);
      submap.put(key2, entry);
    }

    public synchronized void clear() {
      entries.clear();
    }

    private static final class Collector extends Thread {
      private Collector() {
        super("Label-cache entry collector");
        setDaemon(true);
      }

      @Override
      public void run() {
        while (true) {
          try {
            @SuppressWarnings("rawtypes")
            EntrySoftRef ref = (EntrySoftRef) queue.remove();

            // GC has snapped the soft reference "ref". Remove its entry from
            // cache.
            synchronized (ref.cache) {
              @SuppressWarnings("rawtypes")
              OidKeyHashMap<EntrySoftRef> subMap =
              (OidKeyHashMap<EntrySoftRef>) ref.cache.entries.get(ref.key1);
              if (subMap != null) {
                synchronized (subMap) {
                  EntrySoftRef<?, ?, ?> curRef = subMap.get(ref.key2);
                  if (curRef == ref) {
                    subMap.remove(ref.key2);
                    if (subMap.isEmpty()) ref.cache.entries.remove(ref.key1);
                  }
                }
              }
            }
          } catch (InterruptedException e) {
          }
        }
      }
    }
  }

  public void clear() {
  }
}
