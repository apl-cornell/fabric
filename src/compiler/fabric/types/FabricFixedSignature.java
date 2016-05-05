package fabric.types;

import fabric.ast.FabricFieldDecl;

import jif.ast.LabelNode;
import jif.types.FixedSignature;
import jif.types.JifProcedureInstance;
import jif.types.label.Label;

import polyglot.ast.FieldDecl;
import polyglot.ast.ProcedureDecl;
import polyglot.types.Type;
import polyglot.util.Position;

//TODO: This "default signature" design pattern is unevenly applied.
//      We should either pull in all the default to this class or eliminate it.
public class FabricFixedSignature extends FixedSignature implements
FabricDefaultSignature {

  FabricTypeSystem fts;
  // Makes it easier to keep talking about {⊥→;⊤←}
  final private Label bottomCL;

  public FabricFixedSignature(FabricTypeSystem fts) {
    super(fts);
    this.fts = fts;
    bottomCL = fts.pairLabel(Position.compilerGenerated(),
        fts.bottomConfPolicy(Position.compilerGenerated()),
        fts.topIntegPolicy(Position.compilerGenerated()));
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
   * Defaults to either the begin pc label (joined with top integrity).
   * Reasoning is that this already has to flow to the begin conflict label for
   * all methods already.
   *
   * XXX: What about methods which don't perform accesses?
   */
  @Override
  public Label defaultBeginConflict(ProcedureDecl pd) {
    //TODO: Is this reasonable?
    JifProcedureInstance jpi = (JifProcedureInstance) pd.procedureInstance();
    if (!jpi.isDefaultPCBound())
      return fts.join(jpi.pcBound(), bottomCL);
    return fts.topLabel();
  }

  /**
   * Defaults to either the end label (joined with top integrity) or the begin
   * conflict label.  Reasoning is that the both of these already have to flow
   * to the end conflict label for all methods already.
   *
   * XXX: What about methods which don't perform accesses?
   */
  @Override
  public Label defaultEndConflict(ProcedureDecl pd) {
    //TODO: Is this reasonable?
    FabricProcedureInstance fpi = (FabricProcedureInstance) pd.procedureInstance();
    if (!fpi.isDefaultReturnLabel())
      return fts.join(fpi.returnLabel(), bottomCL);
    return fpi.beginConflictLabel();
  }
}
