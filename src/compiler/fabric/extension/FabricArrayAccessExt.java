package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;

import jif.extension.JifArrayAccessExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.AccessPath;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.ArrayType;
import polyglot.types.SemanticException;
import polyglot.util.Position;

/**
 *
 */
public class FabricArrayAccessExt extends JifArrayAccessExt {

  /**
   * @param toJava
   */
  public FabricArrayAccessExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    ArrayAccess n = (ArrayAccess) super.labelCheck(lc);
    Expr array = n.array();
    ArrayType type = (ArrayType) lc.typeSystem().unlabel(array.type());
    Position pos = n.position();

    // Checks for monotonicity
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    ConfPolicy accessPol = ((FabricReferenceType) type).accessPolicy();
    Label accessLabel = ts.toLabel(accessPol);
    Label objLabel = getPathMap(array).NV();
    AccessPath storeap = ts.storeAccessPathFor(array, A);
    accessLabel =
        StoreInstantiator.instantiate(accessLabel, A, array, type,
            objLabel, storeap);

    // Get join of update labels of previous accesses
    FabricPathMap Xt = (FabricPathMap) getPathMap(array);
    NamedLabel accessedLabel = new NamedLabel("accessed label",
        "the join of the update labels of previously accessed objects",
        ts.join(A.accessedLabelBound(),
                ts.join(A.accessedLabel(),
                        Xt.A())));

    NamedLabel accessPolLabel = new NamedLabel("access label of items in " + array,
          "the access label of the array referenced",
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
    FabricPathMap X = (FabricPathMap) getPathMap(n);
    Label newA = ts.join(accessLabel,
                         ts.join(Xt.A(),
                                 ts.join(X.A(),
                                         A.accessedLabel())));
    n = (ArrayAccess) updatePathMap(n, X.A(newA));

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

    return DereferenceHelper.checkAccess(n, array, (FabricReferenceType) type, lc, n.position());
  }

}
