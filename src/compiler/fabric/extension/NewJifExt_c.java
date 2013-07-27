package fabric.extension;

import jif.extension.CallHelper;
import jif.extension.JifNewExt;
import jif.translate.ToJavaExt;
import jif.types.JifConstructorInstance;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import fabric.ast.FabricUtil;
import fabric.types.FabricClassType;
import fabric.types.FabricContext;
import fabric.types.FabricTypeSystem;

public class NewJifExt_c extends JifNewExt {
  public NewJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    New n = (New) super.labelCheck(lc);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);

    Type newType = n.objectType().type();
    // Bypass check if this is a principal object. This condition will be
    // enforced with the $addDefaultDelegates method
    if (newType instanceof FabricClassType
        && !newType.isSubtype(((FabricTypeSystem) lc.typeSystem())
            .DelegatingPrincipal())) {
      FabricClassType ct = (FabricClassType) newType;
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

      Label accessLabel = ts.toLabel(ct.accessPolicy());
      AccessPath storeap = null;
      if (ext.location() != null)
        storeap = ts.exprToAccessPath(ext.location(), lc.jifContext());
      else if (ts.isFabricClass(newType)) {
        if (lc.context().inStaticContext()) {
          // allocation to local worker. safe to treat as top?
          // then no check is necessary
          return n;
//          storeap = ts.workerLocalAccessPath(n.position());
        } else {
          storeap =
              ts.currentStoreAccessPathFor(lc.context().currentClass(),
                  lc.jifContext());
        }
      }
      Label newLabel =
          ts.freshLabelVariable(n.position(), "new" + ct.name(),
              "label of the reference to the newly created " + ct.name()
                  + " object, at " + n.position());
      ClassType unlblCt = (ClassType) ts.unlabel(n.type());

      CallHelper ch =
          lc.createCallHelper(newLabel, n, unlblCt,
              (JifConstructorInstance) n.constructorInstance(), n.arguments(),
              n.position());

      if (accessLabel != null) {
        accessLabel = ch.instantiate(lc.jifContext(), accessLabel);
      }

      Label objectLabel = ct.updateLabel();

      if (objectLabel != null) {
        objectLabel = ch.instantiate(lc.jifContext(), objectLabel);
      }

      Label referenceLabel =
          ext.referenceLabel((FabricContext) lc.jifContext());

      if (referenceLabel != null) {
        referenceLabel = ch.instantiate(lc.jifContext(), referenceLabel);
      }

      ext.labelCheck(lc, objectLabel, accessLabel, referenceLabel);
    }

    return n;
  }
}
