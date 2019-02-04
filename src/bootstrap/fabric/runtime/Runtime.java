package fabric.runtime;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import fabric.util.LinkedList;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * The runtime interface between Fabric programs and the underlying system.
 * Ported from Jif.
 */
public interface Runtime extends fabric.lang.Object {
    public fabric.lang.security.Principal get$dynp();
    
    public fabric.lang.security.Principal set$dynp(
      fabric.lang.security.Principal val);
    
    public fabric.worker.Store get$local();
    
    public fabric.worker.Store set$local(fabric.worker.Store val);
    
    /**
     * Gets the standard error output.
     * The output channel is parameterized by <code>l</code>.
     */
    public java.io.PrintStream stderr(fabric.lang.security.Label l);
    
    /**
     * Gets the standard output.
     * This output channel is parameterized by <code>l</code>.
     */
    public java.io.PrintStream stdout(fabric.lang.security.Label l);
    
    /**
     * Gets the standard input.
     * This input channel is parameterized by <code>l</code>.
     */
    public java.io.InputStream stdin(fabric.lang.security.Label l);
    
    /**
     * Get the standard output parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
    public java.io.PrintStream out();
    
    /**
     * Get the standard input parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
    public java.io.InputStream in();
    
    /**
     * Get the standard error output parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
    public java.io.PrintStream err();
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.runtime.Runtime {
        public fabric.lang.security.Principal get$dynp() {
            return ((fabric.runtime.Runtime._Impl) fetch()).get$dynp();
        }
        
        public fabric.lang.security.Principal set$dynp(
          fabric.lang.security.Principal val) {
            return ((fabric.runtime.Runtime._Impl) fetch()).set$dynp(val);
        }
        
        public fabric.worker.Store get$local() {
            return ((fabric.runtime.Runtime._Impl) fetch()).get$local();
        }
        
        public fabric.worker.Store set$local(fabric.worker.Store val) {
            return ((fabric.runtime.Runtime._Impl) fetch()).set$local(val);
        }
        
        public static fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal arg1)
              throws java.lang.SecurityException {
            return fabric.runtime.Runtime._Impl.getRuntime(arg1);
        }
        
        public static fabric.lang.security.Principal user(
          fabric.lang.security.Principal arg1) {
            return fabric.runtime.Runtime._Impl.user(arg1);
        }
        
        public java.io.PrintStream stderr(fabric.lang.security.Label arg1) {
            return ((fabric.runtime.Runtime) fetch()).stderr(arg1);
        }
        
        public java.io.PrintStream stdout(fabric.lang.security.Label arg1) {
            return ((fabric.runtime.Runtime) fetch()).stdout(arg1);
        }
        
        public java.io.InputStream stdin(fabric.lang.security.Label arg1) {
            return ((fabric.runtime.Runtime) fetch()).stdin(arg1);
        }
        
        public java.io.PrintStream out() {
            return ((fabric.runtime.Runtime) fetch()).out();
        }
        
        public java.io.InputStream in() {
            return ((fabric.runtime.Runtime) fetch()).in();
        }
        
        public java.io.PrintStream err() {
            return ((fabric.runtime.Runtime) fetch()).err();
        }
        
        public static int currentYear(fabric.lang.security.Principal arg1) {
            return fabric.runtime.Runtime._Impl.currentYear(arg1);
        }
        
        public static int currentMonth(fabric.lang.security.Principal arg1) {
            return fabric.runtime.Runtime._Impl.currentMonth(arg1);
        }
        
        public static int currentDayOfMonth(
          fabric.lang.security.Principal arg1) {
            return fabric.runtime.Runtime._Impl.currentDayOfMonth(arg1);
        }
        
        public static int currentHour(fabric.lang.security.Principal arg1) {
            return fabric.runtime.Runtime._Impl.currentHour(arg1);
        }
        
        public static int currentMinute(fabric.lang.security.Principal arg1) {
            return fabric.runtime.Runtime._Impl.currentMinute(arg1);
        }
        
        public static void sleep(fabric.lang.security.Principal arg1,
                                 int arg2) {
            fabric.runtime.Runtime._Impl.sleep(arg1, arg2);
        }
        
        public _Proxy(Runtime._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.runtime.Runtime {
        public fabric.lang.security.Principal get$dynp() { return this.dynp; }
        
        public fabric.lang.security.Principal set$dynp(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.dynp = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal dynp;
        
        public fabric.worker.Store get$local() { return this.local; }
        
        public fabric.worker.Store set$local(fabric.worker.Store val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.local = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.Store local;
        
        private fabric.runtime.Runtime fabric$runtime$Runtime$(
          fabric.lang.security.Principal p) {
            this.set$dynp(p);
            this.set$local(fabric.worker.Worker.getWorker().getLocalStore());
            fabric$lang$Object$();
            return (fabric.runtime.Runtime) this.$getProxy();
        }
        
        /**
     * Gets a <code>Runtime</code> object parameterized with the
     * principal <code>p</code>.
     */
        public static fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal p) throws java.lang.SecurityException {
            fabric.worker.Store local =
              fabric.worker.Worker.getWorker().getLocalStore();
            return (fabric.runtime.Runtime)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.runtime.Runtime._Impl)
                          ((fabric.runtime.Runtime)
                             new fabric.runtime.Runtime._Impl(local).$getProxy(
                                                                       )).fetch(
                                                                            )).
                           fabric$runtime$Runtime$(p));
        }
        
        /** Get the current user  */
        public static fabric.lang.security.Principal user(
          fabric.lang.security.Principal parameter) {
            return fabric.worker.Worker.getWorker().getPrincipal();
        }
        
        private fabric.lang.security.Label defaultOutputLabel() {
            fabric.lang.security.ConfPolicy
              cp =
              fabric.lang.security.LabelUtil._Impl.
              readerPolicy(
                this.
                    get$local(),
                this.
                    get$dynp(),
                (fabric.util.LinkedList)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.LinkedList)
                       new fabric.util.LinkedList._Impl(this.$getStore()).
                       $getProxy()).fabric$util$LinkedList$()));
            return fabric.lang.security.LabelUtil._Impl.toLabel(
                                                          this.get$local(), cp);
        }
        
        private fabric.lang.security.Label defaultInputLabel() {
            fabric.lang.security.IntegPolicy
              ip =
              fabric.lang.security.LabelUtil._Impl.
              writerPolicy(
                this.
                    get$local(),
                this.
                    get$dynp(),
                (fabric.util.LinkedList)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.util.LinkedList)
                       new fabric.util.LinkedList._Impl(this.$getStore()).
                       $getProxy()).fabric$util$LinkedList$()));
            return fabric.lang.security.LabelUtil._Impl.toLabel(
                                                          this.get$local(), ip);
        }
        
        /**
     * Gets the standard error output.
     * The output channel is parameterized by <code>l</code>.
     */
        public java.io.PrintStream stderr(fabric.lang.security.Label l) {
            if (fabric.lang.security.LabelUtil._Impl.
                  relabelsTo(l,
                             ((fabric.runtime.Runtime._Impl)
                                this.fetch()).defaultOutputLabel()))
                return java.lang.System.err;
            throw new java.lang.SecurityException(
                    "The standard error output is not " +
                      "sufficiently secure.");
        }
        
        /**
     * Gets the standard output.
     * This output channel is parameterized by <code>l</code>.
     */
        public java.io.PrintStream stdout(fabric.lang.security.Label l) {
            if (fabric.lang.security.LabelUtil._Impl.
                  relabelsTo(l,
                             ((fabric.runtime.Runtime._Impl)
                                this.fetch()).defaultOutputLabel()))
                return java.lang.System.out;
            throw new java.lang.SecurityException(
                    "The standard output is not " + "sufficiently secure.");
        }
        
        /**
     * Gets the standard input.
     * This input channel is parameterized by <code>l</code>.
     */
        public java.io.InputStream stdin(fabric.lang.security.Label l) {
            if (fabric.lang.security.LabelUtil._Impl.
                  relabelsTo(((fabric.runtime.Runtime._Impl)
                                this.fetch()).defaultInputLabel(), l))
                return java.lang.System.in;
            throw new java.lang.SecurityException(
                    "The standard output is not " + "sufficiently secure.");
        }
        
        /**
     * Get the standard output parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
        public java.io.PrintStream out() { return java.lang.System.out; }
        
        /**
     * Get the standard input parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
        public java.io.InputStream in() { return java.lang.System.in; }
        
        /**
     * Get the standard error output parameterized by the default label, which
     * has only one reader: the principal of this <code>Runtime</code> object.
     */
        public java.io.PrintStream err() { return java.lang.System.err; }
        
        public static int currentYear(fabric.lang.security.Principal dummy) {
            return new java.util.GregorianCalendar().get(
                                                       java.util.Calendar.YEAR);
        }
        
        public static int currentMonth(fabric.lang.security.Principal dummy) {
            return new java.util.GregorianCalendar().
              get(java.util.Calendar.MONTH) - java.util.Calendar.JANUARY + 1;
        }
        
        public static int currentDayOfMonth(
          fabric.lang.security.Principal dummy) {
            return new java.util.GregorianCalendar().
              get(java.util.Calendar.DAY_OF_MONTH);
        }
        
        public static int currentHour(fabric.lang.security.Principal dummy) {
            return new java.util.GregorianCalendar().
              get(java.util.Calendar.HOUR_OF_DAY);
        }
        
        public static int currentMinute(fabric.lang.security.Principal dummy) {
            return new java.util.GregorianCalendar().
              get(java.util.Calendar.MINUTE);
        }
        
        public static void sleep(fabric.lang.security.Principal dummy, int s) {
            try {
                double noise = 0.15;
                double multiplier = 1 + (2 * java.lang.Math.random() - 1) *
                  noise;
                long ms = (long) ((long) s * 1000 * multiplier);
                if (!java.lang.Thread.interrupted()) {
                    java.lang.Thread.sleep(ms);
                }
            }
            catch (java.lang.InterruptedException e) {  }
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.
              set$$accessPolicy(
                fabric.lang.security.LabelUtil._Impl.
                    readerPolicy(
                      this.get$local(),
                      fabric.worker.Worker.getWorker().getPrincipal(),
                      fabric.lang.security.PrincipalUtil._Impl.bottomPrincipal(
                                                                 )));
            return (fabric.runtime.Runtime) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.runtime.Runtime._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.dynp, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeInline(out, this.local);
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
            this.dynp = (fabric.lang.security.Principal)
                          $readRef(fabric.lang.security.Principal._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
            this.local = (fabric.worker.Store) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.runtime.Runtime._Impl src = (fabric.runtime.Runtime._Impl)
                                                 other;
            this.dynp = src.dynp;
            this.local = src.local;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public boolean get$_nativeOK();
        
        public boolean set$_nativeOK(boolean val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.runtime.Runtime._Static {
            public boolean get$_nativeOK() {
                return ((fabric.runtime.Runtime._Static._Impl) fetch()).
                  get$_nativeOK();
            }
            
            public boolean set$_nativeOK(boolean val) {
                return ((fabric.runtime.Runtime._Static._Impl) fetch()).
                  set$_nativeOK(val);
            }
            
            public _Proxy(fabric.runtime.Runtime._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.runtime.Runtime._Static $instance;
            
            static {
                fabric.
                  runtime.
                  Runtime.
                  _Static.
                  _Impl
                  impl =
                  (fabric.runtime.Runtime._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.runtime.Runtime._Static._Impl.class);
                $instance = (fabric.runtime.Runtime._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.runtime.Runtime._Static {
            public boolean get$_nativeOK() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this._nativeOK;
            }
            
            public boolean set$_nativeOK(boolean val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this._nativeOK = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private boolean _nativeOK;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeBoolean(this._nativeOK);
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
                this._nativeOK = in.readBoolean();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.runtime.Runtime._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm530 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled533 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff531 = 1;
                        boolean $doBackoff532 = true;
                        boolean $retry526 = true;
                        boolean $keepReads527 = false;
                        $label524: for (boolean $commit525 = false; !$commit525;
                                        ) {
                            if ($backoffEnabled533) {
                                if ($doBackoff532) {
                                    if ($backoff531 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff531));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e528) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff531 < 5000) $backoff531 *= 2;
                                }
                                $doBackoff532 = $backoff531 <= 32 ||
                                                  !$doBackoff532;
                            }
                            $commit525 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.runtime.Runtime._Static._Proxy.
                                      $instance.
                                      set$_nativeOK(true);
                                    try {
                                        java.lang.System.loadLibrary("jifrt");
                                    }
                                    catch (java.lang.UnsatisfiedLinkError ule) {
                                        fabric.runtime.Runtime._Static._Proxy.
                                          $instance.
                                          set$_nativeOK(false);
                                    }
                                }
                                catch (final fabric.worker.
                                         RetryException $e528) {
                                    throw $e528;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e528) {
                                    throw $e528;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e528) {
                                    throw $e528;
                                }
                                catch (final Throwable $e528) {
                                    $tm530.getCurrentLog().checkRetrySignal();
                                    throw $e528;
                                }
                            }
                            catch (final fabric.worker.RetryException $e528) {
                                $commit525 = false;
                                continue $label524;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e528) {
                                $commit525 = false;
                                $retry526 = false;
                                $keepReads527 = $e528.keepReads;
                                if ($tm530.checkForStaleObjects()) {
                                    $retry526 = true;
                                    $keepReads527 = false;
                                    continue $label524;
                                }
                                fabric.common.TransactionID $currentTid529 =
                                  $tm530.getCurrentTid();
                                if ($e528.tid ==
                                      null ||
                                      !$e528.tid.isDescendantOf(
                                                   $currentTid529)) {
                                    throw $e528;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e528);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e528) {
                                $commit525 = false;
                                fabric.common.TransactionID $currentTid529 =
                                  $tm530.getCurrentTid();
                                if ($e528.tid.isDescendantOf($currentTid529))
                                    continue $label524;
                                if ($currentTid529.parent != null) {
                                    $retry526 = false;
                                    throw $e528;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e528) {
                                $commit525 = false;
                                if ($tm530.checkForStaleObjects())
                                    continue $label524;
                                $retry526 = false;
                                throw new fabric.worker.AbortException($e528);
                            }
                            finally {
                                if ($commit525) {
                                    fabric.common.TransactionID $currentTid529 =
                                      $tm530.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e528) {
                                        $commit525 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e528) {
                                        $commit525 = false;
                                        $retry526 = false;
                                        $keepReads527 = $e528.keepReads;
                                        if ($tm530.checkForStaleObjects()) {
                                            $retry526 = true;
                                            $keepReads527 = false;
                                            continue $label524;
                                        }
                                        if ($e528.tid ==
                                              null ||
                                              !$e528.tid.isDescendantOf(
                                                           $currentTid529))
                                            throw $e528;
                                        throw new fabric.worker.
                                                UserAbortException($e528);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e528) {
                                        $commit525 = false;
                                        $currentTid529 = $tm530.getCurrentTid();
                                        if ($currentTid529 != null) {
                                            if ($e528.tid.equals(
                                                            $currentTid529) ||
                                                  !$e528.tid.
                                                  isDescendantOf(
                                                    $currentTid529)) {
                                                throw $e528;
                                            }
                                        }
                                    }
                                } else if ($keepReads527) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit525) {
                                    {  }
                                    if ($retry526) { continue $label524; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 2, -93, -50, 2, 82, 86,
    27, 13, -96, -53, 46, -101, -10, -35, 108, -117, 65, 72, -45, -5, 8, -62,
    23, 27, -86, -105, 82, 106, 93, -83, 127, -125 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
