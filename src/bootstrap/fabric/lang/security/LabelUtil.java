package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.util.HashSet;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;
import fabric.common.util.Triple;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.lang.security.PrincipalUtil;
import fabric.lang.security.SecurityCache;
import fabric.util.*;

/**
 * A Label is the runtime representation of a Jif label. A Label consists of a
 * set of components, each of which is a fabric.lang.security.Policy. This code
 * is mostly copied from Jif.
 */
public interface LabelUtil
  extends fabric.lang.Object
{
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.LabelUtil
    {
        
        public static native fabric.lang.security.Label noComponents();
        
        public static native fabric.lang.security.Label noComponents(
          fabric.worker.Store arg1);
        
        public static native fabric.lang.security.ConfPolicy bottomConf();
        
        public static native fabric.lang.security.IntegPolicy bottomInteg();
        
        public static native fabric.lang.security.ConfPolicy topConf();
        
        public static native fabric.lang.security.IntegPolicy topInteg();
        
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3);
        
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3);
        
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4);
        
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.PrincipalSet arg3);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.PrincipalSet arg3);
        
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3);
        
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3);
        
        public static native fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3);
        
        public static native fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.util.Collection arg3);
        
        public static native fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4);
        
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Principal arg3,
          fabric.lang.arrays.ObjectArray arg4);
        
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.PrincipalSet arg3);
        
        public static native fabric.lang.security.Label toLabel(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.IntegPolicy arg3);
        
        public static native fabric.lang.security.Label toLabel(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);
        
        public static native fabric.lang.security.Label toLabel(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);
        
        public static native fabric.lang.security.Label liftToLabel(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);
        
        public static native fabric.lang.security.Label liftToLabel(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2);
        
        public static native fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3);
        
        public static native fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3, boolean arg4);
        
        public static native fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3);
        
        public static native fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2,
          fabric.lang.security.Label arg3, boolean arg4);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, boolean arg4);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4,
          boolean arg5);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, boolean arg4);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4,
          boolean arg5);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, boolean arg4);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          fabric.lang.security.ConfPolicy arg3, java.util.Set arg4,
          boolean arg5);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, boolean arg4);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          fabric.lang.security.IntegPolicy arg3, java.util.Set arg4,
          boolean arg5);
        
        public static native boolean equivalentTo(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2);
        
        public static native boolean isReadableBy(
          fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2);
        
        public static native boolean isWritableBy(
          fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2);
        
        public static native boolean relabelsTo(
          fabric.lang.security.Label arg1, fabric.lang.security.Label arg2);
        
        public static native boolean acts_for(
          fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2);
        
        public static native boolean actsFor(
          fabric.lang.security.Label arg1, fabric.lang.security.Principal arg2);
        
        public static native boolean relabelsTo(
          fabric.lang.security.Policy arg1, fabric.lang.security.Policy arg2);
        
        public static native boolean relabelsTo(
          fabric.lang.security.Policy arg1, fabric.lang.security.Policy arg2,
          java.util.Set arg3);
        
        public static native java.lang.String stringValue(
          fabric.lang.security.Label arg1);
        
        public static native java.lang.String toString(
          fabric.lang.security.Label arg1);
        
        public static native int hashCode(fabric.lang.security.Label arg1);
        
        public static native void notifyNewDelegation(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2);
        
        public static native fabric.lang.Object accessCheck(
          fabric.lang.security.Label arg1, fabric.lang.Object arg2);
        
        public _Proxy(LabelUtil._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.LabelUtil
    {
        
        private native fabric.lang.security.LabelUtil
          fabric$lang$security$LabelUtil$();
        
        public static native fabric.lang.security.Label noComponents();
        
        public static native fabric.lang.security.Label noComponents(
          fabric.worker.Store store);
        
        public static native fabric.lang.security.ConfPolicy bottomConf();
        
        public static native fabric.lang.security.IntegPolicy bottomInteg();
        
        public static native fabric.lang.security.ConfPolicy topConf();
        
        public static native fabric.lang.security.IntegPolicy topInteg();
        
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal reader);
        
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection readers);
        
        /**
         * See the Jif signature for the explanation of lbl.
         */
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray readers);
        
        public static native fabric.lang.security.ConfPolicy readerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.PrincipalSet writers);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.lang.security.Principal owner,
          fabric.lang.security.Principal reader);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal reader);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection readers);
        
        /**
         * See the Jif signature for the explanation of lbl.
         */
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray readers);
        
        public static native fabric.lang.security.Label readerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.PrincipalSet readers);
        
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal writer);
        
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection writers);
        
        public static native fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.Principal writer);
        
        public static native fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.util.Collection writers);
        
        /**
         * See the Jif signature for the explanation of lbl.
         */
        public static native fabric.lang.security.Label writerPolicyLabel(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray writers);
        
        /**
         * See the Jif signature for the explanation of lbl.
         */
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Label lbl,
          fabric.lang.security.Principal owner,
          fabric.lang.arrays.ObjectArray writers);
        
        public static native fabric.lang.security.IntegPolicy writerPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal owner,
          fabric.lang.security.PrincipalSet writers);
        
        public static native fabric.lang.security.Label toLabel(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy cPolicy,
          fabric.lang.security.IntegPolicy iPolicy);
        
        public static native fabric.lang.security.Label toLabel(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy policy);
        
        public static native fabric.lang.security.Label toLabel(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy policy);
        
        public static native fabric.lang.security.Label liftToLabel(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy policy);
        
        public static native fabric.lang.security.Label liftToLabel(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy policy);
        
        public static native fabric.lang.security.Label join(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2);
        
        public static native fabric.lang.security.Label join(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2, boolean simplify);
        
        public static native fabric.lang.security.Label meet(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2);
        
        public static native fabric.lang.security.Label meet(
          fabric.worker.Store store, fabric.lang.security.Label l1,
          fabric.lang.security.Label l2, boolean simplify);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, boolean simplify);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s);
        
        public static native fabric.lang.security.ConfPolicy join(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s,
          boolean simplify);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, boolean simplify);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s);
        
        public static native fabric.lang.security.IntegPolicy join(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s,
          boolean simplify);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, boolean simplify);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s);
        
        public static native fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store store, fabric.lang.security.ConfPolicy p1,
          fabric.lang.security.ConfPolicy p2, java.util.Set s,
          boolean simplify);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, boolean simplify);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s);
        
        public static native fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store store, fabric.lang.security.IntegPolicy p1,
          fabric.lang.security.IntegPolicy p2, java.util.Set s,
          boolean simplify);
        
        public static native boolean equivalentTo(
          fabric.lang.security.Label l1, fabric.lang.security.Label l2);
        
        public static native boolean isReadableBy(
          fabric.lang.security.Label lbl, fabric.lang.security.Principal p);
        
        public static native boolean isWritableBy(
          fabric.lang.security.Label lbl, fabric.lang.security.Principal p);
        
        /**
         * @return true iff from <= to in the information-flow ordering.
         */
        public static native boolean relabelsTo(fabric.lang.security.Label from,
                                                fabric.lang.security.Label to);
        
        public static native boolean acts_for(
          fabric.lang.security.Label actor,
          fabric.lang.security.Principal granter);
        
        public static native boolean actsFor(
          fabric.lang.security.Label actor,
          fabric.lang.security.Principal granter);
        
        public static native boolean relabelsTo(
          fabric.lang.security.Policy from, fabric.lang.security.Policy to);
        
        public static native boolean relabelsTo(
          fabric.lang.security.Policy from, fabric.lang.security.Policy to,
          java.util.Set s);
        
        public static native java.lang.String stringValue(
          fabric.lang.security.Label lb);
        
        public static native java.lang.String toString(
          fabric.lang.security.Label lb);
        
        public static native int hashCode(fabric.lang.security.Label lb);
        
        private static native fabric.util.Set simplifyJoin(
          fabric.worker.Store store, fabric.util.Set policies,
          java.util.Set dependencies);
        
        private static native fabric.util.Set simplifyMeet(
          fabric.worker.Store store, fabric.util.Set policies,
          java.util.Set dependencies);
        
        public static native void notifyNewDelegation(
          fabric.lang.security.Principal granter,
          fabric.lang.security.Principal superior);
        
        /**
         * Throws an exception if o's store is not trusted to enforce
         accessLabel.
         * @param accessLabel
         * @param o
         */
        public static native fabric.lang.Object accessCheck(
          fabric.lang.security.Label accessLabel, fabric.lang.Object o);
        
        public native fabric.lang.Object $initLabels();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.LabelUtil._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        
        public fabric.worker.LocalStore get$localStore();
        
        public fabric.worker.LocalStore set$localStore(
          fabric.worker.LocalStore val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.LabelUtil._Static
        {
            
            public fabric.worker.LocalStore get$localStore() {
                return ((fabric.lang.security.LabelUtil._Static._Impl) fetch()).
                  get$localStore();
            }
            
            public fabric.worker.LocalStore set$localStore(
              fabric.worker.LocalStore val) {
                return ((fabric.lang.security.LabelUtil._Static._Impl) fetch()).
                  set$localStore(val);
            }
            
            public _Proxy(fabric.lang.security.LabelUtil._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.LabelUtil._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  LabelUtil.
                  _Static.
                  _Impl impl =
                  (fabric.lang.security.LabelUtil._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.LabelUtil._Static._Impl.class);
                $instance = (fabric.lang.security.LabelUtil._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.LabelUtil._Static
        {
            
            public fabric.worker.LocalStore get$localStore() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.localStore;
            }
            
            public fabric.worker.LocalStore set$localStore(
              fabric.worker.LocalStore val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.localStore = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public fabric.worker.LocalStore localStore;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.LabelUtil._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
}
