package fabric.extension;

import java.util.List;

import jif.ast.JifInstantiator;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathRoot;
import jif.types.label.AccessPathUninterpreted;
import jif.types.label.ArgLabel;
import jif.types.label.Label;
import polyglot.ast.Expr;
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
    } else {
      receiverPath = new AccessPathUninterpreted(receiverExpr, L.position());
    }
    StoreInstantiator inst =
        new StoreInstantiator(receiverType, receiverLbl, receiverPath, null,
            null, null, null, null, callerContext, storePath);
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

  /**
   * @param entryLabel
   * @param a
   * @param target
   * @param reference
   * @param targetLabel
   * @param formalLabels
   * @param formalTypes
   * @param actualLabels
   * @param arguments
   * @param emptyList
   * @param storeap
   * @return
   * @throws SemanticException
   */
  public static Label instantiate(Label entryLabel, JifContext ctx,
      Expr target, ReferenceType type, Label targetLabel,
      List<ArgLabel> formalLabels, List<? extends Type> formalTypes,
      List<Label> actualLabels, List<Expr> arguments,
      List<Label> actualParamLabels,
      AccessPath storeap) throws SemanticException {
    return JifInstantiator.instantiate(entryLabel, ctx, target, type,
        targetLabel, formalLabels, formalTypes, actualLabels, arguments,
        actualParamLabels);
  }

}
