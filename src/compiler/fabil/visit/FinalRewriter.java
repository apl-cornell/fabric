package fabil.visit;

import fabil.types.FabILFlags;

import polyglot.ast.Field;
import polyglot.ast.FieldDecl;
import polyglot.ast.Node;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
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
    if (!(n instanceof FieldDecl || n instanceof Field)) {
      return n;
    }
    if (n instanceof FieldDecl) {
      FieldDecl fd = (FieldDecl) n;

      Flags flags = fd.flags();

      if (flags.isFinal())
        fd = fd.flags(flags.clearFinal().set(FabILFlags.IMMUTABLE));
      return fd;
    }

    Field fd = (Field) n;

    Flags flags = fd.flags();

    if (flags.isFinal()) {
      FieldInstance fi = fd.fieldInstance();
      fd = fd.fieldInstance(fi.flags(flags.clearFinal().set(FabILFlags.IMMUTABLE)));
    }

    return fd;
  }
}
