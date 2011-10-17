package fabric.visit;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import codebases.frontend.CodebaseSource;
import codebases.frontend.LocalSource;
import codebases.frontend.RemoteSource;
import codebases.types.CodebaseTypeSystem;

import jif.ast.JifClassDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Typed;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;
import fabric.ExtensionInfo;
import fabric.Topics;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricSubstType;
import fabric.types.FabricTypeSystem;
import fabric.worker.Store;

/**
 * This class generates a FClass object for each new class in a source file and
 * creates codebase entries for the class and its dependencies.
 */
public class FClassGenerator extends ErrorHandlingVisitor {
  // protected Source src = null;
  protected Codebase codebase = null;
  protected Set<Named> dependencies;

  public FClassGenerator(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @SuppressWarnings("unused")
  @Override
  protected NodeVisitor enterCall(Node n) throws SemanticException {
    if (n instanceof JifClassDecl) {
      FClassGenerator v = (FClassGenerator) copy();
      v.codebase = codebase;
      v.dependencies = new HashSet<Named>();
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

    // if (n instanceof Typed) {
    // Type t = ((Typed) n).type();
    // if (t != null && t.isClass()) {
    // dependencies.add(t.toClass());
    // }
    // } else
    if (n instanceof JifClassDecl) {
      JifClassDecl jcd = (JifClassDecl) n;
      ParsedClassType pct = jcd.type();
      FabricTypeSystem fts = (FabricTypeSystem) ts;
      CodebaseSource src = (CodebaseSource) pct.fromSource();

      if (!src.shouldPublish())
        throw new InternalCompilerError(
            "Running FClassGenerator on unpublished source!");

      Codebase codebase = fts.codebaseFromNS(src.namespace());

      // create a new FClass
      // String[] deps = new String[fcg.dependencies.size()];
      // int i = 0;
      // for (Named dep : fcg.dependencies) {
      // if (dep instanceof FabricSubstType)
      // dep = (Named) ((FabricSubstType) dep).base();
      // deps[i++] = dep.fullName();
      // }

      Store store = fabext.destinationStore();
      Label update_lbl = src.label();
      // Re-use for access label. In general the store may allow
      // a more restricted access label, but we can only use the provider
      // label
      // to statically figure out when we can fetch from the store vs.
      // using a replica.
      Label access_lbl = src.label();
      String className = pct.fullName();

      try {
        FClass fcls =
            (FClass) new FClass._Impl(store, update_lbl, access_lbl, codebase,
                className, toSourceString(src), null, null).$getProxy();
        if (Report.should_report(Topics.mobile, 3)) {
          Report.report(3, "Inserting " + className + " into codebase "
              + SysUtil.oid(codebase));
        }

        codebase.insertClass(className, fcls);
        // // XXX: TODO: something better
        // if (pct.flags().isInterface()
        // && fabts.isSubtype(pct, fabts.FObject()))
        // codebase.insertClass(className + "_JIF_IMPL", fcls);
      } catch (IOException e) {
        throw new SemanticException("Error creating Fabric class for class "
            + className + " in file:" + src + "Cause:" + e);
      }
    }
    // if(src instanceof LocalSource) {
    // if(Report.should_report(Topics.mobile, 3)) {
    // Report.report(3, "Local source " + src + " has dependencies " +
    // toClassNames(fcg.dependencies));
    // }
    //
    // LocalSource loc_src = (LocalSource) src;
    // //create and insert new FClass
    // FabricTypeSystem fabts = (FabricTypeSystem) ts;
    // Store store = fabext.destinationStore();
    // Label lbl = fabext.destinationLabel();
    //
    // //TODO: check that the integrity of the codebase is leq the integrity of
    // c
    // Codebase cb = loc_src.codebase();
    // fcls.setCodebase(cb);
    // if(Report.should_report(Topics.mobile, 3)) {
    // Report.report(3, "Inserting " + className + " into codebase " + cb);
    // }
    // cb.insertClass(className, fcls);
    // if(pct.flags().isInterface()
    // && fabts.isSubtype(pct, fabts.FObject()))
    // cb.insertClass(className + "_JIF_IMPL", fcls);
    //
    // // // add dependencies to codebase;
    // for(Named dep : fcg.dependencies) {
    // if(SysUtil.isPlatformType(dep))
    // continue;
    // if(dep instanceof FabricSubstType)
    // dep = (Named) ((FabricSubstType) dep).base();
    //
    // if(dep instanceof FabricParsedClassType
    // && fabts.isSubtype((Type) dep, fabts.FObject()))
    // fcls.addDependency(dep.fullName());
    // XXX: Dependencies are now added to the codebase lazily during resolution?
    // if(dep instanceof FabricParsedClassType) {
    // FabricParsedClassType ct = (FabricParsedClassType) dep;
    // Source depsrc = ct.fromSource();
    // if(depsrc == null)
    // throw new InternalCompilerError("No source for " + ct);
    //
    // if(depsrc instanceof RemoteSource) {
    // FClass depcls = ((RemoteSource)depsrc).fclass();
    // cb.insertClass(depcls.getName(), depcls);
    // }
    // else if(!(depsrc instanceof LocalSource))
    // throw new InternalCompilerError("Unexpected source type: " +
    // depsrc.getClass());
    // }
    // else
    // throw new InternalCompilerError("Expected FabricParsedClassType but got "
    // + dep.getClass());
    // }
    // }
    // else if(src instanceof RemoteSource) {
    // FClass fcls = ((RemoteSource) src).fclass();
    // fabric.util.Set fclsNames = fcls.dependencies();
    // Set<String> realNames = toClassNames(fcg.dependencies);
    // if(Report.should_report(Topics.mobile, 3)) {
    // Report.report(3, "Remote source " + src + " has dependencies " +
    // realNames);
    // }
    // if(fclsNames.size() != realNames.size())
    // throw new SemanticException("Actual dependencies of " + src
    // + " do not match declared names. ");
    //
    // for(fabric.util.Iterator it = fclsNames.iterator(); it.hasNext();) {
    // String name = (String)
    // fabric.lang.WrappedJavaInlineable.$unwrap(it.next());
    // if(!realNames.contains(name))
    // throw new SemanticException("Actual dependencies of " + src
    // + " do not match declared names.");
    // }
    // if(Report.should_report(Topics.mobile, 3)) {
    // Report.report(1, "Class " + pct + " matches the declared dependencies.");
    // }
    // }
    // }
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
