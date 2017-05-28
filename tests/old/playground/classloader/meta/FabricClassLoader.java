package meta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FabricClassLoader extends ClassLoader {
  private Codebase                    codebase;
  private Map<Class, java.lang.Class> classes;
  
  @Override
  public java.lang.Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
    System.out.println("FabricClassLoader.loadClass(\"" + name + "\")");
    if (codebase.getClassType(name).equals("system")) {
      System.out.println(" -using system classloader");
      return getSystemClassLoader().loadClass(name);
    }
    
    try {
      Class    cls = codebase.resolveClassName(name);
      return   cls.toJavaClass();
      // TODO: resolve
    } catch(IOException e) {
      System.out.println("fail");
      throw new ClassNotFoundException();
    } 
  }
  
  java.lang.Class getJavaClass(Class cls) {
    java.lang.Class result = classes.get(cls);
    if (result == null) {
      result = defineClass(cls.name, cls.bytecode, 0, cls.bytecode.length);
      classes.put(cls, result);
    }
    return result;
  }
  
  private FabricClassLoader(Codebase cb) {
    this.codebase = cb;
    this.classes  = new HashMap<Class, java.lang.Class>();
  }
  
  /* in the full fabric implementation, this object may be part of the local store */
  private static Map<String, FabricClassLoader> classloaders;
  
  static FabricClassLoader getClassLoader(Codebase codebase) {
    classloaders = new HashMap<String, FabricClassLoader>();
    FabricClassLoader result = classloaders.get(codebase.name);
    if (result == null) {
      result = new FabricClassLoader(codebase);
      classloaders.put(codebase.name, result);
    }
    return result;
  }
}
