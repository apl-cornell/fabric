package fabric.extension;

import java.util.LinkedList;
import java.util.List;

import fabric.visit.ProxyRewriter;
import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.util.Position;


public class NewExt_c extends LocatedExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public New rewriteProxies(ProxyRewriter pr) {
    New call = (New) node();
    NodeFactory nf = pr.nodeFactory();

    List<Expr> newargs = new LinkedList<Expr>(call.arguments());
    Expr location = location();
    if (location == null)
      location =
          nf.Call(call.position(), nf.Id(Position.compilerGenerated(),
              "$getCore"));
    newargs.add(0, location);


    TypeNode implType = nf.TypeNodeFromQualifiedName(call.objectType().position(),
                                                     call.objectType().name() + ".$Impl");
    call = call.objectType(implType);
    call = (New) call.arguments(newargs);
    
    return call;
  }
}
