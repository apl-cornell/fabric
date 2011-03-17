package fabric.extension;

import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.ast.JifMethodDecl;
import jif.extension.JifMethodDeclExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.JifMethodInstance;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

public class FabricMethodDeclExt extends JifMethodDeclExt {

  public FabricMethodDeclExt(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    JifMethodDecl n = (JifMethodDecl)node();
    JifMethodInstance jmi = (JifMethodInstance) n.methodInstance();
    JifContext A = lc.context();
    
    FabricParsedClassType pct = (FabricParsedClassType) jmi.container();
    
    Label startLabel = jmi.pcBound();
    Label accessLabel = pct.singleAccessLabel();
    
    // This constraint is not necessary for static methods
    // since they cannot directly access the fields
    if (!n.flags().isStatic()) {
      lc.constrain(new NamedLabel("begin label", "begin label", startLabel),
          LabelConstraint.LEQ,
          new NamedLabel("access label", "access label", accessLabel),
          A.labelEnv(), n.position(),
          new ConstraintMessage() {

        @Override
        public String msg() {
          return "The begin label of all methods in a class must be bounded" +
          " above by the access label of the class";
        }

      }
      );
    }
    return super.labelCheck(lc);
  }
  
}