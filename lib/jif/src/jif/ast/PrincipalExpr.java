package jif.ast;

import polyglot.ast.Expr;

/** An immutable representation of the Jif <code>new principal</code> 
 *  expression. 
 */
public interface PrincipalExpr extends Expr {
    PrincipalNode principal();
    PrincipalExpr principal(PrincipalNode principal);
}
