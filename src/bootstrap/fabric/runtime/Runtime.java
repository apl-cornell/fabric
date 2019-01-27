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
                        fabric.worker.transaction.TransactionManager $tm541 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled544 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff542 = 1;
                        boolean $doBackoff543 = true;
                        boolean $retry537 = true;
                        boolean $keepReads538 = false;
                        $label535: for (boolean $commit536 = false; !$commit536;
                                        ) {
                            if ($backoffEnabled544) {
                                if ($doBackoff543) {
                                    if ($backoff542 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff542));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e539) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff542 < 5000) $backoff542 *= 2;
                                }
                                $doBackoff543 = $backoff542 <= 32 ||
                                                  !$doBackoff543;
                            }
                            $commit536 = true;
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
                                         RetryException $e539) {
                                    throw $e539;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e539) {
                                    throw $e539;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e539) {
                                    throw $e539;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e539) {
                                    throw $e539;
                                }
                                catch (final Throwable $e539) {
                                    $tm541.getCurrentLog().checkRetrySignal();
                                    throw $e539;
                                }
                            }
                            catch (final fabric.worker.RetryException $e539) {
                                $commit536 = false;
                                continue $label535;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e539) {
                                $commit536 = false;
                                $retry537 = false;
                                $keepReads538 = $e539.keepReads;
                                if ($tm541.checkForStaleObjects()) {
                                    $retry537 = true;
                                    $keepReads538 = false;
                                    continue $label535;
                                }
                                fabric.common.TransactionID $currentTid540 =
                                  $tm541.getCurrentTid();
                                if ($e539.tid ==
                                      null ||
                                      !$e539.tid.isDescendantOf(
                                                   $currentTid540)) {
                                    throw $e539;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e539);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e539) {
                                $commit536 = false;
                                fabric.common.TransactionID $currentTid540 =
                                  $tm541.getCurrentTid();
                                if ($e539.tid.isDescendantOf($currentTid540))
                                    continue $label535;
                                if ($currentTid540.parent != null) {
                                    $retry537 = false;
                                    throw $e539;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e539) {
                                $commit536 = false;
                                if ($tm541.checkForStaleObjects())
                                    continue $label535;
                                fabric.common.TransactionID $currentTid540 =
                                  $tm541.getCurrentTid();
                                if ($e539.tid.isDescendantOf($currentTid540)) {
                                    $retry537 = true;
                                }
                                else if ($currentTid540.parent != null) {
                                    $retry537 = false;
                                    throw $e539;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e539) {
                                $commit536 = false;
                                if ($tm541.checkForStaleObjects())
                                    continue $label535;
                                $retry537 = false;
                                throw new fabric.worker.AbortException($e539);
                            }
                            finally {
                                if ($commit536) {
                                    fabric.common.TransactionID $currentTid540 =
                                      $tm541.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e539) {
                                        $commit536 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e539) {
                                        $commit536 = false;
                                        $retry537 = false;
                                        $keepReads538 = $e539.keepReads;
                                        if ($tm541.checkForStaleObjects()) {
                                            $retry537 = true;
                                            $keepReads538 = false;
                                            continue $label535;
                                        }
                                        if ($e539.tid ==
                                              null ||
                                              !$e539.tid.isDescendantOf(
                                                           $currentTid540))
                                            throw $e539;
                                        throw new fabric.worker.
                                                UserAbortException($e539);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e539) {
                                        $commit536 = false;
                                        $currentTid540 = $tm541.getCurrentTid();
                                        if ($currentTid540 != null) {
                                            if ($e539.tid.equals(
                                                            $currentTid540) ||
                                                  !$e539.tid.
                                                  isDescendantOf(
                                                    $currentTid540)) {
                                                throw $e539;
                                            }
                                        }
                                    }
                                } else if ($keepReads538) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit536) {
                                    {  }
                                    if ($retry537) { continue $label535; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 39, -43, -40, 33, 76,
    108, 101, 13, 51, -37, -68, -35, -103, 120, -83, -101, 73, 71, 111, -71, 66,
    44, -41, -86, 41, -86, 45, 83, -26, 27, -107, -56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
