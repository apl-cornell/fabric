package fabric.extension;

import java.util.ArrayList;

import fabric.ast.FabricUtil;
import fabric.types.AccessPathStore;
import fabric.types.FabricClassType;
import fabric.types.FabricContext;
import fabric.types.FabricTypeSystem;

import jif.extension.CallHelper;
import jif.extension.JifNewDel;
import jif.extension.JifNewExt;
import jif.translate.ToJavaExt;
import jif.types.JifConstructorInstance;
import jif.types.JifContext;
import jif.types.label.AccessPath;
import jif.types.label.Label;
import jif.types.principal.DynamicPrincipal;
import jif.visit.LabelChecker;

import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

public class NewJifExt_c extends JifNewExt {
  public NewJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    New n = node();
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    JifContext context = lc.context();

    // add helpful equivalences to the environment
    DynamicPrincipal storePrincipal = (DynamicPrincipal) ext.storePrincipal();
    if (!ext.requiresLocation(ts)
        || storePrincipal.equals(ts.workerLocalPrincipal(Position
            .compilerGenerated()))) {
      // allocations to the local worker are always safe.
      return super.labelCheck(lc);
    }

    AccessPath storeap = storePrincipal.path();
    AccessPath newStore = ts.storeAccessPathFor(n, context);
    if (!newStore.isUninterpreted()) {
      AccessPathStore aps = (AccessPathStore) newStore;
      // Lookup all final access paths reachable and add them to the
      // environment if they have constant initializers
      ts.processFAP(n.type().toReference(), aps.path(), context);
    }

    context.addDefinitionalEquiv(
        ts.dynamicPrincipal(Position.compilerGenerated(), newStore),
        storePrincipal);
    context.addDefinitionalAssertionEquiv(newStore, storeap);

    n = (New) super.labelCheck(lc);
    ext = (NewExt_c) FabricUtil.fabricExt(n);

    Type newType = n.objectType().type();
    // Bypass check if this is a principal object. This condition will be
    // enforced with the $addDefaultDelegates method

    if (newType instanceof FabricClassType
        && !newType.isSubtype(((FabricTypeSystem) lc.typeSystem())
            .PrincipalClass())) {
      FabricClassType ct = (FabricClassType) newType;

      Label accessLabel = ts.toLabel(ct.accessPolicy());

      Label newLabel =
          ts.freshLabelVariable(n.position(), "new" + ct.name(),
              "label of the reference to the newly created " + ct.name()
              + " object, at " + n.position());
      ClassType unlblCt = (ClassType) ts.unlabel(n.type());

      CallHelper ch =
          lc.createCallHelper(newLabel, n, unlblCt,
              (JifConstructorInstance) n.constructorInstance(), n.arguments(),
              n.position());

      // Ugh, recheck call because we need to instantiate a bunch of state...
      ch.checkCall(lc, new ArrayList<>(n.del().throwTypes(ts)), n,
          (n.qualifier() != null) &&
          (!((JifNewDel) node().del()).qualIsNeverNull()));


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
