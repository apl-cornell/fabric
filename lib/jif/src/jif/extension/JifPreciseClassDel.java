package jif.extension;

import java.util.Set;

import polyglot.ast.Expr;

/**
 * Marker interface for nodes that want to track the
 * precise classes of a sub-expression, e.g., JifCastDel.
 */
public interface JifPreciseClassDel {

    /**
     * 
     * @return The Expr that the node is interested in finding the precise 
     * classes for.
     */
    Expr getPreciseClassExpr();

    void setPreciseClass(Set preciseClasses);

}
