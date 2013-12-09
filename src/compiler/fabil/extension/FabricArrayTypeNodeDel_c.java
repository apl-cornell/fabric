package fabil.extension;

import polyglot.ast.ArrayTypeNode;
import polyglot.ast.JLDel_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;
import fabil.ast.FabricArrayTypeNode;
import fabil.types.FabILTypeSystem;

public class FabricArrayTypeNodeDel_c extends JLDel_c {

  @Override
  public Node buildTypes(TypeBuilder tb) {
    FabricArrayTypeNode atn = (FabricArrayTypeNode) jl();
    FabILTypeSystem ts = (FabILTypeSystem) tb.typeSystem();
    return atn.type(ts.fabricArrayOf(atn.position(), atn.base().type()));
  }

  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    ArrayTypeNode atn = (ArrayTypeNode) jl();
    FabILTypeSystem ts = (FabILTypeSystem) ar.typeSystem();
    NodeFactory nf = ar.nodeFactory();

    TypeNode base = atn.base();
    if (!base.isDisambiguated()) return atn;

    Type baseType = base.type();
    if (!baseType.isCanonical()) return atn;

    if (!ts.isFabricType(baseType)) {
      throw new SemanticException(
          "Non-Fabric objects cannot be stored in Fabric arrays.",
          atn.position());
    }

    return nf.CanonicalTypeNode(atn.position(),
        ts.fabricArrayOf(atn.position(), baseType));
  }

}
