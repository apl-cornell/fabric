package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Set;

/**
 * A Label is the runtime representation of a Fabric label. This code is mostly
 * copied from Jif.
 */
public interface Label extends fabric.lang.Object {
    /**
   * Returns true iff this <= l. If the method returns true, then s has all of
   * the delegations (i.e., DelegationPairs) added to it that the result depends
   * upon. If the method returns false, then s has no eleents added to it.
   */
    boolean relabelsTo(fabric.lang.security.Label l, java.util.Set s);
    
    fabric.lang.security.Label join(fabric.worker.Store store,
                                    fabric.lang.security.Label l);
    
    fabric.lang.security.Label meet(fabric.worker.Store store,
                                    fabric.lang.security.Label l);
    
    fabric.lang.security.Label join(fabric.worker.Store store,
                                    fabric.lang.security.Label l,
                                    boolean simplify);
    
    fabric.lang.security.Label meet(fabric.worker.Store store,
                                    fabric.lang.security.Label l,
                                    boolean simplify);
    
    fabric.lang.security.ConfPolicy confPolicy();
    
    fabric.lang.security.IntegPolicy integPolicy();
    
    fabric.lang.security.SecretKeyObject keyObject();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Label {
        public boolean relabelsTo(fabric.lang.security.Label arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.Label) fetch()).relabelsTo(arg1,
                                                                     arg2);
        }
        
        public fabric.lang.security.Label join(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2) {
            return ((fabric.lang.security.Label) fetch()).join(arg1, arg2);
        }
        
        public fabric.lang.security.Label meet(
          fabric.worker.Store arg1, fabric.lang.security.Label arg2) {
            return ((fabric.lang.security.Label) fetch()).meet(arg1, arg2);
        }
        
        public fabric.lang.security.Label join(fabric.worker.Store arg1,
                                               fabric.lang.security.Label arg2,
                                               boolean arg3) {
            return ((fabric.lang.security.Label) fetch()).join(arg1, arg2,
                                                               arg3);
        }
        
        public fabric.lang.security.Label meet(fabric.worker.Store arg1,
                                               fabric.lang.security.Label arg2,
                                               boolean arg3) {
            return ((fabric.lang.security.Label) fetch()).meet(arg1, arg2,
                                                               arg3);
        }
        
        public fabric.lang.security.ConfPolicy confPolicy() {
            return ((fabric.lang.security.Label) fetch()).confPolicy();
        }
        
        public fabric.lang.security.IntegPolicy integPolicy() {
            return ((fabric.lang.security.Label) fetch()).integPolicy();
        }
        
        public fabric.lang.security.SecretKeyObject keyObject() {
            return ((fabric.lang.security.Label) fetch()).keyObject();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -36, 64, -121, 112,
    -35, -115, 80, 58, -33, -114, 23, -28, 86, -90, -127, 3, -15, 72, 51, -43,
    -114, 25, 103, 82, 48, -46, -43, 30, -15, -126, -98, -42 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
