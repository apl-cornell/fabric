package fabric.ast;

import jif.ast.LabelNode;

public interface FabricFieldDecl extends polyglot.ast.FieldDecl {
  public LabelNode accessLabel();  
}