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
                        fabric.worker.transaction.TransactionManager $tm797 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled800 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff798 = 1;
                        boolean $doBackoff799 = true;
                        boolean $retry793 = true;
                        boolean $keepReads794 = false;
                        $label791: for (boolean $commit792 = false; !$commit792;
                                        ) {
                            if ($backoffEnabled800) {
                                if ($doBackoff799) {
                                    if ($backoff798 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff798));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e795) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff798 < 5000) $backoff798 *= 2;
                                }
                                $doBackoff799 = $backoff798 <= 32 ||
                                                  !$doBackoff799;
                            }
                            $commit792 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.Random._Static._Proxy.$instance.
                                      set$serialVersionUID(
                                        (long) 3905348978240129619L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e795) {
                                    throw $e795;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e795) {
                                    throw $e795;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e795) {
                                    throw $e795;
                                }
                                catch (final Throwable $e795) {
                                    $tm797.getCurrentLog().checkRetrySignal();
                                    throw $e795;
                                }
                            }
                            catch (final fabric.worker.RetryException $e795) {
                                $commit792 = false;
                                continue $label791;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e795) {
                                $commit792 = false;
                                $retry793 = false;
                                $keepReads794 = $e795.keepReads;
                                if ($tm797.checkForStaleObjects()) {
                                    $retry793 = true;
                                    $keepReads794 = false;
                                    continue $label791;
                                }
                                fabric.common.TransactionID $currentTid796 =
                                  $tm797.getCurrentTid();
                                if ($e795.tid ==
                                      null ||
                                      !$e795.tid.isDescendantOf(
                                                   $currentTid796)) {
                                    throw $e795;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e795);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e795) {
                                $commit792 = false;
                                fabric.common.TransactionID $currentTid796 =
                                  $tm797.getCurrentTid();
                                if ($e795.tid.isDescendantOf($currentTid796))
                                    continue $label791;
                                if ($currentTid796.parent != null) {
                                    $retry793 = false;
                                    throw $e795;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e795) {
                                $commit792 = false;
                                if ($tm797.checkForStaleObjects())
                                    continue $label791;
                                $retry793 = false;
                                throw new fabric.worker.AbortException($e795);
                            }
                            finally {
                                if ($commit792) {
                                    fabric.common.TransactionID $currentTid796 =
                                      $tm797.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e795) {
                                        $commit792 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e795) {
                                        $commit792 = false;
                                        $retry793 = false;
                                        $keepReads794 = $e795.keepReads;
                                        if ($tm797.checkForStaleObjects()) {
                                            $retry793 = true;
                                            $keepReads794 = false;
                                            continue $label791;
                                        }
                                        if ($e795.tid ==
                                              null ||
                                              !$e795.tid.isDescendantOf(
                                                           $currentTid796))
                                            throw $e795;
                                        throw new fabric.worker.
                                                UserAbortException($e795);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e795) {
                                        $commit792 = false;
                                        $currentTid796 = $tm797.getCurrentTid();
                                        if ($currentTid796 != null) {
                                            if ($e795.tid.equals(
                                                            $currentTid796) ||
                                                  !$e795.tid.
                                                  isDescendantOf(
                                                    $currentTid796)) {
                                                throw $e795;
                                            }
                                        }
                                    }
                                } else if ($keepReads794) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit792) {
                                    {  }
                                    if ($retry793) { continue $label791; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 83, 68, -66, 120, 114,
    -126, -15, -95, 34, -127, -82, 122, 40, 76, 115, -93, -49, -106, -46, 16,
    -128, 117, 73, 96, 84, 34, 55, -98, -100, 8, 29, -15 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
