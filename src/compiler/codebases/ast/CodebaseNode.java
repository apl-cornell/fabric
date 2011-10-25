package codebases.ast;

import java.net.URI;

import polyglot.ast.PackageNode;

public interface CodebaseNode extends PackageNode {
  /**
   * The namespace this alias occurs in.
   */
  URI namespace();

  /**
   * The name used to refer to the external namespace.
   */
  String alias();

  /**
   * The external namespace indicated by this node.
   */
  URI externalNamespace();
}
