package fabil.ast;

import java.util.*;

import polyglot.ast.Stmt_c;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;

public class AbortStmt_c extends Stmt_c implements AbortStmt {
  public AbortStmt_c(Position pos) {
    super(pos);
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    return Collections.EMPTY_LIST;
  }

  public Term firstChild() {
    return null;
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
      w.write("abort;");
  }

  @Override
  public String toString() {
    return "abort";
  }
}
