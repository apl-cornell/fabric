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

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.AscriptionVisitor#ascribe(polyglot.ast.Expr,
   *      polyglot.types.Type)
   */
  @Override
  public Expr ascribe(Expr e, Type toType) {
    if (e.type().isNull()) e = e.type(toType);
    return e;
  }

}
