package fabric.extension;

import jif.extension.JifFieldAssignExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.Assign;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;

public class FabricFieldAssignExt extends JifFieldAssignExt {
  public FabricFieldAssignExt(ToJavaExt toJava) {
    super(toJava);
  }

  // Do same checking as before, with additional checks for 
  @Override
  public Node labelCheckLHS(LabelChecker lc) throws SemanticException {
    Assign assign = (Assign) super.labelCheckLHS(lc);
    final Field fe = (Field) assign.left();

    // Do normal target and field access checking.
    Receiver target = FabricFieldExt.checkTarget(lc, fe);
    DereferenceHelper.checkDereference(target, lc, node().position());

    // TODO: Check the RHS (because I'm pretty sure it's not checked elsewhere?)
    // Update the access
    
    // Label check the access for conflict rules.
    return assign.left(FabricFieldExt.conflictLabelCheck(target, fe, lc, true));
  }
}
