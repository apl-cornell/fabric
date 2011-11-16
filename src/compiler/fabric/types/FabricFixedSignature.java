package fabric.types;

import jif.ast.LabelNode;
import jif.types.FixedSignature;
import jif.types.label.Label;
import polyglot.ast.FieldDecl;
import polyglot.types.Type;
import fabric.ast.FabricFieldDecl;

public class FabricFixedSignature extends FixedSignature implements FabricDefaultSignature {

  FabricTypeSystem fts;
  
  public FabricFixedSignature(FabricTypeSystem fts) {
    super(fts);
    this.fts = fts;
  }

  @Override
  public Label defaultAccessLabel(FieldDecl fd) {
    FabricFieldDecl ffd = (FabricFieldDecl) fd;
    LabelNode ln = ffd.accessLabel();
    if (ln == null) {
      FabricFieldInstance fi = (FabricFieldInstance) ffd.fieldInstance();
      Type t = fi.type();
      Label updateLabel = fts.labelOfType(t);
      return fts.toLabel(updateLabel.confProjection());
    } else {
      return ln.label();
    }
  }
  
}
