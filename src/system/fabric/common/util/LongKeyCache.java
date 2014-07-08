/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.common.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * A map with soft references to its values. When a value in the map is garbage
 * collected by the JVM, its corresponding key is removed from the map.
 */
public class LongKeyCache<V> {

  // The underlying map.
  private final LongKeyMap<ValueSoftRef<V>> map;

  private static final class ValueSoftRef<V> extends SoftReference<V> {
    final long key;
    final LongKeyCache<V> cache;

    public ValueSoftRef(LongKeyCache<V> cache, long key, V value) {
      super(value);
      this.key = key;
      this.cache = cache;
    }

    @Override
    public boolean equals(Object obj) {
      V val = get();
      if (val == null) return false;
      return val.equals(obj);
    }
  }

  private static final ReferenceQueue<Object> queue =
      new ReferenceQueue<Object>();
  private static final Collector collector;

  static {
    collector = new Collector();
    collector.start();
  }

  public LongKeyCache() {
    this.map = new LongKeyHashMap<ValueSoftRef<V>>();
  }

  public synchronized void clear() {
    map.clear();
  }

  public synchronized boolean containsKey(long key) {
    return map.containsKey(key);
  }

  public synchronized V get(long key) {
    ValueSoftRef<V> ref = map.get(key);
    if (ref == null) return null;
    return ref.get();
  }

  public synchronized V put(long key, V value) {
    ValueSoftRef<V> ref = null;
    if (value != null) {
      ref = new ValueSoftRef<V>(this, key, value);
    }

    ref = map.put(key, ref);
    if (ref == null) return null;
    return ref.get();
  }

  public synchronized V remove(long key) {
    ValueSoftRef<V> ref = map.remove(key);
    if (ref == null) return null;
    return ref.get();
  }

  public static final class Collector extends Thread {
    private boolean destroyed;

    private Collector() {
      super("Cache entry collector");
    }

    /**
     * This destroys the background thread responsible for cleaning up
     * garbage-collected cache entries.
     */
    public static void shutdown() {
      collector.destroyed = true;
      collector.interrupt();
    }

    @Override
    public void run() {
      while (!destroyed) {
        try {
          ValueSoftRef<?> ref = (ValueSoftRef<?>) queue.remove();
          synchronized (ref.cache) {
            ValueSoftRef<?> curRef = ref.cache.map.get(ref.key);
            if (ref == curRef) ref.cache.remove(ref.key);
          }
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
