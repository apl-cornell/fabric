package fabric.ast;

import jif.ast.LabelNode;

import polyglot.ast.ArrayTypeNode;

public interface FabricArrayTypeNode extends ArrayTypeNode {

  public LabelNode accessPolicy();

  public FabricArrayTypeNode accessPolicy(LabelNode accessPolicy);
}
