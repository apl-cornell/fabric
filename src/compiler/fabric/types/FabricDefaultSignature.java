package fabric.types;

import polyglot.ast.FieldDecl;
import jif.types.DefaultSignature;
import jif.types.label.Label;

public interface FabricDefaultSignature extends DefaultSignature {

  public Label defaultAccessLabel(FieldDecl fd);

}
