package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.util.Set;

/**
 * A Policy is a component of a label, and is either an integrity policy or a
 * confidentiality policy. This code is mostly copied from Jif.
 */
public interface Policy
  extends fabric.lang.Object
{
    
    /**
     * Does this policy relabel to policy p? If this method returns true, then
     all
     * delegations that this result depend upon (i.e., DelegationPairs) should
     be
     * added to the set s. If this method returns false, then the set is not
     * altered at all.
     * 
     * @param p
     * @param dependencies
     * @return
     */
    boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Policy
    {
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.Policy) fetch()).relabelsTo(arg1,
                                                                      arg2);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
