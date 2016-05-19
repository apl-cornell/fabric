package fabil.extension;

import java.util.ArrayList;
import java.util.List;

import fabil.visit.ProxyRewriter;
import fabil.visit.ReadWriteChecker.State;
import polyglot.ast.Assign;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.FieldAssign;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.types.Flags;

public class FieldAssignExt_c extends ExprExt_c {

  /**
   * For read/write optimization. Access state of the target on the LHS if it is
   * a local variable.
   */
  private State accessState;

  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter pr) {
    FieldAssign assign = node();
    Field field = assign.left();
    Flags flags = field.flags();
    Receiver target = field.target();
    boolean rewriteTarget = true;
    if (target instanceof Special) {
      Special special = (Special) target;
      rewriteTarget =
          special.kind() != Special.THIS || special.qualifier() != null;
    }
    if (rewriteTarget) target = field.visitChild(target, pr);
    String name = field.visitChild(field.id(), pr).id();
    Expr rhs = field.visitChild(assign.right(), pr);

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
    List<Object> subs = new ArrayList<>(2);
    if (flags.isStatic()) {
      quote += "._Static._Proxy.$instance";
      subs.add(field.fieldInstance().container());
    } else {
      if (target instanceof Expr) quote = "%E";
      subs.add(target);
    }

    // Cast the RHS if the type is numeric. This works around Java's
    // inconsistent implicit casting of numeric literals.
    String arg = "(%E)";
    if (field.type().isNumeric()) {
      arg = "((%T) %E)";
      subs.add(field.type());
    }
    subs.add(rhs);

    String setterName = "set$" + name;
    if (!assign.operator().equals(Assign.ASSIGN))
      throw new UnsupportedOperationException(
          "Oooh fancy.. Sorry but " + assign.operator() + " at "
              + assign.position() + " is not supported yet.");
    if (target.type().isArray() && name.equals("length")) {
      // Changing the length of an array. The setter here is different.
      setterName = "setLength";
    }

    return pr.qq().parseExpr(quote + "." + setterName + arg, subs.toArray());
  }

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
