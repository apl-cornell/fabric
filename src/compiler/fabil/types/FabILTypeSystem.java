package fabil.types;

import polyglot.ast.TypeNode;
import polyglot.types.ArrayType;
import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import codebases.types.CodebaseTypeSystem;

public interface FabILTypeSystem extends TypeSystem, CodebaseTypeSystem {
  // TODO: fabric.worker or fabric.lang?
  /**
   * return the ClassType corresponding to
   * fabric.worker.transaction.TransactionManager
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

  ClassType AbortException();

  /** The ClassType of fabric.worker.remote.RemoteWorker. */
  ClassType RemoteWorker();

  ClassType RemoteCallException();

  ClassType Worker();

  ClassType Principal();

  ClassType DelegatingPrincipal();

  /** The ClassType of fabric.worker.Store. */
  Type Store();

  /** The ClassType of fabric.lang.security.Label. */
  Type Label();

  /** The ClassType of fabric.lang.security.ConfPolicy. */
  Type ConfPolicy();

  ClassType InternalError();

  ClassType fabricRuntimeArrayOf(Type type);

  ClassType fabricRuntimeArrayImplOf(Type type);

  ClassType toFabricRuntimeArray(ArrayType type);

  /**
   * Returns the compile-time representation of a Fabric array type.
   */
  FabricArrayType fabricArrayOf(Type baseType);

  FabricArrayType fabricArrayOf(Position pos, Type type);

  FabricArrayType fabricArrayOf(Type type, int dims);

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
   * @return true iff the given class type is a subtype of
   *         fabric.lang.security.Principal.
   */
  boolean isPrincipalClass(ClassType type);

  /**
   * @return true iff the given type is a class type that is a subtype of
   *         fabric.lang.security.Principal.
   */
  boolean isPrincipalClass(Type type);

  /**
   * @return true iff the given type is a class type that is a subtype of
   *         fabric.lang.security.Principal.
   */
  boolean isPrincipalClass(TypeNode type);

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

  // /**
  // * Sets the LoadedClassResolver to use when looking for Fabric runtime
  // * classes.
  // */
  // void setRuntimeClassResolver(LoadedClassResolver lcr);

  /**
   * Create a new stage call instance at the given position with the given type.
   */
  StageInstance stageInstance(Type origType);
}
