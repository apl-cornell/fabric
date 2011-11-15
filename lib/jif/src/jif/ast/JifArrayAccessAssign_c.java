package jif.ast;

import polyglot.ast.ArrayAccess;
import polyglot.ast.ArrayAccessAssign_c;
import polyglot.ast.Expr;
import polyglot.ast.Assign.Operator;
import polyglot.util.Position;

/**
 * A <code>ArrayAccessAssign_c</code> represents a Java assignment expression
 * to an array element.  For instance, <code>A[3] = e</code>.
 * 
 * The class of the <code>Expr</code> returned by
 * <code>ArrayAccessAssign_c.left()</code>is guaranteed to be an
 * <code>ArrayAccess</code>.
 */
public class JifArrayAccessAssign_c extends ArrayAccessAssign_c 
{
  public JifArrayAccessAssign_c(Position pos, ArrayAccess left, Operator op, Expr right) {
        super(pos, left, op, right);
    }

/** Since Jif's type system makes arrays invariant 
   * on the base type, no array store exceptions can 
   * be thrown. */
  public boolean throwsArrayStoreException() {
    return false;
  }

}
