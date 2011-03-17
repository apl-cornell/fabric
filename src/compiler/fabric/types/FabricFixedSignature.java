package fabric.types;

import fabric.ast.FabricFieldDecl;
import polyglot.ast.FieldDecl;
import polyglot.ast.Formal;
import polyglot.ast.ProcedureDecl;
import polyglot.types.FieldInstance;
import polyglot.types.Type;
import polyglot.util.Position;
import jif.ast.LabelNode;
import jif.types.DefaultSignature;
import jif.types.FixedSignature;
import jif.types.JifTypeSystem;
import jif.types.label.Label;

public class FabricFixedSignature extends FixedSignature implements FabricDefaultSignature {

  FabricTypeSystem fts;
  
  public FabricFixedSignature(FabricTypeSystem fts) {
    super(fts);
    this.fts = fts;
  }

  public Label defaultAccessLabel(FieldDecl fd) {
    FabricFieldDecl ffd = (FabricFieldDecl) fd;
    LabelNode ln = ffd.accessLabel();
    if (ln == null) {
      FabricFieldInstance fi = (FabricFieldInstance) ffd.fieldInstance();
      Type t = fi.type();
      return fts.labelOfType(t);
    } else {
      return ln.label();
    }
  }
  
}
