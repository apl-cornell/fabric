package fabric.extension;

import java.util.List;

import jif.types.JifContext;
import jif.types.label.AccessPath;
import polyglot.ast.Expr;
import polyglot.ast.JL_c;
import polyglot.ast.New;
import polyglot.ast.NewOps;
import polyglot.ast.New_c;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;
import fabric.ast.FabricUtil;
import fabric.types.FabricTypeSystem;

public class FabricNewDel extends JL_c implements NewOps {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = v.visitEdge(n, ext.location());
      ext = (NewExt_c) ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public List<Type> throwTypes(TypeSystem ts) {
    List<Type> toReturn = super.throwTypes(ts);
    Node n = this.node();
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = ext.location();
      toReturn.addAll(loc.del().throwTypes(ts));
    }
    return toReturn;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    New n = (New) super.typeCheck(tc);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();
    JifContext context = (JifContext) tc.context();

    if (ext.requiresLocation(ts)) {
      if (ext.location() != null) {
        if (!ts.isSubtype(ext.location().type(), ts.Store()))
          throw new SemanticException("The location needs to be a Store.", ext
              .location().position());
        if (!ts.isFinalAccessExpr(ext.location()))
          throw new SemanticException(
              "The location must be a final access path.", ext.location()
                  .position());
        ext =
            (NewExt_c) ext.storePrincipal(ts.exprToPrincipal(ts,
                ext.location(), context));
        n = (New) FabricUtil.updateFabricExt(n, ext);

      } else {
        if (tc.context().inStaticContext()) {
          // allocation to local worker.
          ext =
              (NewExt_c) ext.storePrincipal(ts.workerLocalPrincipal(Position
                  .compilerGenerated()));
          n = (New) FabricUtil.updateFabricExt(n, ext);
        } else {
          AccessPath storeap =
              ts.currentStoreAccessPathFor(tc.context().currentClass(),
                  (JifContext) tc.context());
          ext =
              (NewExt_c) ext.storePrincipal(ts.dynamicPrincipal(
                  Position.compilerGenerated(), storeap));
          n = (New) FabricUtil.updateFabricExt(n, ext);
        }
      }
    }
    return n;
  }

  @Override
  public Node disambiguateOverride(Node parent, AmbiguityRemover ar)
      throws SemanticException {
    Node n = super.disambiguateOverride(parent, ar);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = ar.visitEdge(n, ext.location());
      ext = (NewExt_c) ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public Node typeCheckOverride(Node parent, TypeChecker tc)
      throws SemanticException {
    Node n = super.typeCheckOverride(parent, tc);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = tc.visitEdge(n, ext.location());
      ext = (NewExt_c) ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public TypeNode findQualifiedTypeNode(AmbiguityRemover ar, ClassType outer,
      TypeNode objectType) throws SemanticException {
    // XXX Should refactor to follow Polyglot design patterns.
    return ((New_c) node()).findQualifiedTypeNode(ar, outer, objectType);
  }

  @Override
  public New findQualifier(AmbiguityRemover ar, ClassType ct)
      throws SemanticException {
    // XXX Should refactor to follow Polyglot design patterns.
    return ((New_c) node()).findQualifier(ar, ct);
  }

  @Override
  public void typeCheckFlags(TypeChecker tc) throws SemanticException {
    // XXX Should refactor to follow Polyglot design patterns.
    ((New_c) node()).typeCheckFlags(tc);
  }

  @Override
  public void typeCheckNested(TypeChecker tc) throws SemanticException {
    // XXX Should refactor to follow Polyglot design patterns.
    ((New_c) node()).typeCheckNested(tc);
  }

  @Override
  public void printQualifier(CodeWriter w, PrettyPrinter tr) {
    // XXX Should refactor to follow Polyglot design patterns.
    ((New_c) node()).printQualifier(w, tr);
  }

  @Override
  public void printArgs(CodeWriter w, PrettyPrinter tr) {
    // XXX Should refactor to follow Polyglot design patterns.
    ((New_c) node()).printArgs(w, tr);
  }

  @Override
  public void printBody(CodeWriter w, PrettyPrinter tr) {
    // XXX Should refactor to follow Polyglot design patterns.
    ((New_c) node()).printBody(w, tr);
  }

  @Override
  public ClassType findEnclosingClass(Context c, ClassType ct) {
    // XXX Should refactor to follow Polyglot design patterns.
    return ((New_c) node()).findEnclosingClass(c, ct);
  }
}
