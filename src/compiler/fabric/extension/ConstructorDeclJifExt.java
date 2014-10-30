package fabric.extension;

import jif.extension.JifConstructorDeclExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.label.AccessPathThis;
import jif.visit.LabelChecker;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabric.types.AccessPathStore;
import fabric.types.FabricTypeSystem;

/**
 *
 */
public class ConstructorDeclJifExt extends JifConstructorDeclExt implements Ext {

  public ConstructorDeclJifExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    // add some useful axioms
    JifContext A = lc.context();
    ClassType ct = A.currentClass();
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    Position pos = Position.compilerGenerated();
    if (ts.descendsFrom(ct, ts.PrincipalClass())) {
      // worker$ actsfor this and store$ actsfor this
      //  holds true for all principals
      A.addActsFor(ts.dynamicPrincipal(pos, new AccessPathStore(
          new AccessPathThis(ct, pos), ts.Store(), pos)), ts.dynamicPrincipal(
              pos, new AccessPathThis(ct, pos)));
      A.addActsFor(ts.workerLocalPrincipal(pos),
          ts.dynamicPrincipal(pos, new AccessPathThis(ct, pos)));
    }
    return super.labelCheck(lc);
  }
}
