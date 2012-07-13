package fabric.extension;

import jif.extension.JifFieldDeclDel;
import jif.types.label.Label;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.AmbiguityRemover;
import fabric.ast.FabricFieldDecl;
import fabric.types.FabricDefaultSignature;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;

public class FabricFieldDeclDel extends JifFieldDeclDel {

  @Override
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    FabricFieldDecl n = (FabricFieldDecl) super.disambiguate(ar);
    FabricFieldInstance ffi = (FabricFieldInstance) n.fieldInstance();
    FabricTypeSystem fts = (FabricTypeSystem) ar.typeSystem();
    FabricDefaultSignature fds = fts.fabricDefaultSignature();
    Label Li;
    if (n.accessLabel() == null) {
      Li = fds.defaultAccessLabel(n);
    } else {
      Li = n.accessLabel().label();
    }
    if (fts.containsThisLabel(Li)) {
      throw new SemanticException(
          "Access label cannot contain \"this\" label.", n.position());
    }

    // TODO: it seems fishy that Li can be null even if accessLabel is not null,
    // but it happens (in r3139 while building MapServ for example).
    ffi.setAccessLabel(Li == null ? null : Li.confProjection());
    return n.fieldInstance(ffi);
  }

}
