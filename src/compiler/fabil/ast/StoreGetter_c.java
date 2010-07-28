package fabil.ast;

import java.util.List;

import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class StoreGetter_c extends Expr_c implements StoreGetter {

  public StoreGetter_c(Position pos) {
    super(pos);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    return succs;
  }

  public Term firstChild() {
    return null;
  }
  
  /** Type check the expression. */
  @Override
  public Node typeCheck(TypeChecker tc) {
    FabILTypeSystem fts = (FabILTypeSystem) tc.typeSystem();
    return type(fts.Store());
  }  
  
}