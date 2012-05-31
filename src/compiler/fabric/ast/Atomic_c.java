package fabric.ast;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.Block_c;
import polyglot.ast.Stmt;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabric.visit.AbortRetryCollector;

public class Atomic_c extends Block_c implements Atomic {

  public Atomic_c(Position pos, List<Stmt> statements) {
    super(pos, statements);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Stmt> statements() {
    return super.statements();
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    // TODO There needs to be an edge for AbortException

    // Find all the aborts and retries that are lexically enclosed in the
    // atomic blocks, and add appropriate edges.
    List<AbortStmt> aborts = new ArrayList<AbortStmt>();
    List<RetryStmt> retries = new ArrayList<RetryStmt>();

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
