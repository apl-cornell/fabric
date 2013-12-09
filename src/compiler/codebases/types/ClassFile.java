package codebases.types;

/**
 * Interface for defining .class files in Fabric.
 */
//XXX: Move this to codebases
public interface ClassFile extends polyglot.types.reflect.ClassFile {
  byte[] getHash();
}
