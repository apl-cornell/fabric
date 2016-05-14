package fabil.types;

import polyglot.types.ProcedureInstance;
import polyglot.types.Type;

/**
 * A {@code StageInstance} represents the type information for a call to the
 * stage procedure.
 */
public interface StageInstance extends ProcedureInstance {

  /**
   * Type of the original expression argument.
   */
  public Type origType();

  /**
   * Destructively update the original expression type.
   */
  public void setOrigType(Type t);
}
