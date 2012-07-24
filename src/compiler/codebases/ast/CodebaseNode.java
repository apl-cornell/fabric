package codebases.ast;

import polyglot.ast.PackageNode;
import fabric.common.FabricLocation;

public interface CodebaseNode extends PackageNode {
  /**
   * The namespace this alias occurs in.
   */
  FabricLocation namespace();

  /**
   * The name used to refer to the external namespace.
   */
  String alias();

  /**
   * The external namespace indicated by this node.
   */
  FabricLocation externalNamespace();
}
