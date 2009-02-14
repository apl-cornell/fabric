package fabil.ast;

import java.util.*;

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

  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    return Collections.EMPTY_LIST;
  }

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
