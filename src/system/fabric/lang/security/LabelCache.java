package fabric.lang.security;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.util.Pair;
import fabric.common.util.Triple;
import fabric.lang.Object;
import fabric.lang.security.SecurityCache.DelegationPair;
import fabric.worker.Store;

/**
 * A top-level cache for label and policy objects, allowing us to intern Label,
 * ConfPolicy, and IntegPolicy objects. Because this cache is top-level (i.e.,
 * it exists outside of transactions), it is separate from SecurityCache, which
 * is a per-transaction cache. This class is thread-safe and is kept in the
 * fabric.lang.security package to ensure that only security-related classes
 * can modify this cache.
 */
public class LabelCache {

  /**
   * Cache for constructing ConfPolicy objects out of an owner and a reader.
   */
  private final Cache<Principal, Principal, ConfPolicy> readerPolicyCache;

  /**
   * Cache for constructing IntegPolicy objects out of an owner and a writer.
   */
  private final Cache<Principal, Principal, IntegPolicy> writerPolicyCache;

  /**
   * Cache for constructing policy joins.
   */
  private final Cache<Policy, Policy, Policy> policyJoinCache;

  /**
   * Cache for constructing policy meets.
   */
  private final Cache<Policy, Policy, Policy> policyMeetCache;

  /**
   * Cache for constructing Label objects out of a ConfPolicy and an IntegPolicy.
   */
  private final Cache<ConfPolicy, IntegPolicy, Label> toLabelCache;

  /**
   * Cache for constructing label joins.
   */
  private final Cache<Label, Label, Label> labelJoinCache;

  /**
   * Cache for constructing label meets.
   */
  private final Cache<Label, Label, Label> labelMeetCache;

  public LabelCache() {
    this.readerPolicyCache = new Cache<>();
    this.writerPolicyCache = new Cache<>();
    this.policyJoinCache = new Cache<>();
    this.policyMeetCache = new Cache<>();
    this.toLabelCache = new Cache<>();
    this.labelJoinCache = new Cache<>();
    this.labelMeetCache = new Cache<>();
  }

  private static final class Key {
    final Store key1store;
    final long key1onum;

    final Store key2store;
    final long key2onum;

    final Store store;

    Key(Triple<? extends Object, ? extends Object, Store> triple) {
      if (triple.first == null) {
        key1store = null;
        key1onum = 0;
      } else {
        key1store = triple.first.$getStore();
        key1onum = triple.first.$getOnum();
      }

      if (triple.second == null) {
        key2store = null;
        key2onum = 0;
      } else {
        key2store = triple.second.$getStore();
        key2onum = triple.second.$getOnum();
      }

      store = triple.third;
    }

    @Override
    public int hashCode() {
      return hashCode(key1store, key1onum) ^ hashCode(key2store, key2onum)
          ^ store.hashCode();
    }

    private static int hashCode(Store store, long onum) {
      if (store == null) return 0;
      if (ONumConstants.isGlobalConstant(onum)) return (int) onum;
      return store.hashCode() ^ (int) onum;
    }

    @Override
    public boolean equals(java.lang.Object o) {
      if (!(o instanceof Key)) return false;
      Key k = (Key) o;
      return equals(key1store, key1onum, k.key1store, k.key1onum)
          && equals(key2store, key2onum, k.key2store, k.key2onum)
          && store.equals(k.store);
    }

    private static boolean equals(Store s1, long onum1, Store s2, long onum2) {
      if (onum1 != onum2) return false;

      if (ONumConstants.isGlobalConstant(onum1)) {
        // Object is considered the same regardless of which store it came from.
        return true;
      }

      return s1 == s2 || s1 != null && s1.equals(s2);
    }

    @Override
    public String toString() {
      return "(" + toOIDString(key1store, key1onum) + ", "
          + toOIDString(key2store, key2onum) + ", " + store.name() + ")";
    }

    private static String toOIDString(Store store, long onum) {
      if (store == null) return "null";
      return store.name() + "/" + onum;
    }
  }

  /**
   * Maps (OID of K1, OID of K2, Store) triples to P proxies).
   */
  private static final class Cache<K1 extends Object, K2 extends Object, P extends Object> {
    private static final class EntrySoftRef<K1 extends Object, K2 extends Object, P extends Object>
        extends SoftReference<P> {
      final Cache<K1, K2, P> cache;
      final Key key;

      private EntrySoftRef(Cache<K1, K2, P> cache, Key key, P value) {
        super(value, queue);
        this.cache = cache;
        this.key = key;
      }
    }

    private static final ReferenceQueue<Object> queue = new ReferenceQueue<>();

    static {
      new Collector().start();
    }

    private final Map<Key, EntrySoftRef<K1, K2, P>> entries;

    Cache() {
      this.entries = new HashMap<>();
    }

    public synchronized P get(Triple<K1, K2, Store> triple) {
      EntrySoftRef<K1, K2, P> entry = entries.get(new Key(triple));
      return entry == null ? null : entry.get();
    }

    public synchronized void put(Entry<Triple<K1, K2, Store>, P> entry) {
      put(entry.getKey(), entry.getValue());
    }

    public synchronized void put(Triple<K1, K2, Store> triple, P val) {
      Key key = new Key(triple);
      EntrySoftRef<K1, K2, P> entry = new EntrySoftRef<>(Cache.this, key, val);
      entries.put(key, entry);
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
              EntrySoftRef<?, ?, ?> curRef =
                  (EntrySoftRef<?, ?, ?>) ref.cache.entries.get(ref.key);
              if (curRef == ref) {
                ref.cache.entries.remove(ref.key);
              }
            }
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }
      }
    }
  }

  public ConfPolicy getReaderPolicy(Triple<Principal, Principal, Store> triple) {
    return readerPolicyCache.get(triple);
  }

  public IntegPolicy getWriterPolicy(Triple<Principal, Principal, Store> triple) {
    return writerPolicyCache.get(triple);
  }

  public Policy getPolicyJoin(Triple<Policy, Policy, Store> triple) {
    return policyJoinCache.get(triple);
  }

  public Policy getPolicyMeet(Triple<Policy, Policy, Store> triple) {
    return policyMeetCache.get(triple);
  }

  public Label getLabel(Triple<ConfPolicy, IntegPolicy, Store> triple) {
    return toLabelCache.get(triple);
  }

  public Label getLabelJoin(Triple<Label, Label, Store> triple) {
    return labelJoinCache.get(triple);
  }

  public Label getLabelMeet(Triple<Label, Label, Store> triple) {
    return labelMeetCache.get(triple);
  }

  /**
   * Adds all dependency-free entries from the given SecurityCache to this
   * LabelCache.
   */
  void addAll(SecurityCache cache) {
    for (Entry<Triple<Principal, Principal, Store>, ConfPolicy> entry : cache
        .readerPolicyEntrySet()) {
      readerPolicyCache.put(entry);
    }

    for (Entry<Triple<Principal, Principal, Store>, IntegPolicy> entry : cache
        .writerPolicyEntrySet()) {
      writerPolicyCache.put(entry);
    }

    for (Entry<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>> entry : cache
        .policyJoinEntrySet()) {
      Pair<Policy, Set<DelegationPair>> value = entry.getValue();
      if (!value.second.isEmpty()) continue;

      policyJoinCache.put(entry.getKey(), value.first);
    }

    for (Entry<Triple<Policy, Policy, Store>, Pair<Policy, Set<DelegationPair>>> entry : cache
        .policyMeetEntrySet()) {
      Pair<Policy, Set<DelegationPair>> value = entry.getValue();
      if (!value.second.isEmpty()) continue;

      policyMeetCache.put(entry.getKey(), value.first);
    }

    for (Entry<Triple<ConfPolicy, IntegPolicy, Store>, Label> entry : cache
        .labelEntrySet()) {
      toLabelCache.put(entry);
    }

    for (Entry<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>> entry : cache
        .labelJoinEntrySet()) {
      Pair<Label, Set<DelegationPair>> value = entry.getValue();
      if (!value.second.isEmpty()) continue;

      labelJoinCache.put(entry.getKey(), value.first);
    }

    for (Entry<Triple<Label, Label, Store>, Pair<Label, Set<DelegationPair>>> entry : cache
        .labelMeetEntrySet()) {
      Pair<Label, Set<DelegationPair>> value = entry.getValue();
      if (!value.second.isEmpty()) continue;

      labelMeetCache.put(entry.getKey(), value.first);
    }
  }
}
