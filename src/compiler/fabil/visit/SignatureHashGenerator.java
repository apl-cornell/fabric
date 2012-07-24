package fabil.visit;

import polyglot.ast.ClassDecl;
import polyglot.ast.Node;
import polyglot.types.ParsedClassType;
import polyglot.visit.NodeVisitor;
import fabil.types.FabILParsedClassType_c;

/**
 * Ensures hashes in FabILParsedClassType_c instances are computed when
 * compiling FabIL and Fabric signatures. This way, the hashes become part of
 * the Polyglot serialized type information.
 */
public class SignatureHashGenerator extends NodeVisitor {

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      ClassDecl decl = (ClassDecl) n;
      ParsedClassType ct = decl.type();
      if (ct instanceof FabILParsedClassType_c) {
        ((FabILParsedClassType_c) ct).getClassHash();
      }
    }

    return super.leave(old, n, v);
  }

}
