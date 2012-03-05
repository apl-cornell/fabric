package fabric.util;

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
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Random
    {
        
        native public boolean get$haveNextNextGaussian();
        
        native public boolean set$haveNextNextGaussian(boolean val);
        
        native public double get$nextNextGaussian();
        
        native public double set$nextNextGaussian(double val);
        
        native public double postInc$nextNextGaussian();
        
        native public double postDec$nextNextGaussian();
        
        native public long get$seed();
        
        native public long set$seed(long val);
        
        native public long postInc$seed();
        
        native public long postDec$seed();
        
        native public fabric.util.Random fabric$util$Random$();
        
        native public fabric.util.Random fabric$util$Random$(long arg1);
        
        native public synchronized void setSeed(long arg1);
        
        native public synchronized int next(int arg1);
        
        native public void nextBytes(fabric.lang.arrays.byteArray arg1);
        
        native public int nextInt();
        
        native public int nextInt(int arg1);
        
        native public long nextLong();
        
        native public boolean nextBoolean();
        
        native public float nextFloat();
        
        native public double nextDouble();
        
        native public synchronized double nextGaussian();
        
        native public fabric.lang.Object $initLabels();
        
        public _Proxy(Random._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Random
    {
        
        native public boolean get$haveNextNextGaussian();
        
        native public boolean set$haveNextNextGaussian(boolean val);
        
        native public double get$nextNextGaussian();
        
        native public double set$nextNextGaussian(double val);
        
        native public double postInc$nextNextGaussian();
        
        native public double postDec$nextNextGaussian();
        
        native public long get$seed();
        
        native public long set$seed(long val);
        
        native public long postInc$seed();
        
        native public long postDec$seed();
        
        native public fabric.util.Random fabric$util$Random$();
        
        native public fabric.util.Random fabric$util$Random$(long seed);
        
        native public synchronized void setSeed(long seed);
        
        native public synchronized int next(int bits);
        
        native public void nextBytes(fabric.lang.arrays.byteArray bytes);
        
        native public int nextInt();
        
        native public int nextInt(int n);
        
        native public long nextLong();
        
        native public boolean nextBoolean();
        
        native public float nextFloat();
        
        native public double nextDouble();
        
        native public synchronized double nextGaussian();
        
        native public fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, long accessLabel,
                     java.io.ObjectInput in, java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in,
                  refTypes, intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public long get$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Random._Static
        {
            
            native public long get$serialVersionUID();
            
            public _Proxy(fabric.util.Random._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Random._Static
        {
            
            native public long get$serialVersionUID();
            
            public _Impl(fabric.worker.Store store)
                  throws fabric.net.UnreachableNodeException {
                super(store);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
