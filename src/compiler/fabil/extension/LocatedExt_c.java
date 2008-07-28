package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.util.Position;
import fabil.visit.LocationAssigner;

/**
 * This class provides common functionality to the New and NewArray for managing
 * a location field
 */
public abstract class LocatedExt_c extends ExprExt_c {
  private Expr location;

  public Expr location() {
    return location;
  }

  public Expr location(Expr location) {
    LocatedExt_c result = (LocatedExt_c) copy();
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
      defaultLocation = qq.parseExpr("$static.$getCore()");
    } else {
      defaultLocation =
          nf.Call(expr.position(), nf.Id(Position.compilerGenerated(),
              "$getCore"));
    }
    
    return location(defaultLocation);
  }
}
