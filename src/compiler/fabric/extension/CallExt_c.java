package fabric.extension;

import java.util.List;

import polyglot.ast.*;
import polyglot.types.MethodInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;

public class CallExt_c extends ExprExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabric.visit.ProxyRewriter)
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Expr rewriteProxiesOverrideImpl(ProxyRewriter pr) {
    Call call = node();
    Receiver target = call.target();
    boolean rewriteTarget = true;
    if (target instanceof Special) {
      Special special = (Special) target;
      rewriteTarget =
          special.kind() != Special.THIS || special.qualifier() != null;
    }

    if (rewriteTarget) target = (Receiver) call.visitChild(target, pr);
    Id name = (Id) call.visitChild(call.id(), pr);
    List<Expr> arguments = call.visitList(call.arguments(), pr);
    call = (Call) call.target(target).id(name).arguments(arguments);

    return ((CallExt_c) call.ext()).rewriteProxiesImpl(pr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Call call = node();
    MethodInstance mi = call.methodInstance();
    Receiver target = call.target();
    Type targetType = target.type();

    // Only rewrite calls to static methods of pure Fabric objects.
    boolean isStaticPureFabric =
        mi.flags().isStatic() && pr.typeSystem().isPureFabricType(targetType);
    if (!isStaticPureFabric) return super.rewriteProxiesImpl(pr);

    NodeFactory nf = pr.nodeFactory();
    Receiver newTarget =
        nf.AmbReceiver(Position.compilerGenerated(), target, nf.Id(Position
            .compilerGenerated(), "$Impl"));
    return call.target(newTarget).targetImplicit(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public Call node() {
    return (Call) super.node();
  }

}
