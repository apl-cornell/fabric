package fabil.types;

/**
 * Interface for defining .class files in Fabric.
 */
public interface ClassFile extends polyglot.types.reflect.ClassFile {
  byte[] getHash();
}
