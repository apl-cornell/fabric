package codebases.ast;

import java.net.URI;

import polyglot.ast.Node;
import polyglot.ast.Prefix;
import polyglot.ast.QualifierNode;

public interface CodebaseNode extends Node, Prefix, QualifierNode {
  /**
   * The namespace associated with this explicit codebase.
   * @return
   */
  URI namespace();
}
