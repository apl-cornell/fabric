package jif.types;

import jif.types.label.Label;
import polyglot.types.SemanticException;

/**
 * A solver of Jif constraints. 
 */
public interface Solver {
    /**
     * Add the constraint c to the system
     */
    void addConstraint(Constraint c) throws SemanticException;
    
    /**
     * Solve the system of constraints.
     * 
     * @throws SemanticException if the Solver cannot find a solution to the
     *             system of contraints.
     */
    VarMap solve() throws SemanticException;
    
    /**
     * Substitute variables in L with the solution for the variables. Should only
     * be called after solve() has been called successfully.
     * @param L
     * @return L with variables replaced appropriately according to the solution.
     */
    Label applyBoundsTo(Label L);
}
