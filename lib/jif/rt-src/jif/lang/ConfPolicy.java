package jif.lang;

import java.util.Set;


public interface ConfPolicy extends Policy {    
    /**
     * Return the join of this policy and p. The set s contains all 
     * delegations (i.e., DelegationPairs) that this join result depends upon.
     */
    ConfPolicy join(ConfPolicy p, Set dependencies);
    ConfPolicy meet(ConfPolicy p, Set dependencies);
    ConfPolicy join(ConfPolicy p);
    ConfPolicy meet(ConfPolicy p);
}
