package bolt.ast;

import polyglot.ast.Ext;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public abstract class Label_c extends LabelComponent_c implements Label {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public Label_c(Position pos) {
    this(pos, null);
  }

  public Label_c(Position pos, Ext ext) {
    super(pos, ext);
  }
}
