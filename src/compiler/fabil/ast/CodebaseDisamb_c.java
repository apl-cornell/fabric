package fabil.ast;

import fabil.types.CodebaseContext;
import fabil.types.CodebasePackage;
import fabil.ast.CodebaseDisamb;
import polyglot.ast.Ambiguous;
import polyglot.ast.Disamb_c;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.Prefix;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.ContextVisitor;

public class CodebaseDisamb_c extends Disamb_c implements CodebaseDisamb {

  @Override
  public Node disambiguate(Ambiguous amb, ContextVisitor v, Position pos,
      Prefix prefix, Id name) throws SemanticException {
    Node n = super.disambiguate(amb, v, pos, prefix, name);
    if(n instanceof PackageNode) {
      //set the codebase of the package 
      PackageNode pn = (PackageNode) n;
      CodebasePackage cbp = (CodebasePackage) pn.package_();
      CodebaseContext ctx = (CodebaseContext) c;
      return pn.package_(cbp.codebase(ctx.currentCodebase()));
    }
    return n;
  }

}
