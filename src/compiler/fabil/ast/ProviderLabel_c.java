package fabil.ast;

import polyglot.ast.ClassLit_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

/**
 * AST node implementation for representing the provider label for a class.
 */
//XXX Should be replaced with extension
@Deprecated
public class ProviderLabel_c extends ClassLit_c implements ProviderLabel {

  @Deprecated
  public ProviderLabel_c(Position pos, TypeNode tn) {
    this(pos, tn, null);
  }

  public ProviderLabel_c(Position pos, TypeNode tn, Ext ext) {
    super(pos, tn, ext);
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    if (!typeNode.type().isClass()) {
      throw new SemanticException("Cannot get provider label of a non-class "
          + "type.", typeNode.position());
    }

    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    return type(ts.Label());
  }

  /** Write the expression to an output file. */
  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
    w.begin(0);
    print(typeNode, w, tr);
    w.write(".provider");
    w.end();
  }

  @Override
  public Node copy(NodeFactory nf) {
    return ((FabILNodeFactory) nf).ProviderLabel(this.position, this.typeNode);
  }
}
