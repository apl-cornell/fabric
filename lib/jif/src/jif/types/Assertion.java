package jif.types;

import polyglot.types.TypeObject;

/**
 * An <code>Assertion</code> represents a condition on labels and/or principals 
 * that is assumed to hold true. Label environments 
 * ({@link jif.types.hierarchy.LabelEnv LabelEnv}) 
 * contain collections of <code>Constraint</code>s.
 * 
 * A n<code>Assertion</code> is not the same as a {@link jif.types.LabelConstraint  
 * LabelConstraint}, which represents a condition on labels that needs to be
 * satisfied (as opposed to being assumed to be satsified).
 * 
 * @see jif.types.hierarchy.LabelEnv 
 * @see jif.types.LabelConstraint 
 */
public interface Assertion extends TypeObject
{
}
