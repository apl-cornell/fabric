package fabric.ast;

import jif.ast.LabelNode;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.FieldDecl_c;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class FabricFieldDecl_c extends FieldDecl_c implements FabricFieldDecl {

  protected LabelNode accessLabel;

  @Deprecated
  public FabricFieldDecl_c(Position pos, Flags flags, TypeNode type,
      LabelNode accessLabel, Id name, Expr init) {
    this(pos, flags, type, accessLabel, name, init, null);
  }

  public FabricFieldDecl_c(Position pos, Flags flags, TypeNode type,
      LabelNode accessLabel, Id name, Expr init, Ext ext) {
    super(pos, flags, type, name, init, ext);
    this.accessLabel = accessLabel;
  }

  @Override
  public LabelNode accessPolicy() {
    return this.accessLabel;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode type = visitChild(this.type, v);
    LabelNode accessLabel = visitChild(this.accessLabel, v);
    Id name = visitChild(this.name, v);
    Expr init = visitChild(this.init, v);
    return reconstruct(type, accessLabel, name, init);
  }

  protected FabricFieldDecl_c reconstruct(TypeNode type, LabelNode accessLabel,
      Id name, Expr init) {
    if (this.type != type || this.accessLabel != accessLabel
        || this.name != name || this.init != init) {
      FabricFieldDecl_c n = (FabricFieldDecl_c) copy();
      n.type = type;
      n.accessLabel = accessLabel;
      n.name = name;
      n.init = init;
      return n;
    }

    return this;
  }

}
