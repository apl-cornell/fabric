package fabric.lang.arrays;

import fabric.lang.Object;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.common.Pair;
import fabric.common.Policy;
import fabric.core.SerializedObject.RefTypeEnum;


/**
 * author: kvikram
 * This class implements a resizable array using regular java arrays as a primitive
 * The smaller java array pieces are arranged as a tree so as to 
 * 
 * This version of ResizableArray assumes elements are Object instances
 * For primitive types, other versions may be written
 * 
 * Possible optimizations:
 * 1. convert the length to a base 128 representation
 *    will make it easier to take logs then
 *
 **/

public interface ResizableArray<T extends Object> extends Object {
  long get$length();
  void set$length(long newSize);

  T set(long i, T value);

  T get(long i);



  public static class $Impl<T extends Object> extends Object.$Impl implements
  ResizableArray<T> {

    /**
     * The number of elements in each little array
     * Dependent on the MTU? Analogous to a block in a file system
     * Also directly determines the fanout 
     */
    int CHUNK_SIZE = 128;

    /**
     * The height of the tree of little arrays. 
     * Depends on the chunk size (determining the branching factor)
     *  and the number of expected elements in the bigger array
     */
    int height;

    /**
     * The number of expected elements in this big array
     * Can be modified even after an instance has been created
     */
    long length;

    /**
     * The class representing the proxy type for the array elements.
     */
    private final Class<? extends Object.$Proxy> proxyType;

    /**
     * The root of the tree of little arrays. 
     * The runtime type of root is simply a java array of objects
     * Each object in the array is either a further array of objects
     *  or is an array element if this array is at the leaf level
     */
    java.lang.Object[] root;

    /**
     * Creates a new object array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, Class<? extends Object.$Proxy> proxyType, long length) {
      super(core);
      this.proxyType = proxyType;
      this.length = length;
      this.height = (int) Math.ceil(Math.log10(length)/Math.log10(CHUNK_SIZE));
      root = new java.lang.Object[CHUNK_SIZE];
    }

    /** TODO XXX is this necessary?
     * Creates a new object array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */


    /**
     * Used for deserializing.
     */
    @SuppressWarnings("unchecked")
    public $Impl(Core core, long onum, int version, Policy policy,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, policy, in, refTypes, intracoreRefs);
      proxyType = (Class<? extends Object.$Proxy>) Class.forName(in.readUTF());
      root = new java.lang.Object[CHUNK_SIZE];
      for (int i = 0; i < CHUNK_SIZE; i++) {
        root[i] =
          $readRef(proxyType, refTypes.next(), in, core, intracoreRefs);
      }
    }

    public long get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return length;
    }

    public void set$length(long newSize) {

      // since this is also there in get$length
      TransactionManager.INSTANCE.registerRead(this);
      int newHeight = (int) Math.ceil(Math.log10(newSize)/Math.log10(CHUNK_SIZE));
      int difference = newHeight - this.height;
      if(difference == 0) return;

      if(difference > 0) {
        // make sure the leaves are at the right level - push down the root
        while(difference-- > 0) {
          java.lang.Object[] newRoot = new java.lang.Object[CHUNK_SIZE];
          newRoot[0] = root;
          root = newRoot;
        }
        return;
      }

      if(difference < 0) {
        // truncate the last so many array slots
        while(difference++ < 0) {
          java.lang.Object[] rootArray = (java.lang.Object[])root[0];
          root = rootArray;
        }
        return;            
      }

    }

    public T get(long i) {
      TransactionManager.INSTANCE.registerRead(this);
      if(root == null) {
        return null;
      }
      return (T)getByLevel(root, height, i, true, null);
    }

    /**
     * getOrSet = true if get and false if set
     */
    private java.lang.Object getByLevel(java.lang.Object[] node, int level, long i, 
        boolean getOrSet, java.lang.Object data) {

      if(node == null) {
        // we're boldly going where no one has gone before
        node = new java.lang.Object[CHUNK_SIZE];
      }

      long divider = (long) Math.pow(CHUNK_SIZE, level - 1);
      int firstDigit = (int) Math.floor(i/divider);
      long otherDigits = i - firstDigit * divider;

      if(level == 1) {
        if(getOrSet) {
          return node[(int)i];
        } else {
          return node[(int)i] = data;
        }
      } else {
        return getByLevel((java.lang.Object[])node[firstDigit], 
            level - 1, otherDigits, getOrSet, data);            
      }
    }

    @SuppressWarnings("unchecked")
    public T set(long i, T data) {
      boolean transactionCreated =
        TransactionManager.INSTANCE.registerWrite(this);
      T result = (T)getByLevel(root, height, i, false, data);
      if (transactionCreated) TransactionManager.INSTANCE.commitTransaction();
      return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void $copyStateFrom(Object.$Impl other) {
      super.$copyStateFrom(other);
      ResizableArray.$Impl<T> src = (ResizableArray.$Impl<T>) other;
      root = deepArrayCopy(src.root);
    }

    private java.lang.Object[] deepArrayCopy(java.lang.Object[] src) {
      java.lang.Object[] dest = new java.lang.Object[CHUNK_SIZE];
      for(int i = 0; i < CHUNK_SIZE; i++) {
        if(src[i] != null) {
          if(src[i] instanceof java.lang.Object[]) {
            java.lang.Object[] deeperArray = (java.lang.Object[])src[i];
            dest[i] = deepArrayCopy(deeperArray);
          } else {
            dest[i] = src[i];
          }
        }
      }
    }

    protected ResizableArray.$Proxy<T> $makeProxy() {
      return new ResizableArray.$Proxy<T>(this);
    }

    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intracoreRefs, List<Pair<String, Long>> intercoreRefs)
    throws IOException {
      super.$serialize(out, refTypes, intracoreRefs, intercoreRefs);
      out.writeUTF(proxyType.getName());
      for (int i = 0; i < CHUNK_SIZE; i++)
        $writeRef($getCore(), root[i], refTypes, out, intracoreRefs,
            intercoreRefs);
    }

  }

  public static class $Proxy<T extends Object> extends Object.$Proxy implements
  ResizableArray<T> {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(ResizableArray.$Impl<T> impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#getLength()
     */
    @SuppressWarnings("unchecked")
    public long get$length() {
      return ((ResizableArray<T>) fetch()).get$length();
    }


    @SuppressWarnings("unchecked")
    public void set$length(long newSize) {
      ((ResizableArray<T>) fetch()).set$length(newSize);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public T get(long i) {
      return ((ResizableArray<T>) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#set(int, fabric.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public T set(long i, T value) {
      return ((ResizableArray<T>) fetch()).set(i, value);
    }
  }


}