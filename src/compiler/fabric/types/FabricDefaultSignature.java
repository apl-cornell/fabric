package fabric.types;

import jif.types.DefaultSignature;
import jif.types.label.Label;

import polyglot.ast.FieldDecl;
import polyglot.ast.ProcedureDecl;

public interface FabricDefaultSignature extends DefaultSignature {

  public Label defaultAccessPolicy(FieldDecl fd);

  /**
   * Return the default begin conflict label for the given procedure.
   */
  public Label defaultBeginConflict(ProcedureDecl pd);

  /**
   * Return the default end conflict label for the given procedure.
   */
  public Label defaultEndConflict(ProcedureDecl pd);
}
