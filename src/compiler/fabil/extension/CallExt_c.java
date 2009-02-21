package fabil.extension;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.*;
import polyglot.types.ClassType;
import polyglot.types.MethodInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.ast.FabILCall;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;
import fabil.visit.RemoteCallRewriter;
import fabil.visit.ThreadRewriter;
import fabil.visit.ReadWriteChecker.State;

public class CallExt_c extends ExprExt_c {

  /**
   * For read/write optimization. Access state of the target if it is a local
   * variable.
   */
  private State accessState;

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabil.visit.ProxyRewriter)
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
   * @see fabil.extension.ExprExt_c#rewriteProxiesImpl(fabil.visit.ProxyRewriter)
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

  @Override
  public Node rewriteThreads(ThreadRewriter tr) {
    // Replace calls to Thread.start() with
    // fabric.client.transaction.TransactionManager.startThread(Thread).
    Call call = node();
    if (!call.name().equals("start")) return super.rewriteThreads(tr);

    FabILTypeSystem ts = tr.typeSystem();
    Receiver target = call.target();
    Type targetType = target.type();
    if (!ts.isThread(targetType)) return super.rewriteThreads(tr);

    return tr.qq().parseExpr(
        "fabric.client.transaction.TransactionManager.startThread(%E)", target);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteRemoteCalls(RemoteCallRewriter rr) {
    FabILCall c = (FabILCall)node();
    if (c.remoteClient() == null) return c;

    NodeFactory nf = rr.nodeFactory();
    
    List<Expr> args = new ArrayList<Expr>(c.arguments().size() + 1);
    args.add(c.remoteClient());
    args.addAll(c.arguments());
    
    Expr target = rr.qq().parseExpr("(" + ((ClassType)c.target().type()).fullName() + ".$Proxy)" + c.target());
    return nf.Call(Position.compilerGenerated(), 
                   target, 
                   nf.Id(Position.compilerGenerated(), c.name() + "$remote"), 
                   args);
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
