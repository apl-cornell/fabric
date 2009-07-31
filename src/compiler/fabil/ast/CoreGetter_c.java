package fabil.ast;

import java.util.Collections;
import java.util.List;

import fabil.types.FabILTypeSystem;

import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.TypeChecker;

public class CoreGetter_c extends Expr_c implements CoreGetter {

  public CoreGetter_c(Position pos) {
    super(pos);
  }

  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    return succs;
  }

  public Term firstChild() {
    return null;
  }
  
  /** Type check the expression. */
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem fts = (FabILTypeSystem) tc.typeSystem();
    return type(fts.Core());
  }  
  
}