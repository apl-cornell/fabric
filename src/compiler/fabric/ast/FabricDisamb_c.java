package fabric.ast;

import java.net.URI;

import jif.ast.JifDisamb_c;
import polyglot.ast.Ambiguous;
import polyglot.ast.Disamb;
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
import codebases.frontend.CBJobExt;
import codebases.types.CodebaseClassType;
import codebases.types.CodebaseTypeSystem;
import codebases.types.NamespaceResolver;
import fabric.types.FabricContext;

/**
 * Disambiguates nodes relative to context and namespace.
 *
 * @author owen
 */
// NB: The body of this class is almost identical to FabilDisamb, but this
// class extends JifDisamb_c.
public class FabricDisamb_c extends JifDisamb_c implements Disamb {
  protected URI namespace;

  /* Convenience fields */
  private CodebaseTypeSystem ts;
  private CodebaseNodeFactory nf;

  @Override
  public Node disambiguate(Ambiguous amb, ContextVisitor v, Position pos,
      Prefix prefix, Id name) throws SemanticException {
    FabricContext ctx = (FabricContext) v.context();
    this.namespace = ctx.namespace();
    this.ts = (CodebaseTypeSystem) v.typeSystem();
    this.nf = (CodebaseNodeFactory) v.nodeFactory();

    return super.disambiguate(amb, v, pos, prefix, name);
  }

  protected Node disambiguateCodebaseNodePrefix(CodebaseNode cn) {
    if (cn.package_() == null) {
      // If there is no package, we use the toplevel namespace resolver
      NamespaceResolver nr = ts.namespaceResolver(cn.externalNamespace());
      if (nr.packageExists(name.id()) && packageOK()) {
        try {
          return nf.CodebaseNode(pos, cn.namespace(), cn.alias(),
              cn.externalNamespace(),
              ts.packageForName(cn.externalNamespace(), name.id()));
        } catch (SemanticException e) {
          throw new InternalCompilerError("Error creating package node: "
              + name, e);
        }
      } else if (typeOK()) {
        try {
          Importable q = nr.find(name.id());
          // This type was loaded w/ a codebase alias
          CBJobExt ext = (CBJobExt) v.job().ext();
          ext.addExternalDependency((CodebaseClassType) q, cn.alias());
          return nf.CanonicalTypeNode(pos, (Type) q);
        } catch (SemanticException e) {
          // just return null.
        }
      }
    } else {

      // Otherwise, we resolve the name using the CodebaseNode's package
      Resolver pc = ts.packageContextResolver(cn.package_());

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
        // This package *was* loaded w/ a codebase alias
        return nf.CodebaseNode(pos, cn.namespace(), cn.alias(),
            cn.externalNamespace(), q.toPackage());
      } else if (q.isType() && typeOK()) {
        // This type *was* loaded w/ a codebase alias
        CBJobExt ext = (CBJobExt) v.job().ext();
        ext.addExternalDependency((CodebaseClassType) q, cn.alias());
        return nf.CanonicalTypeNode(pos, (Type) q);
      }
    }
    return null;
  }

  @Override
  protected Node disambiguatePackagePrefix(PackageNode pn)
      throws SemanticException {
    if (pn instanceof CodebaseNode) {
      return disambiguateCodebaseNodePrefix((CodebaseNode) pn);
    }

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
      // This package was *not* loaded w/ a codebase alias
      return nf.PackageNode(pos, q.toPackage());
    } else if (q.isType() && typeOK()) {
      // This type was *not* loaded w/ a codebase alias
      CBJobExt ext = (CBJobExt) v.job().ext();
      ext.addDependency((CodebaseClassType) q);
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

    // Either a codebase alias or a package
    if (packageOK()) {
      // Is it an explicit codebase?
      FabricContext ctx = (FabricContext) v.context();
      URI ns = ctx.resolveCodebaseName(name.id());
      if (ns != null)
        return nf.CodebaseNode(pos, namespace, name.id(), ns);
      else
        // Must be a package then...
        return nf.PackageNode(pos, ts.packageForName(namespace, name.id()));
    }

    return null;
  }

}
