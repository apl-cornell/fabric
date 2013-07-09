package fabric.types;

import jif.types.DefaultSignature;
import jif.types.label.Label;
import polyglot.ast.FieldDecl;

public interface FabricDefaultSignature extends DefaultSignature {

  public Label defaultAccessPolicy(FieldDecl fd);

}
