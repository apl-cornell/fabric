package fabric.types;

import fabric.ast.FabricFieldDecl;

import jif.ast.LabelNode;
import jif.types.FixedSignature;
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

  /**
   * Defaults to lower bounding conflict labels of accesses to say that there
   * are *no* accesses performed.
   */
  @Override
  public Label defaultBeginConflict(ProcedureDecl pd) {
    FabricProcedureInstance fpi = (FabricProcedureInstance) pd.procedureInstance();
    if (!fpi.isDefaultEndConflict()) return fpi.endConflictLabel();
    return fts.noAccesses();
  }

  /**
   * Defaults to whatever the begin conflict label was, on the assumption that
   * all of the accesses are at the same conflict label.
   */
  @Override
  public Label defaultEndConflict(ProcedureDecl pd) {
    FabricProcedureInstance fpi = (FabricProcedureInstance) pd.procedureInstance();
    return fpi.beginConflictLabel();
  }
}
