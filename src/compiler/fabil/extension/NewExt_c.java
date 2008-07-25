package fabil.extension;

import java.util.LinkedList;
import java.util.List;

import fabil.types.FabricTypeSystem;
import fabil.visit.ProxyRewriter;
import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import polyglot.util.Position;

public class NewExt_c extends LocatedExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    New call = (New) node();
    NodeFactory nf = pr.nodeFactory();

    TypeNode typeNode = call.objectType();
    ClassType type = (ClassType) typeNode.type();

    // Only rewrite if instantiating a pure Fabric type.
    FabricTypeSystem ts = pr.typeSystem();
    if (!ts.isPureFabricType(typeNode))
      return super.rewriteProxiesImpl(pr);

    List<Expr> newargs = new LinkedList<Expr>(call.arguments());
    Expr location = location();
    if (location == null)
      location =
          nf.Call(call.position(), nf.Id(Position.compilerGenerated(),
              "$getCore"));
    newargs.add(0, location);
    
    // XXX Replace with a real label.
    Expr label = nf.NullLit(Position.compilerGenerated());
    newargs.add(1, label);

    TypeNode implType =
        nf.TypeNodeFromQualifiedName(typeNode.position(), type.fullName()
            + ".$Impl");
    call = call.objectType(implType);
    call = (New) call.arguments(newargs);

    return pr.qq().parseExpr("(%T) %E.$getProxy()", typeNode, call);
  }
}
