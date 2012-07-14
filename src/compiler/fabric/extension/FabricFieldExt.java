package fabric.extension;

import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;

public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Receiver target = checkTarget(lc, (Field) node());
    DereferenceHelper.checkDereference(target, lc, node().position());

    return super.labelCheck(lc);
  }

}
