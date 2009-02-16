package fabric.ast;

import java.util.List;

import fabric.types.FabricTypeSystem;

import polyglot.ast.ClassBody;
import polyglot.ast.Id;
import polyglot.ast.TypeNode;
import polyglot.main.Report;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;

public class ClassDecl_c extends jif.ast.JifClassDecl_c {

  @SuppressWarnings("unchecked")
  public ClassDecl_c(Position pos, Flags flags, Id name, List params,
                     TypeNode superClass, List interfaces, List authority,
                     ClassBody body) {
    super(pos, flags, name, params, superClass, interfaces, authority, body);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.ClassDecl_c#setSuperClass(polyglot.visit.AmbiguityRemover,
   *      polyglot.ast.TypeNode)
   */
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
        Report.report(3, "setting superclass of " + type + " to "
            + ts.FObject());
      type.superType(ts.FObject());
    }
  }
}
