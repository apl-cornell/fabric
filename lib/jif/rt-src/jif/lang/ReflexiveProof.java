package jif.lang;

import java.util.Set;

public final class ReflexiveProof extends ActsForProof {

    /**
     * Either p == q or p and q are non null and p.equals(q) and q.equals(p)
     * @param p
     * @param q
     */
    ReflexiveProof(Principal p, Principal q) {
        super(p, q);
    }

    public void gatherDelegationDependencies(Set s) {
        // no dependencies
    }

}
