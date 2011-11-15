package jif.lang;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class FromDisjunctProof extends ActsForProof {
    private final Map disjunctProofs; // map from disjuncts to proofs to Granter 
    FromDisjunctProof(DisjunctivePrincipal actor, Principal granter, 
                      Map disjunctProofs) {
        super(actor, granter);
        this.disjunctProofs = disjunctProofs;
    }
    Map getDisjunctProofs() {
        return disjunctProofs;
    }
    public void gatherDelegationDependencies(Set s) {
        DisjunctivePrincipal dp = (DisjunctivePrincipal)getActor();
        for (Iterator iter = dp.disjuncts.iterator(); iter.hasNext(); ) {
            Principal disjunct = (Principal)iter.next();
            ActsForProof pr = (ActsForProof)this.getDisjunctProofs().get(disjunct);
            pr.gatherDelegationDependencies(s);
        }
    }
}
