package fabric.extension;

import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.LabeledType;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import fabric.types.FabricArrayType;
import fabric.types.FabricClassType;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;


public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Field n = (Field) node();
    JifContext A = lc.context();
    
    Label pc = A.pc();
    Receiver target = checkTarget(lc, n);
    
    DereferenceHelper.checkDereference(target, lc, n.position());
    
    return super.labelCheck(lc);
  }

}
