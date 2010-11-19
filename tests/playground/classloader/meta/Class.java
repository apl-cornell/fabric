package meta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Class {
  /* This portion would be ported to fabric mostly unchanged. */
  Codebase codebase;
  byte[]   bytecode;
  String   name;

  public java.lang.Class toJavaClass() {
    return codebase.getClassLoader().getJavaClass(this);
  }
  
  
  /* this constructor simulates the process of loading a class onto Fabric.
   * In the full Fabric implementation, it will need to be replaced by a fabric
   * program that assembles class files with their context information into a
   * Class object, and commits it to Fabric */
  private Class (String baseName) throws IOException {
    Properties p  = Util.readProperties(baseName + ".properties");
    this.codebase = Codebase.getCodebase(p.getProperty("codebase"));
    this.bytecode = Util.readFully(baseName + ".class");
    this.name     = p.getProperty("name", baseName);
        
  }

  /* this map and the getClass method simulate the oid <-> object mapping, and
   * would be removed in a full fabric implementation */
  private static Map<String, Class> classes;
  
  static {
    classes = new HashMap<String, Class>();
  }
  
  public static Class getClass(String basename) throws IOException {
    Class result = classes.get(basename);
    if (result == null) {
      result = new Class(basename);
      classes.put(basename, result);
    }
    return result;
  }
  
  public Codebase getCodebase() {
    return codebase;
  }
}
