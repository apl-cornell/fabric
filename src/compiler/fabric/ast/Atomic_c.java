package fabric.ast;

import java.util.List;

import polyglot.ast.Block_c;
import polyglot.ast.Stmt;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import fabric.types.FabricTypeSystem;

/**
 * An <code>Atomic</code> represents an <code>atomic</code> block.
 */
public class Atomic_c extends Block_c implements Atomic {
  
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
    FabricTypeSystem ts = (FabricTypeSystem) v.typeSystem();
    v.visitThrow(this, ENTRY, ts.AbortException());
    
    return super.acceptCFG(v, succs);
  }
  
}
