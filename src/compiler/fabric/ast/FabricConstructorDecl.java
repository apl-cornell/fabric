package fabric.ast;

import jif.ast.JifConstructorDecl;
import jif.ast.LabelNode;

/**
 * An immutable representation of the Fabric method declaration.
 * It extends the Jif method declaration with the begin access label and the end
 * confidentiality label.
 */
public interface FabricConstructorDecl extends FabricProcedureDecl,
       JifConstructorDecl {
  FabricConstructorDecl beginConflictLabel(LabelNode beginConflictLabel);

  FabricConstructorDecl endConflictLabel(LabelNode endConflictLabel);
}
