package fabric.ast;

import java.util.List;
import java.util.Collections;

import polyglot.ast.*;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;

public class AbortStmt_c extends Branch_c implements AbortStmt {
  public AbortStmt_c(Position pos) {
    // XXX assume abort statements do not have labels for now
    super(pos, FabricBranch.ABORT, null);
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
