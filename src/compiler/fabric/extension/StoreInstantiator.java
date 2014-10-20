package fabric.extension;

import java.util.List;

import jif.ast.JifInstantiator;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathRoot;
import jif.types.label.AccessPathThis;
import jif.types.label.AccessPathUninterpreted;
import jif.types.label.ArgLabel;
import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import fabric.types.AccessPathStore;

/**
 *
 */
public class StoreInstantiator extends JifInstantiator {
  public static Label instantiate(Label L, JifContext callerContext,
      Expr receiverExpr, ReferenceType receiverType, Label receiverLbl,
      AccessPath storePath) throws SemanticException {
    JifTypeSystem ts = (JifTypeSystem) callerContext.typeSystem();
    AccessPath receiverPath;
    if (ts.isFinalAccessExprOrConst(receiverExpr, receiverType)) {
      receiverPath =
          ts.exprToAccessPath(receiverExpr, receiverType, callerContext);
    } else if (receiverExpr instanceof New) {
      receiverPath = new AccessPathThis(receiverType.toClass(), L.position());
    } else {
      receiverPath = new AccessPathUninterpreted(receiverExpr, L.position());
    }
    StoreInstantiator inst =
        new StoreInstantiator(receiverType, receiverLbl, receiverPath, null,
            null, null, null, null, callerContext, storePath);
    return inst.instantiate(L);
  }

  public static Label instantiate(Label L, JifContext callerContext,
      Expr receiverExpr, ReferenceType receiverType, Label receiverLbl,
      AccessPath storePath, List<ArgLabel> formalArgLabels,
      List<? extends Type> formalArgTypes,
      List<? extends Label> actualArgLabels, List<Expr> actualArgExprs,
      List<? extends Label> actualParamLabels) throws SemanticException {
    JifTypeSystem ts = (JifTypeSystem) callerContext.typeSystem();
    AccessPath receiverPath;
    if (ts.isFinalAccessExprOrConst(receiverExpr, receiverType)) {
      receiverPath =
          ts.exprToAccessPath(receiverExpr, receiverType, callerContext);
    } else if (receiverExpr instanceof New) {
      receiverPath = new AccessPathThis(receiverType.toClass(), L.position());
    } else {
      receiverPath = new AccessPathUninterpreted(receiverExpr, L.position());
    }
    StoreInstantiator inst =
        new StoreInstantiator(receiverType, receiverLbl, receiverPath,
            formalArgLabels, formalArgTypes, actualArgLabels, actualArgExprs,
            actualParamLabels, callerContext, storePath);
    return inst.instantiate(L);
  }

  protected AccessPath storePath;

  /**
   */
  protected StoreInstantiator(ReferenceType receiverType, Label receiverLbl,
      AccessPath receiverPath, List<ArgLabel> formalArgLabels,
      List<? extends Type> formalArgTypes,
      List<? extends Label> actualArgLabels, List<Expr> actualArgExprs,
      List<? extends Label> actualParamLabels, JifContext callerContext,
      AccessPath storePath) {
    super(receiverType, receiverLbl, receiverPath, formalArgLabels,
        formalArgTypes, actualArgLabels, actualArgExprs, actualParamLabels,
        callerContext);
    this.storePath = storePath;
  }

  @Override
  protected Object instantiateImpl(Object L, Position pos) {
    if (L == null) return L;
    StoreAccessPathInstantiator storeAPinst =
        new StoreAccessPathInstantiator(tempThisRoot, storePath);
    try {
      L = substImpl(L, storeAPinst);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unexpected SemanticException "
          + "during label substitution: " + e.getMessage(), pos);
    }
    return super.instantiateImpl(L, pos);
  }

  /**
   * Replaces srcRoot with trgPath in dynamic labels and principals
   */
  protected static class StoreAccessPathInstantiator extends LabelSubstitution {
    private AccessPathRoot srcRoot;
    private AccessPath storePath;

    public StoreAccessPathInstantiator(AccessPathRoot srcRoot,
        AccessPath storePath) {
      this.srcRoot = srcRoot;
      this.storePath = storePath;
    }

    @Override
    public AccessPath substAccessPath(AccessPath ap) {
      if (ap instanceof AccessPathStore) {
        AccessPathStore aps = (AccessPathStore) ap;
        if (aps.path().equals(srcRoot)) return storePath;
      }
      return ap;
    }
  }

  public static Label instantiate(Label L, JifContext callerContext,
      Expr receiverExpr, ReferenceType receiverType, Label receiverLabel,
      List<ArgLabel> formalArgLabels, List<? extends Type> formalArgTypes,
      List<Label> actualArgLabels, List<Expr> actualArgExprs,
      List<Label> actualParamLabels, AccessPath storeap)
          throws SemanticException {
    JifTypeSystem ts = (JifTypeSystem) callerContext.typeSystem();
    AccessPath receiverPath;
    if (ts.isFinalAccessExprOrConst(receiverExpr, receiverType)) {
      receiverPath =
          ts.exprToAccessPath(receiverExpr, receiverType, callerContext);
    } else {
      receiverPath = new AccessPathUninterpreted(receiverExpr, L.position());
    }
    StoreInstantiator inst =
        new StoreInstantiator(receiverType, receiverLabel, receiverPath,
            formalArgLabels, formalArgTypes, actualArgLabels, actualArgExprs,
            actualParamLabels, callerContext, storeap);
    return inst.instantiate(L);
  }

  public static Principal instantiate(Principal P, JifContext callerContext,
      Expr receiverExpr, ReferenceType receiverType, Label receiverLabel,
      List<ArgLabel> formalArgLabels, List<? extends Type> formalArgTypes,
      List<Label> actualArgLabels, List<Expr> actualArgExprs,
      List<Label> actualParamLabels, AccessPath storeap)
          throws SemanticException {
    JifTypeSystem ts = (JifTypeSystem) callerContext.typeSystem();
    AccessPath receiverPath;
    if (ts.isFinalAccessExprOrConst(receiverExpr, receiverType)) {
      receiverPath =
          ts.exprToAccessPath(receiverExpr, receiverType, callerContext);
    } else {
      receiverPath = new AccessPathUninterpreted(receiverExpr, P.position());
    }
    StoreInstantiator inst =
        new StoreInstantiator(receiverType, receiverLabel, receiverPath,
            formalArgLabels, formalArgTypes, actualArgLabels, actualArgExprs,
            actualParamLabels, callerContext, storeap);
    return inst.instantiate(P);
  }

  public static Type instantiate(Type t, JifContext callerContext,
      Expr receiverExpr, ReferenceType receiverType, Label receiverLabel,
      List<ArgLabel> formalArgLabels, List<? extends Type> formalArgTypes,
      List<Label> actualArgLabels, List<Expr> actualArgExprs,
      List<Label> actualParamLabels, AccessPath storeap)
          throws SemanticException {
    JifTypeSystem ts = (JifTypeSystem) callerContext.typeSystem();
    AccessPath receiverPath;
    if (ts.isFinalAccessExprOrConst(receiverExpr, receiverType)) {
      receiverPath =
          ts.exprToAccessPath(receiverExpr, receiverType, callerContext);
    } else {
      receiverPath = new AccessPathUninterpreted(receiverExpr, t.position());
    }
    StoreInstantiator inst =
        new StoreInstantiator(receiverType, receiverLabel, receiverPath,
            formalArgLabels, formalArgTypes, actualArgLabels, actualArgExprs,
            actualParamLabels, callerContext, storeap);
    return inst.instantiate(t);
  }

}
