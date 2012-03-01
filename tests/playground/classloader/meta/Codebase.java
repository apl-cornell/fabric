package meta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Codebase {
  /* in the Fabric implementation, classes would be a map from strings to oids */
  private Map<String, String> classes;
  /* mapping of class names to their type (fabric-loaded or system-loaded) */
  private Map<String, String> classTypes;
  
  /* in the Fabric implementation, name will be the OID */
  String name;
  
  Class resolveClassName(String name) throws IOException {
    return Class.getClass(classes.get(name));
  }
  
  String getClassType(String name) {
    return classTypes.get(name);
  }
  
  FabricClassLoader getClassLoader() {
    return FabricClassLoader.getClassLoader(this);
  }
  
  /* this constructor simulates the process of loading a codebase onto Fabric.
   * In the full fabric implementation, it will need to be replaced by a fabric
   * program that assembles class files with their context information into a
   * Class object, and commits it to Fabric */
  private Codebase (String name) throws IOException {
    this.name    = name;
    this.classes = new HashMap<String, String> ();
    this.classTypes = new HashMap<String, String> ();
    
    Properties p = Util.readProperties(name);
    for (String propName : p.stringPropertyNames()) {
      String className = propName.substring(0, propName.length() - 5);
      if (propName.endsWith("name")) {
        classes.put(className, p.getProperty(propName));
      } else if (propName.endsWith("type")) {
        classTypes.put(className, p.getProperty(propName));
      } else {
        throw new IOException("Malformed codebase file. Unknown property: " + 
            propName);
      }
    }
  }

  /* this map and the getCodebase method simulate the oid <-> object mapping, and
   * would be removed in a full fabric implementation */
  private static Map<String, Codebase> codebases;
  
  static {
    codebases = new HashMap<String, Codebase>();
  }
  
  static Codebase getCodebase(String filename) throws IOException {
    Codebase result = codebases.get(filename);
    if (result == null) {
      result = new Codebase(filename);
      codebases.put(filename, result);
    }
    return result;
  }
}
