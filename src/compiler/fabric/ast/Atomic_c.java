package fabric.ast;

import java.util.List;

import polyglot.ast.Block_c;
import polyglot.util.Position;

public class Atomic_c extends Block_c implements Atomic {

  @SuppressWarnings("unchecked")
  public Atomic_c(Position pos, List statements) {
    super(pos, statements);
  }
}
