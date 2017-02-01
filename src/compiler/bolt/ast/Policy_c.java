package bolt.ast;

import polyglot.ast.Ext;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public abstract class Policy_c extends LabelComponent_c implements Policy {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public Policy_c(Position pos) {
    this(pos, null);
  }

  public Policy_c(Position pos, Ext ext) {
    super(pos, ext);
  }

}
