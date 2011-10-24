package codebases.ast;

import java.net.URI;

import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.Prefix;
import polyglot.ast.QualifierNode;
import polyglot.types.Qualifier;

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
