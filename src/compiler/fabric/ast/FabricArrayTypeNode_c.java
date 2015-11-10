package fabric.ast;

import fabric.types.FabricTypeSystem;

import jif.ast.LabelNode;

import polyglot.ast.ArrayTypeNode_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;

//XXX Should be replaced with extension
@Deprecated
public class FabricArrayTypeNode_c extends ArrayTypeNode_c implements
FabricArrayTypeNode {

  private LabelNode accessPolicy;

  @Deprecated
  public FabricArrayTypeNode_c(Position pos, TypeNode base) {
    this(pos, base, null, null);
  }

  public FabricArrayTypeNode_c(Position pos, TypeNode base, Ext ext) {
    this(pos, base, ext, null);
  }

  @Deprecated
  public FabricArrayTypeNode_c(Position pos, TypeNode base, LabelNode accessPolicy) {
    this(pos, base, null, accessPolicy);
  }

  public FabricArrayTypeNode_c(Position pos, TypeNode base, Ext ext, LabelNode accessPolicy) {
    super(pos, base, ext);
    this.accessPolicy = accessPolicy;
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabricNodeFactory fabricNodeFactory = (FabricNodeFactory) nf;
    return fabricNodeFactory.FabricArrayTypeNode(position, base, accessPolicy);
  }

  @Override
  public Node buildTypes(TypeBuilder tb) {
    FabricTypeSystem ts = (FabricTypeSystem) tb.typeSystem();
    if (accessPolicy != null && accessPolicy.label() != null)
      return type(ts.fabricArrayOf(position(), base().type(), accessPolicy.label().confProjection()));
    return type(ts.fabricArrayOf(position(), base().type(), null));
  }

  @Override
  public Node disambiguate(AmbiguityRemover ar) {
    FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
    NodeFactory nf = ar.nodeFactory();

    if (!base().isDisambiguated()) return this;

    if (!base().type().isCanonical()) return this;

    return nf.CanonicalTypeNode(position(),
        ts.fabricArrayOf(position(), base().type()));
  }
}
