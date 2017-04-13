package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Worker;
import fabric.worker.Store;

/**
 * Utility for describing a timestamp and getting bounds specific to the system
 * node asking for it.
 */
public interface NodeTime extends fabric.lang.Object {
    public fabric.worker.Store get$store();
    
    public fabric.worker.Store set$store(fabric.worker.Store val);
    
    public long get$time();
    
    public long set$time(long val);
    
    public long postInc$time();
    
    public long postDec$time();
    
    /**
   * @param store
   *        the node specifying the expiry
   * @param time
   *        the expiry time specified
   */
    public fabric.metrics.util.NodeTime fabric$metrics$util$NodeTime$(
      fabric.worker.Store store, long time);
    
    /**
   * Create a NodeTime on the current store.
   *
   * @param time
   *        the expiry time specified
   */
    public fabric.metrics.util.NodeTime fabric$metrics$util$NodeTime$(long time);
    
    /**
   * @return the lower bound on this time relative to the node running the
   *       current transaction
   */
    public long lowerBound();
    
    /**
   * @param other
   *        a store we're computing relative to
   * @return the lower bound on this time relative to other
   */
    public long lowerBoundAt(fabric.worker.Store other);
    
    /**
   * @return the upper bound on this time relative to the node running the
   *       current transaction
   */
    public long upperBound();
    
    /**
   * @param other
   *        a store we're computing relative to
   * @return the upper bound on this time relative to other
   */
    public long upperBoundAt(fabric.worker.Store other);
    
    /**
   * @param other
   *        a time relative to the current store.
   * @return true iff this is definitely &gt;= other (relative to the store
   *       running the current transaction)
   */
    public boolean greaterThan(long other);
    
    /**
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &gt;= other (relative to the store
   *       running the current transaction)
   */
    public boolean greaterThan(fabric.metrics.util.NodeTime other);
    
    /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &gt;= other
   */
    public boolean greaterThan(fabric.worker.Store s, fabric.metrics.util.NodeTime other);
    
    /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a time to compare with
   * @return true iff this is definitely &gt;= other
   */
    public boolean greaterThan(fabric.worker.Store s, long other);
    
    /**
   * @param other
   *        a time local to the current node
   * @return true iff this is definitely &lt;= other (relative to the store
   *       running the current transaction)
   */
    public boolean lessThan(long other);
    
    /**
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &lt;= other (relative to the store
   *       running the current transaction)
   */
    public boolean lessThan(fabric.metrics.util.NodeTime other);
    
    /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &lt;= other
   */
    public boolean lessThan(fabric.worker.Store s, fabric.metrics.util.NodeTime other);
    
    /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a time to compare with
   * @return true iff this is definitely &lt;= other
   */
    public boolean lessThan(fabric.worker.Store s, long other);
    
    /**
   * @param other
   *        a time local to the current node
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
    public fabric.metrics.util.NodeTime max(long other);
    
    /**
   * @param other
   *        a {@link NodeTime}
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
    public fabric.metrics.util.NodeTime max(fabric.metrics.util.NodeTime other);
    
    /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a {@link NodeTime}
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
    public fabric.metrics.util.NodeTime max(fabric.worker.Store s, fabric.metrics.util.NodeTime other);
    
    /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a time to compare with
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
    public fabric.metrics.util.NodeTime max(fabric.worker.Store s, long other);
    
    /**
   * Translate this to a {@link NodeTime} on another {@link Store}
   *
   * @param s
   *        the store for the translated result
   * @return the translated {@link NodeTime}
   */
    public fabric.metrics.util.NodeTime translate(fabric.worker.Store s);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object o);
    
    public int hashCode();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.NodeTime {
        public fabric.worker.Store get$store() {
            return ((fabric.metrics.util.NodeTime._Impl) fetch()).get$store();
        }
        
        public fabric.worker.Store set$store(fabric.worker.Store val) {
            return ((fabric.metrics.util.NodeTime._Impl) fetch()).set$store(
                                                                    val);
        }
        
        public long get$time() {
            return ((fabric.metrics.util.NodeTime._Impl) fetch()).get$time();
        }
        
        public long set$time(long val) {
            return ((fabric.metrics.util.NodeTime._Impl) fetch()).set$time(val);
        }
        
        public long postInc$time() {
            return ((fabric.metrics.util.NodeTime._Impl) fetch()).postInc$time(
                                                                    );
        }
        
        public long postDec$time() {
            return ((fabric.metrics.util.NodeTime._Impl) fetch()).postDec$time(
                                                                    );
        }
        
        public fabric.metrics.util.NodeTime fabric$metrics$util$NodeTime$(
          fabric.worker.Store arg1, long arg2) {
            return ((fabric.metrics.util.NodeTime) fetch()).
              fabric$metrics$util$NodeTime$(arg1, arg2);
        }
        
        public fabric.metrics.util.NodeTime fabric$metrics$util$NodeTime$(
          long arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).
              fabric$metrics$util$NodeTime$(arg1);
        }
        
        public long lowerBound() {
            return ((fabric.metrics.util.NodeTime) fetch()).lowerBound();
        }
        
        public long lowerBoundAt(fabric.worker.Store arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).lowerBoundAt(arg1);
        }
        
        public long upperBound() {
            return ((fabric.metrics.util.NodeTime) fetch()).upperBound();
        }
        
        public long upperBoundAt(fabric.worker.Store arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).upperBoundAt(arg1);
        }
        
        public boolean greaterThan(long arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).greaterThan(arg1);
        }
        
        public boolean greaterThan(fabric.metrics.util.NodeTime arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).greaterThan(arg1);
        }
        
        public boolean greaterThan(fabric.worker.Store arg1,
                                   fabric.metrics.util.NodeTime arg2) {
            return ((fabric.metrics.util.NodeTime) fetch()).greaterThan(arg1,
                                                                        arg2);
        }
        
        public boolean greaterThan(fabric.worker.Store arg1, long arg2) {
            return ((fabric.metrics.util.NodeTime) fetch()).greaterThan(arg1,
                                                                        arg2);
        }
        
        public boolean lessThan(long arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).lessThan(arg1);
        }
        
        public boolean lessThan(fabric.metrics.util.NodeTime arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).lessThan(arg1);
        }
        
        public boolean lessThan(fabric.worker.Store arg1,
                                fabric.metrics.util.NodeTime arg2) {
            return ((fabric.metrics.util.NodeTime) fetch()).lessThan(arg1,
                                                                     arg2);
        }
        
        public boolean lessThan(fabric.worker.Store arg1, long arg2) {
            return ((fabric.metrics.util.NodeTime) fetch()).lessThan(arg1,
                                                                     arg2);
        }
        
        public fabric.metrics.util.NodeTime max(long arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).max(arg1);
        }
        
        public fabric.metrics.util.NodeTime max(
          fabric.metrics.util.NodeTime arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).max(arg1);
        }
        
        public fabric.metrics.util.NodeTime max(
          fabric.worker.Store arg1, fabric.metrics.util.NodeTime arg2) {
            return ((fabric.metrics.util.NodeTime) fetch()).max(arg1, arg2);
        }
        
        public fabric.metrics.util.NodeTime max(fabric.worker.Store arg1,
                                                long arg2) {
            return ((fabric.metrics.util.NodeTime) fetch()).max(arg1, arg2);
        }
        
        public fabric.metrics.util.NodeTime translate(
          fabric.worker.Store arg1) {
            return ((fabric.metrics.util.NodeTime) fetch()).translate(arg1);
        }
        
        public _Proxy(NodeTime._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.NodeTime {
        public fabric.worker.Store get$store() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.store;
        }
        
        public fabric.worker.Store set$store(fabric.worker.Store val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.store = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The node specifying the expiry. */
        public fabric.worker.Store store;
        
        public long get$time() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.time;
        }
        
        public long set$time(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.time = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$time() {
            long tmp = this.get$time();
            this.set$time((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$time() {
            long tmp = this.get$time();
            this.set$time((long) (tmp - 1));
            return tmp;
        }
        
        /** The expiry time specified. */
        public long time;
        
        /**
   * @param store
   *        the node specifying the expiry
   * @param time
   *        the expiry time specified
   */
        public fabric.metrics.util.NodeTime fabric$metrics$util$NodeTime$(
          fabric.worker.Store store, long time) {
            this.set$store(store);
            this.set$time((long) time);
            fabric$lang$Object$();
            return (fabric.metrics.util.NodeTime) this.$getProxy();
        }
        
        /**
   * Create a NodeTime on the current store.
   *
   * @param time
   *        the expiry time specified
   */
        public fabric.metrics.util.NodeTime fabric$metrics$util$NodeTime$(
          long time) {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            this.set$store(w.getStore(w.getName()));
            this.set$time((long) time);
            fabric$lang$Object$();
            return (fabric.metrics.util.NodeTime) this.$getProxy();
        }
        
        /**
   * @return the lower bound on this time relative to the node running the
   *       current transaction
   */
        public long lowerBound() {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return lowerBoundAt(store);
        }
        
        /**
   * @param other
   *        a store we're computing relative to
   * @return the lower bound on this time relative to other
   */
        public long lowerBoundAt(fabric.worker.Store other) {
            return this.get$store().equals(other)
              ? this.get$time()
              : this.get$time() -
              fabric.metrics.util.NodeTime._Static._Proxy.$instance.
              get$MAX_DRIFT();
        }
        
        /**
   * @return the upper bound on this time relative to the node running the
   *       current transaction
   */
        public long upperBound() {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return upperBoundAt(store);
        }
        
        /**
   * @param other
   *        a store we're computing relative to
   * @return the upper bound on this time relative to other
   */
        public long upperBoundAt(fabric.worker.Store other) {
            return other.equals(this.get$store())
              ? this.get$time()
              : this.get$time() +
              fabric.metrics.util.NodeTime._Static._Proxy.$instance.
              get$MAX_DRIFT();
        }
        
        /**
   * @param other
   *        a time relative to the current store.
   * @return true iff this is definitely &gt;= other (relative to the store
   *       running the current transaction)
   */
        public boolean greaterThan(long other) {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return greaterThan(store, other);
        }
        
        /**
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &gt;= other (relative to the store
   *       running the current transaction)
   */
        public boolean greaterThan(fabric.metrics.util.NodeTime other) {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return greaterThan(store, other.upperBoundAt(store));
        }
        
        /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &gt;= other
   */
        public boolean greaterThan(fabric.worker.Store s,
                                   fabric.metrics.util.NodeTime other) {
            return greaterThan(s, other.upperBoundAt(s));
        }
        
        /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a time to compare with
   * @return true iff this is definitely &gt;= other
   */
        public boolean greaterThan(fabric.worker.Store s, long other) {
            return lowerBoundAt(s) >= other;
        }
        
        /**
   * @param other
   *        a time local to the current node
   * @return true iff this is definitely &lt;= other (relative to the store
   *       running the current transaction)
   */
        public boolean lessThan(long other) {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return lessThan(store, other);
        }
        
        /**
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &lt;= other (relative to the store
   *       running the current transaction)
   */
        public boolean lessThan(fabric.metrics.util.NodeTime other) {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return lessThan(store, other.lowerBoundAt(store));
        }
        
        /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        another {@link NodeTime} to compare with
   * @return true iff this is definitely &lt;= other
   */
        public boolean lessThan(fabric.worker.Store s,
                                fabric.metrics.util.NodeTime other) {
            return lessThan(s, other.lowerBoundAt(s));
        }
        
        /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a time to compare with
   * @return true iff this is definitely &lt;= other
   */
        public boolean lessThan(fabric.worker.Store s, long other) {
            return upperBoundAt(s) <= other;
        }
        
        /**
   * @param other
   *        a time local to the current node
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
        public fabric.metrics.util.NodeTime max(long other) {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return max(store, other);
        }
        
        /**
   * @param other
   *        a {@link NodeTime}
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
        public fabric.metrics.util.NodeTime max(
          fabric.metrics.util.NodeTime other) {
            fabric.worker.Worker w = fabric.worker.Worker.getWorker();
            fabric.worker.Store store = w.getStore(w.getName());
            return max(store, other.upperBoundAt(store));
        }
        
        /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a {@link NodeTime}
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
        public fabric.metrics.util.NodeTime max(
          fabric.worker.Store s, fabric.metrics.util.NodeTime other) {
            return max(s, other.upperBoundAt(s));
        }
        
        /**
   * @param s
   *        the store performing the comparison
   * @param other
   *        a time to compare with
   * @return the max of the two times (defaulting to other if there's no clear
   *       max).
   */
        public fabric.metrics.util.NodeTime max(fabric.worker.Store s,
                                                long other) {
            return lowerBoundAt(s) >=
              other
              ? (fabric.metrics.util.NodeTime)
                  this.$getProxy()
              : ((fabric.metrics.util.NodeTime)
                   new fabric.metrics.util.NodeTime._Impl(s).$getProxy()).
              fabric$metrics$util$NodeTime$(s, other);
        }
        
        /**
   * Translate this to a {@link NodeTime} on another {@link Store}
   *
   * @param s
   *        the store for the translated result
   * @return the translated {@link NodeTime}
   */
        public fabric.metrics.util.NodeTime translate(fabric.worker.Store s) {
            return ((fabric.metrics.util.NodeTime)
                      new fabric.metrics.util.NodeTime._Impl(s).$getProxy()).
              fabric$metrics$util$NodeTime$(s, lowerBoundAt(s));
        }
        
        public java.lang.String toString() {
            return "(" + this.get$time() + "@" + this.get$store() + ")";
        }
        
        public boolean equals(fabric.lang.Object o) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy(
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.
                        $unwrap(o)) instanceof fabric.metrics.util.NodeTime))
                return false;
            fabric.metrics.util.NodeTime that =
              (fabric.metrics.util.NodeTime)
                fabric.lang.Object._Proxy.$getProxy(o);
            return that.get$store().equals(this.get$store()) &&
              that.get$time() == this.get$time();
        }
        
        public int hashCode() {
            return java.lang.Long.hashCode(this.get$time()) * 32 +
              this.get$store().hashCode();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.NodeTime._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.store);
            out.writeLong(this.time);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.store = (fabric.worker.Store) in.readObject();
            this.time = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.NodeTime._Impl src =
              (fabric.metrics.util.NodeTime._Impl) other;
            this.store = src.store;
            this.time = src.time;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$MAX_DRIFT();
        
        public long set$MAX_DRIFT(long val);
        
        public long postInc$MAX_DRIFT();
        
        public long postDec$MAX_DRIFT();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.NodeTime._Static {
            public long get$MAX_DRIFT() {
                return ((fabric.metrics.util.NodeTime._Static._Impl) fetch()).
                  get$MAX_DRIFT();
            }
            
            public long set$MAX_DRIFT(long val) {
                return ((fabric.metrics.util.NodeTime._Static._Impl) fetch()).
                  set$MAX_DRIFT(val);
            }
            
            public long postInc$MAX_DRIFT() {
                return ((fabric.metrics.util.NodeTime._Static._Impl) fetch()).
                  postInc$MAX_DRIFT();
            }
            
            public long postDec$MAX_DRIFT() {
                return ((fabric.metrics.util.NodeTime._Static._Impl) fetch()).
                  postDec$MAX_DRIFT();
            }
            
            public _Proxy(fabric.metrics.util.NodeTime._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.NodeTime._Static $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  NodeTime.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.NodeTime._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.NodeTime._Static._Impl.class);
                $instance = (fabric.metrics.util.NodeTime._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.NodeTime._Static {
            public long get$MAX_DRIFT() { return this.MAX_DRIFT; }
            
            public long set$MAX_DRIFT(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.MAX_DRIFT = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$MAX_DRIFT() {
                long tmp = this.get$MAX_DRIFT();
                this.set$MAX_DRIFT((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$MAX_DRIFT() {
                long tmp = this.get$MAX_DRIFT();
                this.set$MAX_DRIFT((long) (tmp - 1));
                return tmp;
            }
            
            public long MAX_DRIFT;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.MAX_DRIFT);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.MAX_DRIFT = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.NodeTime._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm35 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff36 = 1;
                        $label31: for (boolean $commit32 = false; !$commit32;
                                       ) {
                            if ($backoff36 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff36);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e33) {
                                        
                                    }
                                }
                            }
                            if ($backoff36 < 5000) $backoff36 *= 2;
                            $commit32 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.util.NodeTime._Static._Proxy.
                                  $instance.
                                  set$MAX_DRIFT((long) 50);
                            }
                            catch (final fabric.worker.RetryException $e33) {
                                $commit32 = false;
                                continue $label31;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e33) {
                                $commit32 = false;
                                fabric.common.TransactionID $currentTid34 =
                                  $tm35.getCurrentTid();
                                if ($e33.tid.isDescendantOf($currentTid34))
                                    continue $label31;
                                if ($currentTid34.parent != null) throw $e33;
                                throw new InternalError(
                                        "Something is broken with transaction management. Got a signal to restart a different transaction than the one being managed.");
                            }
                            catch (final Throwable $e33) {
                                $commit32 = false;
                                if ($tm35.checkForStaleObjects())
                                    continue $label31;
                                throw new fabric.worker.AbortException($e33);
                            }
                            finally {
                                if ($commit32) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e33) {
                                        $commit32 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e33) {
                                        $commit32 = false;
                                        fabric.common.TransactionID
                                          $currentTid34 = $tm35.getCurrentTid();
                                        if ($currentTid34 ==
                                              null ||
                                              $e33.tid.isDescendantOf(
                                                         $currentTid34) &&
                                              !$currentTid34.equals($e33.tid))
                                            continue $label31;
                                        throw $e33;
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit32) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 112, 94, -65, -117,
    -52, -22, -59, -22, 34, 37, -43, -29, 79, -67, -58, 23, 114, -40, -48, -75,
    -110, 114, 41, -15, -10, 8, 121, -51, 38, 20, 113, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492108759000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1ZfXBU1RW/uwn5hoRAAoTwFRZsAHcLtoySlposX9sskEkCU4Mlvry9mzx4+97y3l2yUONI1cJYmz8U8GOU0imK0lSndhinlbQqIlCBoZ2K+oeU8WMUKZ1iq7YFtefcd/czu4+8TjO599y995x7f+fcc869772hy2SMaZCGsNSjqF62LUpN7wqpJxBskwyThvyqZJqd0NstlxcG9n50MDTdTdxBUiFLmq4psqR2ayYj44KbpK2ST6PMt6490LSBlMoouEoy+xhxb2iJG2RmVFe39ao6E4uMmH/PfN/uhzdWPV9AKrtIpaJ1MIkpsl/XGI2zLlIRoZEeapjNoRANdZHxGqWhDmookqpsB0Zd6yLVptKrSSxmULOdmrq6FRmrzViUGnzNRCfC1wG2EZOZbgD8Kgt+jCmqL6iYrClIisIKVUPmFnIXKQySMWFV6gXG2mBCCx+f0bcC+4G9TAGYRliSaUKkcLOihRiZkS2R1NjTCgwgWhyhrE9PLlWoSdBBqi1IqqT1+jqYoWi9wDpGj8EqjNTlnRSYSqKSvFnqpd2MTM7ma7OGgKuUmwVFGKnJZuMzwZ7VZe1Z2m5dXvOtwR9oqzQ3cQHmEJVVxF8CQtOzhNppmBpUk6klWDEvuFeqHd7lJgSYa7KYLZ4X7rxy64LpL52weKbm4Fnbs4nKrFs+0DPuj/X+xlsKEEZJVDcVdIUMzfmutomRpngUvL02OSMOehODL7W/dtvdh+glNykLkCJZV2MR8Krxsh6JKio1VlKNGhKjoQAppVrIz8cDpBjaQUWjVu/acNikLEAKVd5VpPPfYKIwTIEmKoa2ooX1RDsqsT7ejkcJIcVQiAv+oUythXYZlDOMtPr69Aj19agx2g/u7YNCJUPu80HcGorsMw3ZZ8Q0pgCT6AIvAmJa+q/RQ7QTBr0AI/r/nS6O6Kv6XS4w7AwZBnokE3ZJeExLmwpBsUpXQ9ToltXB4QCZMPwo95pS9HQTvJXbxQU7XZ+dI9Jld8dall95tvt1y+NQVpiNkXoLo1dgtHY1gRFgVWAseSE7eSE7DbniXv++wC+4yxSZPLaSM1XATEuiqsTCuhGJwzZwtSZyeT4r7PRmyCCQJCoaO77/3Tt2NRSAk0b7C3HfgNWTHTKpRBOAlgRx0C1X7vzos+f2Duip4GHEMyKmR0piTDZk28jQZRqCnJeaft5M6XD38IDHjfmkFFIdk8AZIW9Mz14jIzabEnkOrTEmSMrRBpKKQ4nkVMb6DL0/1cP3fhxW1ZYboLGyAPIU+e2O6BNvnbl4Ez88Etm0Mi3tdlDWlBbBOFklj9XxKdt3GpQC3zuPtD205/LODdzwwDE714IerP0QuRKErG7cd2LL2385f+DP7tRmMVIUjfWoihznuoz/Cv5cUL7EgmGIHUghGftFCpiZzAFRXHluChtkAxUyEkA3Peu0iB5SworUo1L0lGuVcxYe/utglbXdKvRYxjPIgutPkOqf0kLufn3j59P5NC4ZT6OU/VJsVoqbkJq52TCkbYgjvuNP0x49Lj0Bng8JylS2U55zCLcH4Ru4iNviRl4vzBr7BlYNlrXqeX+BOTLdr8BzM+WLXb6hx+v8Sy9ZEZ/0RZxjVo6IXy+lhcmiQ5FP3Q1Fx9ykuItU8SNb0th6CbIWuEEXHLqmX3QGydiM8cwD1DotmpKxVp8dB2nLZkdBKtNAG7mxXWY5vuU4YAjM1GQelGpwrNOC/gZHJ0Sxnhh3Ed5YwkVm83ouVo2WIbE5j2E6wksPI6VKJBJjuP98pfnQs7r5e93L2gMrOnMYvc1QIhA3W8UZS3ftvv8r7+Buy+Gsi8jsEXeBdBnrMsLXGssXjMMqs+xW4RIrPnxu4MWnB3ZaB3V15rG6XItFfnnui1PeRy6czJG0C1XdSrxV3BqLk8acgMacAaWGEPckQctzGHNVbmO6uDHjyfncOF+JmIdY1PWftPkYXIEhR1g5rYaRCeI46deNzdTwdiTHpmQfEHnhT4MC0N23Cbo2B/x2Cz5WrSPBotQyQZdmgC3EYxnbfr58PNc0/K9I3B9OC3o8bZq0KCa419PyXfX4Ph/44e59obVPLnSLVLAc/JHp0RtVupWqaVPVoteMeJRYzS+4qbi+cGnaLf7NH/RaXjMja+Vs7mdWD51cOVd+0E0KkgE84ladKdSUGbZlBoWHAq0zI3hnJm1VijZYaflcQYNF3e+nb1hqm/PtFoq8J+g72WZOpVM3t5Ibf67hO8inDtskXX43lBiZZvmkR1xxPBhhnsQVx5MCuDFTrSVQFhBSeJOgbmdqoYjLogVX86vlSsWdpZFuo9EWrDb9zxrVQbmZkDFdggacaYQiqwRtya9ROuC4zdh2rOAqU6bq/dRo0WNaiFshF/IboPghKncIKjtDjiI9gt4+qr1Yw2fdYQP/HqzuhKyWgt/M8iqApm+FZ5QOQVucKYAizYI2jc7099uMPYDVfWD6WDQ6GtOvBxTbBb3dGXIU2SDoOgemf8gG/h6sBsH0Kfg2pv8alDugPSToT5wpgCIPCPojB3H8uI0C+7B6mJHyXoPCjdjo7JM0ztiM1XwrapYxUtyj6yoVY1lqQV4hKiHlMyxads2ZWihyVdBPR6VWFZ/1aRu1DmH180y1sGt/LvzfhLKNkIpDgt7lDD+KDAjanx9/9qlhKfErGyV+jdXQKJVA4XsJGXtS0J85UwJF9gv62OiVsBzstzZKHMHq8CiVmANlkJBx5wV9xZkSKPKyoC86CJCjNviPYfU7RkpUClcpO/CNUB4jpKpV0PnOwKPIPEE9DsLglA34M1gdHw34RVAOEjL+rKAHnYFHkacE3e80Bt6w0eBNrM6ORgMvlOfhme2ioK860wBFjgo67DQAzttocAGrt0ejwSwoR+DR411B/+BMAxQ5KehRB97/oQ34i1i9y0hBREpDkCNqT8AD2WSLTnSY/1HkqqBO8v/fbXB/gtWl6+BGjzkHjzvPCHqvM9woco+gA059/t824Pkd/Z/XAY+pAhxl0vuCHnUGHkVeEfSIQ3d3ufODdxVi5xfXAe+BcpmQKYsFneoMPIrUCTpxVB7Db3Kuchvc+MLEVYRPxAbcclQ4qvKix6U/gaVfEPQpZ+hR5ElBf5offTo4u7FarCohvzDd+sTEuWoYqeIvD/HVmTdtYMTbj3yZ6HNQdKqgBc40RBG3Radcy6+h2J8E3mrxwoYjtl725UbM1Z5lY5IbsKpnpIhuiUnWm42cCRdfS30JWM8JesKZmihyXNCXR7eRXpuxr2PVCBvZJ5l9fniEznUPL1A0FgeexCM2viWemuN7jfh6KPtfpQc+aF1Qk+dbzeQR33OF3LP7Kksm7Vv3Jv/6kPwyWBokJeGYqqa/TU1rF0UNGla45Uqtd6tRrtnNqbdx6R93GClEgqq5FlucTYyMy+Rk/NMqttL5lsLmWnz46zvc7nUZlauBI6iLGfjpeugfk/5VVNJ5gX85AIPPjG78/Y9PfXzs44Y5b7y3dvi1ScZbZw8/aDRe+axk2+m5E7f87b/90GstUh8AAA==";
}
