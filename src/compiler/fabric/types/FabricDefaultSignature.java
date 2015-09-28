package fabric.types;

import jif.types.DefaultSignature;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;

import polyglot.ast.FieldDecl;
import polyglot.ast.ProcedureDecl;

public interface FabricDefaultSignature extends DefaultSignature {

  public Label defaultAccessPolicy(FieldDecl fd);

  /**
   * Return the default begin access policy for the given procedure.
   */
  public ConfPolicy defaultBeginAccess(ProcedureDecl pd);

  /**
   * Return the default end confidentiality policy for the given procedure.
   */
  public ConfPolicy defaultEndConf(ProcedureDecl pd);
}
