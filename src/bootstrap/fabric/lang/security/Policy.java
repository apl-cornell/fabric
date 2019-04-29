package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Set;

/**
 * A Policy is a component of a label, and is either an integrity policy or a
 * confidentiality policy. This code is mostly copied from Jif.
 */
public interface Policy extends fabric.lang.Object {
    /**
   * Does this policy relabel to policy p? If this method returns true, then all
   * delegations that this result depend upon (i.e., DelegationPairs) should be
   * added to the set s. If this method returns false, then the set is not
   * altered at all.
   * 
   * @param p
   * @param dependencies
   * @return
   */
    boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.Policy {
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.Policy) fetch()).relabelsTo(arg1,
                                                                      arg2);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -45, -90, -39, 90, -30,
    13, -38, -29, 108, -13, -109, -4, 113, -96, -103, -85, 22, -92, 40, -127,
    18, 8, -6, -30, -14, -122, -48, 9, 25, 85, -20, 69 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
