package fabric.worker.transaction;

import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.Object._Impl;
import fabric.worker.Store;

/**
 * Look aside cache for objects involved with call checking.
 */
public class ObjectLookAsideMap {
  public class Entry {
    private boolean isDeserialized;
    private _Impl deserialized;
    private SerializedObject serialized;
    private Store store;
    private VersionWarranty warranty;

    public Entry(Store store, SerializedObject obj, VersionWarranty warranty) {
      this.isDeserialized = false;
      this.deserialized = null;
      this.serialized = obj;
      this.store = store;
      this.warranty = warranty;
    }

    public Entry(_Impl obj) {
      this.isDeserialized = true;
      this.deserialized = obj;
      this.serialized = null;
      this.store = null;
      this.warranty = null;
    }

    public _Impl getObject() {
      if (!isDeserialized) {
        deserialized = serialized.deserialize(store, warranty);
        isDeserialized = true;
      }
      deserialized.$isLookAside = true;
      return deserialized;
    }
  }

  private OidKeyHashMap<Entry> entryMap;

  public ObjectLookAsideMap() {
    entryMap = new OidKeyHashMap<>();
  }

  public _Impl get(Store s, long onum) {
    Entry result = entryMap.get(s, onum);
    if (result != null) return result.getObject();
    return null;
  }

  public void put(Store store, SerializedObject obj, VersionWarranty war) {
    entryMap.put(store, obj.getOnum(), new Entry(store, obj, war));
  }

  public void put(_Impl obj) {
    entryMap.put(obj.$getStore(), obj.$getOnum(), new Entry(obj));
  }
}
