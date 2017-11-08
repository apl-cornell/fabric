package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
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
    
}
