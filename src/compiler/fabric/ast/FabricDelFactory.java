package fabric.ast;

import polyglot.ast.JL;
import jif.ast.JifDelFactory;

public interface FabricDelFactory extends JifDelFactory {
  JL delAtomic();
}
