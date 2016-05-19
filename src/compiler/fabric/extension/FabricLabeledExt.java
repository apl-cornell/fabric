package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.extension.JifLabeledExt;
import jif.translate.ToJavaExt;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Labeled;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricLabeledExt extends JifLabeledExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricLabeledExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    Labeled ls = (Labeled) node();

    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();
    A = (FabricContext) ls.del().enterScope(A);

    String label = ls.label();

    Label L1 = ts.freshLabelVariable(node().position(), label,
            "conflict label at the labeled position " + label + " ("
                    + ls.position() + ")");
    Label L2 = ts.freshLabelVariable(node().position(), label,
            "conflict label at the labeled position " + label + " ("
                    + ls.position() + ")");

    // Push the current block so that we're not clobbering pre-existing labels
    // of the same name (I think?)
    A = (FabricContext) A.pushBlock();

    // Mark the conflict label associated with this label for both break and
    // continue.
    A.gotoConflictLabel(polyglot.ast.Branch.CONTINUE, label, L1);
    A.gotoConflictLabel(polyglot.ast.Branch.BREAK, label, L2);

    if (A.conflictLabel().equals(ts.noAccesses())) {
      A.setConflictLabel(L1);
    } else {
      A.setConflictLabel(lc.lowerBound(A.conflictLabel(), L1));
    }

    ls = (Labeled) super.labelCheckStmt(lc.context(A));

    FabricPathMap X = (FabricPathMap) getPathMap(ls);
    X = X.setCL(ts.gotoPath(polyglot.ast.Branch.CONTINUE, label), ts.noAccesses());
    X = X.setCL(ts.gotoPath(polyglot.ast.Branch.BREAK, label), ts.noAccesses());
    X = X.CL(L2);

    A = (FabricContext) A.pop();

    A.setConflictLabel(X.CL());

    return ls;
  }
}
