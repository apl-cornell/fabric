package fabil.visit;

import fabil.types.FabILFlags;

import polyglot.ast.FieldDecl;
import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;

/**
 * Rewrites final flags into immutable flags before we do initialization
 * checking.
 *
 * This is a bit of a kludge to allow us to declare fields as final in FabIL in
 * order to avoid tracking them in transactions while getting around the
 * constructor protocol issues (fabric$lang$Object$(), etc.)
 */
public class FinalRewriter extends NodeVisitor {

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof FieldDecl)) {
      return n;
    }
    FieldDecl fd = (FieldDecl) n;
    if (fd.flags().isFinal())
      fd.flags(fd.flags().clearFinal().set(FabILFlags.IMMUTABLE));
    return fd;
  }
}
