package fabric.ast;

import java.net.URI;

import jif.ast.JifDisamb_c;
import polyglot.ast.Ambiguous;
import polyglot.ast.Disamb;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.Prefix;
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
import codebases.types.CodebaseTypeSystem;
import fabric.types.FabricContext;
/**
 * Disambiguates nodes relative to context and namespace.
 * @author owen
 *
 */
//NB: The body of this class is almost identical to FabilDisamb, but this 
//      class extends JifDisamb_c.
public class FabricDisamb_c extends JifDisamb_c implements Disamb {
  protected URI namespace;
  protected CodebaseTypeSystem ts;

  @Override
  public Node disambiguate(Ambiguous amb, ContextVisitor v, Position pos,
      Prefix prefix, Id name) throws SemanticException {
    FabricContext ctx = (FabricContext) v.context();
    this.namespace = ctx.namespace();
    this.ts = (CodebaseTypeSystem) v.typeSystem();
    return super.disambiguate(amb, v, pos, prefix, name);
  }

  @SuppressWarnings("unused")
  @Override
  protected Node disambiguatePackagePrefix(PackageNode pn)
      throws SemanticException {
    Resolver pc = ts.packageContextResolver(namespace, pn.package_());
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

    // Must be a package then...
    if (packageOK()) {
      return nf.PackageNode(pos, ts.packageForName(namespace, name.id()));
    }

    return null;
  }

}
