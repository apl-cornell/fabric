package fabric.tools.classloader;

import java.util.Properties;
import java.util.Set;
import java.util.Queue;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.lang.Codebase;
import fabric.lang.FClass;
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
    if (args.length < 2) {
      showUsage(); 
      return;
    }
    Worker.initialize(args[1]);
    String action = args[0];
    if (action.equals("import")) {
      handleImport(args);
    } else if (action.equals("export")) {
      handleExport(args);
    } else {
      System.err.println("Unrecognized action");
      showUsage();
      return;
    }
    System.exit(0);
  }
  
  private static void handleImport(String[] args) throws IOException {
    if (args.length != 5) {
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
    if (args.length < 5) {
      System.err.println("Incorrect number of arguments");
      showUsage();
      return;
    }
    String baseName = args[2];
    String storeName = args[3];
    final Store s = Worker.getWorker().getStore(storeName);
    CodebaseTool tool = new CodebaseTool(baseName);
    List<String> rootClasses = new LinkedList<String>();
    for (int i = 4; i < args.length; i++)
      rootClasses.add(args[i]);
    
    final Codebase c = tool.storeCode(rootClasses, s);
    if (c != null) {
      System.out.println("Done storing codebase");
      Worker.runInSubTransaction(new Worker.Code<Void>() {
        public Void run() {
          s.getRoot().put(fabric.lang.WrappedJavaInlineable.$wrap("latestCodebase"), c); //XXX for testing
          return null;
        }
      });
      System.out.println("Codebase stored to " + tool.getOid(c));
    }
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
              FClass cls = current.getClass(className);
              if (cls.getBytecode().getLength() != 0)
                toFile(cls);
              if (codebasesLoaded.contains(cls.getCodebase())) {
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
  
  private Label getClassLabel(Store s, String className) {
    Principal p = Worker.getWorker().getPrincipal();
    return LabelUtil._Proxy.toLabel(s, LabelUtil._Proxy.writerPolicy(s, p, p));
  }
  
  private Label getCodebaseLabel(Store s) {
    Principal p = Worker.getWorker().getPrincipal();
    return LabelUtil._Proxy.toLabel(s, LabelUtil._Proxy.writerPolicy(s, p, p));
  }
  
  private Codebase storeCode(final List<String> rootClasses, final Store s)
  throws IOException {
    return Worker.runInSubTransaction(new Worker.Code<Codebase>() {
      public Codebase run() {
        try {
          Queue<String> classesToCreate = new LinkedList<String>(rootClasses);
          Label cl = getCodebaseLabel(s);
          fabric.util.Map/*String, Class*/ classes = (fabric.util.HashMap)new fabric.util.HashMap._Impl/*String, Class*/(s, cl).$getProxy();
          Set<FClass> toSetCodebase = new HashSet<FClass>();
          Set<String> seenClasses = new HashSet<String>();
          while (!classesToCreate.isEmpty()) {
            String currentClass = classesToCreate.remove();
            seenClasses.add(currentClass);
            String filename = classNameToFile(currentClass);
            Label l = getClassLabel(s, currentClass); 
            byte[] bytecode = readFile(filename);
            FClass c = (FClass)new FClass._Impl(
                s, l, currentClass, toByteArray(s, l, bytecode)).$getProxy();

            System.out.println("Crseating class " + currentClass + " at " + getOid(c));
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
              if (seenClasses.contains(dep))
                continue;
              seenClasses.add(dep);
              String file = classNameToFile(dep);
              if (!fileExists(file)) {
                // Assume system class
                // and ignore - parent classloader will load it
              } else {
                byte[] fileBytecode = readFile(file);
                Properties p = readProperties(file + ".fabproperties");
                String oid = p.getProperty("oid");
                FClass depClass = null;
                if (oid != null)
                  depClass = (FClass)fabric.lang.Object._Proxy.$getProxy(getObjectByOid(oid));
                if (depClass == null || !arrEquals(fileBytecode, depClass.getBytecode())) {
                  classesToCreate.add(dep);
                } else {
                  classes.put(fabric.lang.WrappedJavaInlineable.$wrap(dep), depClass);
                }
              }
            }
          }
          Codebase codebase = (Codebase)new Codebase._Impl(s, cl, classes).$getProxy();
          for (FClass c : toSetCodebase) {
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
    for (Object obj : m.keySet()) {
      mn.put(fabric.lang.WrappedJavaInlineable.$wrap(obj), 
          fabric.lang.WrappedJavaInlineable.$wrap(m.get(obj)));
    }
    return mn;
  }
  
  private String classNameToFile(String name) {
    return name.replaceAll("\\.", File.separator) + ".class";
  }
  
  private boolean arrEquals(byte[] a, fabric.lang.arrays.byteArray b) {
    if ((a == null) != (b == null)) return false;
    if (a.length != b.getLength()) return false;
    for (int i = 0; i < a.length; i++)
      if (a[i] != b.get(i)) return false;
    return true;
  }
  
  private fabric.lang.Object getObjectByOid(Store s, long onum) {
    return new fabric.lang.Object._Proxy(s, onum);
  }
  
  private fabric.lang.Object getObjectByOid(String oid) {
    String[] splits = oid.split("\\/");
    if (splits.length != 4)
      throw new IllegalArgumentException("Malformed oid: " + oid);
    
    return getObjectByOid(Worker.getWorker().getStore(splits[2]), 
        Long.parseLong(splits[3]));
  }
  
  private String getOid(fabric.lang.Object obj) {
    return "fab://" + obj.$getStore().name() + "/" + obj.$getOnum();
  }
  
  private byte[] toByteArray(fabric.lang.arrays.byteArray arr) {
    byte[] n = new byte[arr.getLength()];
    for (int i = 0 ; i < n.length; i++)
      n[i] = arr.get(i);
    return n;
  }
  
  private fabric.lang.arrays.byteArray toByteArray(Store s, Label l, byte[] arr) {
    fabric.lang.arrays.byteArray n = 
      (fabric.lang.arrays.byteArray)new fabric.lang.arrays.byteArray._Impl(
          s, l, arr.length).$getProxy();
    for (int i = 0; i < arr.length; i++)
      n.set(i, arr[i]);
    return n;
  }
  
  private void toFile(FClass c) throws IOException {
    String name = c.getName();
    String filename = baseName + File.separator + classNameToFile(name);
    File f= new File(filename).getParentFile();
    if (f != null) f.mkdirs();
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
      for (int i = 0; i < len; i++) {
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
    System.err.println("    codebasetool import [workername] [storeName] [onum] [exportDir]");
    System.err.println("To store a codebase into Fabric from the filesystem");
    System.err.println("    codebasetool export [workername] [pathToCodebase] [storeName] [classToExport] [...]");
  }  
}
