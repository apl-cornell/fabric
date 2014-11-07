package fabil.ast;

import polyglot.ast.ArrayTypeNode_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

//XXX Should be replaced with extension
@Deprecated
public class FabricArrayTypeNode_c extends ArrayTypeNode_c implements
FabricArrayTypeNode {
  @Deprecated
  public FabricArrayTypeNode_c(Position pos, TypeNode base) {
    this(pos, base, null);
  }

  public FabricArrayTypeNode_c(Position pos, TypeNode base, Ext ext) {
    super(pos, base, ext);
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory fabilNodeFactory = (FabILNodeFactory) nf;
    return fabilNodeFactory.FabricArrayTypeNode(position, base);
  }

}
