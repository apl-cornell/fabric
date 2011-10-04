package codebases.types;

import polyglot.types.reflect.ClassFile;

public interface ClassCacheLoader {

  ClassFile loadClass(String name);

}
