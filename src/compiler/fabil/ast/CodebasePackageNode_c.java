package fabil.ast;

import fabil.types.CodebaseContext;
import fabil.types.CodebasePackage;
import polyglot.ast.PackageNode_c;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;

public class CodebasePackageNode_c extends PackageNode_c implements
    CodebasePackageNode {

  public CodebasePackageNode_c(Position pos, Package p) {
    super(pos, p);
  }

  @SuppressWarnings("unused")
  @Override
  public CodebasePackageNode disambiguate(AmbiguityRemover ar)
      throws SemanticException {
    CodebasePackage cbp = (CodebasePackage) package_;
    CodebaseContext ctx = (CodebaseContext) ar.context();
    // Only qualify packages in remote source by the codebase
    if (ctx.currentSource().isRemote() && (cbp == null || !cbp.isCanonical())) {
      return (CodebasePackageNode) package_(cbp.codebase(ctx.currentCodebase()));
    } else return this;
  }
}
