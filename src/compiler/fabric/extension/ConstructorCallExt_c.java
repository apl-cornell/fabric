package fabric.extension;

import java.util.LinkedList;
import java.util.List;

import polyglot.ast.ConstructorCall;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.util.Position;
import fabric.visit.ProxyRewriter;

public class ConstructorCallExt_c extends FabricExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Need to add a $location argument to super(...) and this(...) constructor
    // calls if we are compiling a Diaspora class.
    NodeFactory nf = pr.nodeFactory();
    ConstructorCall call = node();
    List<Expr> args = new LinkedList<Expr>(call.arguments());
    args.add(0, nf.AmbExpr(Position.compilerGenerated(), nf.Id(Position
        .compilerGenerated(), "$location")));
    return call.arguments(args);
  }

  @Override
  public ConstructorCall node() {
    return (ConstructorCall) super.node();
  }
}
