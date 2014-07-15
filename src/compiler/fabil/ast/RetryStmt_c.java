package fabil.ast;

import java.util.Collections;
import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Stmt_c;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;

//XXX Should be replaced with extension
@Deprecated
public class RetryStmt_c extends Stmt_c implements RetryStmt {
  @Deprecated
  public RetryStmt_c(Position pos) {
    this(pos, null);
  }

  public RetryStmt_c(Position pos, Ext ext) {
    super(pos, ext);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    return Collections.EMPTY_LIST;
  }

  @Override
  public Term firstChild() {
    return null;
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
    w.write("retry;");
  }

  @Override
  public String toString() {
    return "retry";
  }
}
