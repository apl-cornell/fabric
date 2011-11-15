package jif.ast;

import polyglot.ast.Expr;


/** An immutable representation of the Jif checked <code>endorse</code> statement. 
 *  <p>Grammar: <code>endorse(expr, label_from to label_to) stmt</code> </p>
 */
public interface CheckedEndorseStmt extends EndorseStmt
{
    Expr expr();
    CheckedEndorseStmt expr(Expr expr);    
}
