package fabric.ast;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.Block_c;
import polyglot.ast.Ext;
import polyglot.ast.Stmt;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabric.visit.AbortRetryCollector;

//XXX Should be replaced with extension
@Deprecated
public class Atomic_c extends Block_c implements Atomic {
  @Deprecated
  public Atomic_c(Position pos, List<Stmt> statements) {
    this(pos, statements, null);
  }

  public Atomic_c(Position pos, List<Stmt> statements, Ext ext) {
    super(pos, statements, ext);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    // TODO There needs to be an edge for AbortException

    // Find all the aborts and retries that are lexically enclosed in the
    // atomic blocks, and add appropriate edges.
    List<AbortStmt> aborts = new ArrayList<>();
    List<RetryStmt> retries = new ArrayList<>();

    for (Stmt s : statements()) {
      AbortRetryCollector c = new AbortRetryCollector(aborts, retries);
      s.visit(c);
    }

    for (AbortStmt abort : aborts) {
      v.edge(abort, this, EXIT);
    }
    for (RetryStmt retry : retries) {
      v.edge(retry, this, ENTRY);
    }

    return super.acceptCFG(v, succs);
  }
}
