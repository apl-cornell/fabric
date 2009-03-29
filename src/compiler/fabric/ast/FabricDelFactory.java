package fabric.ast;

import polyglot.ast.JL;
import jif.ast.JifDelFactory;

/** Factory for delegates for all of the AST nodes in the fabric language. */
public interface FabricDelFactory extends JifDelFactory {
  JL delAtomic();
  JL delClient();
  JL delFabricArrayInit();
  JL delFabricArrayTypeNode();
  JL delNewFabricArray();
  JL delRemoteClientGetter();
}
