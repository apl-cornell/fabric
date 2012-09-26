package fabric.extension;

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.ast.Store;
import fabric.types.FabricTypeSystem;

/**
 * 
 */
public class StoreToFabilExt_c extends ToJavaExt_c implements ToJavaExt {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Store store = (Store) node();
    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();
    if (ts.isTransient(store.expr().type())) {
      return rw.qq().parseExpr("Worker.getWorker().getLocalStore()");
    }

    return rw.qq().parseExpr("%E.$getStore()", store.expr());
  }

}
