package fabil.types;

import polyglot.ast.TypeNode;
import polyglot.types.*;

public interface FabILTypeSystem extends TypeSystem {

  // TODO: fabric.client or fabric.lang?
  /**
   * return the ClassType corresponding to
   * fabric.client.transaction.TransactionManager
   */
  ClassType TransactionManager();

  /** The ClassType of fabric.lang.Object. */
  ClassType FObject();

  /** The ClassType of fabric.lang.JavaInlineable. */
  ClassType JavaInlineable();

  /** The ClassType of fabric.lang.WrappedJavaInlineable. */
  ClassType WrappedJavaInlineable();
  
  /** The ClassType of fabric.common.FabricThread. */
  ClassType FabricThread();
  
  /** The ClassType of java.lang.Thread. */
  ClassType Thread();

  ClassType fArrayOf(Type type);

  ClassType fArrayImplOf(Type type);

  ClassType toFArray(ArrayType type);

  ClassType AbortException();
  
  /** The ClassType of fabric.client.remote.RemoteClient. */
  ClassType RemoteClient();
  
  ClassType RemoteCallException();

  /**
   * @return true iff the given type is a primitive, an array of Fabric types,
   *         or is a class type that is a subtype of fabric.lang.Object.
   */
  boolean isFabricType(Type type);

  /**
   * @return true iff the given type is a primitive, an array of Fabric types,
   *         or is a class type that is a subtype of fabric.lang.Object.
   */
  boolean isFabricType(TypeNode type);

  /**
   * @return true iff the given type is a subtype of java.lang.Thread.
   */
  boolean isThread(Type type);

  /**
   * @return true iff the given type is a subtype of java.lang.Thread.
   */
  boolean isThread(TypeNode type);

  /**
   * Determines whether the given type is a "pure" Fabric type. Fabric types are
   * pure if they are not subtypes of fabric.lang.JavaInlineable.
   */
  boolean isPureFabricType(Type type);

  /**
   * Determines whether the given type is a "pure" Fabric type. Fabric types are
   * pure if they are not subtypes of fabric.lang.JavaInlineable.
   */
  boolean isPureFabricType(TypeNode type);

  /**
   * @return true iff the given type is a subtype of fabric.lang.Object.
   */
  boolean isFabricReference(Type type);

  /**
   * @return true iff the given type is a subtype of fabric.lang.Object.
   */
  boolean isFabricReference(TypeNode type);

  /**
   * @return true iff the given class type is a subtype of fabric.lang.Object.
   */
  boolean isFabricClass(ClassType type);

  /**
   * @return true iff the given type is a class type that is a subtype of
   *         fabric.lang.Object.
   */
  boolean isFabricClass(Type type);

  /**
   * @return true iff the given type is a class type that is a subtype of
   *         fabric.lang.Object.
   */
  boolean isFabricClass(TypeNode type);

  /**
   * @return true iff the given type is an array of Fabric types.
   */
  boolean isFabricArray(ArrayType type);

  /**
   * @return true iff the given type is an array of Fabric types.
   */
  boolean isFabricArray(Type type);

  /**
   * @return true iff the given type is an array of Fabric types.
   */
  boolean isFabricArray(TypeNode type);

  /**
   * Determines whether the given type is a subtype of
   * fabric.lang.JavaInlineable.
   */
  boolean isJavaInlineable(Type type);

  boolean isJavaInlineable(TypeNode type);
  
  /**
   * Determines whether the given ClassType was compiled with fabc.
   */
  boolean isCompiledByFabc(ClassType ct);

  /**
   * Sets the LoadedClassResolver to use when looking for Fabric runtime
   * classes.
   */
  void setRuntimeClassResolver(LoadedClassResolver lcr);
}
