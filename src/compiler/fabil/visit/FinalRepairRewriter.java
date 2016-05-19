package fabil.visit;

import fabil.types.FabILFlags;
import polyglot.ast.FieldDecl;
import polyglot.ast.Node;
import polyglot.types.Flags;
import polyglot.visit.NodeVisitor;

/**
 * Clears immutable flags from field declarations.
 */
public class FinalRepairRewriter extends NodeVisitor {

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof FieldDecl)) {
      return n;
    }

    FieldDecl fd = (FieldDecl) n;
    Flags flags = fd.flags().clear(FabILFlags.IMMUTABLE);
    return fd.flags(flags);
  }
}
