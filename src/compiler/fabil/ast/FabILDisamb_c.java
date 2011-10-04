package fabil.ast;

import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.ContextVisitor;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseContext;
import fabil.types.CodebasePackage;
import fabric.lang.Codebase;

public class FabILDisamb_c extends Disamb_c implements CodebaseDisamb {

  @Override
  public Node disambiguate(Ambiguous amb, ContextVisitor v, Position pos,
      Prefix prefix, Id name) throws SemanticException {
    
    //////////////////////////////////////////////////////
    // Is this right/needed?
    this.v = v;
    this.pos = pos;
    this.prefix = prefix;
    this.name = name;
    this.amb = amb;
    
    this.nf = v.nodeFactory();
    this.ts = v.typeSystem();
    this.c = v.context();
    
    if (prefix instanceof PackageNode) {
      PackageNode pn = (PackageNode) prefix;
      CodebasePackage cbp = (CodebasePackage) pn.package_();

      if (cbp.source() == null || cbp.codebase() == null) {
        CodebaseContext context = (CodebaseContext) c;
        CodebaseSource cbs = context.currentSource();
        
        // Set the codebase and source of the package.
        cbp = cbp.source(cbs);
        prefix = ((PackageNode) prefix).package_(cbp);
      }
    }
    // Is this right/needed?
    //////////////////////////////////////////////////////

    Node n = super.disambiguate(amb, v, pos, prefix, name);
    //Only qualify packages in remote source by the codebase
    if(n instanceof PackageNode) {
      CodebaseContext ctx = (CodebaseContext) c;
      CodebaseSource cbs = ctx.currentSource();
      
      //Set the codebase and source of the package
      PackageNode pn = (PackageNode) n;
      CodebasePackage cbp = (CodebasePackage) pn.package_();
      cbp = cbp.source(cbs);
      
      return pn.package_(cbp);
    }
    return n;
  }

}
