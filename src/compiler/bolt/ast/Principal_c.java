package bolt.ast;

import polyglot.ast.Ext;
import polyglot.ast.Term_c;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public abstract class Principal_c extends Term_c implements Principal {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public Principal_c(Position pos) {
    this(pos, null);
  }

  public Principal_c(Position pos, Ext ext) {
    super(pos, ext);
  }
}
