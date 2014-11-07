package fabil.ast;

import java.util.List;

import polyglot.ast.ClassBody;
import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.TypeNode;
import polyglot.frontend.MissingDependencyException;
import polyglot.frontend.Scheduler;
import polyglot.frontend.goals.Goal;
import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import fabil.types.FabILFlags;
import fabil.types.FabILTypeSystem;

// XXX Should be replaced with extension
@Deprecated
public class ClassDecl_c extends polyglot.ast.ClassDecl_c {

  @Deprecated
  public ClassDecl_c(Position pos, Flags flags, Id name, TypeNode superClass,
      List<TypeNode> interfaces, ClassBody body) {
    this(pos, flags, name, superClass, interfaces, body, null);
  }

  public ClassDecl_c(Position pos, Flags flags, Id name, TypeNode superClass,
      List<TypeNode> interfaces, ClassBody body, Ext ext) {
    super(pos, flags, name, superClass, interfaces, body, ext);
  }

  @Override
  protected ClassDecl_c disambiguateSupertypes(AmbiguityRemover ar)
      throws SemanticException {
    boolean supertypesResolved = true;

    if (!type.supertypesResolved()) {
      if (superClass != null && !superClass.isDisambiguated()) {
        supertypesResolved = false;
      }

      for (TypeNode tn : interfaces) {
        if (!tn.isDisambiguated()) {
          supertypesResolved = false;
        }
      }

      if (!supertypesResolved) {
        Scheduler scheduler = ar.job().extensionInfo().scheduler();
        Goal g = scheduler.SupertypesResolved(type);
        throw new MissingDependencyException(g);
      } else {
        setSuperClass(ar, superClass);
        setInterfaces(ar, interfaces);
        type.setSupertypesResolved(true);
      }
    }

    return this;
  }

  @Override
  protected void setSuperClass(AmbiguityRemover ar, TypeNode superClass)
      throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) ar.typeSystem();
    String fullName = type.fullName();

    if (superClass != null || type.equals(ts.Object())
        || type.equals(ts.FObject()) || fullName.equals(ts.Object().fullName())
        || fullName.equals(ts.FObject().fullName())) {
      super.setSuperClass(ar, superClass);
    } else {
      // Compiling a Fabric class with an unspecified superclass, and the type
      // is not the same as ts.Object() nor ts.FObject(). As such, the default
      // superclass is ts.FObject().
      ClassType supType = ts.FObject();
      if (flags().contains(FabILFlags.NONFABRIC)) {
        supType = ts.Object();
      }

      if (Report.should_report(Report.types, 3))
        Report.report(3, "setting superclass of " + type + " to " + supType);
      type.superType(supType);
    }
  }
}
