package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.FieldAssign;
import polyglot.ast.Id;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.types.Flags;
import fabil.visit.ProxyRewriter;
import fabil.visit.ReadWriteChecker.State;

public class FieldAssignExt_c extends ExprExt_c {

  /**
   * For read/write optimization. Access state of the target on the LHS if it is
   * a local variable.
   */
  private State accessState;

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter pr) {
    FieldAssign assign = node();
    Field field = (Field) assign.left();
    Flags flags = field.flags();
    Receiver target = field.target();
    boolean rewriteTarget = true;
    if (target instanceof Special) {
      Special special = (Special) target;
      rewriteTarget =
          special.kind() != Special.THIS || special.qualifier() != null;
    }
    if (rewriteTarget) target = (Receiver) field.visitChild(target, pr);
    String name = ((Id) field.visitChild(field.id(), pr)).id();
    Expr rhs = (Expr) field.visitChild(assign.right(), pr);

    // If not assigning to a pure Fabric object, keep it as an assignment.
    if (!pr.typeSystem().isPureFabricType(target.type()))
      return assign.right(rhs);

    // If we're assigning to a final field, we must be in a constructor or an
    // initializer. Keep it as an assignment, since no setters will be
    // generated.
    if (flags.isFinal()) return assign.right(rhs);

    if (accessState != null) {
      target = pr.replaceTarget(target, accessState);

      if (accessState.written()) {
        field = field.target(target);
        return assign.left(field).right(rhs);
      }
    }

    String quote = "%T";
    if (target instanceof Expr) quote = "%E";

    if (flags.isStatic()) quote += ".$static";

    return pr.qq().parseExpr(quote + ".set$" + name + "(%E)", target, rhs);
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
