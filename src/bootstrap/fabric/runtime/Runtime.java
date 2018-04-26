package fabric.runtime;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
public interface Runtime
  extends fabric.lang.Object
{
    
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
      implements fabric.runtime.Runtime
    {
        
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
        
        public static native fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal arg1)
              throws java.lang.SecurityException;
        
        public static native fabric.lang.security.Principal user(
          fabric.lang.security.Principal arg1);
        
        public native java.io.PrintStream stderr(
          fabric.lang.security.Label arg1);
        
        public native java.io.PrintStream stdout(
          fabric.lang.security.Label arg1);
        
        public native java.io.InputStream stdin(
          fabric.lang.security.Label arg1);
        
        public native java.io.PrintStream out();
        
        public native java.io.InputStream in();
        
        public native java.io.PrintStream err();
        
        public static native int currentYear(
          fabric.lang.security.Principal arg1);
        
        public static native int currentMonth(
          fabric.lang.security.Principal arg1);
        
        public static native int currentDayOfMonth(
          fabric.lang.security.Principal arg1);
        
        public static native int currentHour(
          fabric.lang.security.Principal arg1);
        
        public static native int currentMinute(
          fabric.lang.security.Principal arg1);
        
        public static native void sleep(fabric.lang.security.Principal arg1,
                                        int arg2);
        
        public _Proxy(Runtime._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.runtime.Runtime
    {
        
        public fabric.lang.security.Principal get$dynp() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.dynp;
        }
        
        public fabric.lang.security.Principal set$dynp(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.dynp = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * The principal under whose authority the JVM is running.
         */
        private fabric.lang.security.Principal dynp;
        
        public fabric.worker.Store get$local() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.local;
        }
        
        public fabric.worker.Store set$local(fabric.worker.Store val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.local = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.Store local;
        
        private native fabric.runtime.Runtime fabric$runtime$Runtime$(
          fabric.lang.security.Principal p);
        
        /**
         * Gets a <code>Runtime</code> object parameterized with the
         * principal <code>p</code>.
         */
        public static native fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal p)
              throws java.lang.SecurityException;
        
        /** Get the current user  */
        public static native fabric.lang.security.Principal user(
          fabric.lang.security.Principal parameter);
        
        private native fabric.lang.security.Label defaultOutputLabel();
        
        private native fabric.lang.security.Label defaultInputLabel();
        
        /**
         * Gets the standard error output.
         * The output channel is parameterized by <code>l</code>.
         */
        public native java.io.PrintStream stderr(fabric.lang.security.Label l);
        
        /**
         * Gets the standard output.
         * This output channel is parameterized by <code>l</code>.
         */
        public native java.io.PrintStream stdout(fabric.lang.security.Label l);
        
        /**
         * Gets the standard input.
         * This input channel is parameterized by <code>l</code>.
         */
        public native java.io.InputStream stdin(fabric.lang.security.Label l);
        
        /**
         * Get the standard output parameterized by the default label, which
         * has only one reader: the principal of this <code>Runtime</code>
         object.
         */
        public native java.io.PrintStream out();
        
        /**
         * Get the standard input parameterized by the default label, which
         * has only one reader: the principal of this <code>Runtime</code>
         object.
         */
        public native java.io.InputStream in();
        
        /**
         * Get the standard error output parameterized by the default label,
         which
         * has only one reader: the principal of this <code>Runtime</code>
         object.
         */
        public native java.io.PrintStream err();
        
        public static native int currentYear(
          fabric.lang.security.Principal dummy);
        
        public static native int currentMonth(
          fabric.lang.security.Principal dummy);
        
        public static native int currentDayOfMonth(
          fabric.lang.security.Principal dummy);
        
        public static native int currentHour(
          fabric.lang.security.Principal dummy);
        
        public static native int currentMinute(
          fabric.lang.security.Principal dummy);
        
        public static native void sleep(fabric.lang.security.Principal dummy,
                                        int s);
        
        public native fabric.lang.Object $initLabels();
        
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
            this.dynp = (fabric.lang.security.Principal)
                          $readRef(fabric.lang.security.Principal._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
            this.local = (fabric.worker.Store) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.runtime.Runtime._Impl src =
              (fabric.runtime.Runtime._Impl) other;
            this.dynp = src.dynp;
            this.local = src.local;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public boolean get$_nativeOK();
        
        public boolean set$_nativeOK(boolean val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.runtime.Runtime._Static
        {
            
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
                  _Impl impl =
                  (fabric.runtime.Runtime._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.runtime.Runtime._Static._Impl.class);
                $instance = (fabric.runtime.Runtime._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.runtime.Runtime._Static
        {
            
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
                this._nativeOK = in.readBoolean();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.runtime.Runtime._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
