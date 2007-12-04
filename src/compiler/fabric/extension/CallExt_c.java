package fabric.extension;

import polyglot.ast.*;
import polyglot.types.MethodInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;

public class CallExt_c extends ExprExt_c {

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
