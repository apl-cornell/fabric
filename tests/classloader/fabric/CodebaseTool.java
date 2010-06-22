package fabric;

import java.util.Properties;
import java.util.Set;
import java.util.Queue;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;
import fabric.worker.Worker;
import java.util.LinkedList;
import java.util.List;
import fabric.worker.Store;
import fabric.lang.security.Label;
import fabric.common.exceptions.UsageError;
import java.security.GeneralSecurityException;

public class CodebaseTool {

  private String baseName;
  
  /**
   * Usage:
   * To load a codebase from Fabric to the filesystem
   *    CodebaseTool import [workername] [storeName] [onum] [exportDir]
   * To store a codebase into Fabric from the filesystem
   *    CodebaseTool export [workername] [pathToCodebase] [storeName] [classToExport] [...]
   */
  public static void main(String[] args) throws IOException, UsageError, GeneralSecurityException {
    if(args.length < 2) {
      showUsage(); 
      return;
    }
    Worker.initialize(args[0]);
    String action = args[1];
    if(action.equals("import")) {
      handleImport(args);
    } else if(action.equals("export")) {
      handleExport(args);
    } else {
      System.err.println("Unrecognized action");
      showUsage();
      return;
    }
    //TODO: worker threads seem to keep program running
    System.exit(0);
  }
  
  private static void handleImport(String[] args) throws IOException {
    if(args.length != 5) {
      System.err.println("Incorrect number of arguments");
      showUsage();
      return;
    }
    String storeName = args[2];
    long onum = Long.parseLong(args[3]);
    String baseName = args[4];
    File f = new File(baseName);
    f.mkdirs();
    CodebaseTool tool = new CodebaseTool(baseName);
    Store s = Worker.getWorker().getStore(storeName);
    List<Codebase> codebases = new LinkedList<Codebase>();
    codebases.add((Codebase)fabric.lang.Object._Proxy.$getProxy(
        tool.getObjectByOid(s, onum)));
    tool.loadCode(codebases);
  }
  
  private static void handleExport(String[] args) throws IOException {
    if(args.length < 5) {
      System.err.println("Incorrect number of arguments");
      showUsage();
      return;
    }
    String baseName = args[2];
    String storeName = args[3];
    final Store s = Worker.getWorker().getStore(storeName);
    CodebaseTool tool = new CodebaseTool(baseName);
    List<String> rootClasses = new LinkedList<String>();
    for(int i = 4; i < args.length; i++)
      rootClasses.add(args[i]);
    
    final Codebase c = tool.storeCode(rootClasses, s);
    Worker.runInSubTransaction(new Worker.Code<Void>() {
      public Void run() {
        s.getRoot().put(fabric.lang.WrappedJavaInlineable.$wrap("latestCodebase"), c); //XXX for testing
        return null;
      }
    });
    
    System.out.println("Codebase stored to " + tool.getOid(c));
  }
  
  public CodebaseTool(String basename) {
    this.baseName = basename;
  }
  
  private void loadCode(final List<Codebase> codebases) {
    Worker.runInSubTransaction(new Worker.Code<Void>() {
      public Void run() {
        Queue<Codebase> codebasesToLoad = new LinkedList<Codebase>(codebases);
        Set<Codebase> codebasesLoaded = new HashSet<Codebase>();
        try {
          while (!codebasesToLoad.isEmpty()) {
            Codebase current = codebasesToLoad.remove();
            codebasesLoaded.add(current);
            fabric.util.Iterator i = current.getClasses().keySet().iterator();
            while(i.hasNext()) {
              String className = i.next().toString();
              Class cls = current.getClass(className);
              if(cls.getBytecode().getLength() != 0)
                toFile(cls);
              if(codebasesLoaded.contains(cls.getCodebase())) {
                codebasesToLoad.add(cls.getCodebase());
                codebasesLoaded.add(current);
              }
            }
          }
        } catch(Exception ex) {
          System.out.println(ex.getMessage());
        }
        return null;
      }
    });
  }
  
  private Codebase storeCode(final List<String> rootClasses, final Store s)
  throws IOException {
    return Worker.runInSubTransaction(new Worker.Code<Codebase>() {
      public Codebase run() {
        try {
          Queue<String> classesToCreate = new LinkedList<String>(rootClasses);
          Label l = Worker.getWorker().getLocalStore().getEmptyLabel();
          fabric.util.Map/*String, Class*/ classes = (fabric.util.HashMap)new fabric.util.HashMap._Impl/*String, Class*/(s, l).$getProxy();
          Set<Class> toSetCodebase = new HashSet<Class>();
          SystemCodebase sysCb = (SystemCodebase)new SystemCodebase._Impl(s, l).$getProxy();
          while (!classesToCreate.isEmpty()) {
            String currentClass = classesToCreate.remove();
            String filename = classNameToFile(currentClass);
            byte[] bytecode = readFile(filename);
            Class c = (Class)new Class._Impl(s, l, currentClass, 
                toByteArray(s, l, bytecode)).$getProxy();
            classes.put(fabric.lang.WrappedJavaInlineable.$wrap(currentClass), c);
            toSetCodebase.add(c);
            if (!fileExists(filename + ".fabproperties")) {
              System.out.println(currentClass + " does not contain a .fabproperties file");
              return null;
            }
            Properties classProperties = readProperties(filename + ".fabproperties"); 
            String[] dependencies = classProperties.containsKey("dependencies") ? 
                classProperties.getProperty("dependencies").split(",") : new String[0];
            for (String dep : dependencies) {
              if (classes.containsKey(fabric.lang.WrappedJavaInlineable.$wrap(dep))) 
                continue; //already processed
              String file = classNameToFile(dep);
              if(!fileExists(file)) {
                // Assume system class
                Class sysClass = (Class)new Class._Impl(s, l, dep, toByteArray(s, l, new byte[0])).$getProxy();
                sysClass.setCodebase(sysCb);
                classes.put(fabric.lang.WrappedJavaInlineable.$wrap(dep), sysClass);
              } else {
                byte[] fileBytecode = readFile(file);
                Properties p = readProperties(file + ".fabproperties");
                String oid = p.getProperty("oid");
                Class depClass = null;
                if (oid != null)
                  depClass = (Class)fabric.lang.Object._Proxy.$getProxy(getObjectByOid(oid));
                if (depClass == null || !arrEquals(fileBytecode, depClass.getBytecode())) {
                  classesToCreate.add(dep);
                } else {
                  classes.put(fabric.lang.WrappedJavaInlineable.$wrap(dep), depClass);
                }
              }
            }
          }
          Codebase codebase = (Codebase)new Codebase._Impl(s, l, classes).$getProxy();
          for(Class c : toSetCodebase) {
            c.setCodebase(codebase);
          }
          return codebase;
        } catch(Exception ex) { 
          System.out.println(ex.getMessage());
          return null;
        }
      }
    });
  }

  /*** Utility methods below ***/

  private fabric.util.Map toFabMap(java.util.Map m, Store s, Label l) {
    fabric.util.Map mn = (fabric.util.Map)new fabric.util.HashMap._Impl(s, l).$getProxy();
    for(Object obj : m.keySet()) {
      mn.put(fabric.lang.WrappedJavaInlineable.$wrap(obj), 
          fabric.lang.WrappedJavaInlineable.$wrap(m.get(obj)));
    }
    return mn;
  }
  
  private String classNameToFile(String name) {
    return name.replaceAll("\\.", File.separator) + ".class";
  }
  
  private boolean arrEquals(byte[] a, fabric.lang.arrays.byteArray b) {
    if((a == null) != (b == null)) return false;
    if(a.length != b.getLength()) return false;
    for(int i = 0; i < a.length; i++)
      if(a[i] != b.get(i)) return false;
    return true;
  }
  
  private fabric.lang.Object getObjectByOid(Store s, long onum) {
    return new fabric.lang.Object._Proxy(s, onum);
  }
  
  private fabric.lang.Object getObjectByOid(String oid) {
    String[] splits = oid.split("\\/");
    if(splits.length != 4)
      throw new IllegalArgumentException("Malformed oid: " + oid);
    
    return getObjectByOid(Worker.getWorker().getStore(splits[2]), 
        Long.parseLong(splits[3]));
  }
  
  private String getOid(fabric.lang.Object obj) {
    return "fab://" + obj.$getStore().name() + "/" + obj.$getOnum();
  }
  
  private byte[] toByteArray(fabric.lang.arrays.byteArray arr) {
    byte[] n = new byte[arr.getLength()];
    for(int i = 0 ; i < n.length; i++)
      n[i] = arr.get(i);
    return n;
  }
  
  private fabric.lang.arrays.byteArray toByteArray(Store s, Label l, byte[] arr) {
    fabric.lang.arrays.byteArray n = 
      (fabric.lang.arrays.byteArray)new fabric.lang.arrays.byteArray._Impl(
          s, l, arr.length).$getProxy();
    for(int i = 0; i < arr.length; i++)
      n.set(i, arr[i]);
    return n;
  }
  
  private void toFile(fabric.Class c) throws IOException {
    String name = c.getName();
    String filename = baseName + File.separator + classNameToFile(name);
    File f= new File(filename).getParentFile();
    if(f != null) f.mkdirs();
    FileOutputStream str = new FileOutputStream(filename);
    str.write(toByteArray(c.getBytecode()));
    str.close();
    Properties p;
    String propsFilename = classNameToFile(name) + ".fabproperties";
    if (fileExists(propsFilename)) {
      p = readProperties(propsFilename);
    } else {
      p = new Properties();
    }
    p.setProperty("oid", getOid(c));
    toFile(p, propsFilename);
  }
  
  private void toFile(Properties p, String name) throws IOException {
    FileOutputStream fs = new FileOutputStream(baseName + File.separator + name);
    p.store(fs, null);
    fs.close();
  }
  
  private boolean fileExists(String filename) {
    return new File(baseName + File.separator + filename).exists();
  }
  
  private byte[] readFile(String filename) throws IOException {
    File f   = new File(baseName + File.separator + filename);
    int  len = (int) f.length();
    byte[] bytecode = new byte[len];
    InputStream  in = new FileInputStream(f);
    try {
      for(int i = 0; i < len; i++) {
        int r = in.read();
        if (r < 0)
          throw new IOException("bytecode shorter than advertised");
        bytecode[i] = (byte) r; 
      }
      
      if (in.read() > 0)
        throw new IOException("bytecode longer than advertised");
      
      return bytecode;
    } finally {
      in.close();
    }
  }
  
  private Properties readProperties(String file) throws IOException {
    InputStream in = new FileInputStream(baseName + File.separator + file);
    Properties  p  = new Properties();
    p.load(in);
    in.close();
    return p;
  }
  
  private static void showUsage() {
    System.err.println("Usage:");
    System.err.println("To load a codebase from Fabric to the filesystem");
    System.err.println("    CodebaseTool import [workername] [storeName] [onum] [exportDir]");
    System.err.println("To store a codebase into Fabric from the filesystem");
    System.err.println("    CodebaseTool export [workername] [pathToCodebase] [storeName] [classToExport] [...]");
  }
  

  /**
   * Loads a codebase and all of its classes into the filesystem
   * @param s store the codebase is located on
   * @param onum the onum of the codebase object
   */
  /*
  void toFilesystem(Store s, long onum) throws IOException {
      Object obj = fabric.lang.Object._Proxy.$getProxy(getObjectByOid(s, onum));
      if(obj == null || !(obj instanceof fabric.Codebase)) {
        System.err.println("Object not a codebase.");
        return;
      }

      final Codebase c = (Codebase)obj;
      Properties p = new Properties();
      fabric.util.Iterator/* String  classesIter = 
        Worker.runInSubTransaction(new Worker.Code<fabric.util.Iterator>() {
          public fabric.util.Iterator run() {
            return c.getClasses().keySet().iterator();
          }
      });
      String className, classType;

      while(classesIter.hasNext()) {
        className = classesIter.next().toString();
        classType = c.getClassType(className);
        p.setProperty(className + ".type", classType);
        if(classType.equals("fabric")) {
          Class cls = c.getClass(className);
          p.setProperty(className + ".oid", getOid(cls));
          toFile(cls);
        }
      }
      toFile(p, "codebase.codebase");
   }
   */
  
  /**
   * Imports new codebase to Fabric, using given properties file
   * @param p properties that define the codebase
   * @return the newly generated codebase
   */
  /*
  Codebase toFabric(Properties p, final Store s) throws IOException {
      final Map<String, String> classTypes = new HashMap<String, String>();
      final Map<String, Class> classNames = new HashMap<String, Class>();
      Iterator propNamesIter = p.stringPropertyNames().iterator();
      while(propNamesIter.hasNext()) {
        String propName = (String)propNamesIter.next();
        String className = propName.substring(0, propName.length() - 5);
        if(propName.endsWith("name")) {
          if(!classNames.containsKey(className))
            classNames.put(className, null);
        } else if(propName.endsWith("type")) {
          classTypes.put(className, p.getProperty(propName));
          if(!classNames.containsKey(className))
            classNames.put(className, null);
        } else if(propName.endsWith("oid")) {
          Class c = (Class)fabric.lang.Object._Proxy.$getProxy(getObjectByOid(
              p.getProperty(propName)));
          
          //Check for differences in class, if there are, construct new class
          byte[] bytecode = readFile(classNameToFile(className));
          if(arrEquals(bytecode, c.getBytecode())) {
            //TODO :rethink this part... see wiki.
            classNames.put(className, c);
          } else {
            classNames.put(className, null);
            //TODO: Also include dependencies in this codebase
            //  (compiler will provide this info.)
          }
        } else {
          throw new IOException("Malformed codebase file. Unknown property: " + 
                     propName);
        }
      }

      return Worker.runInSubTransaction(new Worker.Code<Codebase>() { 
        public Codebase run() {
          //XXX TODO: label for class signatures
          Label l = Worker.getWorker().getLocalStore().getEmptyLabel();
          
          fabric.util.Map/*String, Class* classes = (fabric.util.HashMap)new fabric.util.HashMap._Impl/*String, Class(s, l).$getProxy();
          Iterator<String> classesIter = classNames.keySet().iterator();
          List<Class> toAddCodebase = new LinkedList<Class>();
          while(classesIter.hasNext()) {
            String name = (String)classesIter.next();
            String classType = (String)classTypes.get(name);
            if(classType == null)
              throw new IllegalArgumentException("No class type defined for " + 
                                                     name);
            if(classType.equals("system")) {
              classes.put(fabric.lang.WrappedJavaInlineable.$wrap(name), null);
            } else {
              try {
                if(classNames.get(name) != null) {
                  classes.put(fabric.lang.WrappedJavaInlineable.$wrap(name), 
                      classNames.get(name));
                } else {
                  //Class is new, so create on fabric
                  byte[] bytecode = readFile(classNameToFile(name));
                  Class c = (Class)new Class._Impl(s, l, name, 
                      toByteArray(s, l, bytecode)).$getProxy();
                  toAddCodebase.add(c);
                  classes.put(fabric.lang.WrappedJavaInlineable.$wrap(name), c);
                }
              } catch(IOException ex) {
                throw new RuntimeException("Could not load bytecode", ex);
              }
            }
          }
          Codebase codebase = (Codebase)new Codebase._Impl(s, l, classes, 
              toFabMap(classTypes, Worker.getWorker().getLocalStore(), l)).$getProxy();
          for(Class c : toAddCodebase) {
            c.setCodebase(codebase);
          }
          return codebase;  
        }
      });
  }
  */
  
}
