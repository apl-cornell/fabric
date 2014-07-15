package fabil.visit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.tools.FileObject;

import polyglot.ast.Node;
import polyglot.ast.SourceCollection;
import polyglot.ast.SourceFile;
import polyglot.ast.Typed;
import polyglot.frontend.Job;
import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorInfo;
import polyglot.util.InternalCompilerError;
import polyglot.visit.NodeVisitor;
import fabil.FabILOptions;
import fabil.types.FabILTypeSystem;

/**
 * Collects all types referenced by a class and outputs them to a file.
 *
 * @author Lucas Waye <lrw48@cornell.edu>
 */
public class ClassReferencesCollector extends NodeVisitor {

  /** The Java class suffixes for each Fabric class */
  private static final String[] GENERATED_CLASSES = new String[] { "",
    "$_Proxy", "$_Impl", "$_Static", "$_Static$_Proxy", "$_Static$_Impl" };

  /** Objects referenced in the generated Java source */
  private static final String[] ALWAYS_REQUIRED_CLASSES = new String[] {
    "fabric.net.UnreachableNodeException", "java.lang.Cloneable",
    "java.lang.Object", };

  private static final String PROPERTIES_EXTENSION = ".fabproperties";

  private FabILTypeSystem ts;
  private Job job;
  private Set<String> classes;
  private Map<String, Set<String>> nestedClasses;

  public ClassReferencesCollector(Job job, TypeSystem ts) {
    this.job = job;
    this.ts = (FabILTypeSystem) ts;
    classes = new HashSet<>(Arrays.asList(ALWAYS_REQUIRED_CLASSES));
    nestedClasses = new HashMap<>();
  }

  @Override
  public void finish() {
    FabILOptions opts = (FabILOptions) job.extensionInfo().getOptions();
    if (opts.dumpDependencies()) writeDependencies();
  }

  private void writeDependencies() {
    Node ast = job.ast();
    if (ast instanceof SourceFile) {
      SourceFile sfn = (SourceFile) ast;
      writeDependencies(sfn);
    } else if (ast instanceof SourceCollection) {
      SourceCollection sc = (SourceCollection) ast;
      List<SourceFile> sources = sc.sources();
      for (SourceFile sfn : sources) {
        writeDependencies(sfn);
      }
    } else {
      throw new InternalCompilerError("AST root must be a SourceFile; "
          + "found a " + ast.getClass().getName());
    }
  }

  private void writeDependencies(SourceFile sfn) {
    String pkg = null;
    if (sfn.package_() != null) pkg = sfn.package_().package_().fullName();
    FileObject of =
        job.extensionInfo().targetFactory().outputFileObject(pkg, sfn.source());

    String tlClass = of.getName();
    tlClass = tlClass.substring(0, tlClass.length() - 5);
    if (pkg != null) tlClass = pkg + "." + tlClass;
    Set<String> nested = nestedClasses.get(tlClass);

    String basePath = of.toUri().getPath();
    basePath = basePath.substring(0, basePath.length() - 5);

    for (String classSuffix : GENERATED_CLASSES) {
      StringBuilder path = new StringBuilder();
      path.append(basePath).append(classSuffix).append(".class")
      .append(PROPERTIES_EXTENSION);
      writeDependencies(new File(path.toString()));
    }
    /* write deps for any nested classes declared in this sourcefile */
    if (nested != null) {
      String absPath = of.toUri().getPath();
      int lastChar = absPath.lastIndexOf(File.separatorChar);
      String parent = lastChar > 0 ? absPath.substring(0, lastChar) : null;
      for (String nestedClass : nested) {
        StringBuilder path = new StringBuilder();
        if (parent != null) path.append(parent).append(File.separatorChar);
        path.append(nestedClass).append(".class").append(PROPERTIES_EXTENSION);
        writeDependencies(new File(path.toString()));
      }
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
    f.getParentFile().mkdirs();
    try (FileOutputStream fs = new FileOutputStream(f)) {
      p.store(fs, null);
    } catch (IOException e) {
      job.compiler()
      .errorQueue()
      .enqueue(ErrorInfo.IO_ERROR,
          "I/O error while writing dependencies: " + e.getMessage());
    }
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof Typed)) return n;
    Typed typeNode = (Typed) n;
    Type type = typeNode.type();

    // Generated code is not type-checked
    // The types not checked are manually added from ALWAYS_REQUIRED_CLASSES.
    if (type != null) {
      if (type.isClass()) {
        ClassType ct = (ClassType) type;
        String typeName = type.toString();
        Set<String> nested = null;
        /* generate correct filename for nested classes */
        if (ct.isNested()) {
          typeName = ct.name();
          while (ct.outer() != null) {
            ct = ct.outer();
            typeName = ct.name() + "$" + typeName;
          }
          nested = nestedClasses.get(ct.fullName());
          if (nested == null) {
            nested = new HashSet<>();
            nestedClasses.put(ct.fullName(), nested);
          }
        }

        if (type.descendsFrom(ts.FObject()) || type.equals(ts.FObject())) {
          for (String classSuffix : GENERATED_CLASSES) {
            classes.add(typeName + classSuffix);
            if (nested != null) nested.add(typeName + classSuffix);
          }

        } else {
          classes.add(typeName);
          if (nested != null) nested.add(typeName);
        }
      }
    }
    return n;
  }
}
