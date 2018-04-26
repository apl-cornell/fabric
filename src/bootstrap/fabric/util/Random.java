package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.io.Serializable;

/**
 * This class generates pseudorandom numbers.  It uses the same
 * algorithm as the original JDK-class, so that your programs behave
 * exactly the same way, if started with the same seed.
 *
 * The algorithm is described in <em>The Art of Computer Programming,
 * Volume 2</em> by Donald Knuth in Section 3.2.1.  It is a 48-bit seed,
 * linear congruential formula.
 *
 * If two instances of this class are created with the same seed and
 * the same calls to these classes are made, they behave exactly the
 * same way.  This should be even true for foreign implementations
 * (like this), so every port must use the same algorithm as described
 * here.
 *
 * If you want to implement your own pseudorandom algorithm, you
 * should extend this class and overload the <code>next()</code> and
 * <code>setSeed(long)</code> method.  In that case the above
 * paragraph doesn't apply to you.
 *
 * This class shouldn't be used for security sensitive purposes (like
 * generating passwords or encryption keys.  See <code>SecureRandom</code>
 * in package <code>java.security</code> for this purpose.
 *
 * For simple random doubles between 0.0 and 1.0, you may consider using
 * Math.random instead.
 *
 * @see java.security.SecureRandom
 * @see Math#random()
 * @author Jochen Hoenicke
 * @author Eric Blake (ebb9@email.byu.edu)
 * @status updated to 1.4
 */
public interface Random
  extends java.io.Serializable, fabric.lang.Object
{
    
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
    
    /**
     * Creates a new pseudorandom number generator.  The seed is initialized
     * to the current time, as if by
     * <code>setSeed(System.currentTimeMillis());</code>.
     *
     * @see System#currentTimeMillis()
     */
    public fabric.util.Random fabric$util$Random$();
    
    /**
     * Creates a new pseudorandom number generator, starting with the
     * specified seed, using <code>setSeed(seed);</code>.
     *
     * @param seed the initial seed
     */
    public fabric.util.Random fabric$util$Random$(long seed);
    
    /**
     * Sets the seed for this pseudorandom number generator.  As described
     * above, two instances of the same random class, starting with the
     * same seed, should produce the same results, if the same methods
     * are called.  The implementation for java.util.Random is:
     *
     <pre>public synchronized void setSeed(long seed)
     {
     this.seed = (seed ^ 0x5DEECE66DL) & ((1L &lt;&lt; 48) - 1);
     haveNextNextGaussian = false;
     }</pre>
     *
     * @param seed the new seed
     */
    public void setSeed(long seed);
    
    /**
     * Generates the next pseudorandom number.  This returns
     * an int value whose <code>bits</code> low order bits are
     * independent chosen random bits (0 and 1 are equally likely).
     * The implementation for java.util.Random is:
     *
     <pre>protected synchronized int next(int bits)
     {
     seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L &lt;&lt; 48) - 1);
     return (int) (seed &gt;&gt;&gt; (48 - bits));
     }</pre>
     *
     * @param bits the number of random bits to generate, in the range 1..32
     * @return the next pseudorandom value
     * @since 1.1
     */
    public int next(int bits);
    
    /**
     * Fills an array of bytes with random numbers.  All possible values
     * are (approximately) equally likely.
     * The JDK documentation gives no implementation, but it seems to be:
     *
     <pre>public void nextBytes(byte[] bytes)
     {
     for (int i = 0; i &lt; bytes.length; i += 4)
     {
     int random = next(32);
     for (int j = 0; i + j &lt; bytes.length && j &lt; 4; j++)
     {
     bytes[i+j] = (byte) (random & 0xff)
     random &gt;&gt;= 8;
     }
     }
     }</pre>
     *
     * @param bytes the byte array that should be filled
     * @throws NullPointerException if bytes is null
     * @since 1.1
     */
    public void nextBytes(fabric.lang.arrays.byteArray bytes);
    
    /**
     * Generates the next pseudorandom number.  This returns
     * an int value whose 32 bits are independent chosen random bits
     * (0 and 1 are equally likely).  The implementation for
     * java.util.Random is:
     * 
     <pre>public int nextInt()
     {
     return next(32);
     }</pre>
     *
     * @return the next pseudorandom value
     */
    public int nextInt();
    
    /**
     * Generates the next pseudorandom number.  This returns
     * a value between 0(inclusive) and <code>n</code>(exclusive), and
     * each value has the same likelihodd (1/<code>n</code>).
     * (0 and 1 are equally likely).  The implementation for
     * java.util.Random is:
     * 
     <pre>
     public int nextInt(int n)
     {
     if (n &lt;= 0)
     throw new IllegalArgumentException("n must be positive");
    
     if ((n & -n) == n)  // i.e., n is a power of 2
     return (int)((n * (long) next(31)) &gt;&gt; 31);
    
     int bits, val;
     do
     {
     bits = next(31);
     val = bits % n;
     }
     while(bits - val + (n-1) &lt; 0);
    
     return val;
     }</pre>
     *   
     * <p>This algorithm would return every value with exactly the same
     * probability, if the next()-method would be a perfect random number
     * generator.
     *
     * The loop at the bottom only accepts a value, if the random
     * number was between 0 and the highest number less then 1<<31,
     * which is divisible by n.  The probability for this is high for small
     * n, and the worst case is 1/2 (for n=(1<<30)+1).
     *
     * The special treatment for n = power of 2, selects the high bits of
     * the random number (the loop at the bottom would select the low order
     * bits).  This is done, because the low order bits of linear congruential
     * number generators (like the one used in this class) are known to be
     * ``less random'' than the high order bits.
     *
     * @param n the upper bound
     * @throws IllegalArgumentException if the given upper bound is negative
     * @return the next pseudorandom value
     * @since 1.2
     */
    public int nextInt(int n);
    
    /**
     * Generates the next pseudorandom long number.  All bits of this
     * long are independently chosen and 0 and 1 have equal likelihood.
     * The implementation for java.util.Random is:
     *
     <pre>public long nextLong()
     {
     return ((long) next(32) &lt;&lt; 32) + next(32);
     }</pre>
     *
     * @return the next pseudorandom value
     */
    public long nextLong();
    
    /**
     * Generates the next pseudorandom boolean.  True and false have
     * the same probability.  The implementation is:
     * 
     <pre>public boolean nextBoolean()
     {
     return next(1) != 0;
     }</pre>
     *
     * @return the next pseudorandom boolean
     * @since 1.2
     */
    public boolean nextBoolean();
    
    /**
     * Generates the next pseudorandom float uniformly distributed
     * between 0.0f (inclusive) and 1.0f (exclusive).  The
     * implementation is as follows.
     * 
     <pre>public float nextFloat()
     {
     return next(24) / ((float)(1 &lt;&lt; 24));
     }</pre>
     *
     * @return the next pseudorandom float
     */
    public float nextFloat();
    
    /**
     * Generates the next pseudorandom double uniformly distributed
     * between 0.0 (inclusive) and 1.0 (exclusive).  The
     * implementation is as follows.
     *
     <pre>public double nextDouble()
     {
     return (((long) next(26) &lt;&lt; 27) + next(27)) / (double)(1L &lt;&lt;
     53);
     }</pre>
     *
     * @return the next pseudorandom double
     */
    public double nextDouble();
    
    /**
     * Generates the next pseudorandom, Gaussian (normally) distributed
     * double value, with mean 0.0 and standard deviation 1.0.
     * The algorithm is as follows.
     * 
     <pre>public synchronized double nextGaussian()
     {
     if (haveNextNextGaussian)
     {
     haveNextNextGaussian = false;
     return nextNextGaussian;
     }
     else
     {
     double v1, v2, s;
     do
     {
     v1 = 2 * nextDouble() - 1; // between -1.0 and 1.0
     v2 = 2 * nextDouble() - 1; // between -1.0 and 1.0
     s = v1 * v1 + v2 * v2;
     }
     while (s >= 1);
    
     double norm = Math.sqrt(-2 * Math.log(s) / s);
     nextNextGaussian = v2 * norm;
     haveNextNextGaussian = true;
     return v1 * norm;
     }
     }</pre>
     *
     * <p>This is described in section 3.4.1 of <em>The Art of Computer
     * Programming, Volume 2</em> by Donald Knuth.
     *
     * @return the next pseudorandom Gaussian distributed double
     */
    public double nextGaussian();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.util.Random
    {
        
        public boolean get$haveNextNextGaussian() {
            return ((fabric.util.Random._Impl) fetch()).
              get$haveNextNextGaussian();
        }
        
        public boolean set$haveNextNextGaussian(boolean val) {
            return ((fabric.util.Random._Impl) fetch()).
              set$haveNextNextGaussian(val);
        }
        
        public double get$nextNextGaussian() {
            return ((fabric.util.Random._Impl) fetch()).get$nextNextGaussian();
        }
        
        public double set$nextNextGaussian(double val) {
            return ((fabric.util.Random._Impl) fetch()).set$nextNextGaussian(
                                                          val);
        }
        
        public double postInc$nextNextGaussian() {
            return ((fabric.util.Random._Impl) fetch()).
              postInc$nextNextGaussian();
        }
        
        public double postDec$nextNextGaussian() {
            return ((fabric.util.Random._Impl) fetch()).
              postDec$nextNextGaussian();
        }
        
        public long get$seed() {
            return ((fabric.util.Random._Impl) fetch()).get$seed();
        }
        
        public long set$seed(long val) {
            return ((fabric.util.Random._Impl) fetch()).set$seed(val);
        }
        
        public long postInc$seed() {
            return ((fabric.util.Random._Impl) fetch()).postInc$seed();
        }
        
        public long postDec$seed() {
            return ((fabric.util.Random._Impl) fetch()).postDec$seed();
        }
        
        public native fabric.util.Random fabric$util$Random$();
        
        public native fabric.util.Random fabric$util$Random$(long arg1);
        
        public synchronized native void setSeed(long arg1);
        
        public synchronized native int next(int arg1);
        
        public native void nextBytes(fabric.lang.arrays.byteArray arg1);
        
        public native int nextInt();
        
        public native int nextInt(int arg1);
        
        public native long nextLong();
        
        public native boolean nextBoolean();
        
        public native float nextFloat();
        
        public native double nextDouble();
        
        public synchronized native double nextGaussian();
        
        public _Proxy(Random._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Random
    {
        
        public boolean get$haveNextNextGaussian() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.haveNextNextGaussian;
        }
        
        public boolean set$haveNextNextGaussian(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.haveNextNextGaussian = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * True if the next nextGaussian is available.  This is used by
         * nextGaussian, which generates two gaussian numbers by one call,
         * and returns the second on the second call.
         *
         * @serial whether nextNextGaussian is available
         * @see #nextGaussian()
         * @see #nextNextGaussian
         */
        private boolean haveNextNextGaussian;
        
        public double get$nextNextGaussian() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.nextNextGaussian;
        }
        
        public double set$nextNextGaussian(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.nextNextGaussian = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$nextNextGaussian() {
            double tmp = this.get$nextNextGaussian();
            this.set$nextNextGaussian((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$nextNextGaussian() {
            double tmp = this.get$nextNextGaussian();
            this.set$nextNextGaussian((double) (tmp - 1));
            return tmp;
        }
        
        /**
         * The next nextGaussian, when available.  This is used by nextGaussian,
         * which generates two gaussian numbers by one call, and returns the
         * second on the second call.
         *
         * @serial the second gaussian of a pair
         * @see #nextGaussian()
         * @see #haveNextNextGaussian
         */
        private double nextNextGaussian;
        
        public long get$seed() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.seed;
        }
        
        public long set$seed(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.seed = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$seed() {
            long tmp = this.get$seed();
            this.set$seed((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$seed() {
            long tmp = this.get$seed();
            this.set$seed((long) (tmp - 1));
            return tmp;
        }
        
        /**
         * The seed.  This is the number set by setSeed and which is used
         * in next.
         *
         * @serial the internal state of this generator
         * @see #next(int)
         */
        private long seed;
        
        /**
         * Creates a new pseudorandom number generator.  The seed is initialized
         * to the current time, as if by
         * <code>setSeed(System.currentTimeMillis());</code>.
         *
         * @see System#currentTimeMillis()
         */
        public native fabric.util.Random fabric$util$Random$();
        
        /**
         * Creates a new pseudorandom number generator, starting with the
         * specified seed, using <code>setSeed(seed);</code>.
         *
         * @param seed the initial seed
         */
        public native fabric.util.Random fabric$util$Random$(long seed);
        
        /**
         * Sets the seed for this pseudorandom number generator.  As described
         * above, two instances of the same random class, starting with the
         * same seed, should produce the same results, if the same methods
         * are called.  The implementation for java.util.Random is:
         *
         <pre>public synchronized void setSeed(long seed)
         {
         this.seed = (seed ^ 0x5DEECE66DL) & ((1L &lt;&lt; 48) - 1);
         haveNextNextGaussian = false;
         }</pre>
         *
         * @param seed the new seed
         */
        public synchronized native void setSeed(long seed);
        
        /**
         * Generates the next pseudorandom number.  This returns
         * an int value whose <code>bits</code> low order bits are
         * independent chosen random bits (0 and 1 are equally likely).
         * The implementation for java.util.Random is:
         *
         <pre>protected synchronized int next(int bits)
         {
         seed = (seed * 0x5DEECE66DL + 0xBL) & ((1L &lt;&lt; 48) - 1);
         return (int) (seed &gt;&gt;&gt; (48 - bits));
         }</pre>
         *
         * @param bits the number of random bits to generate, in the range 1..32
         * @return the next pseudorandom value
         * @since 1.1
         */
        public synchronized native int next(int bits);
        
        /**
         * Fills an array of bytes with random numbers.  All possible values
         * are (approximately) equally likely.
         * The JDK documentation gives no implementation, but it seems to be:
         *
         <pre>public void nextBytes(byte[] bytes)
         {
         for (int i = 0; i &lt; bytes.length; i += 4)
         {
         int random = next(32);
         for (int j = 0; i + j &lt; bytes.length && j &lt; 4; j++)
         {
         bytes[i+j] = (byte) (random & 0xff)
         random &gt;&gt;= 8;
         }
         }
         }</pre>
         *
         * @param bytes the byte array that should be filled
         * @throws NullPointerException if bytes is null
         * @since 1.1
         */
        public native void nextBytes(fabric.lang.arrays.byteArray bytes);
        
        /**
         * Generates the next pseudorandom number.  This returns
         * an int value whose 32 bits are independent chosen random bits
         * (0 and 1 are equally likely).  The implementation for
         * java.util.Random is:
         * 
         <pre>public int nextInt()
         {
         return next(32);
         }</pre>
         *
         * @return the next pseudorandom value
         */
        public native int nextInt();
        
        /**
         * Generates the next pseudorandom number.  This returns
         * a value between 0(inclusive) and <code>n</code>(exclusive), and
         * each value has the same likelihodd (1/<code>n</code>).
         * (0 and 1 are equally likely).  The implementation for
         * java.util.Random is:
         * 
         <pre>
         public int nextInt(int n)
         {
         if (n &lt;= 0)
         throw new IllegalArgumentException("n must be positive");
        
         if ((n & -n) == n)  // i.e., n is a power of 2
         return (int)((n * (long) next(31)) &gt;&gt; 31);
        
         int bits, val;
         do
         {
         bits = next(31);
         val = bits % n;
         }
         while(bits - val + (n-1) &lt; 0);
        
         return val;
         }</pre>
         *   
         * <p>This algorithm would return every value with exactly the same
         * probability, if the next()-method would be a perfect random number
         * generator.
         *
         * The loop at the bottom only accepts a value, if the random
         * number was between 0 and the highest number less then 1<<31,
         * which is divisible by n.  The probability for this is high for small
         * n, and the worst case is 1/2 (for n=(1<<30)+1).
         *
         * The special treatment for n = power of 2, selects the high bits of
         * the random number (the loop at the bottom would select the low order
         * bits).  This is done, because the low order bits of linear
         congruential
         * number generators (like the one used in this class) are known to be
         * ``less random'' than the high order bits.
         *
         * @param n the upper bound
         * @throws IllegalArgumentException if the given upper bound is negative
         * @return the next pseudorandom value
         * @since 1.2
         */
        public native int nextInt(int n);
        
        /**
         * Generates the next pseudorandom long number.  All bits of this
         * long are independently chosen and 0 and 1 have equal likelihood.
         * The implementation for java.util.Random is:
         *
         <pre>public long nextLong()
         {
         return ((long) next(32) &lt;&lt; 32) + next(32);
         }</pre>
         *
         * @return the next pseudorandom value
         */
        public native long nextLong();
        
        /**
         * Generates the next pseudorandom boolean.  True and false have
         * the same probability.  The implementation is:
         * 
         <pre>public boolean nextBoolean()
         {
         return next(1) != 0;
         }</pre>
         *
         * @return the next pseudorandom boolean
         * @since 1.2
         */
        public native boolean nextBoolean();
        
        /**
         * Generates the next pseudorandom float uniformly distributed
         * between 0.0f (inclusive) and 1.0f (exclusive).  The
         * implementation is as follows.
         * 
         <pre>public float nextFloat()
         {
         return next(24) / ((float)(1 &lt;&lt; 24));
         }</pre>
         *
         * @return the next pseudorandom float
         */
        public native float nextFloat();
        
        /**
         * Generates the next pseudorandom double uniformly distributed
         * between 0.0 (inclusive) and 1.0 (exclusive).  The
         * implementation is as follows.
         *
         <pre>public double nextDouble()
         {
         return (((long) next(26) &lt;&lt; 27) + next(27)) / (double)(1L
         &lt;&lt; 53);
         }</pre>
         *
         * @return the next pseudorandom double
         */
        public native double nextDouble();
        
        /**
         * Generates the next pseudorandom, Gaussian (normally) distributed
         * double value, with mean 0.0 and standard deviation 1.0.
         * The algorithm is as follows.
         * 
         <pre>public synchronized double nextGaussian()
         {
         if (haveNextNextGaussian)
         {
         haveNextNextGaussian = false;
         return nextNextGaussian;
         }
         else
         {
         double v1, v2, s;
         do
         {
         v1 = 2 * nextDouble() - 1; // between -1.0 and 1.0
         v2 = 2 * nextDouble() - 1; // between -1.0 and 1.0
         s = v1 * v1 + v2 * v2;
         }
         while (s >= 1);
        
         double norm = Math.sqrt(-2 * Math.log(s) / s);
         nextNextGaussian = v2 * norm;
         haveNextNextGaussian = true;
         return v1 * norm;
         }
         }</pre>
         *
         * <p>This is described in section 3.4.1 of <em>The Art of Computer
         * Programming, Volume 2</em> by Donald Knuth.
         *
         * @return the next pseudorandom Gaussian distributed double
         */
        public synchronized native double nextGaussian();
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.util.Random._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.haveNextNextGaussian);
            out.writeDouble(this.nextNextGaussian);
            out.writeLong(this.seed);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.haveNextNextGaussian = in.readBoolean();
            this.nextNextGaussian = in.readDouble();
            this.seed = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.util.Random._Impl src = (fabric.util.Random._Impl) other;
            this.haveNextNextGaussian = src.haveNextNextGaussian;
            this.nextNextGaussian = src.nextNextGaussian;
            this.seed = src.seed;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public long get$serialVersionUID();
        
        public long set$serialVersionUID(long val);
        
        public long postInc$serialVersionUID();
        
        public long postDec$serialVersionUID();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.util.Random._Static
        {
            
            public long get$serialVersionUID() {
                return ((fabric.util.Random._Static._Impl) fetch()).
                  get$serialVersionUID();
            }
            
            public long set$serialVersionUID(long val) {
                return ((fabric.util.Random._Static._Impl) fetch()).
                  set$serialVersionUID(val);
            }
            
            public long postInc$serialVersionUID() {
                return ((fabric.util.Random._Static._Impl) fetch()).
                  postInc$serialVersionUID();
            }
            
            public long postDec$serialVersionUID() {
                return ((fabric.util.Random._Static._Impl) fetch()).
                  postDec$serialVersionUID();
            }
            
            public _Proxy(fabric.util.Random._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.util.Random._Static $instance;
            
            static {
                fabric.
                  util.
                  Random.
                  _Static.
                  _Impl impl =
                  (fabric.util.Random._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(fabric.util.Random._Static._Impl.class);
                $instance = (fabric.util.Random._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Random._Static
        {
            
            public long get$serialVersionUID() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.serialVersionUID;
            }
            
            public long set$serialVersionUID(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.serialVersionUID = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$serialVersionUID() {
                long tmp = this.get$serialVersionUID();
                this.set$serialVersionUID((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$serialVersionUID() {
                long tmp = this.get$serialVersionUID();
                this.set$serialVersionUID((long) (tmp - 1));
                return tmp;
            }
            
            /**
             * Compatible with JDK 1.0+.
             */
            private long serialVersionUID;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.serialVersionUID);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Random._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
