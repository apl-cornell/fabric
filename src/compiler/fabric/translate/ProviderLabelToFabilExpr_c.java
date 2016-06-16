package fabric.translate;

import fabil.ast.FabILNodeFactory;
import jif.translate.JifToJavaRewriter;
import jif.translate.ProviderLabelToJavaExpr_c;
import jif.types.label.Label;
import jif.types.label.ProviderLabel;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class ProviderLabelToFabilExpr_c extends ProviderLabelToJavaExpr_c {

  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw, Expr thisQualifier,
      boolean simplify) throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();
    ProviderLabel provider = (ProviderLabel) label;
    if (provider.isTrusted()) {
      return label.typeSystem().bottomLabel().toJava(rw, thisQualifier,
          simplify);
    }

    Position pos = provider.position();
    if (pos == null) pos = Position.compilerGenerated();
    return nf.ProviderLabel(pos, rw.typeToJava(provider.classType(), pos));
  }

}
