package bolt.ast;

import polyglot.ast.Ext;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public abstract class IntegPolicy_c extends Policy_c implements IntegPolicy {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public IntegPolicy_c(Position pos) {
    this(pos, null);
  }

  public IntegPolicy_c(Position pos, Ext ext) {
    super(pos, ext);
  }
}
