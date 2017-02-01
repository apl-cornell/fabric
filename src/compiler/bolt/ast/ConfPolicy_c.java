package bolt.ast;

import polyglot.ast.Ext;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public abstract class ConfPolicy_c extends Policy_c implements ConfPolicy {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public ConfPolicy_c(Position pos) {
    this(pos, null);
  }

  public ConfPolicy_c(Position pos, Ext ext) {
    super(pos, ext);
  }
}
