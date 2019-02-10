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
                        fabric.worker.transaction.TransactionManager $tm507 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled510 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff508 = 1;
                        boolean $doBackoff509 = true;
                        boolean $retry503 = true;
                        boolean $keepReads504 = false;
                        $label501: for (boolean $commit502 = false; !$commit502;
                                        ) {
                            if ($backoffEnabled510) {
                                if ($doBackoff509) {
                                    if ($backoff508 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff508));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e505) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff508 < 5000) $backoff508 *= 2;
                                }
                                $doBackoff509 = $backoff508 <= 32 ||
                                                  !$doBackoff509;
                            }
                            $commit502 = true;
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
                                         RetryException $e505) {
                                    throw $e505;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e505) {
                                    throw $e505;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e505) {
                                    throw $e505;
                                }
                                catch (final Throwable $e505) {
                                    $tm507.getCurrentLog().checkRetrySignal();
                                    throw $e505;
                                }
                            }
                            catch (final fabric.worker.RetryException $e505) {
                                $commit502 = false;
                                continue $label501;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e505) {
                                $commit502 = false;
                                $retry503 = false;
                                $keepReads504 = $e505.keepReads;
                                if ($tm507.checkForStaleObjects()) {
                                    $retry503 = true;
                                    $keepReads504 = false;
                                    continue $label501;
                                }
                                fabric.common.TransactionID $currentTid506 =
                                  $tm507.getCurrentTid();
                                if ($e505.tid ==
                                      null ||
                                      !$e505.tid.isDescendantOf(
                                                   $currentTid506)) {
                                    throw $e505;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e505);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e505) {
                                $commit502 = false;
                                fabric.common.TransactionID $currentTid506 =
                                  $tm507.getCurrentTid();
                                if ($e505.tid.isDescendantOf($currentTid506))
                                    continue $label501;
                                if ($currentTid506.parent != null) {
                                    $retry503 = false;
                                    throw $e505;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e505) {
                                $commit502 = false;
                                if ($tm507.checkForStaleObjects())
                                    continue $label501;
                                $retry503 = false;
                                throw new fabric.worker.AbortException($e505);
                            }
                            finally {
                                if ($commit502) {
                                    fabric.common.TransactionID $currentTid506 =
                                      $tm507.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e505) {
                                        $commit502 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e505) {
                                        $commit502 = false;
                                        $retry503 = false;
                                        $keepReads504 = $e505.keepReads;
                                        if ($tm507.checkForStaleObjects()) {
                                            $retry503 = true;
                                            $keepReads504 = false;
                                            continue $label501;
                                        }
                                        if ($e505.tid ==
                                              null ||
                                              !$e505.tid.isDescendantOf(
                                                           $currentTid506))
                                            throw $e505;
                                        throw new fabric.worker.
                                                UserAbortException($e505);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e505) {
                                        $commit502 = false;
                                        $currentTid506 = $tm507.getCurrentTid();
                                        if ($currentTid506 != null) {
                                            if ($e505.tid.equals(
                                                            $currentTid506) ||
                                                  !$e505.tid.
                                                  isDescendantOf(
                                                    $currentTid506)) {
                                                throw $e505;
                                            }
                                        }
                                    }
                                } else if ($keepReads504) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit502) {
                                    {  }
                                    if ($retry503) { continue $label501; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -49, -8, -45, -101,
    -30, -10, -70, -108, 38, 5, 95, -127, -116, -14, -34, -65, -106, -62, -4,
    24, 84, -114, 82, -49, 5, 90, 32, -97, -78, 118, 101, 44 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
