package fabric.ast;

import jif.ast.LabelNode;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.FieldDecl_c;
import polyglot.ast.Id;
import polyglot.ast.Javadoc;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

// XXX Should be replaced with extension
@Deprecated
public class FabricFieldDecl_c extends FieldDecl_c implements FabricFieldDecl {

  protected LabelNode accessPolicy;

  @Deprecated
  public FabricFieldDecl_c(Position pos, Flags flags, TypeNode type,
      LabelNode accessPolicy, Id name, Expr init, Javadoc javadoc) {
    this(pos, flags, type, accessPolicy, name, init, javadoc, null);
  }

  public FabricFieldDecl_c(Position pos, Flags flags, TypeNode type,
      LabelNode accessPolicy, Id name, Expr init, Javadoc javadoc, Ext ext) {
    super(pos, flags, type, name, init, javadoc, ext);
    this.accessPolicy = accessPolicy;
  }

  @Override
  public LabelNode accessPolicy() {
    return this.accessPolicy;
  }

  @Override
  public FabricFieldDecl accessPolicy(LabelNode accessPolicy) {
    return accessPolicy(this, accessPolicy);
  }

  protected <N extends FabricFieldDecl_c> N accessPolicy(N n,
      LabelNode accessPolicy) {
    if (n.accessPolicy == accessPolicy) return n;
    n = copyIfNeeded(n);
    n.accessPolicy = accessPolicy;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode type = visitChild(this.type, v);
    LabelNode accessPolicy = visitChild(this.accessPolicy, v);
    Id name = visitChild(this.name, v);
    Expr init = visitChild(this.init, v);
    return reconstruct(this, type, accessPolicy, name, init);
  }

  protected <N extends FabricFieldDecl_c> N reconstruct(N n, TypeNode type,
      LabelNode accessPolicy, Id name, Expr init) {
    n = super.reconstruct(n, type, name, init);
    n = accessPolicy(n, accessPolicy);
    return n;
  }

}
