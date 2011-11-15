package jif.ast;

import java.util.Set;

import polyglot.ast.Node;

/** The root of various constraint nodes. 
 */
public interface ConstraintNode extends Node {
    Set constraints();
    ConstraintNode constraints(Set constraint);
}
