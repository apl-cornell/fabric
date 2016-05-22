package fabric.extension;

import jif.extension.JifFieldDeclDel;
import jif.types.label.ConfPolicy;
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
    ConfPolicy Li;
    if (n.accessPolicy() == null) {
      Li = (ConfPolicy) fds.defaultAccessPolicy(n).confProjection().simplify();
    } else {
      Li = (ConfPolicy) n.accessPolicy().label().confProjection().simplify();
    }
    // TODO: it seems fishy that Li can be null even if accessLabel is not null,
    // but it happens (in r3139 while building MapServ for example).
    if (!fts.accessPolicyValid(Li)) {
      throw new SemanticException(
          "Access policies may only contain static elements like (principal or label) parameters and constants.",
          n.position());
    }

    ffi.setAccessPolicy(Li);
    return n.fieldInstance(ffi);
  }
}
