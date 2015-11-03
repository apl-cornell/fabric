package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;

import jif.ast.JifExt_c;
import jif.ast.JifInstantiator;
import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.AccessPath;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }

  /**
   * Adds constraints to lc reflecting the possible conflict side effects of
   * accessing the reference and the field.
   */
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    final Field fe = (Field) super.labelCheck(lc);
    Receiver ref = checkTarget(lc, fe);
    Position pos = fe.position();

    /* Perform access checks for the target. */
    DereferenceHelper.checkDereference(ref, lc, pos);

    /* Secure txn access checks */
    if (ref == null)
      throw new InternalCompilerError("null receiver shouldn't happen at this point");
    
    //Id fieldName = fe.id();
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricFieldInstance fieldType = (FabricFieldInstance) fe.fieldInstance();
    FabricContext A = (FabricContext) lc.context();
    FabricReferenceType refType = (FabricReferenceType) targetType(ts, A, ref, fe);

    // Get the access policy on the field itself
    ConfPolicy accessPol = fieldType.accessPolicy();
    if (accessPol == null)
      accessPol = refType.accessPolicy();
    Label objLabel = JifExt_c.getPathMap(ref).NV();
    Label L = ts.toLabel(accessPol);
    if (ref instanceof Expr) {
      AccessPath storeap = ts.storeAccessPathFor((Expr) ref, A);
      L = StoreInstantiator.instantiate(L, A, (Expr) ref, refType, objLabel, storeap);
    }
    NamedLabel accessPolLabel = new NamedLabel("field access label",
          "the access label of the field referenced (defaults to the object's access label)",
          L);

    // Get join of confidentiality policies of previous accesses
    FabricPathMap Xt = (FabricPathMap) getPathMap(ref);
    NamedLabel accessedConfLabel = new NamedLabel("accessed conf label",
        "the join of the confidentiality policies of previously referenced fields",
        ts.join(ts.toLabel(A.accessedConf()), Xt.AC()));

    lc.constrain(accessedConfLabel, LabelConstraint.LEQ, accessPolLabel,
        A.labelEnv(), pos, new ConstraintMessage() {
      @Override
      public String msg() {
        return "The field access " + fe + " could leak information about "
             + "preceding accesses to the store the field is located on, "
             + "which has confidentiality lower bounded by the field's access "
             + "label.";
      }
    });

    // Fold in this field's confidentiality into the AC FabricPath.
    Label endConfLabel;
    if (ref instanceof Expr) {
      AccessPath storeap = ts.storeAccessPathFor((Expr) ref, A);
      endConfLabel = StoreInstantiator.instantiate(fieldType.label(),
          A, (Expr) ref, refType, objLabel, storeap);
    } else {
      endConfLabel = fieldType.label();
    }
    FabricPathMap X = (FabricPathMap) getPathMap(fe);
    Label newAC = ts.join(ts.join(X.AC(), endConfLabel), ts.toLabel(A.accessedConf()));
    return updatePathMap(fe, X.AC(newAC));
  }

}
