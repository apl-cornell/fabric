package fabric.types;

import polyglot.ast.TypeNode;
import polyglot.types.ArrayType;
import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.types.TypeSystem;

public interface FabricTypeSystem extends TypeSystem {

  // TODO: fabric.client or fabric.lang?
  /** return the ClassType corresponding to fabric.client.TransactionManager */
  ClassType TransactionManager();
  
  /** The ClassType of fabric.lang.Object. */
  ClassType FObject();

  ClassType fArrayOf(Type type);
  ClassType fArrayImplOf(Type type);
  
  ClassType toFArray(ArrayType type);
  
  boolean isFabric(ClassType type);
  boolean isFabric(Type type);
  boolean isFabric(TypeNode type);
}
