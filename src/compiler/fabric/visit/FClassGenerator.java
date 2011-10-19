package fabric.visit;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import jif.ast.JifClassDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Typed;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseClassType;
import fabric.ExtensionInfo;
import fabric.Topics;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.security.Label;
import fabric.types.FabricTypeSystem;
import fabric.worker.Store;

/**
 * This class generates a FClass object for each new class in a source file and
 * creates codebase entries for the class and its dependencies.
 */
public class FClassGenerator extends ErrorHandlingVisitor {
  // protected Source src = null;
  protected Codebase codebase = null;
  protected Set<CodebaseClassType> dependencies;

  public FClassGenerator(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @SuppressWarnings("unused")
  @Override
  protected NodeVisitor enterCall(Node n) throws SemanticException {
    if (n instanceof JifClassDecl) {
      FClassGenerator v = (FClassGenerator) copy();
      v.codebase = codebase;
      v.dependencies = new HashSet<CodebaseClassType>();
      return v;
    }
    return this;
  }

  @Override
  public NodeVisitor begin() {
    FClassGenerator v = (FClassGenerator) super.begin();
    return v;
  }

  @Override
  protected Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {

    fabric.ExtensionInfo fabext = (ExtensionInfo) job.extensionInfo();

    if (n instanceof Typed) {
      Type t = ((Typed) n).type();
      if (t != null && t.isClass()) {
        dependencies.add((CodebaseClassType) t.toClass());
      }
    } else if (n instanceof JifClassDecl) {

      FClassGenerator fcg = (FClassGenerator) v;

      JifClassDecl jcd = (JifClassDecl) n;
      ParsedClassType pct = jcd.type();
      FabricTypeSystem fts = (FabricTypeSystem) ts;
      CodebaseSource src = (CodebaseSource) pct.fromSource();

      if (!src.shouldPublish())
        throw new InternalCompilerError(
            "Running FClassGenerator on unpublished source!");

      Codebase codebase = fts.codebaseFromNS(src.namespace());
      Label update_lbl = fts.sourceUpdateLabel(src);
      Label access_lbl = fts.sourceAccessLabel(src);

      Store store = fabext.destinationStore();
      String className = pct.fullName();

      try {
        FClass fcls =
            (FClass) new FClass._Impl(store, update_lbl, access_lbl, codebase,
                className, toSourceString(src), null, null).$getProxy();
        if (Report.should_report(Topics.mobile, 3)) {
          Report.report(3, "Inserting " + className + " with label " + update_lbl + " into codebase "
              + NSUtil.namespace(codebase));
        }
        codebase.insertClass(className, fcls);
        
        for (CodebaseClassType dep : fcg.dependencies) {
          if (fabext.platformNamespace().equals(dep.canonicalNamespace())
              || src.namespace().equals(dep.canonicalNamespace())) continue;
          URI fcls_ref = dep.canonicalNamespace().resolve(dep.fullName());

          if (Report.should_report(Topics.mobile, 3)) {
            Report.report(3, "Inserting " + dep.fullName() + "(" + fcls_ref +")" + " into codebase "
                + NSUtil.namespace(codebase));
          }
          codebase.insertClass(dep.fullName(), NSUtil.fetch_fclass(fcls_ref));
        }
      } catch (IOException e) {
        throw new SemanticException("Error creating Fabric class for class "
            + className + " in file:" + src + "Cause:" + e);
      }
    }
    return n;
  }

  public static String toSourceString(CodebaseSource src) throws IOException {
    StringBuilder result = new StringBuilder();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(src.open());
      char[] buf = new char[1024];
      int r = 0;
      while ((r = reader.read(buf)) != -1)
        result.append(buf, 0, r);
    } finally {
      if (reader != null)
        reader.close();
      else src.close();
    }
    return result.toString();
  }
}
