package fabil.ast;

import polyglot.ast.NodeFactory;
import polyglot.types.Package;
import polyglot.util.Position;

public interface CodebaseNodeFactory extends NodeFactory {
  /**
   * Returns a disambiguator for nodes from this factory.
   */
  CodebaseDisamb disamb();
  
  CodebasePackageNode PackageNode(Position pos, Package p);
}
