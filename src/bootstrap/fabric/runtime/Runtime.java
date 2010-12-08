package fabric.runtime;


public interface Runtime extends fabric.lang.Object {
    
    public fabric.lang.security.Principal get$dynp();
    
    public fabric.lang.security.Principal set$dynp(
      fabric.lang.security.Principal val);
    
    public fabric.worker.Store get$local();
    
    public fabric.worker.Store set$local(fabric.worker.Store val);
    
    public java.io.PrintStream stderr(fabric.lang.security.Label l);
    
    public java.io.PrintStream stdout(fabric.lang.security.Label l);
    
    public java.io.InputStream stdin(fabric.lang.security.Label l);
    
    public java.io.PrintStream out();
    
    public java.io.InputStream in();
    
    public java.io.PrintStream err();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.runtime.Runtime
    {
        
        native public fabric.lang.security.Principal get$dynp();
        
        native public fabric.lang.security.Principal set$dynp(
          fabric.lang.security.Principal val);
        
        native public fabric.worker.Store get$local();
        
        native public fabric.worker.Store set$local(fabric.worker.Store val);
        
        native public static fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal arg1)
              throws java.lang.SecurityException;
        
        native public static fabric.lang.security.Principal user(
          fabric.lang.security.Principal arg1);
        
        native public java.io.PrintStream stderr(
          fabric.lang.security.Label arg1);
        
        native public java.io.PrintStream stdout(
          fabric.lang.security.Label arg1);
        
        native public java.io.InputStream stdin(
          fabric.lang.security.Label arg1);
        
        native public java.io.PrintStream out();
        
        native public java.io.InputStream in();
        
        native public java.io.PrintStream err();
        
        native public static int currentYear(
          fabric.lang.security.Principal arg1);
        
        native public static int currentMonth(
          fabric.lang.security.Principal arg1);
        
        native public static int currentDayOfMonth(
          fabric.lang.security.Principal arg1);
        
        native public static int currentHour(
          fabric.lang.security.Principal arg1);
        
        native public static int currentMinute(
          fabric.lang.security.Principal arg1);
        
        native public static void sleep(fabric.lang.security.Principal arg1,
                                        int arg2);
        
        public _Proxy(Runtime._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.runtime.Runtime
    {
        
        native public fabric.lang.security.Principal get$dynp();
        
        native public fabric.lang.security.Principal set$dynp(
          fabric.lang.security.Principal val);
        
        native public fabric.worker.Store get$local();
        
        native public fabric.worker.Store set$local(fabric.worker.Store val);
        
        private _Impl(fabric.worker.Store $location,
                      fabric.lang.security.Label $label,
                      fabric.lang.security.Principal p) {
            super($location, $label);
        }
        
        native public static fabric.runtime.Runtime getRuntime(
          fabric.lang.security.Principal p)
              throws java.lang.SecurityException;
        
        native public static fabric.lang.security.Principal user(
          fabric.lang.security.Principal parameter);
        
        native private fabric.lang.security.Label defaultOutputLabel();
        
        native private fabric.lang.security.Label defaultInputLabel();
        
        native public java.io.PrintStream stderr(fabric.lang.security.Label l);
        
        native public java.io.PrintStream stdout(fabric.lang.security.Label l);
        
        native public java.io.InputStream stdin(fabric.lang.security.Label l);
        
        native public java.io.PrintStream out();
        
        native public java.io.InputStream in();
        
        native public java.io.PrintStream err();
        
        native public static int currentYear(
          fabric.lang.security.Principal dummy);
        
        native public static int currentMonth(
          fabric.lang.security.Principal dummy);
        
        native public static int currentDayOfMonth(
          fabric.lang.security.Principal dummy);
        
        native public static int currentHour(
          fabric.lang.security.Principal dummy);
        
        native public static int currentMinute(
          fabric.lang.security.Principal dummy);
        
        native public static void sleep(fabric.lang.security.Principal dummy,
                                        int s);
        
        native protected fabric.lang.Object._Proxy $makeProxy();
        
        native public void $serialize(java.io.ObjectOutput out,
                                      java.util.List refTypes,
                                      java.util.List intraStoreRefs,
                                      java.util.List interStoreRefs)
              throws java.io.IOException;
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, in, refTypes,
                  intraStoreRefs);
        }
        
        native public void $copyAppStateFrom(fabric.lang.Object._Impl other);
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public boolean get$_nativeOK();
        
        public boolean set$_nativeOK(boolean val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.runtime.Runtime._Static
        {
            
            native public boolean get$_nativeOK();
            
            native public boolean set$_nativeOK(boolean val);
            
            public _Proxy(fabric.runtime.Runtime._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.runtime.Runtime._Static
        {
            
            native public boolean get$_nativeOK();
            
            native public boolean set$_nativeOK(boolean val);
            
            public _Impl(fabric.worker.Store store,
                         fabric.lang.security.Label label)
                  throws fabric.net.UnreachableNodeException {
                super(store, label);
            }
            
            native protected fabric.lang.Object._Proxy $makeProxy();
            
            native private void $init();
        }
        
    }
    
}
