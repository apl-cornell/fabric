package fabil.extension;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
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
   * @see
   * fabil.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabil.visit.ProxyRewriter
   * )
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

    if (name.id().equals("getClass") && arguments.isEmpty()) {
      // Calls to getClass() are rewritten so that the call target is an exact
      // proxy.
      target =
          pr.qq().parseExpr("fabric.lang.Object._Proxy.$getProxy(%E)", target);
    }

    call = (Call) call.target(target).id(name).arguments(arguments);
    return ((CallExt_c) call.ext()).rewriteProxiesImpl(pr);
  }

  /*
   * (non-Javadoc)
   * @see
   * fabil.extension.ExprExt_c#rewriteProxiesImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    Call call = node();
    MethodInstance mi = call.methodInstance();
    Receiver target = call.target();
    Type targetType = target.type();

    NodeFactory nf = pr.nodeFactory();
    QQ qq = pr.qq();

    // Only rewrite calls to static or private methods of pure Fabric objects.
    boolean isPureFabric = pr.typeSystem().isPureFabricType(targetType);
    Flags flags = mi.flags();
    boolean isStaticPureFabric = isPureFabric && flags.isStatic();
    boolean isPrivatePureFabric = isPureFabric && flags.isPrivate();

    if (isStaticPureFabric) {
      Receiver newTarget;

      if (call.name().equals("$getCore") || call.name().equals("get$label")) {
        // HACK: A static $getCore() or get$label(). Assume this was generated
        // by
        // the compiler as a default location/label and rewrite the call to
        // target
        // the static instance object.
        newTarget =
            qq.parseExpr(target.type().toClass().fullName()
                + "._Static._Proxy.$instance");
      } else {
        newTarget =
            nf.AmbReceiver(Position.compilerGenerated(), target, nf.Id(Position
                .compilerGenerated(), "_Impl"));
      }
      return call.target(newTarget).targetImplicit(false);
    }

    if (isPrivatePureFabric) {
      TypeNode castType =
          nf.AmbTypeNode(Position.compilerGenerated(), nf.CanonicalTypeNode(
              Position.compilerGenerated(), targetType), nf.Id(Position
              .compilerGenerated(), "_Impl"));
      Expr fetchCall = qq.parseExpr("(%T) %E.fetch()", castType, target);
      return call.target(fetchCall).targetImplicit(false);
    }

    return super.rewriteProxiesImpl(pr);
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
    FabILCall c = (FabILCall) node();
    if (c.remoteClient() == null) return c;

    NodeFactory nf = rr.nodeFactory();

    List<Expr> args = new ArrayList<Expr>(c.arguments().size());
    // The first argument is changed from the local client to the remote client.
    args.add(c.remoteClient());
    args.addAll(c.arguments());

    Expr target = (Expr) c.target();
    if (target instanceof Special) {
      target = rr.qq().parseExpr("%E.$getProxy()", target);
    }

    target =
        rr.qq().parseExpr(
            "(" + ((ClassType) c.target().type()).fullName() + "._Proxy) %E",
            target);
    return nf.Call(Position.compilerGenerated(), target,
    // <name>_remote => <name>$remote
        nf.Id(Position.compilerGenerated(), c.name().substring(0,
            c.name().length() - 7)
            + "$remote"), args);
  }

  /*
   * (non-Javadoc)
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
