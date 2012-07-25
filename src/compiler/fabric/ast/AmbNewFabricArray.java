package fabric.ast;

import jif.ast.AmbNewArray;
import polyglot.ast.Expr;

public interface AmbNewFabricArray extends AmbNewArray {

  public Expr location();

  public AmbNewFabricArray location(Expr loc);

}
