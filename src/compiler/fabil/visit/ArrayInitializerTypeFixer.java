package fabil.visit;

import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.visit.AscriptionVisitor;

public class ArrayInitializerTypeFixer extends AscriptionVisitor {

  public ArrayInitializerTypeFixer(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  public Expr ascribe(Expr e, Type toType) {
    if (e.type() == null) e.prettyPrint(System.err);
    if (e.type().isNull()) e = e.type(toType);
    return e;
  }

}
