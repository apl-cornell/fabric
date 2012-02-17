package fabric.extension;

import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.Type;
import polyglot.types.SemanticException;
import jif.extension.JifCastExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

public class FabricCastExt extends JifCastExt {

  public FabricCastExt(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    FabricTypeSystem fts = (FabricTypeSystem) lc.typeSystem();
    Cast cast = (Cast) node();
    JifContext A = lc.context();

    Label pc = A.pc();
    Expr ref = (Expr) lc.labelCheck(cast.expr());
    
    Label objLabel = getPathMap(ref).NV();
    Label pcConf = fts.join(pc, fts.noComponentsLabel());
    Label objConf = fts.join(objLabel, fts.noComponentsLabel());
    Label lhs = fts.join(pcConf,objConf);
    
    Type refType = ref.type();
    // Casting of arrays not supported yet
    if (refType instanceof FabricClassType) {
      FabricClassType fct = (FabricClassType) refType;
      if (fts.isFabricClass(fct)) {
        // We do not need access label checking for non fabric classes
        // since their instances will always be read only locally
        Label rhs = fts.toLabel(fct.classAccessPolicy());
        lc.constrain(new NamedLabel("pc ⊔ object policy", lhs), LabelConstraint.LEQ,
            new NamedLabel("access policy", rhs), A.labelEnv(), cast.position(),
            new ConstraintMessage() {

          @Override
          public String msg() {
            return "The join of the pc and the target label at a cast" +
            " should be bounded above by the object access label.";
          }
        }
        );
      }
    }
    return super.labelCheck(lc);
  }

}
