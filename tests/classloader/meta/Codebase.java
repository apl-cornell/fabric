package meta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Codebase {
  /* in the fabric implementation, classes would be a map from strings to oids */
  private Map<String, String> classes;
  
  /* in the fabric implementation, name will be the OID */
  String name;
  
  Class resolveClassName(String name) throws IOException {
    return Class.getClass(classes.get(name));
  }
  
  FabricClassLoader getClassLoader() {
    return FabricClassLoader.getClassLoader(this);
  }
  
  /* this constructor simulates the process of loading a codebase on to the fabric.
   * In the full fabric implementation, it will need to be replaced by a fabric
   * program that assembles class files with their context information into a
   * Class object, and commits it to the fabric */
  private Codebase (String name) throws IOException {
    this.name    = name;
    this.classes = new HashMap<String, String> ();
    
    Properties p = Util.readProperties(name);
    for (String className : p.stringPropertyNames())
      classes.put(className, p.getProperty(className));
  }

  /* this map and the getCodebase method simulate the oid <-> object mapping, and
   * would be removed in a full fabric implementation */
  private static Map<String, Codebase> codebases;
  
  static Codebase getCodebase(String filename) throws IOException {
    Codebase result = codebases.get(filename);
    if (result == null) {
      result = new Codebase(filename);
      codebases.put(filename, result);
    }
    return result;
  }
}
