package fabric.extension;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Receiver;
import polyglot.qq.QQ;
import polyglot.types.*;
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
    Flags flags = field.flags();
    Receiver target = field.target();
    Type targetType = target.type();
    QQ qq = pr.qq();

    // Only rewrite field accesses on Fabric objects.
    if (targetType.isArray() && !pr.typeSystem().isFabric(targetType.toArray().ultimateBase()))
      return super.rewriteProxiesImpl(pr);
    if (!targetType.isArray() && !pr.typeSystem().isFabric(targetType))
      return super.rewriteProxiesImpl(pr);

    // Don't rewrite if accessing a (static final) field on an interface.
    FieldInstance fi = field.fieldInstance();
    ReferenceType container = fi.container();
    if (container instanceof ClassType
        && ((ClassType) container).flags().isInterface())
      return super.rewriteProxiesImpl(pr);

    if (flags.isStatic()) {
      ClassType targetClassType = (ClassType) targetType;
      if (flags.isFinal()) {
        // Static final fields are accessed directly from the $Static interface.
        return qq.parseExpr(targetClassType.fullName() + ".$Static."
            + field.name());
      }

      // Static non-final fields are accessed via accessor methods on the
      // $static object.
      return qq.parseExpr(targetClassType.fullName() + ".$static.get$"
          + field.name() + "()");
    }

    Call getter = (Call) qq.parseExpr("get$" + field.name() + "()");
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
