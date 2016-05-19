package fabric.translate;

import fabric.types.FabricFieldInstance;
import jif.translate.FieldToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.NodeFactory;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class FieldToFabilExt_c extends FieldToJavaExt_c {

  @Override
  public Expr exprToJava(JifToJavaRewriter rw) throws SemanticException {
    Field n = (Field) super.exprToJava(rw);
    Field old = (Field) node();
    FabricFieldInstance fi = (FabricFieldInstance) old.fieldInstance();

    if (fi.splitClassName() != null) {
      // Retarget the field access to go through the split object.
      NodeFactory nf = rw.java_nf();
      Expr newTarget = nf.Field(n.position(), n.target(),
          nf.Id(Position.compilerGenerated(), fi.splitClassName()));
      n = n.target(newTarget);
    }

    return n;
  }

}
