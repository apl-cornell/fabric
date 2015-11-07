package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;

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
    Field fe = (Field) super.labelCheck(lc);
    Receiver refR = fe.target();
    Position pos = fe.position();

    if (refR instanceof Expr) {
      Expr ref = (Expr) refR;
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
      FabricContext A = (FabricContext) lc.context();
      FabricFieldInstance ffi = (FabricFieldInstance) fe.fieldInstance();
      FabricReferenceType refType = (FabricReferenceType) targetType(ts, A, ref, fe);

      ConfPolicy accessPol = ffi.accessPolicy();
      if (accessPol == null)
        accessPol = ffi.label().confProjection();
      Label accessLabel = ts.toLabel(accessPol);
      Label objLabel = getPathMap(ref).NV();
      AccessPath storeap = ts.storeAccessPathFor(ref, A);
      accessLabel =
          StoreInstantiator.instantiate(accessLabel, A, ref, refType,
              objLabel, storeap);

      // Get join of update labels of previous accesses
      FabricPathMap Xt = (FabricPathMap) getPathMap(ref);
      NamedLabel accessedLabel = new NamedLabel("accessed label",
          "the join of the update labels of previously accessed objects",
          ts.join(A.accessedLabelBound(),
                  ts.join(A.accessedLabel(),
                          Xt.A())));

      NamedLabel accessPolLabel = new NamedLabel("access label of " + ref,
            "the access label of the object referenced",
            ts.join(accessLabel,
              ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));

      // Check that this won't cause abort leaks.
      lc.constrain(accessedLabel, LabelConstraint.LEQ, accessPolLabel,
          A.labelEnv(), pos, new ConstraintMessage() {
        @Override
        public String msg() {
          return "The objects accessed during this method could leak "
               + "information about preceding accesses to the stores "
               + "of previously accessed objects, which have "
               + "access lower bounded by the method's begin access "
               + "label.";
        }
      });
      
      // Fold in this field's access label into the A FabricPath.
      FabricPathMap X = (FabricPathMap) getPathMap(fe);
      Label newA = ts.join(accessLabel,
                           ts.join(Xt.A(),
                                   ts.join(X.A(),
                                           A.accessedLabel())));
      fe = (Field) updatePathMap(fe, X.A(newA));

      // Early check of end access bound to get better location reporting.
      NamedLabel newANamed = new NamedLabel("accessed label",
          "the join of the update labels of previously accessed objects",
          newA);

      NamedLabel endConfBoundLabel = new NamedLabel("end access label",
          "the upper bound on the update labels of objects accessed in this method",
          ts.join(A.endAccessBound(),
            ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));

      lc.constrain(newANamed, LabelConstraint.LEQ, endConfBoundLabel,
          A.labelEnv(), pos, new ConstraintMessage() {
        @Override
        public String msg() {
          return "The current method makes more restricted accesses than the ending "
               + "access label allows.";
        }
      });
    }

    /* Perform access checks for the target. */
    return DereferenceHelper.checkDereference(fe, refR, lc, pos);
  }

}
