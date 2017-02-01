package bolt.ast;

import polyglot.ast.Ext;
import polyglot.ast.Lang;
import polyglot.ast.Node;
import polyglot.ast.NodeOps;
import polyglot.ext.jl7.ast.J7Lang_c;
import polyglot.util.InternalCompilerError;

public class BoltLang_c extends J7Lang_c implements BoltLang {
  public static final BoltLang_c INSTANCE = new BoltLang_c();

  public static BoltLang lang(NodeOps n) {
    while (n != null) {
      Lang lang = n.lang();
      if (lang instanceof BoltLang) return (BoltLang) lang;
      if (n instanceof Ext)
        n = ((Ext) n).pred();
      else return null;
    }
    throw new InternalCompilerError("Impossible to reach");
  }

  protected BoltLang_c() {
  }

  protected static BoltExt boltExt(Node n) {
    return BoltExt.ext(n);
  }

  @Override
  protected NodeOps NodeOps(Node n) {
    return boltExt(n);
  }

  // TODO:  Implement dispatch methods for new AST operations.
  // TODO:  Override *Ops methods for AST nodes with new extension nodes.
}
