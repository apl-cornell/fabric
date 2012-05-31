package fabric.ast;

import polyglot.ast.Expr;
import jif.ast.AmbNewArray;

public interface AmbNewFabricArray extends AmbNewArray {

  public Expr location();

  public AmbNewFabricArray location(Expr loc);

}
