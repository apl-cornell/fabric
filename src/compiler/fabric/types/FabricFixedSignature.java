package fabric.types;

import jif.ast.LabelNode;
import jif.types.FixedSignature;
import jif.types.label.Label;
import polyglot.ast.FieldDecl;
import polyglot.types.Type;
import fabric.ast.FabricFieldDecl;

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

}
