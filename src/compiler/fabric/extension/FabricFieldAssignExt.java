package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifFieldAssignExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;

import polyglot.ast.Assign;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class FabricFieldAssignExt extends JifFieldAssignExt {
  public FabricFieldAssignExt(ToJavaExt toJava) {
    super(toJava);
  }

  // Do same checking as before, with additional checks for 
  @Override
  public Node labelCheckLHS(LabelChecker lc) throws SemanticException {
    Assign assign = (Assign) super.labelCheckLHS(lc);
    Field fe = (Field) assign.left();

    // Do normal target and field access checking.
    DereferenceHelper.checkDereference(fe.target(), lc, node().position());
    
    // Label check the access for conflict rules.
    assign = assign.left(FabricFieldExt.conflictLabelCheck(fe, lc, true));

    // Update the path map.
    FabricPathMap Xfe = (FabricPathMap) getPathMap(fe);
    FabricPathMap X = (FabricPathMap) getPathMap(assign);
    return updatePathMap(assign, X.CL(Xfe.CL()));
  }

  @Override
  protected void updateContextForRHS(LabelChecker lc, JifContext A,
      PathMap Xleft) {
    super.updateContextForRHS(lc, A, Xleft);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfleft = (FabricPathMap) Xleft;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfleft.CL()));
  }
}
