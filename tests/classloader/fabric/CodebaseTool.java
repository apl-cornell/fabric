package fabric;

import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;
import fabric.worker.Worker;
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
   *    CodebaseTool export [workername] [pathToCodebase] [CodebasePropertiesFilename] [storeName]
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
    tool.toFilesystem(s, onum);
  }
  
  private static void handleExport(String[] args) throws IOException {
    if(args.length != 5) {
      System.err.println("Incorrect number of arguments");
      showUsage();
      return;
    }
    String baseName = args[2];
    String propsFile = args[3];
    String propsFileFull = baseName + File.separator + propsFile;
    String storeName = args[4];
    final Store s = Worker.getWorker().getStore(storeName);
    File f = new File(propsFileFull);
    if(!f.exists()) {
      System.err.println("Properties file " + propsFileFull + " does not exist.");
      return;
    }
    CodebaseTool tool = new CodebaseTool(baseName);
    Properties props = tool.readProperties(propsFile);
    final Codebase c = tool.toFabric(props, s);
    Worker.runInSubTransaction(new Worker.Code<Void>() {
      public Void run() {
        s.getRoot().put(fabric.lang.WrappedJavaInlineable.$wrap("latestCodebase"), c); //XXX for testing
        return null;
      }
    });
    
    System.out.println("Codebase stored to fab://" + s.name() + "/" + c.$getOnum());
  }
  
  public CodebaseTool(String basename) {
    this.baseName = basename;
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
  
  /**
   * Loads a codebase and all of its classes into the filesystem
   * @param s store the codebase is located on
   * @param onum the onum of the codebase object
   */
  void toFilesystem(Store s, long onum) throws IOException {
      Object obj = fabric.lang.Object._Proxy.$getProxy(getObjectByOid(s, onum));
      if(obj == null || !(obj instanceof fabric.Codebase)) {
        System.err.println("Object not a codebase.");
        return;
      }

      final Codebase c = (Codebase)obj;
      Properties p = new Properties();
      fabric.util.Iterator/* String */ classesIter = 
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
  
  fabric.util.Map toFabMap(java.util.Map m, Store s, Label l) {
    fabric.util.Map mn = (fabric.util.Map)new fabric.util.HashMap._Impl(s, l).$getProxy();
    for(Object obj : m.keySet()) {
      mn.put(fabric.lang.WrappedJavaInlineable.$wrap(obj), 
          fabric.lang.WrappedJavaInlineable.$wrap(m.get(obj)));
    }
    return mn;
  }
  
  /**
   * Imports new codebase to Fabric, using given properties file
   * @param p properties that define the codebase
   * @return the newly generated codebase
   */
  Codebase toFabric(Properties p, final Store s) throws IOException {
      final Map<String, String> classTypes = new HashMap<String, String>();
      final Set<String> classNames = new HashSet<String>();
      Iterator propNamesIter = p.stringPropertyNames().iterator();
      while(propNamesIter.hasNext()) {
        String propName = (String)propNamesIter.next();
        String className = propName.substring(0, propName.length() - 5);
        if(propName.endsWith("name")) {
          classNames.add(className);
        } else if(propName.endsWith("type")) {
          classTypes.put(className, p.getProperty(propName));
          classNames.add(className);
        } else if(propName.endsWith("oid")) {
          //TODO: check in fabric to see if there are any differences
          //for now ignore, and assume there are always differences...
        } else {
          throw new IOException("Malformed codebase file. Unknown property: " + 
                     propName);
        }
      }

      return Worker.runInSubTransaction(new Worker.Code<Codebase>() { 
        public Codebase run() {
          Label l = Worker.getWorker().getLocalStore().getEmptyLabel();
          fabric.util.Map/*String, Class*/ classes = (fabric.util.HashMap)new fabric.util.HashMap._Impl/*String, Class*/(s, l).$getProxy();
          Iterator<String> classesIter = classNames.iterator();
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
                byte[] bytecode = readFile(name.replaceAll("\\.", "/") + ".class");
                Class c = (Class)new Class._Impl(s, l, name, 
                    toByteArray(s, l, bytecode)).$getProxy();
                classes.put(fabric.lang.WrappedJavaInlineable.$wrap(name), c);
              } catch(IOException ex) {
                throw new RuntimeException("Could not load bytecode", ex);
              }
            }
          }
          Codebase codebase = (Codebase)new Codebase._Impl(s, l, classes, 
              toFabMap(classTypes, Worker.getWorker().getLocalStore(), l)).$getProxy();
          return codebase;  
        }
      });
  }
  
  byte[] toByteArray(fabric.lang.arrays.byteArray arr) {
    byte[] n = new byte[arr.getLength()];
    for(int i = 0 ; i < n.length; i++)
      n[i] = arr.get(i);
    return n;
  }
  
  fabric.lang.arrays.byteArray toByteArray(Store s, Label l, byte[] arr) {
    fabric.lang.arrays.byteArray n = 
      (fabric.lang.arrays.byteArray)new fabric.lang.arrays.byteArray._Impl(
          s, l, arr.length).$getProxy();
    for(int i = 0; i < arr.length; i++)
      n.set(i, arr[i]);
    return n;
  }
  
  private void toFile(fabric.Class c) throws IOException {
    String name = c.getName();
    String filename = baseName + File.separator + name.replaceAll("\\.", 
        File.separator) + ".class";
    File f= new File(filename).getParentFile();
    if(f != null) f.mkdirs();
    FileOutputStream str = new FileOutputStream(filename);
    str.write(toByteArray(c.getBytecode()));
    str.close();
  }
  
  private void toFile(Properties p, String name) throws IOException {
    FileOutputStream fs = new FileOutputStream(baseName + File.separator + name);
    p.store(fs, null);
    fs.close();
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
    System.err.println("    CodebaseTool export [workername] [pathToCodebase] [CodebasePropertiesFilename] [storeName]");
  }
  
}
