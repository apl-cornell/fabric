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
package fabric.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;

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

  /**
   * The host name of the store for the object being pointed to.
   */
  private final String storeName;

  public Surrogate(Store store, long onum, int version, long expiry,
      long label, ObjectInput serializedInput, Iterator<RefTypeEnum> refTypes,
      Iterator<Long> intraStoreRefs) throws IOException, ClassNotFoundException {
    super(store, onum, version, expiry, label, serializedInput, refTypes, intraStoreRefs);
    this.storeName = serializedInput.readUTF();
    this.store = Worker.getWorker().getStore(storeName);
    this.onum = serializedInput.readLong();
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object._Impl#$makeProxy()
   */
  @Override
  protected _Proxy $makeProxy() {
    throw new InternalError("Attempted to make proxy for a surrogate.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object._Impl#$serialize(java.io.ObjectOutput,
   *      java.util.List, java.util.List, java.util.List)
   */
  @Override
  public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
      List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs) {
    // This should never be called. Surrogates are created in serialized form on
    // the store and should never be transmitted by the worker.
    throw new InternalError("Attempted to serialize a surrogate.");
  }
}
