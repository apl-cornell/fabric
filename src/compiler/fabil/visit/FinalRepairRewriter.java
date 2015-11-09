package fabil.visit;

import fabil.types.FabILFlags;

import polyglot.ast.Field;
import polyglot.ast.FieldDecl;
import polyglot.ast.Node;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
import polyglot.visit.NodeVisitor;

/**
 * Rewrites immutable flags into final flags after we do initialization
 * checking.
 *
 * @see FinalRewriter
 */
public class FinalRepairRewriter extends NodeVisitor {

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof FieldDecl || n instanceof Field)) {
      return n;
    }
    if (n instanceof FieldDecl) {
      FieldDecl fd = (FieldDecl) n;

      Flags flags = fd.flags();

      if (flags.contains(FabILFlags.IMMUTABLE))
        fd = fd.flags(flags.clear(FabILFlags.IMMUTABLE).Final());
      return fd;
    }

    Field fd = (Field) n;
    Flags flags = fd.flags();

    if (flags.contains(FabILFlags.IMMUTABLE)) {
      FieldInstance fi = fd.fieldInstance();
      fd = fd.fieldInstance(fi.flags(flags.clear(FabILFlags.IMMUTABLE).Final()));
    }

    return fd;
  }
}
