package fabil.extension;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.*;
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
   * @see fabil.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabil.visit.ProxyRewriter)
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
    if (flags.isFinal()) {
      // We need to rewrite the left-hand side if translating a static field.
      if (!flags.isStatic()) return assign.right(rhs);
      
      Expr lhs = pr.qq().parseExpr("this." + name);
      return assign.left(lhs).right(rhs);
    }

    if (accessState != null) {
      target = pr.replaceTarget(target, accessState);

      if (accessState.written()) {
        field = field.target(target);
        return assign.left(field).right(rhs);
      }
    }

    String quote = "%T";
    List<Object> subs = new ArrayList<Object>(2);
    if (flags.isStatic()) {
      quote += ".$Static.$Proxy.$instance";
      subs.add(field.fieldInstance().container());
    } else {
      if (target instanceof Expr) quote = "%E";
      subs.add(target);
    }
    subs.add(rhs);
    
    String setterName = "set$" + name;
    if (target.type().isArray() && name.equals("length")) {
      // Changing the length of an array.  The setter here is different.
      setterName = "setLength";
    }

    return pr.qq().parseExpr(quote + "." + setterName + "(%E)", subs);
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
