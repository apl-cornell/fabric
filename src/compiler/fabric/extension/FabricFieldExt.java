package fabric.extension;

import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

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
    final Field fe = (Field) super.labelCheck(lc);
    Receiver ref = fe.target();
    Position pos = fe.position();

    /* Perform access checks for the target. */
    return DereferenceHelper.checkDereference(fe, ref, lc, pos);
  }

}
