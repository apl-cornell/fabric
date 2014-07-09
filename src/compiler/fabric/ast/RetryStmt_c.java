package fabric.ast;

import java.util.Collections;
import java.util.List;

import polyglot.ast.Branch_c;
import polyglot.ast.Ext;
import polyglot.ast.Term;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;

//XXX Should be replaced with extension
@Deprecated
public class RetryStmt_c extends Branch_c implements RetryStmt {
  @Deprecated
  public RetryStmt_c(Position pos) {
    this(pos, null);
  }

  public RetryStmt_c(Position pos, Ext ext) {
    // XXX assume abort statements do not have labels for now
    super(pos, FabricBranch.RETRY, null, ext);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    return Collections.EMPTY_LIST;
  }

  @Override
  public Term firstChild() {
    return null;
  }
}
