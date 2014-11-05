package fabric.util;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface Arrays extends fabric.lang.Object {
  public static interface ArrayList extends java.io.Serializable,
      fabric.util.RandomAccess, fabric.util.AbstractList {

    public fabric.lang.arrays.ObjectArray get$a();

    public fabric.lang.arrays.ObjectArray set$a(
        fabric.lang.arrays.ObjectArray val);

    public fabric.util.Arrays.ArrayList fabric$util$Arrays$ArrayList$(
        fabric.lang.arrays.ObjectArray a);

    @Override
    public fabric.lang.Object get(int index);

    @Override
    public int size();

    @Override
    public fabric.lang.Object set(int index, fabric.lang.Object element);

    @Override
    public boolean contains(fabric.lang.Object o);

    @Override
    public int indexOf(fabric.lang.Object o);

    @Override
    public int lastIndexOf(fabric.lang.Object o);

    @Override
    public fabric.lang.arrays.ObjectArray toArray();

    @Override
    public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray array);

    @Override
    public fabric.lang.Object $initLabels();

    public static class _Proxy extends fabric.util.AbstractList._Proxy
        implements fabric.util.Arrays.ArrayList {

      @Override
      native public fabric.lang.arrays.ObjectArray get$a();

      @Override
      native public fabric.lang.arrays.ObjectArray set$a(
          fabric.lang.arrays.ObjectArray val);

      @Override
      native public fabric.util.Arrays.ArrayList fabric$util$Arrays$ArrayList$(
          fabric.lang.arrays.ObjectArray arg1);

      @Override
      native public int size();

      @Override
      native public boolean contains(fabric.lang.Object arg1);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray arg1);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Proxy(ArrayList._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    final public static class _Impl extends fabric.util.AbstractList._Impl
        implements fabric.util.Arrays.ArrayList {

      @Override
      native public fabric.lang.arrays.ObjectArray get$a();

      @Override
      native public fabric.lang.arrays.ObjectArray set$a(
          fabric.lang.arrays.ObjectArray val);

      @Override
      native public fabric.util.Arrays.ArrayList fabric$util$Arrays$ArrayList$(
          fabric.lang.arrays.ObjectArray a);

      @Override
      native public fabric.lang.Object get(int index);

      @Override
      native public int size();

      @Override
      native public fabric.lang.Object set(int index, fabric.lang.Object element);

      @Override
      native public boolean contains(fabric.lang.Object o);

      @Override
      native public int indexOf(fabric.lang.Object o);

      @Override
      native public int lastIndexOf(fabric.lang.Object o);

      @Override
      native public fabric.lang.arrays.ObjectArray toArray();

      @Override
      native public fabric.lang.arrays.ObjectArray toArray(
          fabric.lang.arrays.ObjectArray array);

      @Override
      native public fabric.lang.Object $initLabels();

      public _Impl(fabric.worker.Store $location) {
        super($location);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      @Override
      native public void $serialize(java.io.ObjectOutput out,
          java.util.List refTypes, java.util.List intraStoreRefs,
          java.util.List interStoreRefs) throws java.io.IOException;

      public _Impl(fabric.worker.Store store, long onum, int version,
          VersionWarranty warranty, RWLease lease, long label,
          long accessLabel, java.io.ObjectInput in,
          java.util.Iterator refTypes, java.util.Iterator intraStoreRefs,
          java.util.Iterator interStoreRefs) throws java.io.IOException,
          java.lang.ClassNotFoundException {
        super(store, onum, version, warranty, lease, label, accessLabel, in,
            refTypes, intraStoreRefs, interStoreRefs);
      }

      @Override
      native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }

    interface _Static extends fabric.lang.Object, Cloneable {

      public long get$serialVersionUID();

      final class _Proxy extends fabric.lang.Object._Proxy implements
          fabric.util.Arrays.ArrayList._Static {

        @Override
        native public long get$serialVersionUID();

        public _Proxy(fabric.util.Arrays.ArrayList._Static._Impl impl) {
          super(impl);
        }

        public _Proxy(fabric.worker.Store store, long onum) {
          super(store, onum);
        }
      }

      class _Impl extends fabric.lang.Object._Impl implements
          fabric.util.Arrays.ArrayList._Static {

        @Override
        native public long get$serialVersionUID();

        public _Impl(fabric.worker.Store store)
            throws fabric.net.UnreachableNodeException {
          super(store);
        }

        @Override
        native protected fabric.lang.Object._Proxy $makeProxy();

        native private void $init();
      }

    }

  }

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Arrays {

    native public static int binarySearch(fabric.lang.arrays.byteArray arg1,
        byte arg2);

    native public static int binarySearch(fabric.lang.arrays.charArray arg1,
        char arg2);

    native public static int binarySearch(fabric.lang.arrays.shortArray arg1,
        short arg2);

    native public static int binarySearch(fabric.lang.arrays.intArray arg1,
        int arg2);

    native public static int binarySearch(fabric.lang.arrays.longArray arg1,
        long arg2);

    native public static int binarySearch(fabric.lang.arrays.floatArray arg1,
        float arg2);

    native public static int binarySearch(fabric.lang.arrays.doubleArray arg1,
        double arg2);

    native public static int binarySearch(fabric.lang.arrays.ObjectArray arg1,
        fabric.lang.Object arg2);

    native public static int binarySearch(fabric.lang.arrays.ObjectArray arg1,
        fabric.lang.Object arg2, fabric.util.Comparator arg3);

    native public static boolean equals(fabric.lang.arrays.booleanArray arg1,
        fabric.lang.arrays.booleanArray arg2);

    native public static boolean equals(fabric.lang.arrays.byteArray arg1,
        fabric.lang.arrays.byteArray arg2);

    native public static boolean equals(fabric.lang.arrays.charArray arg1,
        fabric.lang.arrays.charArray arg2);

    native public static boolean equals(fabric.lang.arrays.shortArray arg1,
        fabric.lang.arrays.shortArray arg2);

    native public static boolean equals(fabric.lang.arrays.intArray arg1,
        fabric.lang.arrays.intArray arg2);

    native public static boolean equals(fabric.lang.arrays.longArray arg1,
        fabric.lang.arrays.longArray arg2);

    native public static boolean equals(fabric.lang.arrays.floatArray arg1,
        fabric.lang.arrays.floatArray arg2);

    native public static boolean equals(fabric.lang.arrays.doubleArray arg1,
        fabric.lang.arrays.doubleArray arg2);

    native public static boolean equals(fabric.lang.arrays.ObjectArray arg1,
        fabric.lang.arrays.ObjectArray arg2);

    native public static void fill(fabric.lang.arrays.booleanArray arg1,
        boolean arg2);

    native public static void fill(fabric.lang.arrays.booleanArray arg1,
        int arg2, int arg3, boolean arg4);

    native public static void fill(fabric.lang.arrays.byteArray arg1, byte arg2);

    native public static void fill(fabric.lang.arrays.byteArray arg1, int arg2,
        int arg3, byte arg4);

    native public static void fill(fabric.lang.arrays.charArray arg1, char arg2);

    native public static void fill(fabric.lang.arrays.charArray arg1, int arg2,
        int arg3, char arg4);

    native public static void fill(fabric.lang.arrays.shortArray arg1,
        short arg2);

    native public static void fill(fabric.lang.arrays.shortArray arg1,
        int arg2, int arg3, short arg4);

    native public static void fill(fabric.lang.arrays.intArray arg1, int arg2);

    native public static void fill(fabric.lang.arrays.intArray arg1, int arg2,
        int arg3, int arg4);

    native public static void fill(fabric.lang.arrays.longArray arg1, long arg2);

    native public static void fill(fabric.lang.arrays.longArray arg1, int arg2,
        int arg3, long arg4);

    native public static void fill(fabric.lang.arrays.floatArray arg1,
        float arg2);

    native public static void fill(fabric.lang.arrays.floatArray arg1,
        int arg2, int arg3, float arg4);

    native public static void fill(fabric.lang.arrays.doubleArray arg1,
        double arg2);

    native public static void fill(fabric.lang.arrays.doubleArray arg1,
        int arg2, int arg3, double arg4);

    native public static void fill(fabric.lang.arrays.ObjectArray arg1,
        fabric.lang.Object arg2);

    native public static void fill(fabric.lang.arrays.ObjectArray arg1,
        int arg2, int arg3, fabric.lang.Object arg4);

    native public static void sort(fabric.lang.arrays.byteArray arg1);

    native public static void sort(fabric.lang.arrays.byteArray arg1, int arg2,
        int arg3);

    native public static void sort(fabric.lang.arrays.charArray arg1);

    native public static void sort(fabric.lang.arrays.charArray arg1, int arg2,
        int arg3);

    native public static void sort(fabric.lang.arrays.shortArray arg1);

    native public static void sort(fabric.lang.arrays.shortArray arg1,
        int arg2, int arg3);

    native public static void sort(fabric.lang.arrays.intArray arg1);

    native public static void sort(fabric.lang.arrays.intArray arg1, int arg2,
        int arg3);

    native public static void sort(fabric.lang.arrays.longArray arg1);

    native public static void sort(fabric.lang.arrays.longArray arg1, int arg2,
        int arg3);

    native public static void sort(fabric.lang.arrays.floatArray arg1);

    native public static void sort(fabric.lang.arrays.floatArray arg1,
        int arg2, int arg3);

    native public static void sort(fabric.lang.arrays.doubleArray arg1);

    native public static void sort(fabric.lang.arrays.doubleArray arg1,
        int arg2, int arg3);

    native public static void sort(fabric.lang.arrays.ObjectArray arg1);

    native public static void sort(fabric.lang.arrays.ObjectArray arg1,
        fabric.util.Comparator arg2);

    native public static void sort(fabric.lang.arrays.ObjectArray arg1,
        int arg2, int arg3);

    native public static void sort(fabric.lang.arrays.ObjectArray arg1,
        int arg2, int arg3, fabric.util.Comparator arg4);

    native public static fabric.util.List asList(
        fabric.lang.arrays.ObjectArray arg1);

    native public static int hashCode(fabric.lang.arrays.longArray arg1);

    native public static int hashCode(fabric.lang.arrays.intArray arg1);

    native public static int hashCode(fabric.lang.arrays.shortArray arg1);

    native public static int hashCode(fabric.lang.arrays.charArray arg1);

    native public static int hashCode(fabric.lang.arrays.byteArray arg1);

    native public static int hashCode(fabric.lang.arrays.booleanArray arg1);

    native public static int hashCode(fabric.lang.arrays.floatArray arg1);

    native public static int hashCode(fabric.lang.arrays.doubleArray arg1);

    native public static int hashCode(fabric.lang.arrays.ObjectArray arg1);

    native public static int deepHashCode(fabric.lang.arrays.ObjectArray arg1);

    native public static boolean deepEquals(
        fabric.lang.arrays.ObjectArray arg1, fabric.lang.arrays.ObjectArray arg2);

    native public static java.lang.String toString(
        fabric.lang.arrays.booleanArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.byteArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.charArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.shortArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.intArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.longArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.floatArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.doubleArray arg1);

    native public static java.lang.String toString(
        fabric.lang.arrays.ObjectArray arg1);

    native public static java.lang.String deepToString(
        fabric.lang.arrays.ObjectArray arg1);

    native public static void arraycopy(fabric.lang.arrays.ObjectArray arg1,
        int arg2, fabric.lang.arrays.ObjectArray arg3, int arg4, int arg5);

    @Override
    native public fabric.lang.Object $initLabels();

    public _Proxy(Arrays._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.util.Arrays {

    private _Impl(fabric.worker.Store $location) {
      super($location);
    }

    native public static int binarySearch(fabric.lang.arrays.byteArray a,
        byte key);

    native public static int binarySearch(fabric.lang.arrays.charArray a,
        char key);

    native public static int binarySearch(fabric.lang.arrays.shortArray a,
        short key);

    native public static int binarySearch(fabric.lang.arrays.intArray a, int key);

    native public static int binarySearch(fabric.lang.arrays.longArray a,
        long key);

    native public static int binarySearch(fabric.lang.arrays.floatArray a,
        float key);

    native public static int binarySearch(fabric.lang.arrays.doubleArray a,
        double key);

    native public static int binarySearch(fabric.lang.arrays.ObjectArray a,
        fabric.lang.Object key);

    native public static int binarySearch(fabric.lang.arrays.ObjectArray a,
        fabric.lang.Object key, fabric.util.Comparator c);

    native public static boolean equals(fabric.lang.arrays.booleanArray a1,
        fabric.lang.arrays.booleanArray a2);

    native public static boolean equals(fabric.lang.arrays.byteArray a1,
        fabric.lang.arrays.byteArray a2);

    native public static boolean equals(fabric.lang.arrays.charArray a1,
        fabric.lang.arrays.charArray a2);

    native public static boolean equals(fabric.lang.arrays.shortArray a1,
        fabric.lang.arrays.shortArray a2);

    native public static boolean equals(fabric.lang.arrays.intArray a1,
        fabric.lang.arrays.intArray a2);

    native public static boolean equals(fabric.lang.arrays.longArray a1,
        fabric.lang.arrays.longArray a2);

    native public static boolean equals(fabric.lang.arrays.floatArray a1,
        fabric.lang.arrays.floatArray a2);

    native public static boolean equals(fabric.lang.arrays.doubleArray a1,
        fabric.lang.arrays.doubleArray a2);

    native public static boolean equals(fabric.lang.arrays.ObjectArray a1,
        fabric.lang.arrays.ObjectArray a2);

    native public static void fill(fabric.lang.arrays.booleanArray a,
        boolean val);

    native public static void fill(fabric.lang.arrays.booleanArray a,
        int fromIndex, int toIndex, boolean val);

    native public static void fill(fabric.lang.arrays.byteArray a, byte val);

    native public static void fill(fabric.lang.arrays.byteArray a,
        int fromIndex, int toIndex, byte val);

    native public static void fill(fabric.lang.arrays.charArray a, char val);

    native public static void fill(fabric.lang.arrays.charArray a,
        int fromIndex, int toIndex, char val);

    native public static void fill(fabric.lang.arrays.shortArray a, short val);

    native public static void fill(fabric.lang.arrays.shortArray a,
        int fromIndex, int toIndex, short val);

    native public static void fill(fabric.lang.arrays.intArray a, int val);

    native public static void fill(fabric.lang.arrays.intArray a,
        int fromIndex, int toIndex, int val);

    native public static void fill(fabric.lang.arrays.longArray a, long val);

    native public static void fill(fabric.lang.arrays.longArray a,
        int fromIndex, int toIndex, long val);

    native public static void fill(fabric.lang.arrays.floatArray a, float val);

    native public static void fill(fabric.lang.arrays.floatArray a,
        int fromIndex, int toIndex, float val);

    native public static void fill(fabric.lang.arrays.doubleArray a, double val);

    native public static void fill(fabric.lang.arrays.doubleArray a,
        int fromIndex, int toIndex, double val);

    native public static void fill(fabric.lang.arrays.ObjectArray a,
        fabric.lang.Object val);

    native public static void fill(fabric.lang.arrays.ObjectArray a,
        int fromIndex, int toIndex, fabric.lang.Object val);

    native public static void sort(fabric.lang.arrays.byteArray a);

    native public static void sort(fabric.lang.arrays.byteArray a,
        int fromIndex, int toIndex);

    native private static int med3(int a, int b, int c,
        fabric.lang.arrays.byteArray d);

    native private static void swap(int i, int j, fabric.lang.arrays.byteArray a);

    native private static void vecswap(int i, int j, int n,
        fabric.lang.arrays.byteArray a);

    native private static void qsort(fabric.lang.arrays.byteArray array,
        int from, int count);

    native public static void sort(fabric.lang.arrays.charArray a);

    native public static void sort(fabric.lang.arrays.charArray a,
        int fromIndex, int toIndex);

    native private static int med3(int a, int b, int c,
        fabric.lang.arrays.charArray d);

    native private static void swap(int i, int j, fabric.lang.arrays.charArray a);

    native private static void vecswap(int i, int j, int n,
        fabric.lang.arrays.charArray a);

    native private static void qsort(fabric.lang.arrays.charArray array,
        int from, int count);

    native public static void sort(fabric.lang.arrays.shortArray a);

    native public static void sort(fabric.lang.arrays.shortArray a,
        int fromIndex, int toIndex);

    native private static int med3(int a, int b, int c,
        fabric.lang.arrays.shortArray d);

    native private static void swap(int i, int j,
        fabric.lang.arrays.shortArray a);

    native private static void vecswap(int i, int j, int n,
        fabric.lang.arrays.shortArray a);

    native private static void qsort(fabric.lang.arrays.shortArray array,
        int from, int count);

    native public static void sort(fabric.lang.arrays.intArray a);

    native public static void sort(fabric.lang.arrays.intArray a,
        int fromIndex, int toIndex);

    native private static int med3(int a, int b, int c,
        fabric.lang.arrays.intArray d);

    native private static void swap(int i, int j, fabric.lang.arrays.intArray a);

    native private static void vecswap(int i, int j, int n,
        fabric.lang.arrays.intArray a);

    native private static int compare(int a, int b);

    native private static void qsort(fabric.lang.arrays.intArray array,
        int from, int count);

    native public static void sort(fabric.lang.arrays.longArray a);

    native public static void sort(fabric.lang.arrays.longArray a,
        int fromIndex, int toIndex);

    native private static int med3(int a, int b, int c,
        fabric.lang.arrays.longArray d);

    native private static void swap(int i, int j, fabric.lang.arrays.longArray a);

    native private static void vecswap(int i, int j, int n,
        fabric.lang.arrays.longArray a);

    native private static int compare(long a, long b);

    native private static void qsort(fabric.lang.arrays.longArray array,
        int from, int count);

    native public static void sort(fabric.lang.arrays.floatArray a);

    native public static void sort(fabric.lang.arrays.floatArray a,
        int fromIndex, int toIndex);

    native private static int med3(int a, int b, int c,
        fabric.lang.arrays.floatArray d);

    native private static void swap(int i, int j,
        fabric.lang.arrays.floatArray a);

    native private static void vecswap(int i, int j, int n,
        fabric.lang.arrays.floatArray a);

    native private static void qsort(fabric.lang.arrays.floatArray array,
        int from, int count);

    native public static void sort(fabric.lang.arrays.doubleArray a);

    native public static void sort(fabric.lang.arrays.doubleArray a,
        int fromIndex, int toIndex);

    native private static int med3(int a, int b, int c,
        fabric.lang.arrays.doubleArray d);

    native private static void swap(int i, int j,
        fabric.lang.arrays.doubleArray a);

    native private static void vecswap(int i, int j, int n,
        fabric.lang.arrays.doubleArray a);

    native private static void qsort(fabric.lang.arrays.doubleArray array,
        int from, int count);

    native public static void sort(fabric.lang.arrays.ObjectArray a);

    native public static void sort(fabric.lang.arrays.ObjectArray a,
        fabric.util.Comparator c);

    native public static void sort(fabric.lang.arrays.ObjectArray a,
        int fromIndex, int toIndex);

    native public static void sort(fabric.lang.arrays.ObjectArray a,
        int fromIndex, int toIndex, fabric.util.Comparator c);

    native public static fabric.util.List asList(
        final fabric.lang.arrays.ObjectArray a);

    native public static int hashCode(fabric.lang.arrays.longArray v);

    native public static int hashCode(fabric.lang.arrays.intArray v);

    native public static int hashCode(fabric.lang.arrays.shortArray v);

    native public static int hashCode(fabric.lang.arrays.charArray v);

    native public static int hashCode(fabric.lang.arrays.byteArray v);

    native public static int hashCode(fabric.lang.arrays.booleanArray v);

    native public static int hashCode(fabric.lang.arrays.floatArray v);

    native public static int hashCode(fabric.lang.arrays.doubleArray v);

    native public static int hashCode(fabric.lang.arrays.ObjectArray v);

    native public static int deepHashCode(fabric.lang.arrays.ObjectArray v);

    native public static boolean deepEquals(fabric.lang.arrays.ObjectArray v1,
        fabric.lang.arrays.ObjectArray v2);

    native public static java.lang.String toString(
        fabric.lang.arrays.booleanArray v);

    native public static java.lang.String toString(
        fabric.lang.arrays.byteArray v);

    native public static java.lang.String toString(
        fabric.lang.arrays.charArray v);

    native public static java.lang.String toString(
        fabric.lang.arrays.shortArray v);

    native public static java.lang.String toString(fabric.lang.arrays.intArray v);

    native public static java.lang.String toString(
        fabric.lang.arrays.longArray v);

    native public static java.lang.String toString(
        fabric.lang.arrays.floatArray v);

    native public static java.lang.String toString(
        fabric.lang.arrays.doubleArray v);

    native public static java.lang.String toString(
        fabric.lang.arrays.ObjectArray v);

    native private static void deepToString(fabric.lang.arrays.ObjectArray v,
        java.lang.StringBuilder b, fabric.util.HashSet seen);

    native public static java.lang.String deepToString(
        fabric.lang.arrays.ObjectArray v);

    native public static void arraycopy(fabric.lang.arrays.ObjectArray src,
        int srcPos, fabric.lang.arrays.ObjectArray dest, int destPos, int length);

    @Override
    native public fabric.lang.Object $initLabels();

    @Override
    native protected fabric.lang.Object._Proxy $makeProxy();

    @Override
    native public void $serialize(java.io.ObjectOutput out,
        java.util.List refTypes, java.util.List intraStoreRefs,
        java.util.List interStoreRefs) throws java.io.IOException;

    public _Impl(fabric.worker.Store store, long onum, int version,
        VersionWarranty warranty, RWLease lease, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, lease, label, accessLabel, in,
          refTypes, intraStoreRefs, interStoreRefs);
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Arrays._Static {

      public _Proxy(fabric.util.Arrays._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.Arrays._Static {

      public _Impl(fabric.worker.Store store)
          throws fabric.net.UnreachableNodeException {
        super(store);
      }

      @Override
      native protected fabric.lang.Object._Proxy $makeProxy();

      native private void $init();
    }

  }

}
