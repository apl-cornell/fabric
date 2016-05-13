
package fabric.translate;

import fabil.types.FabILTypeSystem;

import fabric.visit.FabricToFabilRewriter;

import jif.translate.JifToJavaRewriter;
import jif.translate.WritersToReadersLabelToJavaExpr_c;
import jif.types.label.Label;
import jif.types.label.WritersToReadersLabel;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class FabricWritersToReadersLabelToFabilExpr_c extends WritersToReadersLabelToJavaExpr_c {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw)
      throws SemanticException {
    FabricToFabilRewriter frw = (FabricToFabilRewriter) rw;
    FabILTypeSystem ts = (FabILTypeSystem) frw.java_ts();
    WritersToReadersLabel L = (WritersToReadersLabel) label;
    return rw.qq().parseExpr("%E.writersToReaders(%T.getWorker().getLocalStore())", rw.labelToJava(L.label()), ts.Worker());
  }
}
