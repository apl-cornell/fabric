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
