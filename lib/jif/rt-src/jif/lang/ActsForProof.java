package jif.lang;

import java.util.Set;

public abstract class ActsForProof {
    private final Principal actor;
    private final Principal granter;
    
    ActsForProof(Principal actor, Principal granter) {
        this.actor = actor;
        this.granter = granter;
    }

    public Principal getActor() {
        return actor;
    }
    public Principal getGranter() {
        return granter;
    }
    
    /* Gather the set of PrincipalUtil.DelegationPairs that
     * this proof relies on.
     */
    public void gatherDelegationDependencies(Set s) { }

}
