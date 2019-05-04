package fabric.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
  return (((long) next(26) &lt;&lt; 27) + next(27)) / (double)(1L &lt;&lt; 53);
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
      implements fabric.util.Random {
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
        
        public fabric.util.Random fabric$util$Random$() {
            return ((fabric.util.Random) fetch()).fabric$util$Random$();
        }
        
        public fabric.util.Random fabric$util$Random$(long arg1) {
            return ((fabric.util.Random) fetch()).fabric$util$Random$(arg1);
        }
        
        public synchronized void setSeed(long arg1) {
            ((fabric.util.Random) fetch()).setSeed(arg1);
        }
        
        public synchronized int next(int arg1) {
            return ((fabric.util.Random) fetch()).next(arg1);
        }
        
        public void nextBytes(fabric.lang.arrays.byteArray arg1) {
            ((fabric.util.Random) fetch()).nextBytes(arg1);
        }
        
        public int nextInt() {
            return ((fabric.util.Random) fetch()).nextInt();
        }
        
        public int nextInt(int arg1) {
            return ((fabric.util.Random) fetch()).nextInt(arg1);
        }
        
        public long nextLong() {
            return ((fabric.util.Random) fetch()).nextLong();
        }
        
        public boolean nextBoolean() {
            return ((fabric.util.Random) fetch()).nextBoolean();
        }
        
        public float nextFloat() {
            return ((fabric.util.Random) fetch()).nextFloat();
        }
        
        public double nextDouble() {
            return ((fabric.util.Random) fetch()).nextDouble();
        }
        
        public synchronized double nextGaussian() {
            return ((fabric.util.Random) fetch()).nextGaussian();
        }
        
        public _Proxy(Random._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.util.Random {
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
        public fabric.util.Random fabric$util$Random$() {
            fabric$util$Random$(java.lang.System.currentTimeMillis());
            return (fabric.util.Random) this.$getProxy();
        }
        
        /**
   * Creates a new pseudorandom number generator, starting with the
   * specified seed, using <code>setSeed(seed);</code>.
   *
   * @param seed the initial seed
   */
        public fabric.util.Random fabric$util$Random$(long seed) {
            fabric$lang$Object$();
            setSeed(seed);
            return (fabric.util.Random) this.$getProxy();
        }
        
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
        public synchronized void setSeed(long seed) {
            this.set$seed((long) ((seed ^ 25214903917L) & (1L << 48) - 1));
            this.set$haveNextNextGaussian(false);
        }
        
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
        public synchronized int next(int bits) {
            this.set$seed((long)
                            (this.get$seed() * 25214903917L + 11L & (1L << 48) -
                               1));
            return (int) (this.get$seed() >>> 48 - bits);
        }
        
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
        public void nextBytes(fabric.lang.arrays.byteArray bytes) {
            int random;
            int max = bytes.get$length() & ~3;
            for (int i = 0; i < max; i += 4) {
                random = next(32);
                bytes.set(i, (byte) random);
                bytes.set(i + 1, (byte) (random >> 8));
                bytes.set(i + 2, (byte) (random >> 16));
                bytes.set(i + 3, (byte) (random >> 24));
            }
            if (max < bytes.get$length()) {
                random = next(32);
                for (int j = max; j < bytes.get$length(); j++) {
                    bytes.set(j, (byte) random);
                    random >>= 8;
                }
            }
        }
        
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
        public int nextInt() { return next(32); }
        
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
        public int nextInt(int n) {
            if (n <= 0)
                throw new java.lang.IllegalArgumentException(
                        "n must be positive");
            if ((n & -n) == n) return (int) (n * (long) next(31) >> 31);
            int bits;
            int val;
            do  {
                bits = next(31);
                val = bits % n;
            }while(bits - val + (n - 1) < 0); 
            return val;
        }
        
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
        public long nextLong() { return ((long) next(32) << 32) + next(32); }
        
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
        public boolean nextBoolean() { return next(1) != 0; }
        
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
        public float nextFloat() { return next(24) / (float) (1 << 24); }
        
        /**
   * Generates the next pseudorandom double uniformly distributed
   * between 0.0 (inclusive) and 1.0 (exclusive).  The
   * implementation is as follows.
   *
<pre>public double nextDouble()
{
  return (((long) next(26) &lt;&lt; 27) + next(27)) / (double)(1L &lt;&lt; 53);
}</pre>
   *
   * @return the next pseudorandom double
   */
        public double nextDouble() {
            return (((long) next(26) << 27) + next(27)) / (double) (1L << 53);
        }
        
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
        public synchronized double nextGaussian() {
            if (this.get$haveNextNextGaussian()) {
                this.set$haveNextNextGaussian(false);
                return this.get$nextNextGaussian();
            }
            double v1;
            double v2;
            double s;
            do  {
                v1 = 2 * nextDouble() - 1;
                v2 = 2 * nextDouble() - 1;
                s = v1 * v1 + v2 * v2;
            }while(s >= 1); 
            double norm = java.lang.Math.sqrt(-2 * java.lang.Math.log(s) / s);
            this.set$nextNextGaussian((double) (v2 * norm));
            this.set$haveNextNextGaussian(true);
            return v1 * norm;
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.util.Random) this.$getProxy();
        }
        
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
          implements fabric.util.Random._Static {
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
                  _Impl
                  impl =
                  (fabric.util.Random._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(fabric.util.Random._Static._Impl.class);
                $instance = (fabric.util.Random._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.util.Random._Static {
            public long get$serialVersionUID() { return this.serialVersionUID; }
            
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.serialVersionUID = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.util.Random._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm819 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled822 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff820 = 1;
                        boolean $doBackoff821 = true;
                        boolean $retry815 = true;
                        boolean $keepReads816 = false;
                        $label813: for (boolean $commit814 = false; !$commit814;
                                        ) {
                            if ($backoffEnabled822) {
                                if ($doBackoff821) {
                                    if ($backoff820 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff820));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e817) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff820 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff820 =
                                          java.lang.Math.
                                            min(
                                              $backoff820 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff821 = $backoff820 <= 32 ||
                                                  !$doBackoff821;
                            }
                            $commit814 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.util.Random._Static._Proxy.$instance.
                                  set$serialVersionUID((long)
                                                         3905348978240129619L);
                            }
                            catch (final fabric.worker.RetryException $e817) {
                                $commit814 = false;
                                continue $label813;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e817) {
                                $commit814 = false;
                                $retry815 = false;
                                $keepReads816 = $e817.keepReads;
                                fabric.common.TransactionID $currentTid818 =
                                  $tm819.getCurrentTid();
                                if ($e817.tid ==
                                      null ||
                                      !$e817.tid.isDescendantOf(
                                                   $currentTid818)) {
                                    throw $e817;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e817);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e817) {
                                $commit814 = false;
                                fabric.common.TransactionID $currentTid818 =
                                  $tm819.getCurrentTid();
                                if ($e817.tid.isDescendantOf($currentTid818))
                                    continue $label813;
                                if ($currentTid818.parent != null) {
                                    $retry815 = false;
                                    throw $e817;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e817) {
                                $commit814 = false;
                                $retry815 = false;
                                if ($tm819.inNestedTxn()) {
                                    $keepReads816 = true;
                                }
                                throw $e817;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid818 =
                                  $tm819.getCurrentTid();
                                if ($commit814) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e817) {
                                        $commit814 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e817) {
                                        $commit814 = false;
                                        $retry815 = false;
                                        $keepReads816 = $e817.keepReads;
                                        if ($e817.tid ==
                                              null ||
                                              !$e817.tid.isDescendantOf(
                                                           $currentTid818))
                                            throw $e817;
                                        throw new fabric.worker.
                                                UserAbortException($e817);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e817) {
                                        $commit814 = false;
                                        $currentTid818 = $tm819.getCurrentTid();
                                        if ($currentTid818 != null) {
                                            if ($e817.tid.equals(
                                                            $currentTid818) ||
                                                  !$e817.tid.
                                                  isDescendantOf(
                                                    $currentTid818)) {
                                                throw $e817;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm819.inNestedTxn() &&
                                          $tm819.checkForStaleObjects()) {
                                        $retry815 = true;
                                        $keepReads816 = false;
                                    }
                                    if ($keepReads816) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e817) {
                                            $currentTid818 = $tm819.getCurrentTid();
                                            if ($currentTid818 != null &&
                                                  ($e817.tid.equals($currentTid818) || !$e817.tid.isDescendantOf($currentTid818))) {
                                                throw $e817;
                                            } else {
                                                $retry815 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit814) {
                                    {  }
                                    if ($retry815) { continue $label813; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -28, -73, -3, -43, 126,
    45, 83, 87, 67, 118, -118, 88, 117, -91, 104, 25, -85, 78, -88, 71, 11,
    -115, -9, -15, 60, 102, 19, -107, -69, 94, -25, 110 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0ZC2wUx3XufD7/sbExYIONMRdafnclragSAyocGF9ygIUxTY2K2dubszfe273s7plzGhCtUkFBddXWEBIRpKikSYhLpDQoVQlq+kkKpYIm6Yekn1AVRFKgLaqaoKpp+t7M3G+9d9hSEfPeeGbezHtv3m/2xm+SUtMg7TEpoqh+ayRBTX+nFAmFuyXDpNGgKpnmVhjtl6s8ocPvPRNtdRN3mFTLkqZriiyp/ZppkWnhB6VhKaBRK9C7JdSxnVTISNglmYMWcW9fmzJIW0JXRwZU3RKHTNj/0JLA2GM76l4sIbV9pFbReizJUuSgrlk0ZfWR6jiNR6hhrolGabSPTNcojfZQQ5FU5WFYqGt9pN5UBjTJShrU3EJNXR3GhfVmMkENdmZ6ENnXgW0jKVu6AezXcfaTlqIGwoppdYSJN6ZQNWo+RPYQT5iUxlRpABbODKelCLAdA504DssrFWDTiEkyTZN4hhQtapF5doqMxL77YQGQlsWpNahnjvJoEgyQes6SKmkDgR7LULQBWFqqJ+EUizQX3BQWlSckeUgaoP0WmW1f182nYFUFUwuSWKTRvoztBHfWbLuznNu6uWnl6Je0Ls1NXMBzlMoq8l8ORK02oi00Rg2qyZQTVi8OH5ZmntnvJgQWN9oW8zUvP3Lrc0tbXz3L18xxWLM58iCVrX75eGTaG3ODi+4pQTbKE7qpoCnkSc5utVvMdKQSYO0zMzvipD89+eqW17+w9wS97iaVIeKVdTUZB6uaLuvxhKJSYwPVqCFZNBoiFVSLBtl8iJRBP6xolI9ujsVMaoWIR2VDXp39DSqKwRaoojLoK1pMT/cTkjXI+qkEIaQMGnHB/zFCllyD/kJCSg9apDMwqMdpIKIm6S4w7wA0KhnyYAD81lDkZbKeGAmYhhwwkpqlwEo+zoXfImlRPe4HDhL/t51SyHPdLpcL1DlP1qM0IplwN8JO1nar4ApduhqlRr+sjp4JkYYzjzNbqUD7NsFGmTZccL9z7ZEhl3YsuXb9rZP957mdIa1QFrgI54zfIecMmKlGv/FDJPJDJBp3pfzBY6HnmXl4TeZHGfpqoL83oUpWTDfiKeJyMWFmMHq2J9zqEEQLCAjVi3q+eN/O/e0lYJCJXR68I1jqs7tHNqiEoCeBzffLtfve++CFw7v1rKNYxDfBfydSov+12zVj6DKNQnzLbr+4TTrVf2a3z42xowLCmiWB4UGMaLWfkeeHHemYhtooDZMq1IGk4lQ6EFVag4a+KzvCbnwagnp++agsG4MsHK7qSTx56cL7n2aJIh05a3NCbA+1OnK8FTerZX45Pav7rQalsO6PR7q/fejmvu1M8bBigdOBPoRB8FIJ3FM3vnr2obff/dPxX7uzl2URbyIZURU5xWSZ/jH8c0H7LzZ0ORxADFYVFO7elvH3BJ68MMsbeL4K0QdYN329WlyPKjFFiqgULeU/tXctP3VjtI5ftwojXHkGWXrnDbLjTWvJ3vM7Pmxl27hkzDxZ/WWX8XDWkN15jWFII8hH6stvtjz+c+lJsHwIRqbyMGXxhTB9EHaBdzNdLGNwuW3uMwjaubbmsnGPOTG0d2KOzNpiX2D8aHNw9XXu5xlbxD3mO/j5NinHTe4+Ef+Xu937mpuU9ZE6lp4lzdomQZgCM+iDBGsGxWCY1OTN5ydLnhk6Mr421+4HOcfavSAbX6CPq7FfyQ2fGw4oohKV9AlooLOySo69N3G2IYFwRspFWOdeRrKAwYUIFqWNsSxhKMNgWanMpm7ctEJsdkPgqzmbWmTGoDRMN0EthG2DlDRNRdIcLqXbUOLgV8Mi39L9Ywc+9o+OcYPkRcmCCXVBLg0vTJjUNQiWpOCU+cVOYRSd117YffrZ3ft40q7PT7HrtWT8e7/96Jf+I5fPOYTysoiuq1TSeFxBuCJf3+3QVhJSvk3g1Q767uL6RrBqomKRapXAK/IUW6fZlYoTa4ToiNZB/IjqEEBoQQZnQFsPJ80UuNqBwe6iDCJVlcCePAY9JtS6Tkx5VJ3nMztLs3DTddDug832CdznwNIDzjZagt3VFqZMLMItUqHE40kLYxSziyWgNZMV39ugJIdA1Btah+O9jJlUAcPH7uKszbN/XlHjHBD40Rwmc6KPi/UbLZGeFd2fqf2BJzbZBFxiRld1eJWk0GZbCpWvzF6Pf2XsWHTz08vdIuStB3pLTyxT6TBVc46uQeuf8DzayIr2bPy6fL3lnuDQ1QFu/fNsJ9tXP7dx/NyGhfK33KQkE6gmvBTyiTryw1OlQeGho23NC1JtGd2iQZG7oPWAfR4QWMs1gKzZMIPcmW+Q5YIkLvCA/Vqc04ZZZC6JAErzBl62+TAu+HjZ5ssyM5Qvgh9aPyFVLwn8jamJgCSjAu8vLIIra5+9bNdHisixB8GuycuBEYEsY7UFmebluObvBeSw+4yb+QzUquaIJkM9pkEij9rSRqPY828C/34Kgu4rIujXEOyF4AwPmJ5CEWhYV6I2gZtwhyXQwO7qXhP4+5MVGLwwYegW5HAaxeFHbdLOFhu+KPDRO0rrxHcJvNcZG4eKKOAJBKMgJCYI7B92MlH0su8S0vCSwEemZqJI8pjA37yzLBDVbLEIbJBVfTzdX3jmdtMZ3/u3eRyyP+BzFv5j/N3rb9a0nGQPBw++3lgcsX/5mPhhI+97BROwOke3dvuIjFjU8UJW8IWQh2x/YudEcW8ojSmapKYzkVel2gB/RB9GcLxo+mFECE5mCVIZDt38nHS24ZU11pWQPHSNSqIEYEBOOQo2xCXJ4YTdPBstYmw/KDL3QwSnQG4ZmUgzV5dljhe9jLMUo3iqyG4/QnAU/AzNem36fg462TbGrvNQTvQK3DU120aSDQKvKWzbucy9XmTuLIIfQ0BCxkNaYZecAw2ecy2vCHxiamwjyXMCf+eOLsm4YLteLML7Gwh+MQneIb65gP+2YYEHpsQ7I4kJvHNyKr9UZO4dBG9ZpBzZDotqs9eJ71Y4dCkh8y8J/NOp8Y0kPxH4lcnx/ecic39B8AeLVDEbzz4ugk6sN8O58DRY0CXwZ6fGOpKsEPhTk2P9r0XmbiC4KtyzU9Ulyymwwrs1PWOTZi6wsokQ302B35maNEjytsBvTU6aD4rM3UZwy4LiE6RZl3lDbXQqkqBmcIHZf/KKwBcLcO6cFhDYi4VGsdMFgU9PSiAXKTLHDvs3VGRazovRSSR2GS1w8EFCFl8R+FdTuwwkuSjwucnxXlFkrgqBB7zCp2iKFZYi8NBJZ5P019ScfMKmmuwfSlOQcXm5i9+e5jh8+xW/P8jBn9HjV+9f2ljgu+/sCb8ICbqTx2rLZx3r/R0vTdK/LVSESXksqaq532hy+t6EQWMK02kF/2KTYDLD+7kq50MxlCSIUDRXPV8xCwTiK/Cv2UzDzWngqmGbNScN/B1r/J+zbnvLt15mnxZBq21XXv7oN3uW9Xw+OHzggeTTg03Pb3p2Q9XXP7y1MtZw6PSOa9r/ALQ4UiFfGwAA";
}
