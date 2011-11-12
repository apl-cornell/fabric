package fabric.lang.security;

public interface ThisPrincipalPlaceholder
  extends fabric.lang.security.AbstractPrincipal
{
    public static class _Proxy
    extends fabric.
      lang.
      security.
      AbstractPrincipal.
      _Proxy
      implements fabric.lang.security.ThisPrincipalPlaceholder
    {
        
        public static native fabric.lang.security.ThisPrincipalPlaceholder
          getInstance(
          );
        
        public _Proxy(ThisPrincipalPlaceholder.
                        _Impl impl) {
            super(impl);
        }
        
        public _Proxy(fabric.
                        worker.
                        Store store,
                      long onum) {
            super(store,
                  onum);
        }
    }
    
    final public static class _Impl
    extends fabric.
      lang.
      security.
      AbstractPrincipal.
      _Impl
      implements fabric.lang.security.ThisPrincipalPlaceholder
    {
        
        public static native fabric.lang.security.ThisPrincipalPlaceholder
          getInstance(
          );
        
        public _Impl(fabric.
                       worker.
                       Store $location,
                     fabric.
                       lang.
                       security.
                       Label $label,
                     fabric.
                       lang.
                       security.
                       Label $accesslabel) {
            super($location,
                  $label,
                  $accesslabel);
        }
        
        protected native fabric.
          lang.
          Object.
          _Proxy
          $makeProxy(
          );
        
        public native void
          $serialize(
          java.
            io.
            ObjectOutput out,
          java.
            util.
            List refTypes,
          java.
            util.
            List intraStoreRefs,
          java.
            util.
            List interStoreRefs)
              throws java.
          io.
          IOException;
        
        public _Impl(fabric.
                       worker.
                       Store store,
                     long onum,
                     int version,
                     long expiry,
                     long label,
                     long accessLabel,
                     java.
                       io.
                       ObjectInput in,
                     java.
                       util.
                       Iterator refTypes,
                     java.
                       util.
                       Iterator intraStoreRefs)
              throws java.
          io.
          IOException,
            java.
          lang.
          ClassNotFoundException {
            super(store,
                  onum,
                  version,
                  expiry,
                  label,
                  accessLabel,
                  in,
                  refTypes,
                  intraStoreRefs);
        }
    }
    
    interface _Static
      extends fabric.
                lang.
                Object,
              Cloneable
    {
        
        public fabric.lang.security.ThisPrincipalPlaceholder
          get$INSTANCE(
          );
        
        public fabric.lang.security.ThisPrincipalPlaceholder
          set$INSTANCE(
          fabric.lang.security.ThisPrincipalPlaceholder val);
        
        final class _Proxy
        extends fabric.
          lang.
          Object.
          _Proxy
          implements fabric.
                       lang.
                       security.
                       ThisPrincipalPlaceholder.
                       _Static
        {
            
            public native fabric.lang.security.ThisPrincipalPlaceholder
              get$INSTANCE(
              );
            
            public native fabric.lang.security.ThisPrincipalPlaceholder
              set$INSTANCE(
              fabric.lang.security.ThisPrincipalPlaceholder val);
            
            public _Proxy(fabric.
                            lang.
                            security.
                            ThisPrincipalPlaceholder.
                            _Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.
                            worker.
                            Store store,
                          long onum) {
                super(store,
                      onum);
            }
            
            final public static fabric.
              lang.
              security.
              ThisPrincipalPlaceholder.
              _Static
              $instance;
            
            static {$instance = null; }
        }
        
        class _Impl
        extends fabric.
          lang.
          Object.
          _Impl
          implements fabric.
                       lang.
                       security.
                       ThisPrincipalPlaceholder.
                       _Static
        {
            
            public native fabric.lang.security.ThisPrincipalPlaceholder
              get$INSTANCE(
              );
            
            public native fabric.lang.security.ThisPrincipalPlaceholder
              set$INSTANCE(
              fabric.lang.security.ThisPrincipalPlaceholder val);
            
            private fabric.lang.security.ThisPrincipalPlaceholder
              INSTANCE;
            
            public _Impl(fabric.
                           worker.
                           Store store,
                         fabric.
                           lang.
                           security.
                           Label label,
                         fabric.
                           lang.
                           security.
                           Label accessLabel)
                  throws fabric.
              net.
              UnreachableNodeException {
                super(store,
                      label,
                      accessLabel);
            }
            
            protected native fabric.
              lang.
              Object.
              _Proxy
              $makeProxy(
              );
            
            private native void
              $init(
              );
        }
    }
}
