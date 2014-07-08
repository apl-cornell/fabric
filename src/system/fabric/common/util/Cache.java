/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A thread-safe map with soft references to its values. When a value in the map
 * is garbage collected by the JVM, its corresponding key is removed from the
 * map.
 */
public class Cache<K, V> {

  // The underlying map.
  private final Map<K, ValueSoftRef<K, V>> map;

  private static final class ValueSoftRef<K, V> extends SoftReference<V> {
    final K key;
    final Cache<K, V> cache;

    public ValueSoftRef(Cache<K, V> cache, K key, V value) {
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

  static {
    new Collector().start();
  }

  public Cache() {
    this.map = new HashMap<K, ValueSoftRef<K, V>>();
  }

  public synchronized void clear() {
    map.clear();
  }

  public synchronized boolean containsKey(K key) {
    return map.containsKey(key);
  }

  public synchronized V get(K key) {
    ValueSoftRef<K, V> ref = map.get(key);
    if (ref == null) return null;
    return ref.get();
  }

  public synchronized V put(K key, V value) {
    ValueSoftRef<K, V> ref = null;
    if (value != null) {
      ref = new ValueSoftRef<K, V>(this, key, value);
    }

    ref = map.put(key, ref);
    if (ref == null) return null;
    return ref.get();
  }

  public synchronized V remove(Object key) {
    ValueSoftRef<K, V> ref = map.remove(key);
    if (ref == null) return null;
    return ref.get();
  }

  /**
   * Returns a snapshot of the keys currently in the cache. This set is NOT
   * backed by the underlying map. If new keys are inserted or removed from the
   * cache, they will not be reflected by the set returned. However, no
   * synchronization is needed for working with the set.
   */
  public synchronized Set<K> keys() {
    return new HashSet<K>(map.keySet());
  }

  private static final class Collector extends Thread {
    private Collector() {
      super("Cache entry collector");
      setDaemon(true);
    }

    @Override
    public void run() {
      while (true) {
        try {
          ValueSoftRef<?, ?> ref = (ValueSoftRef<?, ?>) queue.remove();
          synchronized (ref.cache) {
            ValueSoftRef<?, ?> curRef = ref.cache.map.get(ref.key);
            if (ref == curRef) ref.cache.remove(ref.key);
          }
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
