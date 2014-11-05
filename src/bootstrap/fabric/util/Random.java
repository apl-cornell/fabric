package fabric.util;

import fabric.common.RWLease;
import fabric.common.VersionWarranty;

public interface Random extends java.io.Serializable, fabric.lang.Object {

  public boolean get$haveNextNextGaussian();

  public boolean set$haveNextNextGaussian(boolean val);

  public double get$nextNextGaussian();

  public double set$nextNextGaussian(double val);

  public double postInc$nextNextGaussian();

  public double postDec$nextNextGaussian();

  public long get$seed();

  public long set$seed(long val);

  public long postInc$seed();

  public long postDec$seed();

  public fabric.util.Random fabric$util$Random$();

  public fabric.util.Random fabric$util$Random$(long seed);

  public void setSeed(long seed);

  public int next(int bits);

  public void nextBytes(fabric.lang.arrays.byteArray bytes);

  public int nextInt();

  public int nextInt(int n);

  public long nextLong();

  public boolean nextBoolean();

  public float nextFloat();

  public double nextDouble();

  public double nextGaussian();

  @Override
  public fabric.lang.Object $initLabels();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Random {

    @Override
    native public boolean get$haveNextNextGaussian();

    @Override
    native public boolean set$haveNextNextGaussian(boolean val);

    @Override
    native public double get$nextNextGaussian();

    @Override
    native public double set$nextNextGaussian(double val);

    @Override
    native public double postInc$nextNextGaussian();

    @Override
    native public double postDec$nextNextGaussian();

    @Override
    native public long get$seed();

    @Override
    native public long set$seed(long val);

    @Override
    native public long postInc$seed();

    @Override
    native public long postDec$seed();

    @Override
    native public fabric.util.Random fabric$util$Random$();

    @Override
    native public fabric.util.Random fabric$util$Random$(long arg1);

    @Override
    native public synchronized void setSeed(long arg1);

    @Override
    native public synchronized int next(int arg1);

    @Override
    native public void nextBytes(fabric.lang.arrays.byteArray arg1);

    @Override
    native public int nextInt();

    @Override
    native public int nextInt(int arg1);

    @Override
    native public long nextLong();

    @Override
    native public boolean nextBoolean();

    @Override
    native public float nextFloat();

    @Override
    native public double nextDouble();

    @Override
    native public synchronized double nextGaussian();

    @Override
    native public fabric.lang.Object $initLabels();

    public _Proxy(Random._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      fabric.util.Random {

    @Override
    native public boolean get$haveNextNextGaussian();

    @Override
    native public boolean set$haveNextNextGaussian(boolean val);

    @Override
    native public double get$nextNextGaussian();

    @Override
    native public double set$nextNextGaussian(double val);

    @Override
    native public double postInc$nextNextGaussian();

    @Override
    native public double postDec$nextNextGaussian();

    @Override
    native public long get$seed();

    @Override
    native public long set$seed(long val);

    @Override
    native public long postInc$seed();

    @Override
    native public long postDec$seed();

    @Override
    native public fabric.util.Random fabric$util$Random$();

    @Override
    native public fabric.util.Random fabric$util$Random$(long seed);

    @Override
    native public synchronized void setSeed(long seed);

    @Override
    native public synchronized int next(int bits);

    @Override
    native public void nextBytes(fabric.lang.arrays.byteArray bytes);

    @Override
    native public int nextInt();

    @Override
    native public int nextInt(int n);

    @Override
    native public long nextLong();

    @Override
    native public boolean nextBoolean();

    @Override
    native public float nextFloat();

    @Override
    native public double nextDouble();

    @Override
    native public synchronized double nextGaussian();

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
        VersionWarranty warranty, RWLease lease, long label, long accessLabel,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, warranty, lease, label, accessLabel, in,
          refTypes, intraStoreRefs, interStoreRefs);
    }

    @Override
    native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
  }

  interface _Static extends fabric.lang.Object, Cloneable {

    public long get$serialVersionUID();

    final class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Random._Static {

      @Override
      native public long get$serialVersionUID();

      public _Proxy(fabric.util.Random._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

    class _Impl extends fabric.lang.Object._Impl implements
        fabric.util.Random._Static {

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
