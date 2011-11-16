package fabric.extension;

import jif.ast.JifMethodDecl;
import jif.extension.JifMethodDeclExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.JifMethodInstance;
import jif.types.JifProcedureInstance;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;

public class MethodDeclJifExt extends JifMethodDeclExt {

  protected boolean is_remote;
  public MethodDeclJifExt(ToJavaExt toJava) {
    super(toJava);
    this.is_remote = false;
  }
  
  @Override
  public void checkBeginLabel(JifProcedureInstance jpi, LabelChecker lc)
      throws SemanticException {
    JifMethodDecl n = (JifMethodDecl)node();
    JifMethodInstance jmi = (JifMethodInstance) n.methodInstance();
    JifContext A = lc.context();
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    
    FabricParsedClassType pct = (FabricParsedClassType) jmi.container();
    
    Label startLabel = jmi.pcBound();
    Label accessLabel = ts.toLabel(pct.classAccessPolicy());
    
    // take the meet of the start label with bottom integrity
    // so that the integrity component is ignored
    FabricTypeSystem fts = (FabricTypeSystem) lc.typeSystem();
    startLabel = fts.meet(startLabel, 
        fts.pairLabel(Position.compilerGenerated(),
            fts.topConfPolicy(Position.compilerGenerated()), 
            fts.bottomIntegPolicy(Position.compilerGenerated())));
    
    // This constraint is not necessary for static methods
    // since they cannot directly access the fields
    // Also, if accessLabel is null, it means there are no fields
    // and again the constraint is not necessary
    if (!n.flags().isStatic() && accessLabel != null) {
      lc.constrain(new NamedLabel("begin label", "begin label", startLabel),
          LabelConstraint.LEQ,
          new NamedLabel("access label", "access label", accessLabel),
          A.labelEnv(), n.position(),
          new ConstraintMessage() {

        @Override
        public String msg() {
          return "The confidentiality component of the begin label of all methods in a class must be bounded" +
          " above by the access label of the class";
        }

      }
      );
    }
    
    super.checkBeginLabel(jpi, lc);
  }
  
  
  public void setRemote() {
    is_remote = true;
  }
  
  public boolean isRemote() {
    return is_remote;
  }

}
