package fabric.ast;

import polyglot.ast.ArrayTypeNode_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

public class FabricArrayTypeNode_c extends ArrayTypeNode_c implements
    FabricArrayTypeNode {

  public FabricArrayTypeNode_c(Position pos, TypeNode base) {
    super(pos, base);
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabricNodeFactory fabricNodeFactory = (FabricNodeFactory) nf;
    return fabricNodeFactory.FabricArrayTypeNode(position, base);
  }

}
