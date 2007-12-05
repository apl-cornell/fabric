package fabric.extension;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.*;
import fabric.visit.ProxyRewriter;

public class FieldExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabric.visit.ProxyRewriter)
   */
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
    
    if (rewriteTarget) target = (Receiver) field.visitChild(target, pr);
    Id name = (Id) field.visitChild(field.id(), pr);
    field = field.target(target).id(name);
    
    return ((FieldExt_c)field.ext()).rewriteProxiesImpl(pr);
  }

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

    // Only rewrite field accesses on pure Fabric objects.
    if (!pr.typeSystem().isPureFabricType(targetType))
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
