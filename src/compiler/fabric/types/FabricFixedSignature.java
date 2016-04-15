package fabric.types;

import fabric.ast.FabricFieldDecl;

import jif.ast.LabelNode;
import jif.types.FixedSignature;
import jif.types.JifProcedureInstance;
import jif.types.label.Label;

import polyglot.ast.FieldDecl;
import polyglot.ast.ProcedureDecl;
import polyglot.types.Type;

//TODO: This "default signature" design pattern is unevenly applied.
//      We should either pull in all the default to this class or eliminate it.
public class FabricFixedSignature extends FixedSignature implements
FabricDefaultSignature {

  FabricTypeSystem fts;

  public FabricFixedSignature(FabricTypeSystem fts) {
    super(fts);
    this.fts = fts;
  }

  @Override
  public Label defaultAccessPolicy(FieldDecl fd) {
    FabricFieldDecl ffd = (FabricFieldDecl) fd;
    LabelNode ln = ffd.accessPolicy();
    if (ln == null) {
      FabricFieldInstance fi = (FabricFieldInstance) ffd.fieldInstance();
      Type t = fi.type();
      Label updateLabel = fts.labelOfType(t);
      return fts.toLabel(updateLabel.confProjection());
    } else {
      return ln.label();
    }
  }

  @Override
  public Label defaultBeginConflict(ProcedureDecl pd) {
    //TODO: Is this reasonable?
    JifProcedureInstance jpi = (JifProcedureInstance) pd.procedureInstance();
    if (!jpi.isDefaultPCBound())
      return jpi.pcBound();
    return fts.topLabel();
  }

  @Override
  public Label defaultEndConflict(ProcedureDecl pd) {
    //TODO: Is this reasonable?
    FabricProcedureInstance fpi = (FabricProcedureInstance) pd.procedureInstance();
    if (!fpi.isDefaultReturnLabel())
      return fpi.returnLabel();
    if (!fpi.isDefaultBeginConflict())
      return fpi.beginConflictLabel();
    if (!fpi.isDefaultPCBound())
      return fpi.pcBound();
    return fts.bottomLabel();
  }
}
