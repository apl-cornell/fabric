package fabric.ast;

import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Stmt_c;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;

// XXX Should be replaced with extension
//@Deprecated
public class StageStmt_c extends Stmt_c implements StageStmt {
  //@Deprecated
  public StageStmt_c(Position pos) {
    this(pos, null);
  }

  public StageStmt_c(Position pos, Ext ext) {
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

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    w.write("stage;");
  }

  @Override
  public String toString() {
    return "stage";
  }

}
