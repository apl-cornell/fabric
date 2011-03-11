package fabil.ast;

import fabil.types.CodebaseContext;
import fabil.types.CodebasePackage;
import fabil.ast.CodebaseDisamb;
import fabil.frontend.CodebaseSource;
import polyglot.ast.Ambiguous;
import polyglot.ast.Disamb_c;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.Prefix;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.ContextVisitor;

public class CodebaseDisamb_c extends Disamb_c implements CodebaseDisamb {

  @Override
  public Node disambiguate(Ambiguous amb, ContextVisitor v, Position pos,
      Prefix prefix, Id name) throws SemanticException {
    Node n = super.disambiguate(amb, v, pos, prefix, name);
    CodebaseContext ctx = (CodebaseContext) c;
    CodebaseSource cbs = ctx.currentSource();
    //Only qualify packages in remote source by the codebase
    if(cbs.isRemote() && n instanceof PackageNode) {
      //set the codebase of the package 
      PackageNode pn = (PackageNode) n;
      CodebasePackage cbp = (CodebasePackage) pn.package_();
      return pn.package_(cbp.codebase(ctx.currentCodebase()));
    }
    return n;
  }

}
