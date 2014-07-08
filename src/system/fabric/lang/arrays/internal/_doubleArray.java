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
package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.common.RefTypeEnum;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.security.Label;

public interface _doubleArray extends Object {
  int get$length();

  double set(int i, double value);

  double get(int i);

  public static class _Impl extends Object._Impl implements _doubleArray,
      _InternalArrayImpl {
    private double[] value;

    /**
     * Creates a new double array at the given Store with the given length.
     * 
     * @param store
     *                The store on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public _Impl(Store store, Label label, int length) {
      this(store, label, new double[length]);
    }

    /**
     * Creates a new double array at the given Store using the given backing
     * array.
     * 
     * @param store
     *                The store on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public _Impl(Store store, Label label, double[] value) {
      super(store, label);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version, long expiry, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, expiry, label, in, refTypes, intraStoreRefs);
      value = new double[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readDouble();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.doubleArray#getLength()
     */
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.doubleArray#get(int)
     */
    public double get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.doubleArray#set(int, double)
     */
    public double set(int i, double value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      double result = this.value[i] = value;
      if (transactionCreated) TransactionManager.getInstance().commitTransaction();
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$copyAppStateFrom(fabric.lang.Object._Impl)
     */
    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _doubleArray._Impl src = (_doubleArray._Impl) other;
      value = src.value;
    }

    public void cloneValues() {
      value = value.clone();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$makeProxy()
     */
    @Override
    protected _doubleArray._Proxy $makeProxy() {
      return new _doubleArray._Proxy(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$serialize(java.io.ObjectOutput)
     */
    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (int i = 0; i < value.length; i++)
        out.writeDouble(value[i]);
    }
  }

  public static class _Proxy extends Object._Proxy implements _doubleArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_doubleArray._Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.doubleArray#getLength()
     */
    public int get$length() {
      return ((_doubleArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.doubleArray#get(int)
     */
    public double get(int i) {
      return ((_doubleArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.doubleArray#set(int, double)
     */
    public double set(int i, double value) {
      return ((_doubleArray) fetch()).set(i, value);
    }
  }
}
