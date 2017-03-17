package bolt.ast;

import polyglot.ast.Ext;
import polyglot.ast.Term_c;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;

public abstract class LabelComponent_c extends Term_c
    implements LabelComponent {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public LabelComponent_c(Position pos) {
    this(pos, null);
  }

  public LabelComponent_c(Position pos, Ext ext) {
    super(pos, ext);
  }
}
