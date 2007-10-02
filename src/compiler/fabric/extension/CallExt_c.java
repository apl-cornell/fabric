package fabric.extension;

import polyglot.ast.Call;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Receiver;
import polyglot.types.MethodInstance;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;

public class CallExt_c extends FabricExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.FabricExt_c#rewriteProxies(fabric.visit.ProxyRewriter)
   */
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    Call call = node();
    MethodInstance mi = call.methodInstance();
    if (!mi.flags().isStatic()) return super.rewriteProxies(pr);

    NodeFactory nf = pr.nodeFactory();
    Receiver target =
        nf.AmbReceiver(Position.compilerGenerated(), call.target(), nf.Id(
            Position.compilerGenerated(), "$Impl"));
    return call.target(target);
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
