package fabric.extension;

import java.util.List;

import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.NodeFactory;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.types.MethodInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;
import fabric.visit.ReadWriteChecker.State;

public class CallExt_c extends ExprExt_c {

  private State accessState;

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

    // optimization to reduce register reads and writes
    if (accessState != null) {
      target = pr.replaceTarget(target, accessState);
    } else if (rewriteTarget) {
      target = (Receiver) call.visitChild(target, pr);
    }
    
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

  public void accessState(State s) {
    this.accessState = s;
  }
  
  public State accessState() {
    return accessState;
  }

}
