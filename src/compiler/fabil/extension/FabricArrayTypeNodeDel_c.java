package fabil.extension;

import polyglot.ast.*;
import polyglot.types.Type;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;
import fabil.types.FabILTypeSystem;

public class FabricArrayTypeNodeDel_c extends JL_c {

  @Override
  public Node buildTypes(TypeBuilder tb) {
    ArrayTypeNode atn = (ArrayTypeNode) jl();
    FabILTypeSystem ts = (FabILTypeSystem) tb.typeSystem();
    return atn.type(ts.fabricArrayOf(atn.position(), atn.base().type()));
  }

  @Override
  public Node disambiguate(AmbiguityRemover ar) {
    ArrayTypeNode atn = (ArrayTypeNode) jl();
    FabILTypeSystem ts = (FabILTypeSystem) ar.typeSystem();
    NodeFactory nf = ar.nodeFactory();

    TypeNode base = atn.base();
    if (!base.isDisambiguated()) return atn;

    Type baseType = base.type();
    if (!baseType.isCanonical()) return atn;

    return nf.CanonicalTypeNode(atn.position(), ts.fabricArrayOf(
        atn.position(), baseType));
  }

}
