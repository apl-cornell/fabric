package fabil.types;

import java.util.ArrayList;
import java.util.List;

import polyglot.types.Flags;
import polyglot.types.ProcedureInstance_c;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * A {@code StageInstance} represents the type information for a call to the
 * stage procedure.
 */
public class StageInstance_c extends ProcedureInstance_c implements StageInstance {

  public StageInstance_c(TypeSystem ts, Type origType) {
    super(ts, Position.COMPILER_GENERATED,
            null, Flags.NONE,
            null, new ArrayList<Type>());
    List<Type> formalTypes = new ArrayList<>();
    formalTypes.add(origType);
    formalTypes.add(((FabILTypeSystem) ts).Label());
    this.formalTypes = formalTypes;
  }

  @Override
  public String designator() {
    return "stage-call";
  }

  @Override
  public String signature() {
    return "public " + formalTypes.get(0) + " stage(" + formalTypes.get(0) + ", Label)";
  }

  @Override
  public boolean isCanonical() {
    return listIsCanonical(formalTypes);
  }

  @Override
  public Type origType() {
    return formalTypes.get(0);
  }

  @Override
  public void setOrigType(Type t) {
    formalTypes.set(0, t);
  }

  @Override
  public boolean callValidImpl(List<? extends Type> argTypes) {
    return argTypes.size() == 2 &&
      argTypes.get(0).typeEquals(formalTypes.get(0)) &&
      argTypes.get(1).typeEquals(((FabILTypeSystem) ts).Label());
  }

  @Override
  public void setContainer(ReferenceType container) {
    if (container != null)
      throw new InternalCompilerError("Can't set the container of a StageInstance!");
  }

  @Override
  public void setFlags(Flags flags) {
    if (container != null)
      throw new InternalCompilerError("Can't set the flags of a StageInstance!");
  }

  @Override
  public void setFormalTypes(List<? extends Type> formalTypes) {
    if (formalTypes.size() != 2)
      throw new InternalCompilerError("StageInstance must have exactly 2 arguments!");
    if (!formalTypes.get(1).typeEquals(((FabILTypeSystem) ts).Label()))
      throw new InternalCompilerError("StageInstance second argument must be a Label!");
    super.setFormalTypes(formalTypes);
  }

  @Override
  public void setThrowTypes(List<? extends Type> throwTypes) {
    if (container != null)
      throw new InternalCompilerError("Can't set the throw types of a StageInstance!");
  }
}
