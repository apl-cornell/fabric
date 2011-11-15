package jif.ast;

import jif.types.label.Policy;
import polyglot.ast.Node;

/** A policy label node. A policy label is like <code>owner: r1, r2,...rn</code>. 
 */
public interface PolicyNode extends Node {
    // if the node is disambiguated, then this should return a non-null value.
    Policy policy();
    
    PrincipalNode owner();
    PolicyNode owner(PrincipalNode owner);
}
