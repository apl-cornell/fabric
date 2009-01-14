package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.util.Position;
import fabil.visit.LocationAssigner;

/**
 * Provides common functionality to the New and NewArray for managing label and
 * location fields.
 */
public abstract class AnnotatedExt_c extends ExprExt_c {
  private Expr label;
  private Expr location;

  public Expr label() {
    return label;
  }
  
  public Expr location() {
    return location;
  }
  
  public Expr label(Expr label) {
    AnnotatedExt_c result = (AnnotatedExt_c) copy();
    result.label = label;
    
    return (Expr) node().ext(result);
  }

  public Expr location(Expr location) {
    AnnotatedExt_c result = (AnnotatedExt_c) copy();
    result.location = location;

    return (Expr) node().ext(result);
  }

  @Override
  public Expr assignLocations(LocationAssigner la) {
    Expr expr = node();
    if (location != null) return expr;
    
    NodeFactory nf = la.nodeFactory();
    QQ qq = la.qq();

    // Need a location. By default, we colocate with the context.
    Expr defaultLocation;
    if (la.context().inStaticContext()) {
      defaultLocation = qq.parseExpr("%T.$Static.$Proxy.$instance.$getCore()",
          la.context().currentClass());
    } else {
      defaultLocation =
          nf.Call(expr.position(), nf.Id(Position.compilerGenerated(),
              "$getCore"));
    }
    
    return location(defaultLocation);
  }
}
