package fabil.extension;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Id;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import fabil.visit.ProxyRewriter;
import fabil.visit.ReadWriteChecker.State;

public class FieldExt_c extends ExprExt_c {

  /**
   * For read/write optimization. Access state of the target if it is a local
   * variable.
   */
  private State accessState;

  @Override
  protected Expr rewriteProxiesOverrideImpl(ProxyRewriter pr) {
    Field field = node();
    Receiver target = field.target();
    boolean rewriteTarget = true;
    if (target instanceof Special) {
      Special special = (Special) target;
      rewriteTarget =
          special.kind() != Special.THIS || special.qualifier() != null;
    }

    if (rewriteTarget) target = field.visitChild(target, pr);
    Id name = field.visitChild(field.id(), pr);
    field = field.target(target).id(name);

    return ((FieldExt_c) field.ext()).rewriteProxiesImpl(pr);
  }

  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Field field = node();
    Flags flags = field.flags();
    Receiver target = field.target();
    Type targetType = target.type();
    QQ qq = pr.qq();

    // Only rewrite field accesses on pure Fabric objects.
    if (!pr.typeSystem().isPureFabricType(targetType))
      return super.rewriteProxiesImpl(pr);

    // Don't rewrite if accessing a (static final) field on an interface.
    FieldInstance fi = field.fieldInstance();
    ReferenceType container = fi.container();
    if (container instanceof ClassType
        && ((ClassType) container).flags().isInterface())
      return super.rewriteProxiesImpl(pr);

    // optimization to reduce register reads and writes
    if (accessState != null) {
      target = pr.replaceTarget(target, accessState);

      if (accessState.read() && !field.name().equals("length")) {
        return field.target(target);
      }
    }

    if (flags.isStatic()) {
      ClassType targetClassType = (ClassType) container;
      // Static fields are accessed via accessor methods on the
      // _Static._Proxy.$instance object.
      return qq.parseExpr(targetClassType.translate(null)
          + "._Static._Proxy.$instance.get$" + field.name() + "()");
    }

    Call getter = (Call) qq.parseExpr("get$" + field.name() + "()");
    return getter.target(target).targetImplicit(false);
  }

  @Override
  public Field node() {
    return (Field) super.node();
  }

  public void accessState(State s) {
    this.accessState = s;
  }

  public State accessState() {
    return accessState;
  }

}
