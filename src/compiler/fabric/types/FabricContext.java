package fabric.types;

import jif.types.JifContext;
import polyglot.types.Context;
import fabric.lang.Codebase;

public interface FabricContext extends JifContext {
  Codebase currentCodebase();
  Context pushCodebase(Codebase codebase);

}
