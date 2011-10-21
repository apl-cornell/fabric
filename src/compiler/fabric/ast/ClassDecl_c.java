package fabric.ast;

import java.util.List;

import jif.types.label.Label;

import fabric.ExtensionInfo;
import fabric.FabricOptions;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.FieldDecl;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.main.Report;
import polyglot.types.Context;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeChecker;

public class ClassDecl_c extends jif.ast.JifClassDecl_c {

  @SuppressWarnings("rawtypes")
  public ClassDecl_c(Position pos, Flags flags, Id name, List params,
                     TypeNode superClass, List interfaces, List authority, List constraints,
                     ClassBody body) {
    super(pos, flags, name, params, superClass, interfaces, authority, constraints, body);
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
