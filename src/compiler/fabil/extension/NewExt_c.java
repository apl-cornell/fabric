package fabil.extension;

import java.util.LinkedList;
import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import fabil.ast.New;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class NewExt_c extends AnnotatedExt_c {

  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    New call = node();
    NodeFactory nf = pr.nodeFactory();

    TypeNode typeNode = call.objectType();
    ClassType type = (ClassType) typeNode.type();

    // Only rewrite if instantiating a pure Fabric type.
    FabILTypeSystem ts = pr.typeSystem();
    if (!ts.isPureFabricType(typeNode)) return super.rewriteProxiesImpl(pr);

    List<Expr> newargs = new LinkedList<>(call.arguments());
    newargs.add(0, call.location());

    TypeNode implType =
        nf.TypeNodeFromQualifiedName(typeNode.position(), type.translate(null)
            + "._Impl");
    call = call.objectType(implType);
    call = (New) call.arguments(newargs);

    return pr.qq().parseExpr("(%T) %E.$getProxy()", typeNode, call);
  }

  @Override
  public New node() {
    return (New) super.node();
  }
}
