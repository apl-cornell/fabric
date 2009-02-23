package fabric.ast;

import java.util.List;

import polyglot.ast.Expr_c;
import polyglot.ast.Term;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;

public class Client_c extends Expr_c implements Client {
  public Client_c(Position pos) {
    super(pos);
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    return succs;
  }

  public Term firstChild() {
    return null;
  }
}
