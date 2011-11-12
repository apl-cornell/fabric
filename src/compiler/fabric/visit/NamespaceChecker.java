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
import fabric.types.FabricTypeSystem;

public class NamespaceChecker extends ErrorHandlingVisitor {

  public NamespaceChecker(Job job, FabricTypeSystem ts,
      FabricNodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  protected Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {
    if (n instanceof SourceFile) {
      CBJobExt jobExt = (CBJobExt) job.ext();
      CodebaseSource source = (CodebaseSource) job.source();
      FabricTypeSystem ts = (FabricTypeSystem) this.ts;
      URI src_ns = source.namespace();
      NamespaceResolver resolver = ts.namespaceResolver(src_ns);

      if (Report.should_report(Topics.mobile, 3)) {
        Report.report(3, "Checking namespace consistency of " + source);
      }

      Set<String> closure = new HashSet<String>();
      Set<CodebaseClassType> seen = new HashSet<CodebaseClassType>(jobExt.dependencies());      
      for(CodebaseClassType dep : jobExt.dependencies()) {
        if (!(dep instanceof ParsedClassType)) {
          throw new InternalCompilerError("Expected ParsedClassType for " + dep
              + ", but got: " + dep.getClass());
        }
        //add direct dependency to closure
        if (closure.contains(dep.fullName())) {
          //sanity check
          CodebaseClassType ct = (CodebaseClassType) resolver.check(dep.fullName());
          if (!(ct != null && ct.equals(dep)))
            //Should never happen
            throw new InternalCompilerError("Namespace inconsistency for direct dependency.");          
        
        } else closure.add(dep.fullName());
        
        Stack<CodebaseClassType> new_deps = new Stack<CodebaseClassType>();
        new_deps.addAll(directDependencies(dep));
        new_deps.removeAll(seen);
        
        while (!new_deps.isEmpty()) {
          CodebaseClassType new_ct = new_deps.pop();
          if (closure.contains(new_ct.fullName())) {
            CodebaseClassType ct =
                (CodebaseClassType) resolver.check(new_ct.fullName());
            if (ct != null && ct.equals(new_ct))
              continue;
            else {
              throw new SemanticException(
                  "Detected namespace inconsistency for source " + source + "."
                      + "The source's codebase resolves " + ct.fullName()
                      + " to a class object in " + ct.canonicalNamespace() + ","
                      + " but a dependency, reached through " + dep.fullName()
                      + ", resolves " + new_ct.fullName()
                      + " to a class object in " + new_ct.canonicalNamespace() + "."
                      + "  If this is intentional, consider using an explicit codebase"         
                      + " to link with " + dep.fullName() + "."                     
                      );
            }
          }
          else closure.add(new_ct.fullName());
          
          new_deps.addAll(directDependencies(new_ct));
          new_deps.removeAll(seen);
        }
      }
    }
    return n;
  }

  protected Collection<CodebaseClassType> directDependencies(CodebaseClassType cct) {
    if (!(cct instanceof ParsedClassType))
      throw new InternalCompilerError("Expected ParsedClassType for " + cct
          + ", but got: " + cct.getClass());

    ParsedClassType ct = (ParsedClassType) cct;
    ExtensionInfo extInfo = (ExtensionInfo) job.extensionInfo();
    Scheduler scheduler = extInfo.scheduler();
    
    if (cct.canonicalNamespace().equals(extInfo.platformNamespace()))
      return Collections.emptySet();

    CodebaseSource cs = (CodebaseSource) ct.fromSource();
    if (cs == null)
      throw new InternalCompilerError("Null source for " + cct);
    

    if (!scheduler.sourceHasJob( ct.fromSource()))
      throw new InternalCompilerError("No job for " + ct);
    
    Job dep_job = scheduler.loadSource((FileSource) ct.fromSource(), true);

    if (dep_job == null)
      throw new InternalCompilerError("Null job for " + ct);
    
    CBJobExt dep_jobExt = (CBJobExt) dep_job.ext();
    
    if (Report.should_report(Topics.mobile, 3)) {
      Report.report(3, "Class " + cct + " has deps " + dep_jobExt.dependencies());
    }
    
    return dep_jobExt.dependencies();
  }
//XXX: This algorithm (once upon a time) not only checked consistency, but also built the 
//set of classes that needed to be relinked to the new codebase.  For now, we just do 
//a simple check by computed the closure of dependencies. 
  
//  protected static Set<FClass> buildRelink(FClass fcls) {
//    if (Report.should_report(Topics.mobile, 3))
//      Report.report(1, "Building inverse dependency graph for " + fcls.getName());
//
//    // Invert dependency graph
//    FClass depclass;
//    LinkedList<FClass> worklist = new LinkedList<FClass>();
//    worklist.add(fcls);
//    
//    Map<String, FClass> ns = new LinkedHashMap<String, FClass>();
//    Map<String, Set<FClass>> invdeps = new LinkedHashMap<String, Set<FClass>>();
//    Set<FClass> relink = new HashSet<FClass>();
//    Set<FClass> seen = new HashSet<FClass>();
//    
//    while ((depclass = worklist.poll()) != null) {
//      if (seen.contains(depclass))
//        continue;
//      
//      Codebase depcb = depclass.getCodebase();
//      if (!LabelUtil._Impl.relabelsTo(depclass.get$label(), depcb.get$label())) {
//        Report.report(1, "WARNING: The label of class "
//            + SysUtil.absoluteName(depclass)
//            + " is more restrictive than the label of its codebase.");
//        relink.add(depclass);
//      }
//      Set<String> depdeps = convert(depclass.dependencies());
//      if (Report.should_report(Topics.mobile, 3))
//        Report.report(3, depclass + " has dependencies: " + depdeps);
//      
//      for (String s : depdeps) {
//        Set<FClass> hasdep = invdeps.get(s);
//        if (hasdep == null) {
//          hasdep = new HashSet<FClass>();
//          invdeps.put(s, hasdep);
//        }
//        hasdep.add(depclass);
//        FClass depdep = depcb.resolveClassName(s);
//        if (seen.add(depclass))
//          worklist.add(depdep);
//        
//        //check if depclass has inconsistencies
//        if (ns.containsKey(s) && !ns.get(s).equals(depdep))
//          relink.add(depclass);
//        //otherwise, add to namespace
//        else if (!ns.containsKey(s))
//          ns.put(s,depdep);
//      }
//    }
//    
//    worklist.addAll(relink);
//    while ((depclass = worklist.poll()) != null) {
//      //for each class in the dirty set, add referring classes 
//      // to the dirty set
//      Set<FClass> to_add = new HashSet<FClass>(invdeps.get(depclass.getName()));
//      
//      //add new classes to the worklist
//      to_add.removeAll(relink);
//      worklist.addAll(to_add);
//      relink.addAll(to_add);
//    }
//    return relink;
//  }

//  protected static Set<String> convert(fabric.lang.arrays.ObjectArray s) {
//    return null;
//    Set<String> c = new HashSet<String>(s.size());
//    for (fabric.util.Iterator it = s.iterator(); it.hasNext();)
//      c.add((String) WrappedJavaInlineable.$unwrap(it.next()));
//    return c;
//  }
}
