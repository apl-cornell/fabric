package fabric.extension;

import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;
import fabric.types.FabricClassType;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;


public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    FabricTypeSystem fts = (FabricTypeSystem) lc.typeSystem();
    Field n = (Field) node();
    FabricFieldInstance ffi = (FabricFieldInstance) n.fieldInstance();
    JifContext A = lc.context();
    
    Label pc = A.pc();
    Receiver target = checkTarget(lc, n);
    
    // TODO: Ignoring non-Expr targets. Is that OK?
    if (target instanceof Expr) {
      Label objLabel = getPathMap(target).NV();
      Label pcConf = fts.join(pc, fts.noComponentsLabel());
      Label objConf = fts.join(objLabel, fts.noComponentsLabel());
      Label lhs = fts.join(pcConf,objConf);
      
//    Label rhs = ffi.accessLabel();
      // Use the access label from the class rather than the field instance
      // This might change in future when we no longer require all field
      // access labels in a class to be the same
      FabricClassType fct = (FabricClassType) ffi.container();
      Label rhs = fct.getFoldedAccessLabel();
      lc.constrain(new NamedLabel("pc ⊔ object label", lhs), LabelConstraint.LEQ,
          new NamedLabel("access label", rhs), A.labelEnv(), n.position(),
          new ConstraintMessage() {
            
            public String msg() {
              return "The join of the pc and the target label at a field access" +
              		" should be bounded above by the field access label.";
            }
          }
      );
    }
    return super.labelCheck(lc);
  }

}
