package fabil.ast;

import polyglot.ast.Node;
import polyglot.ast.PackageNode_c;
import polyglot.types.Package;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebasePackage;
import fabric.visit.CodebaseTypeBuilder;

public class CodebasePackageNode_c extends PackageNode_c implements
    CodebasePackageNode {

  protected CodebaseSource source;

  public CodebasePackageNode_c(Position pos, Package p) {
    super(pos, p);
  }

  @Override
  public Node buildTypes(TypeBuilder tb) throws SemanticException {
    CodebaseTypeBuilder ftb = (CodebaseTypeBuilder) tb;
    CodebasePackageNode result = (CodebasePackageNode) super.buildTypes(tb);
    return result.source(ftb.currentSource());
  }

  @SuppressWarnings("unused")
  @Override
  public CodebasePackageNode disambiguate(AmbiguityRemover ar)
      throws SemanticException {
    CodebasePackage cbp = (CodebasePackage) package_;
    cbp = cbp.source(source);
    
    // Only qualify packages in remote source by the codebase

    if (source.isRemote() 
        && (cbp == null || !cbp.isCanonical())) {

      cbp = cbp.codebase(source.codebase());
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
