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
                        fabric.worker.transaction.TransactionManager $tm831 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled834 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff832 = 1;
                        boolean $doBackoff833 = true;
                        boolean $retry827 = true;
                        boolean $keepReads828 = false;
                        $label825: for (boolean $commit826 = false; !$commit826;
                                        ) {
                            if ($backoffEnabled834) {
                                if ($doBackoff833) {
                                    if ($backoff832 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff832));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e829) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff832 < 5000) $backoff832 *= 2;
                                }
                                $doBackoff833 = $backoff832 <= 32 ||
                                                  !$doBackoff833;
                            }
                            $commit826 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.util.Random._Static._Proxy.$instance.
                                      set$serialVersionUID(
                                        (long) 3905348978240129619L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e829) {
                                    throw $e829;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e829) {
                                    throw $e829;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e829) {
                                    throw $e829;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e829) {
                                    throw $e829;
                                }
                                catch (final Throwable $e829) {
                                    $tm831.getCurrentLog().checkRetrySignal();
                                    throw $e829;
                                }
                            }
                            catch (final fabric.worker.RetryException $e829) {
                                $commit826 = false;
                                continue $label825;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e829) {
                                $commit826 = false;
                                $retry827 = false;
                                $keepReads828 = $e829.keepReads;
                                if ($tm831.checkForStaleObjects()) {
                                    $retry827 = true;
                                    $keepReads828 = false;
                                    continue $label825;
                                }
                                fabric.common.TransactionID $currentTid830 =
                                  $tm831.getCurrentTid();
                                if ($e829.tid ==
                                      null ||
                                      !$e829.tid.isDescendantOf(
                                                   $currentTid830)) {
                                    throw $e829;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e829);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e829) {
                                $commit826 = false;
                                fabric.common.TransactionID $currentTid830 =
                                  $tm831.getCurrentTid();
                                if ($e829.tid.isDescendantOf($currentTid830))
                                    continue $label825;
                                if ($currentTid830.parent != null) {
                                    $retry827 = false;
                                    throw $e829;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e829) {
                                $commit826 = false;
                                if ($tm831.checkForStaleObjects())
                                    continue $label825;
                                fabric.common.TransactionID $currentTid830 =
                                  $tm831.getCurrentTid();
                                if ($e829.tid.isDescendantOf($currentTid830)) {
                                    $retry827 = true;
                                }
                                else if ($currentTid830.parent != null) {
                                    $retry827 = false;
                                    throw $e829;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e829) {
                                $commit826 = false;
                                if ($tm831.checkForStaleObjects())
                                    continue $label825;
                                $retry827 = false;
                                throw new fabric.worker.AbortException($e829);
                            }
                            finally {
                                if ($commit826) {
                                    fabric.common.TransactionID $currentTid830 =
                                      $tm831.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e829) {
                                        $commit826 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e829) {
                                        $commit826 = false;
                                        $retry827 = false;
                                        $keepReads828 = $e829.keepReads;
                                        if ($tm831.checkForStaleObjects()) {
                                            $retry827 = true;
                                            $keepReads828 = false;
                                            continue $label825;
                                        }
                                        if ($e829.tid ==
                                              null ||
                                              !$e829.tid.isDescendantOf(
                                                           $currentTid830))
                                            throw $e829;
                                        throw new fabric.worker.
                                                UserAbortException($e829);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e829) {
                                        $commit826 = false;
                                        $currentTid830 = $tm831.getCurrentTid();
                                        if ($currentTid830 != null) {
                                            if ($e829.tid.equals(
                                                            $currentTid830) ||
                                                  !$e829.tid.
                                                  isDescendantOf(
                                                    $currentTid830)) {
                                                throw $e829;
                                            }
                                        }
                                    }
                                } else if ($keepReads828) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit826) {
                                    {  }
                                    if ($retry827) { continue $label825; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -31, 87, -59, 6, 98,
    102, 83, -68, 45, -16, 27, -115, 70, -13, 37, -13, -57, 36, 22, -4, -98, 14,
    -100, -26, -117, 115, -76, 38, -25, 96, 46, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
