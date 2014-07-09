package fabric.ast;

import java.util.List;

import polyglot.ast.ArrayInit_c;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabric.types.FabricTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class FabricArrayInit_c extends ArrayInit_c implements FabricArrayInit {
  // XXX: this code copied from fabil.FabricArrayInit_c

  protected Expr location;
  protected Expr label;

  @Deprecated
  public FabricArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr location) {
    this(pos, elements, label, location, null);
  }

  public FabricArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr location, Ext ext) {
    super(pos, elements, ext);

    this.location = location;
    this.label = label;
  }

  @Override
  public FabricArrayInit elements(List<Expr> elements) {
    return (FabricArrayInit) super.elements(elements);
  }

  public Expr location() {
    return location;
  }

  @Override
  public FabricArrayInit_c location(Expr location) {
    return location(this, location);
  }

  protected <N extends FabricArrayInit_c> N location(N n, Expr location) {
    if (n.location == location) return n;
    n = copyIfNeeded(n);
    n.location = location;
    return n;
  }

  public Expr label() {
    return label;
  }

  @Override
  public FabricArrayInit_c label(Expr label) {
    return label(this, label);
  }

  protected <N extends FabricArrayInit_c> N label(N n, Expr label) {
    if (n.label == label) return n;
    n = copyIfNeeded(n);
    n.label = label;
    return n;
  }

  /**
   * Reconstructs the initializer.
   */
  protected <N extends FabricArrayInit_c> N reconstruct(N n,
      List<Expr> elements, Expr location, Expr label) {
    n = super.reconstruct(n, elements);
    n = location(n, location);
    n = label(n, label);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    List<Expr> elements = visitList(this.elements, v);
    Expr location = visitChild(this.location, v);
    Expr label = visitChild(this.label, v);
    return reconstruct(this, elements, location, label);
  }

  @Override
  protected Type arrayOf(TypeSystem ts, Type baseType) {
    return ((FabricTypeSystem) ts).fabricArrayOf(position(), baseType);
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabricNodeFactory fabNF = (FabricNodeFactory) nf;
    return fabNF.FabricArrayInit(this.position, this.label, this.location,
        this.elements);
  }

}
