package jif.lang;

import java.util.*;
import java.util.Iterator;
import java.util.Set;

/**
 * A conjunction of two or more (non-null) principals
 */
public final class ConjunctivePrincipal implements Principal {
    final Set conjuncts;
    private Integer hashCode;
    
    ConjunctivePrincipal(Set conjuncts) {
        this.conjuncts = conjuncts;
    }
    public String name() {
        StringBuffer sb = new StringBuffer();
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next(); 
            sb.append(PrincipalUtil.toString(p));
            if (iter.hasNext()) sb.append("&");
        }
        return sb.toString();
    }

    public boolean delegatesTo(Principal p) {
        if (p instanceof ConjunctivePrincipal) {
            ConjunctivePrincipal cp = (ConjunctivePrincipal)p;
            return cp.conjuncts.containsAll(this.conjuncts);
        }
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal q = (Principal)iter.next();
            if (!PrincipalUtil.delegatesTo(q, p)) return false;
        }
        // every conjuct delegates to p.
        return true;
    }

    public int hashCode() {
        if (hashCode == null) {
            hashCode = new Integer(conjuncts.hashCode()); 
        }
        return hashCode.intValue();
    }

    public boolean equals(Principal p) {
        if (p instanceof ConjunctivePrincipal) {
            ConjunctivePrincipal that = (ConjunctivePrincipal)p;
            return this.hashCode() == that.hashCode() && this.conjuncts.equals(that.conjuncts) &&
                that.conjuncts.equals(this.conjuncts);
        }
        return false;
    }

    public boolean isAuthorized(Object authPrf, Closure closure, Label lb, boolean executeNow) {
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next();
            if (!p.isAuthorized(authPrf, closure, lb, executeNow)) return false;
        }
        // all conjuncts authorize the closure.
        return true;
    }
    

    public ActsForProof findProofUpto(Principal p, Object searchState) {
        Map proofs = new HashMap();
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal q = (Principal)iter.next();
            ActsForProof prf = PrincipalUtil.findActsForProof(p, q, searchState);
            if (prf == null) return null;
            proofs.put(q, prf);
        }
        
        // proofs contains a proof for every conjunct,
        // which is sufficent for a proof to the conjunctive principal
        return new ToConjunctProof(p, this, proofs);

    }

    public ActsForProof findProofDownto(Principal q, Object searchState) {
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal witness = (Principal)iter.next();
            ActsForProof prf = PrincipalUtil.findActsForProof(witness, q, searchState);
            if (prf != null) {
                // have found a proof from witness to q
                DelegatesProof step = new DelegatesProof(this, witness);
                return new TransitiveProof(step, witness, prf);
            }
        }
        return null;
    }
}
