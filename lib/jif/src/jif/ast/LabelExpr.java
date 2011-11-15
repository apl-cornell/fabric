package jif.ast;

import polyglot.ast.Expr;

/** An immutable representation of the Jif <code>new label</code> 
 *  statement. 
 */
public interface LabelExpr extends Expr {
    LabelNode label();
    LabelExpr label(LabelNode label);
}
