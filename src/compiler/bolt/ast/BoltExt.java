package bolt.ast;

import polyglot.ast.Ext;
import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.util.InternalCompilerError;
import polyglot.util.SerialVersionUID;

public class BoltExt extends Ext_c {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public static BoltExt ext(Node n) {
    Ext e = n.ext();
    while (e != null && !(e instanceof BoltExt)) {
      e = e.ext();
    }
    if (e == null) {
      throw new InternalCompilerError(
          "No Bolt extension object for node " + n + " (" + n.getClass() + ")",
          n.position());
    }
    return (BoltExt) e;
  }

  @Override
  public final BoltLang lang() {
    return BoltLang_c.INSTANCE;
  }
}
