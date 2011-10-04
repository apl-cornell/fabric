package fabil.ast;

import fabil.frontend.CodebaseSource;
import polyglot.ast.PackageNode;
import polyglot.types.SemanticException;
import polyglot.visit.AmbiguityRemover;

public interface CodebasePackageNode extends PackageNode {
   CodebasePackageNode disambiguate(AmbiguityRemover ar) throws SemanticException;
   CodebaseSource source();
   CodebasePackageNode source(CodebaseSource source);
}
