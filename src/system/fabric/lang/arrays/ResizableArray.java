package fabric.lang.arrays;

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
import fabric.lang.Object;

/**
 * author: kvikram
 * This class implements a resizable array using ObjectArray as a primitive
 * The smaller array pieces are arranged as a tree so as to support efficient
 * indexing operations 
 * 
 * This version of ResizableArray assumes elements are Object instances
 * For primitive types, other versions may be written
 * 
 * Possible optimizations:
 * 1. convert the length to a base 128 representation
 *    will make it easier to take logs then
 *    
 * Bugs:
 * 1. If the array is indeed of ObjectArray instance, then things could get messed up
 *    We're assuming for now that this case won't arise
 *
 **/

public interface ResizableArray<T extends Object> extends Object {
  int get$length();

  void set$length(int newSize);

  T set(int i, T value);

  T get(int i);

  public static class $Impl<T extends Object> extends Object.$Impl implements
      ResizableArray<T> {

    /**
     * The number of elements in each little array Dependent on the MTU?
     * Analogous to a block in a file system Also directly determines the fanout
     */
    int CHUNK_SIZE = 128;

    /**
     * The height of the tree of little arrays. Depends on the chunk size
     * (determining the branching factor) and the number of expected elements in
     * the bigger array
     */
    int height;

    /**
     * The number of expected elements in this big array Can be modified even
     * after an instance has been created
     */
    int length;

    /**
     * The class representing the proxy type for the array elements.
     */
    private final Class<? extends Object.$Proxy> proxyType;

    /**
     * The root of the tree of little arrays. The runtime type of root is simply
     * a java array of objects Each object in the array is either a further
     * array of objects or is an array element if this array is at the leaf
     * level
     */
    ObjectArray<Object> root;

    /**
     * Creates a new object array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, Class<? extends Object.$Proxy> proxyType, int length) {
      super(core);
      this.proxyType = proxyType;
      this.length = length;
      this.height =
          (int) Math.ceil(Math.log10(length) / Math.log10(CHUNK_SIZE));
      root = new ObjectArray.$Impl<Object>(core, proxyType, CHUNK_SIZE);
    }

    /**
     * Creates a new object array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public $Impl(Core core, Class<? extends Object.$Proxy> proxyType, T[] value) {
      this(core, proxyType, value.length);
      for (int i = 0; i < length; i++) {
        this.set(i, value[i]);
      }
    }

    /**
     * Used for deserializing.
     */
    @SuppressWarnings("unchecked")
    public $Impl(Core core, long onum, int version, Policy policy,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      // FIXME MJL: Why doesn't this restore length and height fields?
      super(core, onum, version, policy, in, refTypes, intracoreRefs);
      proxyType = (Class<? extends Object.$Proxy>) Class.forName(in.readUTF());
      root = new ObjectArray.$Impl<Object>(core, proxyType, CHUNK_SIZE);
      for (int i = 0; i < CHUNK_SIZE; i++) {
        root.set(i, $readRef(proxyType, refTypes.next(), in, core,
            intracoreRefs));
      }
    }

    public int get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return length;
    }

    @SuppressWarnings("unchecked")
    public void set$length(int newSize) {

      // This method implicitly reads the state of this object (viz height)
      TransactionManager.INSTANCE.registerRead(this);
      
      int newHeight =
          (int) Math.ceil(Math.log10(newSize) / Math.log10(CHUNK_SIZE));
      int difference = newHeight - this.height;
      if (difference == 0) return;

      if (difference > 0) {
        // make sure the leaves are at the right level - push down the root
        while (difference-- > 0) {
          ObjectArray<Object> newRoot =
              new ObjectArray.$Impl<Object>($getCore(), proxyType, CHUNK_SIZE);
          newRoot.set(0, root);
          root = newRoot;
        }
        return;
      }

      // difference > 0.  Truncate the last so many array slots
      while (difference++ < 0) {
        ObjectArray<Object> rootArray = (ObjectArray<Object>) root.get(0);
        root = rootArray;
      }
      return;
    }

    @SuppressWarnings("unchecked")
    public T get(int i) {
      TransactionManager.INSTANCE.registerRead(this);
      if (root == null) {
        return null;
      }
      return (T) getByLevel(root, height, i, true, null);
    }

    /**
     * getOrSet = true if get and false if set
     */
    @SuppressWarnings("unchecked")
    private Object getByLevel(ObjectArray<Object> node, int level, int i,
        boolean getOrSet, Object data) {

      if (node == null) {
        // we're boldly going where no one has gone before
        node = new ObjectArray.$Impl<Object>($getCore(), proxyType, CHUNK_SIZE);
      }

      int divider = (int) Math.pow(CHUNK_SIZE, level - 1);
      int firstDigit = (int) Math.floor(i / divider);
      int otherDigits = i - firstDigit * divider;

      if (level == 1) {
        if (getOrSet) {
          return node.get(i);
        } else {
          return node.set(i, data);
        }
      } else {
        return getByLevel((ObjectArray<Object>) node.get(firstDigit),
            level - 1, otherDigits, getOrSet, data);
      }
    }

    @SuppressWarnings("unchecked")
    public T set(int i, T data) {
      boolean transactionCreated =
          TransactionManager.INSTANCE.registerWrite(this);
      T result = (T) getByLevel(root, height, i, false, data);
      if (transactionCreated) TransactionManager.INSTANCE.commitTransaction();
      return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void $copyStateFrom(Object.$Impl other) {
      super.$copyStateFrom(other);
      ResizableArray.$Impl<T> src = (ResizableArray.$Impl<T>) other;
      // FIXME MJL: Why doesn't this copy over length and height?
      // FIXME MJL: This is supposed to be a shallow copy.
      root = deepArrayCopy(src.root);
    }

    @SuppressWarnings("unchecked")
    private ObjectArray<Object> deepArrayCopy(ObjectArray<Object> src) {
      ObjectArray<Object> dest =
          new ObjectArray.$Impl<Object>(src.$getCore(), proxyType, CHUNK_SIZE);
      for (int i = 0; i < CHUNK_SIZE; i++) {
        Object ithSlot = src.get(i);
        if (ithSlot != null) {
          if (ithSlot instanceof ObjectArray) {
            // the slot plot thickens
            ObjectArray<Object> deeperArray = (ObjectArray<Object>) ithSlot;
            dest.set(i, deepArrayCopy(deeperArray));
          } else {
            dest.set(i, ithSlot);
          }
        }
      }
      return dest;
    }

    @Override
    protected ResizableArray.$Proxy<T> $makeProxy() {
      return new ResizableArray.$Proxy<T>(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intracoreRefs, List<Pair<String, Long>> intercoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intracoreRefs, intercoreRefs);
      // FIXME MJL: Why doesn't this write out length, height?
      out.writeUTF(proxyType.getName());
      for (int i = 0; i < CHUNK_SIZE; i++)
        $writeRef($getCore(), root.get(i), refTypes, out, intracoreRefs,
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
    public int get$length() {
      return ((ResizableArray<T>) fetch()).get$length();
    }

    @SuppressWarnings("unchecked")
    public void set$length(int newSize) {
      ((ResizableArray<T>) fetch()).set$length(newSize);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
      return ((ResizableArray<T>) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#set(int, fabric.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      return ((ResizableArray<T>) fetch()).set(i, value);
    }
  }

}
