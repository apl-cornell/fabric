package fabric.parse;

import jif.parse.Amb;
import jif.parse.Grm;
import jif.parse.Wrapper;
import polyglot.ast.Expr;
import polyglot.util.Position;
import fabric.ast.FabricNodeFactory;

/**
 *
 */
public class AmbStore extends Amb {
  public Amb expr;

  /**
   * @param parser
   * @param pos
   */
  public AmbStore(Amb expr, Grm parser, Position pos) {
    super(parser, pos);
    this.expr = expr;
  }

  @Override
  public Expr wrap() throws Exception {
    return new Wrapper(this);
  }

  @Override
  public Expr toExpr() throws Exception {
    return ((FabricNodeFactory) parser.nf).Store(pos, expr.toExpr());
  }
}
