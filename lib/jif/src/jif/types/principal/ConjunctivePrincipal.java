package jif.types.principal;

import java.util.Set;


/** 
 * A conjunctive principal represents the conjunction of two principals A&B.
 * The conjunctive principal A&B can act for A and it can act for B.
 */
public interface ConjunctivePrincipal extends Principal {
    Set conjuncts();
}
