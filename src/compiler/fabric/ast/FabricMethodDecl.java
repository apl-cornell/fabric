package fabric.ast;

import jif.ast.JifMethodDecl;
import jif.ast.LabelNode;

/**
 * An immutable representation of the Fabric method declaration.
 * It extends the Jif method declaration with the begin conflict label and the
 * end conflict label.
 */
public interface FabricMethodDecl extends FabricProcedureDecl, JifMethodDecl {
  FabricMethodDecl beginConflictLabel(LabelNode beginConflictLabel);

  FabricMethodDecl endConflictLabel(LabelNode endConflictLabel);
}
