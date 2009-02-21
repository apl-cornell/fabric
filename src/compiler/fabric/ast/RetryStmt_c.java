package fabric.ast;

import java.util.List;
import java.util.Collections;

import polyglot.ast.Branch_c;
import polyglot.ast.Term;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;

public class RetryStmt_c extends Branch_c implements RetryStmt {
  public RetryStmt_c(Position pos) {
    // XXX assume abort statements do not have labels for now
    super(pos, FabricBranch.RETRY, null);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    return Collections.EMPTY_LIST;
  }

  @Override
  public Term firstChild() {
    return null;
  }
}
