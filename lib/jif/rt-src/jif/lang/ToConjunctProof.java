package jif.lang;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ToConjunctProof extends ActsForProof {
    private final Map conjunctProofs;
    ToConjunctProof(Principal actor, ConjunctivePrincipal granter,
                    Map conjunctProofs) {
        super(actor, granter);
        this.conjunctProofs = conjunctProofs;
    }
    Map getConjunctProofs() {
        return conjunctProofs;
    }
    public void gatherDelegationDependencies(Set s) {
        ConjunctivePrincipal cp = (ConjunctivePrincipal)getGranter();
        for (Iterator iter = cp.conjuncts.iterator(); iter.hasNext(); ) {
            Principal conjunct = (Principal)iter.next();
            ActsForProof pr = (ActsForProof)this.getConjunctProofs().get(conjunct);
            pr.gatherDelegationDependencies(s);
        }
    }
}
