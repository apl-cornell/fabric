package fabric.extension;

import fabric.types.FabricArrayType;
import fabric.types.FabricClassType;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.LabeledType;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;


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
      ReferenceType container = ffi.container();
      if (container instanceof FabricArrayType) {
        FabricArrayType fat = (FabricArrayType) container;
        if (fts.isFabricArray(fat)) {
          Label al = ffi.accessLabel();
          if (al == null) {
            // TODO Using the array label as its access label
            // Need to add syntax to specify array access labels
            al = ((LabeledType)fat.base()).labelPart();
          }
          lc.constrain(new NamedLabel("pc ⊔ object label", lhs), LabelConstraint.LEQ,
              new NamedLabel("access label", al), A.labelEnv(), n.position(),
              new ConstraintMessage() {

            public String msg() {
              return "The join of the pc and the target label at a array access" +
              " should be bounded above by the array access label.";
            }
          }
          );          
        }
      }
      if (container instanceof FabricClassType) {
        FabricClassType fct = (FabricClassType) container;
        if (fts.isFabricClass(fct)) {
          // We do not need access label checking for non fabric classes
          // since their instances will always be read only locally
          Label rhs = fct.getFoldedAccessLabel();
          lc.constrain(new NamedLabel("pc ⊔ object label", lhs), LabelConstraint.LEQ,
              new NamedLabel("access label", rhs), A.labelEnv(), n.position(),
              new ConstraintMessage() {

            @Override
            public String msg() {
              return "The join of the pc and the target label at a field access" +
              " should be bounded above by the field access label.";
            }
          }
          );
        }
      }
    }
    return super.labelCheck(lc);
  }

}
