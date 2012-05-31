package fabil.ast;

import polyglot.ast.ClassLit_c;
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
public class ProviderLabel_c extends ClassLit_c implements ProviderLabel {

  /**
   * @param pos
   * @param tn
   */
  public ProviderLabel_c(Position pos, TypeNode tn) {
    super(pos, tn);
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
    return ((FabILNodeFactory) nf).providerLabel(this.position, this.typeNode);
  }
}
