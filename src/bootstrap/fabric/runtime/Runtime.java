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
                        fabric.worker.transaction.TransactionManager $tm529 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled532 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff530 = 1;
                        boolean $doBackoff531 = true;
                        boolean $retry525 = true;
                        boolean $keepReads526 = false;
                        $label523: for (boolean $commit524 = false; !$commit524;
                                        ) {
                            if ($backoffEnabled532) {
                                if ($doBackoff531) {
                                    if ($backoff530 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff530));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e527) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff530 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff530 =
                                          java.lang.Math.
                                            min(
                                              $backoff530 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff531 = $backoff530 <= 32 ||
                                                  !$doBackoff531;
                            }
                            $commit524 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.runtime.Runtime._Static._Proxy.$instance.
                                  set$_nativeOK(true);
                                try { java.lang.System.loadLibrary("jifrt"); }
                                catch (java.lang.UnsatisfiedLinkError ule) {
                                    fabric.runtime.Runtime._Static._Proxy.
                                      $instance.
                                      set$_nativeOK(false);
                                }
                            }
                            catch (final fabric.worker.RetryException $e527) {
                                $commit524 = false;
                                continue $label523;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e527) {
                                $commit524 = false;
                                $retry525 = false;
                                $keepReads526 = $e527.keepReads;
                                fabric.common.TransactionID $currentTid528 =
                                  $tm529.getCurrentTid();
                                if ($e527.tid ==
                                      null ||
                                      !$e527.tid.isDescendantOf(
                                                   $currentTid528)) {
                                    throw $e527;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e527);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e527) {
                                $commit524 = false;
                                fabric.common.TransactionID $currentTid528 =
                                  $tm529.getCurrentTid();
                                if ($e527.tid.isDescendantOf($currentTid528))
                                    continue $label523;
                                if ($currentTid528.parent != null) {
                                    $retry525 = false;
                                    throw $e527;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e527) {
                                $commit524 = false;
                                $retry525 = false;
                                if ($tm529.inNestedTxn()) {
                                    $keepReads526 = true;
                                }
                                throw $e527;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid528 =
                                  $tm529.getCurrentTid();
                                if ($commit524) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e527) {
                                        $commit524 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e527) {
                                        $commit524 = false;
                                        $retry525 = false;
                                        $keepReads526 = $e527.keepReads;
                                        if ($e527.tid ==
                                              null ||
                                              !$e527.tid.isDescendantOf(
                                                           $currentTid528))
                                            throw $e527;
                                        throw new fabric.worker.
                                                UserAbortException($e527);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e527) {
                                        $commit524 = false;
                                        $currentTid528 = $tm529.getCurrentTid();
                                        if ($currentTid528 != null) {
                                            if ($e527.tid.equals(
                                                            $currentTid528) ||
                                                  !$e527.tid.
                                                  isDescendantOf(
                                                    $currentTid528)) {
                                                throw $e527;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm529.inNestedTxn() &&
                                          $tm529.checkForStaleObjects()) {
                                        $retry525 = true;
                                        $keepReads526 = false;
                                    }
                                    if ($keepReads526) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e527) {
                                            $currentTid528 = $tm529.getCurrentTid();
                                            if ($currentTid528 != null &&
                                                  ($e527.tid.equals($currentTid528) || !$e527.tid.isDescendantOf($currentTid528))) {
                                                throw $e527;
                                            } else {
                                                $retry525 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit524) {
                                    {  }
                                    if ($retry525) { continue $label523; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -23, -25, 29, 64, -79,
    2, -101, 106, 52, 84, 70, -51, -17, -21, -78, 53, -60, 39, -75, -58, -126,
    42, -85, -67, -24, 75, -72, 113, -39, -98, -125, 93 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1ZfWwU1xF/d/7+AH9hPmxsjDFEfN0JQqIkbkngCsHhiF0bUDEi7t7eO3thb3fZfWefA7RAFZlWFWqJQ4ICrtS6TZo4QUGiQWktUSXQfJCotE1p/khCJCJIgVQkbRKpadKZt+++1ncXn2RLb2a9b2Z2fvNm5r3dG7tJCiyTNIekgKJ62KBBLc96KdDm75BMiwZ9qmRZm+Fuj1yW33b02lPBRjdx+0m5LGm6psiS2qNZjEz375T6Ja9GmXdLZ1vrdlIio+IGyepjxL19bdQkTYauDvaqOhMPmWD/saXe4ccfqjyVRyq6SYWidTGJKbJP1xiNsm5SHqbhADWtNcEgDXaTKo3SYBc1FUlVHgZBXesm1ZbSq0ksYlKrk1q62o+C1VbEoCZ/Zuwmuq+D22ZEZroJ7lfa7keYonr9isVa/aQwpFA1aO0mPyD5flIQUqVeEJzpj6Hwcove9XgfxEsVcNMMSTKNqeTvUrQgI/OcGnHELRtBAFSLwpT16fFH5WsS3CDVtkuqpPV6u5ipaL0gWqBH4CmM1GU0CkLFhiTvknppDyOznXId9hRIlfCwoAojtU4xbgnWrM6xZkmrdfPBbx3eo23Q3MQFPgeprKL/xaDU6FDqpCFqUk2mtmL5Ev9Raeb4ITchIFzrELZlXtx7675ljWdftWXq08i0B3ZSmfXIo4HpF+f6Ft+dh24UG7qlYCqkIOer2iFmWqMGZPvMuEWc9MQmz3ae37b/GXrdTUrbSKGsq5EwZFWVrIcNRaXm/VSjpsRosI2UUC3o4/NtpAiu/YpG7bvtoZBFWRvJV/mtQp3/DyEKgQkMURFcK1pIj10bEuvj11GDEFIEg7hg3CCk6afAp8O/Kxl5wNunh6k3oEboAKS3FwaVTLnPC3VrKvJyWTcGvZYpe82IxhSQtO/H/+20uQe8MKbUWhR9rxxwuSCs82Q9SAOSBWsk8mVthwolsUFXg9TskdXD422kZvwYz5kSzHMLcpVHxQXrPNfZIZJ1hyNr1916vucNO99QVwQNitL2ziO88wjvwKFyrCEPdCUPdKUxV9TjG2l7lqdKocVrKm6jHGzcY6gSC+lmOEpcLg5oBtfnOQIrvAs6BzSH8sVdOx74/qHmPEhOYyAf1wtEW5ylkmgwbXAlQf73yBVD1z47eXSfnigaRlom1PJETazFZmd0TF2mQeh1CfNLmqTTPeP7WtzYR0qgxTEJkhD6RaPzGSk12RrrbxiNAj8pwxhIKk7FmlIp6zP1gcQdvurTkVTbCYDBcjjIW+O3u4wT/3jro9v5phHrohVJ7baLstakykVjFbxGqxKx32xSCnLvPtHx6GM3h7bzwIPEgnQPbEHqg4qVoFR185FXd7/z/nujf3MnFouRQiMSUBU5yrFUfQ1/Lhhf4cDywxvIoQn7ROk3xWvfwCcvSvgGXUCFTgSuWy1btLAeVEKKFFApZsqXFQtXnL5xuNJebhXu2MEzybJvNpC4P2ct2f/GQ583cjMuGXehRPwSYnZrq0lYXmOa0iD6ET3wl4Zjf5JOQOZDY7KUhynvNYTHg/AFXMljsZzTFY65VUia7WjN5ffzrIltfj3ul4lc7PaOHa/zrb5u13o8F9HG/DS1vlVKKpOVz4T/424uPOcmRd2kkm/Vksa2StCuIA26YbO1fOKmn0xLmU/dOO1dojVea3OddZD0WGcVJHoMXKM0XpfaiW8nDgSiGoM0375wnRL8VzhbYyCdEXURfnEPV1nA6SIki3kg3YwUGabSD5nFSIkSDkcYrj1/ylIGbg9qBtephSIWbQ6ReSwqR0yFDUIbUDRZMSSVi81xdjK7OJHemep0I4wacPai4OfSOL0ug9N4uRrJvTFHC1RdFi6ApzXC0wHd3EVNTxfUIZ28ezXo3jIYb4OX5TZv+DSNew9mc49hi8cDZDRumGd0tTD4ieBXkgzDEvTA+VHpp+0b02Q4hDqs4Kx9SKGHhn/ytefwsF3d9mlvwYQDV7KOfeLjoKfxwEXhKfOzPYVrrL96ct/vn943ZJ+GqlPPLuu0SPi5v//vgueJy6+l2RuLArquUon31cpo+oC58HJJIk78r1CcPVYIvjQpTkmdgCCEhkzHRO7+6MHhkWD7r1e4RTvZBmFmurFcpf1UTTJVjcGY8BqyiR+OE73h8vWGu327Puy1gzHP8WSn9G83jb12/yL5iJvkxZvAhBN5qlJraumXmhReKLTNKQ2gKR4rzE9yF4xaSLwym7veT07WRIqnD/xqR4LGjLwn+CVn4BNN2pWwsoY/J5yli/MDZy8js+zibBGnpRZxWmpJ+BlM7RRoqAl6/h2Cz5wkOl6HS5B0OiBWCUu1ghfnAHEgvUCs9dTz7Y+3yC7RItdFZWrw/VW0oBJsQbxhRbnJQSS7GSntpUxEI2MwMAjLCSkYEjw4JcFAS7LgW3MIxiNZ1nsIyQ9hD4lY9ra7xoGHp+4ie4ELrgr+ZgY8SHZOTFRUuSD4ucyOJ/t1OMvcz5D8GPbwIA1JEZW1R5gRYX4pQON7S13aXZCLoERdOpAL7YUrPCP4L3IDiSojgj8+OZDHssw9iWSYkSoBsk2LYcSJIw4AZbFV2gsZuELwTCWIZEcqgFKhUit4xaTS6wi3+sssKEaRnOC7LLyfmfGtn9efovNzCetiJpXCOLU3EywopJpHBY/mBgtVBgTfnQOs57LAOonkaRuWHuHn9d+kcx1T6ueEzLgu+MXcXEeVPwv+eg6u/y6L62eQvABnMXBd0SYsCE+zb1iQuTCOEzJzj+A7c0OFKorg8uQK5Y9Z5l5G8gdG8rItRD2MUUJmBQTvzM1lVPmu4Bsn5/LrWeYuIDnHiFvhJ66XMgV5jJDZHYLfm5vHqLJa8Lsm5/Ffs8y9jeQtCDIUcbog811vJYwXCZlzXPC9GVzObddDS3sE1yZVBPau924WOPzodYmRMtgYTKqxbVSye9MWJEttL74HcKE7pUN6O4yXCKmrsfmcT6YEKVq6JfiVHJD+MwvSG0iuwAuVQLoJXrH5h8QP0uHCI+o44HpK8ANTggst7RfcygHXv7Pg+gzJx7A9ClzfkQbbQ9nBYXqehVawUPCCKQGHlvJtXvd5DuC+ygzORfDmF4n03KBHzIywVsF4GZwYFfzglMBCSwcEZ5OH5SrOAqsUiZuRabFcVLQIo1mBnYdG2Gjz+i+nBBha+q/gH2cG5k6YWsM95Aiqs6CbgaQcN1aVUiNdN8nv15WgA2kJ6jfA+ICQeXcK3pwBKZJEr3ejarFQmS94fWZIyc42ZplrQgIvQGUtiqaIY3XsnFCdfK62v5vxqQmfbKKMFIm3JPzeWJ/mm7/4/Un2vUJHP9y4rDbD9/7ZE34RFHrPj1QUzxrZcol/x47/tlTiJ8WhiKomf5dLui40TBpSeFBL7K90Bge9mJHpqT8QAABxhQhdt9lyy+DQZ8vhf8t5oOtixNXMTdZFTPw1c+zTWV8UFm++zD8qQ3CbPrracN8L7id3rtq8/s1/XT91xyu3nT5/cMmz49c2ntn9zsiPdvwfCmLsiWUdAAA=";
}
