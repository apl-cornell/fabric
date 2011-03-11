package fabil.ast;

import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseContext;
import fabil.types.CodebasePackage;
import polyglot.ast.PackageNode_c;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;

public class CodebasePackageNode_c extends PackageNode_c implements
    CodebasePackageNode {

  protected CodebaseSource source;

  public CodebasePackageNode_c(Position pos, Package p) {
    super(pos, p);
  }

  @SuppressWarnings("unused")
  @Override
  public CodebasePackageNode disambiguate(AmbiguityRemover ar)
      throws SemanticException {
    CodebasePackage cbp = (CodebasePackage) package_;
    CodebaseContext ctx = (CodebaseContext) ar.context();
    cbp = cbp.source(ctx.currentSource());
    
    // Only qualify packages in remote source by the codebase

    if (ctx.currentSource().isRemote() 
        && (cbp == null || !cbp.isCanonical())) {

      cbp = cbp.codebase(ctx.currentCodebase());
      return (CodebasePackageNode) package_(cbp);
    } else return this;
  }

  public CodebasePackageNode source(CodebaseSource source) {
    CodebasePackageNode_c cbpn = (CodebasePackageNode_c) copy();
    cbpn.source = source;
    return cbpn;
  }
  
  public CodebaseSource source() {
    return source;
  }
}
