package fabric.extension;

import fabric.types.FabricClassType;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;


public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }
  
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
      Label lhs = fts.join(pc, objLabel);
      
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
