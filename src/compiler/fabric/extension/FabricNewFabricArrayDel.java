package fabric.extension;

import java.util.List;

import jif.extension.JifNewArrayDel;
import jif.types.JifContext;
import jif.types.label.AccessPath;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.ast.FabricUtil;
import fabric.ast.NewFabricArray;
import fabric.types.FabricTypeSystem;

public class FabricNewFabricArrayDel extends JifNewArrayDel {
  @Override
  public Node visitChildren(NodeVisitor v) {
    Node n = super.visitChildren(v);
    LocatedExt_c ext = (LocatedExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = v.visitEdge(n, ext.location());
      ext = ext.location(loc);
      return FabricUtil.updateFabricExt(n, ext);
    }
    return n;
  }

  @Override
  public List<Type> throwTypes(TypeSystem ts) {
    List<Type> result = super.throwTypes(ts);
    Node n = this.node();
    LocatedExt_c ext = (LocatedExt_c) FabricUtil.fabricExt(n);
    if (ext.location() != null) {
      Expr loc = ext.location();
      result.addAll(loc.del().throwTypes(ts));
    }
    return result;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    NewFabricArray n = (NewFabricArray) super.typeCheck(tc);
    LocatedExt_c ext = (LocatedExt_c) FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();
    JifContext context = (JifContext) tc.context();

    if (ext.location() != null) {
      if (!ts.isSubtype(ext.location().type(), ts.Store())) {
        throw new SemanticException("Array location must be a store.", ext
            .location().position());
      }

      if (!ts.isFinalAccessExpr(ext.location())) {
        throw new SemanticException(
            "Array location must be a final access path.", ext.location()
            .position());
      }

      ext = ext.storePrincipal(ts.exprToPrincipal(ts, ext.location(), context));
      return FabricUtil.updateFabricExt(n, ext);
    }

    if (tc.context().inStaticContext()) {
      // Allocate to local worker.
      ext =
          ext.storePrincipal(ts.workerLocalPrincipal(Position
              .compilerGenerated()));
      return FabricUtil.updateFabricExt(n, ext);
    }

    AccessPath storeap =
        ts.currentStoreAccessPathFor(tc.context().currentClass(), context);
    ext =
        ext.storePrincipal(ts.dynamicPrincipal(Position.compilerGenerated(),
            storeap));
    return FabricUtil.updateFabricExt(n, ext);
  }
}
