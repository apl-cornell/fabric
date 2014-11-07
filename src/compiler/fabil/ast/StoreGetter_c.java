package fabil.ast;

import java.util.List;

import polyglot.ast.Expr_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class StoreGetter_c extends Expr_c implements StoreGetter {

  @Deprecated
  public StoreGetter_c(Position pos) {
    this(pos, null);
  }

  public StoreGetter_c(Position pos, Ext ext) {
    super(pos, ext);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    return succs;
  }

  @Override
  public Term firstChild() {
    return null;
  }

  /** Type check the expression. */
  @Override
  public Node typeCheck(TypeChecker tc) {
    FabILTypeSystem fts = (FabILTypeSystem) tc.typeSystem();
    return type(fts.Store());
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    throw new InternalCompilerError("StoreGetter should have been rewritten.");
  }

}
