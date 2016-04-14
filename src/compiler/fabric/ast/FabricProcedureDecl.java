package fabric.ast;

import jif.ast.JifProcedureDecl;
import jif.ast.LabelNode;

/** An immutable representation of the Fabric procedure declaration.
 *  It extends the Jif procedure declaration with the begin conflict policy and
 *  the end conflict policy.
 */
public interface FabricProcedureDecl extends JifProcedureDecl {
    LabelNode beginConflictLabel();

    LabelNode endConflictLabel();
}
