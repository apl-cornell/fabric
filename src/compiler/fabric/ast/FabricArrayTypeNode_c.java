package fabric.ast;

import fabric.types.FabricTypeSystem;
import polyglot.ast.ArrayTypeNode_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;

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
  

  @Override
  public Node buildTypes(TypeBuilder tb) {
    FabricTypeSystem ts = (FabricTypeSystem) tb.typeSystem();
    return type(ts.fabricArrayOf(position(), base().type()));
  }

  @Override
  public Node disambiguate(AmbiguityRemover ar) {
    FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
    NodeFactory nf = ar.nodeFactory();

    if (!base().isDisambiguated())
      return this;

    if (!base().type().isCanonical())
      return this;

    return nf.CanonicalTypeNode(position(),
                                ts.fabricArrayOf(position(), base().type()));
  }
}
