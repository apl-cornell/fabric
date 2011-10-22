package fabil.ast;

import java.net.URI;

import polyglot.ast.Ambiguous;
import polyglot.ast.Disamb;
import polyglot.ast.Disamb_c;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.Prefix;
import polyglot.types.Importable;
import polyglot.types.Named;
import polyglot.types.NoClassException;
import polyglot.types.Qualifier;
import polyglot.types.Resolver;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.VarInstance;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.ContextVisitor;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNodeFactory;
import codebases.types.CBPackage;
import codebases.types.CodebaseTypeSystem;
import codebases.types.NamespaceResolver;
import fabil.types.FabILContext;

public class FabILDisamb extends Disamb_c implements Disamb {
  protected URI namespace;
  
  /* Convenience fields */
  private CodebaseTypeSystem ts;
  private CodebaseNodeFactory nf;
  
  @Override
  public Node disambiguate(Ambiguous amb, ContextVisitor v, Position pos,
      Prefix prefix, Id name) throws SemanticException {
    FabILContext ctx = (FabILContext) v.context();
    this.namespace = ctx.namespace();
    this.ts = (CodebaseTypeSystem) v.typeSystem();
    this.nf = (CodebaseNodeFactory) v.nodeFactory();

    if(prefix instanceof PackageNode) {
      PackageNode pn = (PackageNode) prefix;
      this.namespace = ((CBPackage)pn.package_()).namespace();
    } else if(prefix instanceof CodebaseNode) {
      CodebaseNode cn = (CodebaseNode) prefix;
      this.namespace = cn.namespace();
    }
    
    Node result = super.disambiguate(amb, v, pos, prefix, name);
    if(result != null) 
      return result;
    else if(prefix instanceof CodebaseNode) {
      CodebaseNode cn = (CodebaseNode) prefix;
      result = disambiguateCodebaseNodePrefix(cn);
    }
    return result;
  }

  protected Node disambiguateCodebaseNodePrefix(CodebaseNode cn) {
    NamespaceResolver nr = ts.namespaceResolver(cn.namespace());
    
    if(nr.packageExists(name.id()) && packageOK()) {
      try {
        return nf.PackageNode(pos, ts.packageForName(cn.namespace(), name.id()));
      } catch (SemanticException e) {
        throw new InternalCompilerError("Error creating package node: " + name, e);
      }
    }
    else if(typeOK()){
      try {
        Importable q = nr.find(name.id());
        return nf.CanonicalTypeNode(pos, (Type) q);
      } catch (SemanticException e) {
        //just return null.
      }
    }
    return null;
  }

  @SuppressWarnings("unused")
  @Override
  protected Node disambiguatePackagePrefix(PackageNode pn)
      throws SemanticException {

    Resolver pc = ts.packageContextResolver(pn.package_());

    Named n;

    try {
      n = pc.find(name.id());
    } catch (SemanticException e) {
      return null;
    }

    Qualifier q = null;

    if (n instanceof Qualifier) {
      q = (Qualifier) n;
    } else {
      return null;
    }

    if (q.isPackage() && packageOK()) {
      return nf.PackageNode(pos, q.toPackage());
    } else if (q.isType() && typeOK()) {
      return nf.CanonicalTypeNode(pos, q.toType());
    }

    return null;
  }

  @Override
  protected Node disambiguateNoPrefix() throws SemanticException {
    if (exprOK()) {
      // First try local variables and fields.
      VarInstance vi = c.findVariableSilent(name.id());

      if (vi != null) {
        Node n = disambiguateVarInstance(vi);
        if (n != null) return n;
      }
    }

    // no variable found. try types.
    if (typeOK()) {
      try {
        Named n = c.find(name.id());
        if (n instanceof Type) {
          Type type = (Type) n;
          if (!type.isCanonical()) {
            throw new InternalCompilerError(
                "Found an ambiguous type in the context: " + type, pos);
          }
          return nf.CanonicalTypeNode(pos, type);
        }
      } catch (NoClassException e) {
        if (!name.id().equals(e.getClassName())) {
          // hmm, something else must have gone wrong
          // rethrow the exception
          throw e;
        }

        // couldn't find a type named name.
        // It must be a package--ignore the exception.
      }
    }
    
    //Either a codebase alias or a package
    if(packageOK()) {
      //Is it an explicit codebase? 
      FabILContext ctx = (FabILContext) v.context();
      URI ns = ctx.resolveCodebaseName(name.id());
      if(ns != null)
        return nf.CodebaseNode(pos, ns);
      else
        // Must be a package then...
        return nf.PackageNode(pos, ts.packageForName(namespace, name.id()));
    }

    return null;
  }

}
