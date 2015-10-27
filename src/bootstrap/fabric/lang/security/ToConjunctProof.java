package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import fabric.util.Iterator;
import fabric.util.Map;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface ToConjunctProof
  extends fabric.lang.security.ActsForProof
{
    
    public fabric.util.Map get$conjunctProofs();
    
    public fabric.util.Map set$conjunctProofs(fabric.util.Map val);
    
    public fabric.lang.security.ToConjunctProof
      fabric$lang$security$ToConjunctProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.ConjunctivePrincipal granter,
      fabric.util.Map conjunctProofs);
    
    public fabric.util.Map getConjunctProofs();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.ToConjunctProof
    {
        
        public fabric.util.Map get$conjunctProofs() {
            return ((fabric.lang.security.ToConjunctProof._Impl) fetch()).
              get$conjunctProofs();
        }
        
        public fabric.util.Map set$conjunctProofs(fabric.util.Map val) {
            return ((fabric.lang.security.ToConjunctProof._Impl) fetch()).
              set$conjunctProofs(val);
        }
        
        public native fabric.lang.security.ToConjunctProof
          fabric$lang$security$ToConjunctProof$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.ConjunctivePrincipal arg2, fabric.util.Map arg3);
        
        public native fabric.util.Map getConjunctProofs();
        
        public _Proxy(ToConjunctProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.ToConjunctProof
    {
        
        public fabric.util.Map get$conjunctProofs() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.conjunctProofs;
        }
        
        public fabric.util.Map set$conjunctProofs(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.conjunctProofs = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Map conjunctProofs;
        
        public native fabric.lang.security.ToConjunctProof
          fabric$lang$security$ToConjunctProof$(
          fabric.lang.security.Principal actor,
          fabric.lang.security.ConjunctivePrincipal granter,
          fabric.util.Map conjunctProofs);
        
        public native fabric.util.Map getConjunctProofs();
        
        public native void gatherDelegationDependencies(java.util.Set s);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ToConjunctProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.conjunctProofs, refTypes, out,
                      intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, long label, long accessLabel,
                     java.io.ObjectInput in, java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, label, accessLabel, in,
                  refTypes, intraStoreRefs, interStoreRefs);
            this.conjunctProofs = (fabric.util.Map)
                                    $readRef(fabric.util.Map._Proxy.class,
                                             (fabric.common.RefTypeEnum)
                                               refTypes.next(),
                                             in,
                                             store,
                                             intraStoreRefs,
                                             interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.ToConjunctProof._Impl src =
              (fabric.lang.security.ToConjunctProof._Impl) other;
            this.conjunctProofs = src.conjunctProofs;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ToConjunctProof._Static
        {
            
            public _Proxy(fabric.lang.security.ToConjunctProof._Static.
                            _Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ToConjunctProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ToConjunctProof.
                  _Static.
                  _Impl impl =
                  (fabric.lang.security.ToConjunctProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ToConjunctProof._Static._Impl.class);
                $instance = (fabric.lang.security.ToConjunctProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ToConjunctProof._Static
        {
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, long label, long accessLabel,
                         java.io.ObjectInput in, java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, label, accessLabel, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.ToConjunctProof._Static._Proxy(
                  this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
