package fabric.ast;

import java.util.List;

import jif.ast.ConstraintNode;
import jif.ast.ParamDecl;
import jif.ast.PrincipalNode;
import jif.types.Assertion;
import polyglot.ast.ClassBody;
import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.TypeNode;
import polyglot.main.Report;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import fabric.types.FabricTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class ClassDecl_c extends jif.ast.JifClassDecl_c {
  @Deprecated
  public ClassDecl_c(Position pos, Flags flags, Id name,
      List<ParamDecl> params, TypeNode superClass, List<TypeNode> interfaces,
      List<PrincipalNode> authority,
      List<ConstraintNode<Assertion>> constraints, ClassBody body) {
    this(pos, flags, name, params, superClass, interfaces, authority,
        constraints, body, null);
  }

  public ClassDecl_c(Position pos, Flags flags, Id name,
      List<ParamDecl> params, TypeNode superClass, List<TypeNode> interfaces,
      List<PrincipalNode> authority,
      List<ConstraintNode<Assertion>> constraints, ClassBody body, Ext ext) {
    super(pos, flags, name, params, superClass, interfaces, authority,
        constraints, body, ext);
  }

  @Override
  protected void setSuperClass(AmbiguityRemover ar, TypeNode superClass)
      throws SemanticException {
    // XXX: I think this is taken care of by the ExplicitSuperclassAdder now
    FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
    String fullName = type.fullName();

    if (superClass != null || type.equals(ts.Object())
        || type.equals(ts.FObject()) || fullName.equals(ts.Object().fullName())
        || fullName.equals(ts.FObject().fullName())) {
      super.setSuperClass(ar, superClass);
    } else {
      // Compiling a Fabric class with an unspecified superclass, and the type
      // is not the same as ts.Object() nor ts.FObject(). As such, the default
      // superclass is ts.FObject().
      if (Report.should_report(Report.types, 3))
        Report.report(3,
            "setting superclass of " + type + " to " + ts.FObject());
      type.superType(ts.FObject());
    }
  }
}
