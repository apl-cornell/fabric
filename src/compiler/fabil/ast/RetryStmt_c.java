package fabil.ast;

import java.util.Collections;
import java.util.List;

import polyglot.ast.Stmt_c;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;

public class RetryStmt_c extends Stmt_c implements RetryStmt {
  public RetryStmt_c(Position pos) {
    super(pos);
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
