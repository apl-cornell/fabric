package fabric.ast;

import java.util.List;


import polyglot.ast.Block_c;
import polyglot.ast.Stmt;
import polyglot.util.Position;

/**
 * An <code>Atomic</code> represents an <code>atomic</code> block.
 */
public class Atomic_c extends Block_c implements Atomic {
  public Atomic_c(Position pos, List<Stmt> statements) {
    super(pos, statements);
  }
}
