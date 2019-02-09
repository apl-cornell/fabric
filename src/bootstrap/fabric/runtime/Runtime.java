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
                        fabric.worker.transaction.TransactionManager $tm551 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled554 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff552 = 1;
                        boolean $doBackoff553 = true;
                        boolean $retry547 = true;
                        boolean $keepReads548 = false;
                        $label545: for (boolean $commit546 = false; !$commit546;
                                        ) {
                            if ($backoffEnabled554) {
                                if ($doBackoff553) {
                                    if ($backoff552 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff552));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e549) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff552 < 5000) $backoff552 *= 2;
                                }
                                $doBackoff553 = $backoff552 <= 32 ||
                                                  !$doBackoff553;
                            }
                            $commit546 = true;
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
                                         RetryException $e549) {
                                    throw $e549;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e549) {
                                    throw $e549;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e549) {
                                    throw $e549;
                                }
                                catch (final Throwable $e549) {
                                    $tm551.getCurrentLog().checkRetrySignal();
                                    throw $e549;
                                }
                            }
                            catch (final fabric.worker.RetryException $e549) {
                                $commit546 = false;
                                continue $label545;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e549) {
                                $commit546 = false;
                                $retry547 = false;
                                $keepReads548 = $e549.keepReads;
                                if ($tm551.checkForStaleObjects()) {
                                    $retry547 = true;
                                    $keepReads548 = false;
                                    continue $label545;
                                }
                                fabric.common.TransactionID $currentTid550 =
                                  $tm551.getCurrentTid();
                                if ($e549.tid ==
                                      null ||
                                      !$e549.tid.isDescendantOf(
                                                   $currentTid550)) {
                                    throw $e549;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e549);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e549) {
                                $commit546 = false;
                                fabric.common.TransactionID $currentTid550 =
                                  $tm551.getCurrentTid();
                                if ($e549.tid.isDescendantOf($currentTid550))
                                    continue $label545;
                                if ($currentTid550.parent != null) {
                                    $retry547 = false;
                                    throw $e549;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e549) {
                                $commit546 = false;
                                if ($tm551.checkForStaleObjects())
                                    continue $label545;
                                $retry547 = false;
                                throw new fabric.worker.AbortException($e549);
                            }
                            finally {
                                if ($commit546) {
                                    fabric.common.TransactionID $currentTid550 =
                                      $tm551.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e549) {
                                        $commit546 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e549) {
                                        $commit546 = false;
                                        $retry547 = false;
                                        $keepReads548 = $e549.keepReads;
                                        if ($tm551.checkForStaleObjects()) {
                                            $retry547 = true;
                                            $keepReads548 = false;
                                            continue $label545;
                                        }
                                        if ($e549.tid ==
                                              null ||
                                              !$e549.tid.isDescendantOf(
                                                           $currentTid550))
                                            throw $e549;
                                        throw new fabric.worker.
                                                UserAbortException($e549);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e549) {
                                        $commit546 = false;
                                        $currentTid550 = $tm551.getCurrentTid();
                                        if ($currentTid550 != null) {
                                            if ($e549.tid.equals(
                                                            $currentTid550) ||
                                                  !$e549.tid.
                                                  isDescendantOf(
                                                    $currentTid550)) {
                                                throw $e549;
                                            }
                                        }
                                    }
                                } else if ($keepReads548) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit546) {
                                    {  }
                                    if ($retry547) { continue $label545; }
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
