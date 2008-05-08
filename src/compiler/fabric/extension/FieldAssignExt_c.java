package fabric.extension;

import polyglot.ast.*;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import fabric.visit.ProxyRewriter;
import fabric.visit.ReadWriteChecker.State;

public class FieldAssignExt_c extends ExprExt_c {

  private State accessState;

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    FieldAssign assign = node();
    Field field = (Field) assign.left();
    Flags flags = field.flags();
    Receiver target = (Receiver) field.visitChild(field.target(), rewriter);
    String name = ((Id) field.visitChild(field.id(), rewriter)).id();
    Expr rhs = (Expr) field.visitChild(assign.right(), rewriter);
    
    // XXX hack for now since locals could have turned into Impls by optimization
    if (rhs instanceof Local) {
      rhs = rewriter.qq().parseExpr("(%E == null ? null : %E.$getProxy())", rhs);
    }

    // If we're assigning to a final field, we must be in a constructor or an
    // initializer. Keep it as an assignment, since no setters will be
    // generated.
    if (flags.isFinal()) return assign.right(rhs);
    
    if (accessState != null) {
      ClassType ct = (ClassType) target.type();
      
      if (!accessState.resident()) {
        target = rewriter.qq().parseExpr("(%E = (%T) %E.fetch())", 
            target, ct, target);
      }
      
      if (accessState.written()) {
        if (!(target instanceof Special)) {
          target = rewriter.qq().parseExpr("((" + ct.fullName() + ".$Impl) %E)",
              target);
        }
        
        field = field.target(target);
        return assign.left(field).right(rhs);
      }
    }

    String quote = "%T";
    if (target instanceof Expr) quote = "%E";
    
    if (flags.isStatic()) quote += ".$static";
    
    return rewriter.qq()
        .parseExpr(quote + ".set$" + name + "(%E)", target, rhs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public FieldAssign node() {
    return (FieldAssign) super.node();
  }

  public void accessState(State s) {
    this.accessState = s;
  }
  
  public State accessState() {
    return accessState;
  }

}
