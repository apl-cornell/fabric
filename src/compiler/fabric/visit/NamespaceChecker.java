package fabric.visit;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import polyglot.ast.Node;
import polyglot.ast.SourceFile;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.main.Report;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;
import codebases.frontend.CBJobExt;
import codebases.frontend.CodebaseSource;
import codebases.types.CodebaseClassType;
import codebases.types.NamespaceResolver;
import fabric.ExtensionInfo;
import fabric.Topics;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;

public class NamespaceChecker extends ErrorHandlingVisitor {

  public NamespaceChecker(Job job, FabricTypeSystem ts, FabricNodeFactory nf) {
    super(job, ts, nf);
  }

  /**
   */
  @Override
  protected Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {
    if (n instanceof SourceFile) {
      CBJobExt jobExt = (CBJobExt) job.ext();
      CodebaseSource source = (CodebaseSource) job.source();
      FabricTypeSystem ts = (FabricTypeSystem) this.ts;
      URI src_ns = source.canonicalNamespace();
      NamespaceResolver resolver = ts.namespaceResolver(src_ns);
      NamespaceResolver completeNamespace = resolver.copy();

      if (Report.should_report(Topics.mobile, 3)) {
        Report.report(3, "Checking namespace consistency of " + source);
      }

      Set<CodebaseClassType> seen = new HashSet<>(jobExt.dependencies());
      for (CodebaseClassType dep : jobExt.dependencies()) {
        if (!(dep instanceof ParsedClassType)) {
          throw new InternalCompilerError("Expected ParsedClassType for " + dep
              + ", but got: " + dep.getClass());
        }
        // add direct dependency to closure
        // sanity check
        CodebaseClassType direct_ct =
            (CodebaseClassType) completeNamespace.check(dep.fullName());
        if (direct_ct == null) {
          // Should never happen
          throw new InternalCompilerError(
              "No namespace entry for direct dependency.");
        } else if (!direct_ct.equals(dep)) {
          // Should never happen
          throw new InternalCompilerError(
              "Namespace inconsistency for direct dependency.");
        }

        Stack<CodebaseClassType> new_deps = new Stack<>();
        new_deps.addAll(directDependencies(dep));
        new_deps.removeAll(seen);

        while (!new_deps.isEmpty()) {
          CodebaseClassType new_ct = new_deps.pop();
          CodebaseClassType ct =
              (CodebaseClassType) completeNamespace.check(new_ct.fullName());
          if (ct == null) {
            completeNamespace.add(new_ct.fullName(), new_ct);
          } else if (ct.equals(new_ct))
            continue;
          else {
            throw new SemanticException(
                "Detected namespace inconsistency for source "
                    + source
                    + "."
                    + "The source's codebase resolves "
                    + ct.fullName()
                    + " to a class object in "
                    + ct.canonicalNamespace()
                    + ","
                    + " but a dependency, reached through "
                    + dep.fullName()
                    + ", resolves "
                    + new_ct.fullName()
                    + " to a class object in "
                    + new_ct.canonicalNamespace()
                    + "."
                    + "  If this is intentional, consider using an explicit codebase"
                    + " to link with " + dep.fullName() + ".");
          }
          for (CodebaseClassType ndDep : directDependencies(new_ct)) {
            if (!seen.contains(ndDep)) {
              seen.add(ndDep);
              new_deps.add(ndDep);
            }
          }
        }
      }
      if (Report.should_report(Topics.mobile, 3)) {
        Report.report(3, "Consistent namespace for " + source);
      }
    }
    return n;
  }

  protected Collection<CodebaseClassType> directDependencies(
      CodebaseClassType cct) {
    if (!(cct instanceof FabricParsedClassType))
      throw new InternalCompilerError("Expected ParsedClassType for " + cct
          + ", but got: " + cct.getClass());

    FabricParsedClassType ct = (FabricParsedClassType) cct;
    ExtensionInfo extInfo = (ExtensionInfo) job.extensionInfo();
    if (cct.canonicalNamespace().equals(extInfo.platformNamespace()))
      return Collections.emptySet();

    if (ct.namespaceDependencies() == null) {
      setDependencies(ct);
      if (ct.namespaceDependencies() == null)
        throw new InternalCompilerError("Class " + ct
            + " has no namespace dependencies!");
    }
    return ct.namespaceDependencies();
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
