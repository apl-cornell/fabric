package fabric.extension;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Receiver;
import polyglot.types.ClassType;
import polyglot.types.FieldInstance;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import fabric.visit.ProxyRewriter;

public class FieldExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Field field = node();
    Receiver target = field.target();
    Type targetType = target.type();

    // TODO Need to handle static fields.

    // Only rewrite field accesses on Fabric objects.
    if (!targetType.isArray() && !pr.typeSystem().isFabric(targetType))
      return super.rewriteProxiesImpl(pr);

    // Don't rewrite if accessing a (static final) field on an interface.
    FieldInstance fi = field.fieldInstance();
    ReferenceType container = fi.container();
    if (container instanceof ClassType
        && ((ClassType) container).flags().isInterface())
      return super.rewriteProxiesImpl(pr);

    Call getter = (Call) pr.qq().parseExpr("get$" + field.id().id() + "()");
    return getter.target(target).targetImplicit(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Field node() {
    return (Field) super.node();
  }

}
