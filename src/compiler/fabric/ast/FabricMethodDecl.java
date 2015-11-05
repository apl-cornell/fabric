package fabric.ast;

import jif.ast.JifMethodDecl;
import jif.ast.LabelNode;

/**
 * An immutable representation of the Fabric method declaration.
 * It extends the Jif method declaration with the begin access label and the end
 * confidentiality label.
 */
public interface FabricMethodDecl extends FabricProcedureDecl, JifMethodDecl {
  FabricMethodDecl beginAccessLabel(LabelNode beginAccessLabel);

  FabricMethodDecl endAccessLabel(LabelNode endAccessLabel);
}
