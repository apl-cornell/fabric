package fabric.ast;

import java.util.ArrayList;
import java.util.List;

import fabric.ast.AbortStmt;
import fabric.ast.RetryStmt;
import fabric.visit.AbortRetryCollector;

import polyglot.ast.Block_c;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;

public class Atomic_c extends Block_c implements Atomic {

  @SuppressWarnings("unchecked")
  public Atomic_c(Position pos, List statements) {
    super(pos, statements);
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    // TODO There needs to be an edge for AbortException
    
    // Find all the aborts and retries that are lexically enclosed in the 
    // atomic blocks, and add appropriate edges.
    List<AbortStmt> aborts = new ArrayList<AbortStmt>();
    List<RetryStmt> retries = new ArrayList<RetryStmt>();
    
    AbortRetryCollector c = new AbortRetryCollector(aborts, retries);
    this.visit(c);
    
    for (AbortStmt abort : aborts) {
      v.edge(abort, this, EXIT);
    }
    for (RetryStmt retry : retries) {
      v.edge(retry, this, ENTRY);
    }
    
    return super.acceptCFG(v, succs);
  }
}
