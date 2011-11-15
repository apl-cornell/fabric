package jif.types.principal;

import java.util.Set;


/** 
 * A disjunctive principal represents the disjunction of two principals "A or B".
 * The conjunctive principal "A or B" delegates its authority to A and B, that is,
 * A can act for "A or B" and B can act for "A or B".
 */
public interface DisjunctivePrincipal extends Principal {
    Set disjuncts();
}
