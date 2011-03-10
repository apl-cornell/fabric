package fabil.ast;

import polyglot.ast.PackageNode;
import polyglot.types.SemanticException;
import polyglot.visit.AmbiguityRemover;

public interface CodebasePackageNode extends PackageNode {
   CodebasePackageNode disambiguate(AmbiguityRemover ar) throws SemanticException;
}
