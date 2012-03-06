package fabric.worker;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.common.util.OidKeyHashMap;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.IntegPolicy;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/**
 * A per-store cache for label and policy objects, allowing us to intern Label,
 * ConfPolicy, and IntegPolicy objects. This class is thread-safe.
 */
public class LabelCache {

  /**
   * Maps OIDs to (maps of OIDs to T proxies).
   */
  private static final class Cache<P extends Object> {
    private static final class EntrySoftRef<P extends Object> extends
        SoftReference<P> {
      final Cache<P> cache;
      final Object._Proxy key1;
      final Object._Proxy key2;

      private EntrySoftRef(Cache<P> cache, Object._Proxy key1,
          Object._Proxy key2, P value) {
        super(value);
        this.cache = cache;
        this.key1 = key1;
        this.key2 = key2;
      }
    }

    private static final ReferenceQueue<EntrySoftRef<?>> queue =
        new ReferenceQueue<EntrySoftRef<?>>();

    static {
      new Collector().start();
    }

    private final OidKeyHashMap<OidKeyHashMap<EntrySoftRef<P>>> entries;

    Cache() {
      this.entries = new OidKeyHashMap<OidKeyHashMap<EntrySoftRef<P>>>();
    }

    public synchronized P get(Object key1, Object key2) {
      OidKeyHashMap<EntrySoftRef<P>> submap = entries.get(key1);
      if (submap == null) return null;

      EntrySoftRef<P> entry = submap.get(key2);
      if (entry == null) return null;

      return entry.get();
    }

    public synchronized void put(Object key1, Object key2, P val) {
      OidKeyHashMap<EntrySoftRef<P>> submap = entries.get(key1);
      if (submap == null) {
        submap = new OidKeyHashMap<LabelCache.Cache.EntrySoftRef<P>>();
        entries.put(key1, submap);
      }

      Object._Proxy key1Proxy = key1 == null ? null : key1.$getProxy();
      Object._Proxy key2Proxy = key2 == null ? null : key2.$getProxy();

      EntrySoftRef<P> entry =
          new EntrySoftRef<P>(Cache.this, key1Proxy, key2Proxy, val);
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

      @SuppressWarnings("rawtypes")
      @Override
      public void run() {
        while (true) {
          try {
            EntrySoftRef ref = (EntrySoftRef) queue.remove();

            // GC has snapped the soft reference "ref". Remove its entry from
            // cache.
            synchronized (ref.cache) {
              @SuppressWarnings("unchecked")
              OidKeyHashMap<EntrySoftRef> subMap =
                  (OidKeyHashMap<EntrySoftRef>) ref.cache.entries.get(ref.key1);
              if (subMap != null) {
                synchronized (subMap) {
                  EntrySoftRef curRef = subMap.get(ref.key2);
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

  /**
   * Cache for constructing labels out of ConfPolicy and IntegPolicy objects.
   */
  private final Cache<Label> toLabelCache;

  /**
   * Cache for constructing label joins.
   */
  private final Cache<Label> labelJoinCache;

  /**
   * Cache for constructing label meets.
   */
  private final Cache<Label> labelMeetCache;

  /**
   * Cache for constructing ConfPolicy objects out of an owner and a reader.
   */
  private final Cache<ConfPolicy> readerPolicyCache;

  /**
   * Cache for constructing ConfPolicy joins.
   */
  private final Cache<ConfPolicy> confPolicyJoinCache;

  /**
   * Cache for constructing ConfPolicy meets.
   */
  private final Cache<ConfPolicy> confPolicyMeetCache;

  /**
   * Cache for constructing IntegPolicy objects out of an owner and a writer.
   */
  private final Cache<IntegPolicy> writerPolicyCache;

  /**
   * Cache for constructing IntegPolicy joins.
   */
  private final Cache<IntegPolicy> integPolicyJoinCache;

  /**
   * Cache for constructing IntegPolicy meets.
   */
  private final Cache<IntegPolicy> integPolicyMeetCache;

  LabelCache() {
    this.toLabelCache = new Cache<Label>();
    this.labelJoinCache = new Cache<Label>();
    this.labelMeetCache = new Cache<Label>();

    this.readerPolicyCache = new Cache<ConfPolicy>();
    this.confPolicyJoinCache = new Cache<ConfPolicy>();
    this.confPolicyMeetCache = new Cache<ConfPolicy>();

    this.writerPolicyCache = new Cache<IntegPolicy>();
    this.integPolicyJoinCache = new Cache<IntegPolicy>();
    this.integPolicyMeetCache = new Cache<IntegPolicy>();
  }

  public Label toLabel(ConfPolicy conf, IntegPolicy integ) {
    return toLabelCache.get(conf, integ);
  }

  /**
   * Adds to the intern cache a label that was constructed by
   * LabelUtil.toLabel(conf, integ).
   */
  public void addToLabel(ConfPolicy conf, IntegPolicy integ, Label label) {
    toLabelCache.put(conf, integ, label);
  }

  public ConfPolicy readerPolicy(Principal owner, Principal reader) {
    return readerPolicyCache.get(owner, reader);
  }

  /**
   * Adds to the intern cache a ConfPolicy that was constructed by
   * LabelUtil.readerPolicy(owner, reader).
   */
  public void addReaderPolicy(Principal owner, Principal reader,
      ConfPolicy policy) {
    readerPolicyCache.put(owner, reader, policy);
  }

  public IntegPolicy writerPolicy(Principal owner, Principal writer) {
    return writerPolicyCache.get(owner, writer);
  }

  /**
   * Adds to the intern cache an IntegPolicy that was constructed by
   * LabelUtil.writerPolicy(owner, writer).
   */
  public void addWriterPolicy(Principal owner, Principal writer,
      IntegPolicy policy) {
    writerPolicyCache.put(owner, writer, policy);
  }

  public Label join(Label l1, Label l2) {
    // Canonicalize ordering of l1 and l2.
    if (compare(l1, l2) > 0) {
      Label tmp = l1;
      l1 = l2;
      l2 = tmp;
    }
    return labelJoinCache.get(l1, l2);
  }

  /**
   * Adds to the intern cache a label that was constructed by LabelUtil.join(l1,
   * l2).
   */
  public void addJoin(Label l1, Label l2, Label join) {
    labelJoinCache.put(l1, l2, join);
  }

  public ConfPolicy join(ConfPolicy p1, ConfPolicy p2) {
    // Canonicalize ordering of p1 and p2.
    if (compare(p1, p2) > 0) {
      ConfPolicy tmp = p1;
      p1 = p2;
      p2 = tmp;
    }
    return confPolicyJoinCache.get(p1, p2);
  }

  /**
   * Adds to the intern cache a label that was constructed by LabelUtil.join(p1,
   * p2).
   */
  public void addJoin(ConfPolicy p1, ConfPolicy p2, ConfPolicy join) {
    confPolicyJoinCache.put(p1, p2, join);
  }

  public IntegPolicy join(IntegPolicy p1, IntegPolicy p2) {
    // Canonicalize ordering of p1 and p2.
    if (compare(p1, p2) > 0) {
      IntegPolicy tmp = p1;
      p1 = p2;
      p2 = tmp;
    }
    return integPolicyJoinCache.get(p1, p2);
  }

  /**
   * Adds to the intern cache a label that was constructed by LabelUtil.join(p1,
   * p2).
   */
  public void addJoin(IntegPolicy p1, IntegPolicy p2, IntegPolicy join) {
    integPolicyJoinCache.put(p1, p2, join);
  }

  public Label meet(Label l1, Label l2) {
    // Canonicalize ordering of l1 and l2.
    if (compare(l1, l2) > 0) {
      Label tmp = l1;
      l1 = l2;
      l2 = tmp;
    }
    return labelMeetCache.get(l1, l2);
  }

  /**
   * Adds to the intern cache a label that was constructed by LabelUtil.meet(l1,
   * l2).
   */
  public void addMeet(Label l1, Label l2, Label meet) {
    labelMeetCache.put(l1, l2, meet);
  }

  public ConfPolicy meet(ConfPolicy p1, ConfPolicy p2) {
    // Canonicalize ordering of p1 and p2.
    if (compare(p1, p2) > 0) {
      ConfPolicy tmp = p1;
      p1 = p2;
      p2 = tmp;
    }
    return confPolicyMeetCache.get(p1, p2);
  }

  /**
   * Adds to the intern cache a label that was constructed by
   * LabelUtil.meetPol(p1, p2).
   */
  public void addMeet(ConfPolicy p1, ConfPolicy p2, ConfPolicy meet) {
    confPolicyMeetCache.put(p1, p2, meet);
  }

  public IntegPolicy meet(IntegPolicy p1, IntegPolicy p2) {
    // Canonicalize ordering of p1 and p2.
    if (compare(p1, p2) > 0) {
      IntegPolicy tmp = p1;
      p1 = p2;
      p2 = tmp;
    }
    return integPolicyMeetCache.get(p1, p2);
  }

  /**
   * Adds to the intern cache a label that was constructed by
   * LabelUtil.meetPol(p1, p2).
   */
  public void addMeet(IntegPolicy p1, IntegPolicy p2, IntegPolicy meet) {
    integPolicyMeetCache.put(p1, p2, meet);
  }
  
  public void clear() {
    toLabelCache.clear();
    labelJoinCache.clear();
    labelMeetCache.clear();

    readerPolicyCache.clear();
    confPolicyJoinCache.clear();
    confPolicyMeetCache.clear();

    writerPolicyCache.clear();
    integPolicyJoinCache.clear();
    integPolicyMeetCache.clear();
  }

  private static int compare(Object o1, Object o2) {
    int storeCompare = o1.$getStore().name().compareTo(o2.$getStore().name());
    if (storeCompare != 0) return storeCompare;

    long onum1 = o1.$getOnum();
    long onum2 = o2.$getOnum();
    if (onum1 == onum2) return 0;
    if (onum1 < onum2) return -1;
    return 1;
  }
}
