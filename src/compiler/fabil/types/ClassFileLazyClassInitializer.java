package fabil.types;

import polyglot.types.TypeSystem;

/**
 * This version hangs on to the class file after initialization.
 */
public class ClassFileLazyClassInitializer extends
    polyglot.types.reflect.ClassFileLazyClassInitializer {
  
  protected ClassFile classFile;

  public ClassFileLazyClassInitializer(ClassFile file, TypeSystem ts) {
    super(file, ts);
    this.classFile = file;
  }

  public ClassFile classFile() {
    return classFile;
  }

}
