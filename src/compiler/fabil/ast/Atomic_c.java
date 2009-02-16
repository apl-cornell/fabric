package fabil.ast;

import java.util.*;

import polyglot.ast.Block_c;
import polyglot.ast.Stmt;
import polyglot.types.LocalInstance;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabil.types.FabILTypeSystem;
import fabil.visit.AbortRetryCollector;

/**
 * An <code>Atomic</code> represents an <code>atomic</code> block.
 */
public class Atomic_c extends Block_c implements Atomic {
  protected List<LocalInstance> updatedLocals;
  protected boolean mayAbort;
  
  public Atomic_c(Position pos, List<Stmt> statements) {
    super(pos, statements);
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    // the current semantics of an atomic block is that it executes like a
    // normal block, unless an exception occurs in which case it is rolled
    // back and an AbortException is thrown, with the body of the block
    // appearing as if it never executed. we model this by having an exception
    // edge come out of the entry of this block.
    FabILTypeSystem ts = (FabILTypeSystem) v.typeSystem();
    v.visitThrow(this, ENTRY, ts.AbortException());
    
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
  
  public List<LocalInstance> updatedLocals() {
    return updatedLocals;
  }
  
  public Atomic updatedLocals(List<LocalInstance> s) {
    if (s == this.updatedLocals) {
      return this;
    }
    Atomic_c n = (Atomic_c)this.copy();
    n.updatedLocals = s;
    return n;
  }
  
  public boolean mayAbort() {
    return mayAbort;
  }
  
  public Atomic mayAbort(boolean b) {
    if (b == mayAbort) {
      return this;
    }
    Atomic_c n = (Atomic_c)this.copy();
    n.mayAbort = b;
    return n;
  }
}
