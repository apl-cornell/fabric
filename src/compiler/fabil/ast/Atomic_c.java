package fabil.ast;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.Block_c;
import polyglot.ast.Ext;
import polyglot.ast.Stmt;
import polyglot.types.LocalInstance;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabil.types.FabILTypeSystem;
import fabil.visit.AbortRetryCollector;

/**
 * An <code>Atomic</code> represents an <code>atomic</code> block.
 */
//XXX Should be replaced with extension
@Deprecated
public class Atomic_c extends Block_c implements Atomic {
  protected List<LocalInstance> updatedLocals;
  protected boolean mayAbort;

  @Deprecated
  public Atomic_c(Position pos, List<Stmt> statements) {
    this(pos, statements, null);
  }

  public Atomic_c(Position pos, List<Stmt> statements, Ext ext) {
    super(pos, statements, ext);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    // the current semantics of an atomic block is that it executes like a
    // normal block, unless an exception occurs in which case it is rolled
    // back and an AbortException is thrown, with the body of the block
    // appearing as if it never executed. we model this by having an exception
    // edge come out of the entry of this block.
    FabILTypeSystem ts = (FabILTypeSystem) v.typeSystem();
    v.visitThrow(this, ENTRY, ts.AbortException());

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

  @Override
  public List<LocalInstance> updatedLocals() {
    return updatedLocals;
  }

  @Override
  public Atomic updatedLocals(List<LocalInstance> s) {
    if (s == this.updatedLocals) {
      return this;
    }
    Atomic_c n = (Atomic_c) this.copy();
    n.updatedLocals = s;
    return n;
  }

  @Override
  public boolean mayAbort() {
    return mayAbort;
  }

  @Override
  public Atomic mayAbort(boolean b) {
    if (b == mayAbort) {
      return this;
    }
    Atomic_c n = (Atomic_c) this.copy();
    n.mayAbort = b;
    return n;
  }
}
