package fabil.visit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import polyglot.ast.Node;
import polyglot.ast.SourceCollection;
import polyglot.ast.SourceFile;
import polyglot.ast.Typed;
import polyglot.frontend.Job;

import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorInfo;
import polyglot.util.InternalCompilerError;
import polyglot.visit.NodeVisitor;
import fabil.FabILOptions;
import fabil.types.FabILTypeSystem;
import java.util.Set;

/**
 * Collects all types referenced by a class and outputs them to a file.
 * 
 * @author Lucas Waye <lrw48@cornell.edu>
 */
public class ClassReferencesCollector extends NodeVisitor {

  /** The Java class suffixes for each Fabric class */
  private static final String[] GENERATED_CLASSES =  new String[] { 
    "", "$_Proxy", "$_Impl", "$_Static", "$_Static$_Proxy", "$_Static$_Impl" 
  };
  
  /** Objects referenced in the generated Java source */
  private static final String[] ALWAYS_REQUIRED_CLASSES = new String[] {
    "fabric.net.UnreachableNodeException",
    "java.lang.Cloneable",
    "java.lang.Object",
  };
  
  private static final String PROPERTIES_EXTENSION = ".fabproperties";
  
  private FabILTypeSystem ts;
  private Job job;
  private Set<String> classes;
  
  public ClassReferencesCollector(Job job, TypeSystem ts) {
    this.job = job;
    this.ts = (FabILTypeSystem)ts;
    classes = new HashSet<String>(Arrays.asList(ALWAYS_REQUIRED_CLASSES));
  }
  
  @Override
  public void finish() {
    FabILOptions opts = (FabILOptions)job.extensionInfo().getOptions();
    if (opts.dumpDependencies())
      writeDependencies();
  }
  
  private void writeDependencies() {
    Node ast = job.ast();
    if (ast instanceof SourceFile) {
      SourceFile sfn = (SourceFile) ast;
      writeDependencies(sfn);
    } else if (ast instanceof SourceCollection) {
      SourceCollection sc = (SourceCollection) ast;
      for (Iterator i = sc.sources().iterator(); i.hasNext(); ) {
          SourceFile sfn = (SourceFile) i.next();
          writeDependencies(sfn);
      }
    } else {
      throw new InternalCompilerError("AST root must be a SourceFile; " +
                                      "found a " + ast.getClass().getName());
    }
  }
  
  private void writeDependencies(SourceFile sfn) {
    String pkg = sfn.package_().package_().fullName();
    File of = job.extensionInfo().targetFactory().outputFile(pkg, sfn.source());
    String basePath = of.getAbsolutePath();
    basePath = basePath.substring(0, basePath.length()-5);
    for (String classSuffix : GENERATED_CLASSES) {
      StringBuilder path = new StringBuilder();
      path.append(basePath).append(classSuffix).append(".class").append(
          PROPERTIES_EXTENSION);
      writeDependencies(new File(path.toString()));
    }
  }
  
  private void writeDependencies(File f) {
    StringBuilder deps = new StringBuilder();
    for (String cls : classes) {
      deps.append(cls).append(",");
    }
    String result = deps.substring(0, deps.length() - 1);
    Properties p = new Properties();
    p.setProperty("dependencies", result);
    try {
      f.getParentFile().mkdirs();
      FileOutputStream fs = new FileOutputStream(f);
      p.store(fs, null);
      fs.close();
    } catch (IOException e) {
      job.compiler().errorQueue().enqueue(ErrorInfo.IO_ERROR,
                    "I/O error while writing dependencies: " + e.getMessage());
    }
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.NodeVisitor#leave(polyglot.ast.Node, polyglot.ast.Node,
   *      polyglot.visit.NodeVisitor)
   */
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof Typed)) return n;
    Typed typeNode = (Typed) n;
    Type type = typeNode.type();

    // Generated code is not type-checked
    // The types not checked are manually added from ALWAYS_REQUIRED_CLASSES. 
    if (type != null) {
      if (type.isClass()) {
        String typeName = type.toString();
        if (type.descendsFrom(ts.FObject()) || type.equals(ts.FObject())) {
          for (String classSuffix : GENERATED_CLASSES)
            classes.add(typeName + classSuffix);
        } else {
          classes.add(typeName);
        }
      }
    }
    return n; 
  }
}
