package fabric.visit;

import static fabric.common.FabricLocationFactory.getLocation;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import jif.ast.JifClassDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;
import codebases.ast.CodebaseDecl;
import codebases.frontend.CBJobExt;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseClassType;
import fabil.visit.ClassHashGenerator;
import fabric.ExtensionInfo;
import fabric.Topics;
import fabric.common.FabricLocation;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;
import fabric.worker.Store;

/**
 * This class generates a FClass object for each new class in a source file and
 * creates codebase entries for the class and its dependencies.
 */
public class FClassGenerator extends ErrorHandlingVisitor {
  // protected Source src = null;
  protected FabricLocation namespace;
  protected Codebase codebase;

  public FClassGenerator(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
    CodebaseSource src = (CodebaseSource) job.source();
    this.namespace = src.canonicalNamespace();
    FabricTypeSystem fts = (FabricTypeSystem) ts;
    this.codebase = fts.codebaseFromNS(namespace);
    // Sanity check
    fabric.ExtensionInfo fabext = (ExtensionInfo) job.extensionInfo();
    if (!codebase.$getStore().name().equals(fabext.destinationStore().name())) {
      throw new InternalCompilerError("Expected codebase to be at store "
          + fabext.destinationStore().name() + " but got "
          + codebase.$getStore().name());
    }
  }

  @Override
  public NodeVisitor begin() {
    FClassGenerator v = (FClassGenerator) super.begin();
    return v;
  }

  @Override
  protected Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {
    FabricTypeSystem fts = (FabricTypeSystem) ts;
    fabric.ExtensionInfo fabext = (ExtensionInfo) job.extensionInfo();
    if (n instanceof CodebaseDecl) {
      CodebaseDecl cd = (CodebaseDecl) n;
      FabricLocation cb_ns =
          fts.namespaceResolver(namespace).resolveCodebaseName(cd.name().id());
      if (Report.should_report(Topics.mobile, 3))
        Report.report(3, "Adding codebase alias " + cd.name()
            + " to new codebase");

      Codebase cb = cb_ns.getCodebase();
      if (cb == null)
        throw new SemanticException("Codebase " + cb_ns + " does not exist.");
      if (namespace.equals(cb_ns))
        throw new SemanticException("Codebase " + cb_ns
            + " is the current namespace.");

      codebase.addCodebaseName(cd.name().id(), cb);

    } else if (n instanceof JifClassDecl) {
      JifClassDecl jcd = (JifClassDecl) n;
      FabricParsedClassType pct = (FabricParsedClassType) jcd.type();
      CodebaseSource src = (CodebaseSource) pct.fromSource();
      setDependencies(pct);
      if (pct.namespaceDependencies() == null) {
        throw new InternalCompilerError("Class " + pct
            + " has no namespace dependencies!");
      }
      // Set the canonical namespace of this type to the codebase
      // (For serialization when compiling directly to bytecode)
      pct.setCanonicalNamespace(getLocation(false, NSUtil.namespace(codebase)));

      if (!src.shouldPublish())
        throw new InternalCompilerError(
            "Running FClassGenerator on unpublished source!");

      Label update_lbl = fts.sourceUpdateLabel(src);
      ConfPolicy access_lbl = fts.sourceAccessPolicy(src);

      Store store = fabext.destinationStore();
      String className = pct.fullName();

      try {
        FClass fcls =
            new FClass._Impl(store).fabric$lang$FClass$(update_lbl, access_lbl,
                codebase, className, ClassHashGenerator.toSourceString(src),
                null);

        if (Report.should_report(Topics.mobile, 3)) {
          Report.report(3, "Inserting " + className + " with label "
              + update_lbl + " into codebase " + NSUtil.namespace(codebase));
        }
        if (codebase.resolveClassName(className) != null)
          throw new SemanticException("class " + className
              + " already exists in " + codebase);
        codebase.insertClass(className, fcls);

        CBJobExt jobExt = (CBJobExt) job.ext();
        Set<CodebaseClassType> dependencies = jobExt.dependencies();
        for (CodebaseClassType dep : dependencies) {
          if (fabext.platformNamespace().equals(dep.canonicalNamespace())
              || src.namespace().equals(dep.canonicalNamespace())) continue;
          FabricLocation depNS = dep.canonicalNamespace();
          URI fcls_ref = depNS.getUri().resolve(dep.fullName());

          if (Report.should_report(Topics.mobile, 3)) {
            Report.report(3, "Inserting " + dep.fullName() + " [" + fcls_ref
                + "] " + " into codebase " + NSUtil.namespace(codebase));
          }
          FClass entry = codebase.resolveClassName(dep.fullName());
          FClass depcls = NSUtil.fetch_fclass(fcls_ref);
          if (entry != null) {
            if (!depcls.equals(entry))
              throw new SemanticException("class " + dep.fullName()
                  + " already exists in " + codebase);
            else continue;
          }
          codebase.insertClass(dep.fullName(), depcls);
        }
      } catch (IOException e) {
        throw new SemanticException("Error creating Fabric class for class "
            + className + " in file:" + src + "Cause:" + e);
      }
    }
    return n;
  }

  Collection<CodebaseClassType> setDependencies(FabricParsedClassType ct) {
    ExtensionInfo extInfo = (ExtensionInfo) job.extensionInfo();
    if (ct.canonicalNamespace().equals(extInfo.platformNamespace()))
      return Collections.emptySet();

    if (ct.namespaceDependencies() == null) {
      Scheduler scheduler = job.extensionInfo().scheduler();
      CodebaseSource cs = (CodebaseSource) ct.fromSource();

      if (cs == null) throw new InternalCompilerError("Null source for " + ct);

      if (!scheduler.sourceHasJob(ct.fromSource()))
        throw new InternalCompilerError("No job for " + ct);

      if (Report.should_report(Topics.mobile, 3)) {
        Report.report(3, "Loading job for " + ct + ":" + ct.fromSource());
      }

      Job dep_job = scheduler.loadSource((FileSource) ct.fromSource(), true);

      if (dep_job == null)
        throw new InternalCompilerError("Null job for " + ct);

      CBJobExt dep_jobExt = (CBJobExt) dep_job.ext();

      if (Report.should_report(Topics.mobile, 3)) {
        Report.report(3,
            "Class " + ct + " has deps " + dep_jobExt.dependencies());
      }
      ct.setNamespaceDependencies(dep_jobExt.dependencies());
      for (CodebaseClassType cct : ct.namespaceDependencies()) {
        FabricParsedClassType fct = (FabricParsedClassType) cct;
        setDependencies(fct);
      }
    }
    return ct.namespaceDependencies();
  }
}
