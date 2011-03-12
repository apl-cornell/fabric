package fabric.visit;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import jif.ast.JifClassDecl;
import fabil.types.CodebaseClassType;
import fabric.ExtensionInfo;
import fabric.FabricScheduler;
import fabric.Topics;
import fabric.ast.FabricNodeFactory;
import fabric.frontend.LocalSource;
import fabric.frontend.RemoteSource;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.WrappedJavaInlineable;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;
import polyglot.ast.Node;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.Scheduler;
import polyglot.frontend.Source;
import polyglot.frontend.goals.Goal;
import polyglot.main.Report;
import polyglot.types.Named;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;

public class NamespaceChecker extends ErrorHandlingVisitor {

  public NamespaceChecker(Job job, FabricTypeSystem ts,
      FabricNodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  protected Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {
    if(n instanceof JifClassDecl) {
      if (Report.should_report(Topics.fabric, 3))
        Report.report(3, "Checking namespace consistency for " + n);
      fabric.ExtensionInfo extInfo = (ExtensionInfo) job.extensionInfo();
      
      ParsedClassType pct = ((JifClassDecl) n).type();
      String className = pct.fullName();

      FabricTypeSystem fabts = (FabricTypeSystem) ts;
      Codebase cb = ((CodebaseClassType) pct).codebase();
      FClass fcls = cb.resolveClassName(className);
      fabric.util.Set fclsNames = fcls.dependencies();
      
      Map<String, FClass> namespace = new LinkedHashMap<String,FClass>();
      Set<FClass> relink = new HashSet<FClass>();
      Map<String, Set<FClass>> invdeps = invertDependencies(fcls);

      //Initialize namespace and build dirty  
      namespace.put(className, fcls);
      Set<FClass> dirty = buildDirty(fcls, invdeps);

      //Extend namespace with dependencies of current class  
      for(fabric.util.Iterator it = fclsNames.iterator(); it.hasNext();) {
        String name = (String) WrappedJavaInlineable.$unwrap(it.next());
        namespace.put(name, cb.resolveClassName(name));
      }
              
      //Extend namespace with dependencies of dirty classes,
      // but only if the dirty class is visible in the namespace
      for(FClass d : dirty) {          
        if (!cb.resolveClassName(d.getName()).equals(d)) {
          // This should only happen if we are linking against
          // an FClass with an inconsistent namespace
          Report.report(3, "Warning: inconsistent class " + fullName(d)
              + " is not visible from the namespace of this class."
              + " This may indicate that one of the following classes is inconsistent:");
          for(FClass i : invdeps.get(d.getName()))
            Report.report(2, "\t " + fullName(i));
          continue;
        }
        Codebase dcb = d.getCodebase();
        for (fabric.util.Iterator it = 
                d.dependencies().iterator(); it.hasNext();) {
          String dname = (String) WrappedJavaInlineable.$unwrap(it.next());
          FClass dfcls = dcb.resolveClassName(dname);
          if(!namespace.containsKey(dname))
            namespace.put(dname, dfcls);
        }
      }
      
      //We now have a complete namespace.  Find each dependency that 
      // references a name in the namespace and check that its mapping is 
      // consistent.
      for(Map.Entry<String, Set<FClass>> entry : invdeps.entrySet()) {
        String name = entry.getKey();
        if(namespace.containsKey(name)) {
          for(FClass hasdep : entry.getValue()) {              
            Codebase dcb = hasdep.getCodebase();
            if(!namespace.get(name).equals(dcb.resolveClassName(name))) {
              if(relink.add(hasdep)) {
                //TODO: check mode 
                //If we are compiling to bytecode, we also have to 
                // relink the sources that have resolved types against
                // the classes we are relinking.
                for (fabric.util.Iterator it = hasdep.dependencies().iterator(); it.hasNext();) {
                  String dname = (String) WrappedJavaInlineable.$unwrap(it.next());
                  FClass f = cb.resolveClassName(dname);
                  if(f == null)
                    continue;
                  Named chk = fabts.systemResolver().check(fullName(f));
                  if(chk != null && chk instanceof ParsedClassType) {
                    ParsedClassType chkpct = (ParsedClassType) chk;
                    if(!(chkpct.fromSource() instanceof RemoteSource))
                      relink.add(f);
                  }
                }
              }
            }
          }
        }         
      }
      
//      //Sanity check: 
//      //XXX: if the entry is dirty it should either be 
//      // in relink or be a new class.
//      for(FClass d : dirty) {
//        if(!relink.contains(d)) {
//          Named chk = fabts.systemResolver().check(d.getName());
//          if(chk == null) {
//            String fullName = fullName(d);
//            Named chk2 = fabts.systemResolver().check(fullName);
//            if(chk2 == null)
//              throw new InternalCompilerError("Expected " + chk2 + " to be loaded.");
//          } else {
//            ParsedClassType chkpct = (ParsedClassType) chk;
//            if(chkpct.fromSource() instanceof RemoteSource)
//              throw new InternalCompilerError("Expected " + chk + " to be relinked.");
//          }
//        }
//      }
      if(relink.size() > 0){
        throw new SemanticException(
            "Detected namespace inconsistencies for class " + className + "." 
                + " The following classes must be relinked with the current codebase: "
                + relink);
      }
      Report.report(1, "Class " + pct + " is consistent with codebase " + cb);
    }
    return n;
  }

  protected static Map<String, Set<FClass>> invertDependencies(FClass fcls) {
    if (Report.should_report(Topics.fabric, 1))
      Report.report(1, "Building inverse dependency graph for " + fcls.getName());

    // Invert dependency graph
    FClass depclass;
    LinkedList<FClass> worklist = new LinkedList<FClass>();
    worklist.add(fcls);
    Map<String, Set<FClass>> invdeps = new LinkedHashMap<String, Set<FClass>>();
    Set<FClass> seen = new HashSet<FClass>();
    while ((depclass = worklist.poll()) != null) {
      if(seen.contains(depclass))
        continue;
      
      Codebase depcb = depclass.getCodebase();
      Set<String> depdeps = convert(depclass.dependencies());
      Report.report(1, depclass + " has dependencies: " + depdeps);

      for (String s : depdeps) {
        Set<FClass> hasdep = invdeps.get(s);
        if (hasdep == null) {
          hasdep = new HashSet<FClass>();
          invdeps.put(s, hasdep);
        }
        hasdep.add(depclass);
        if(seen.add(depclass))
          worklist.add(depcb.resolveClassName(s));
      }
    }
    return invdeps;
  }

  // Build dirty set
  // If a class has a dirty dependency, then it is dirty.
  protected static Set<FClass> buildDirty(FClass fcls,
      Map<String, Set<FClass>> invdeps) {
    if (Report.should_report(Topics.fabric, 3))
      Report.report(1, "Building dirty set for " + fcls.getName());

    Set<FClass> dirty = new HashSet<FClass>();
    LinkedList<String> worklist = new LinkedList<String>();
    worklist.add(fcls.getName());
    dirty.add(fcls);

    String dirty_name;
    while ((dirty_name = worklist.poll()) != null) {
      Set<FClass> hasdep = invdeps.get(dirty_name);
      if(hasdep != null) {
        for (FClass d : hasdep) {
          if (dirty.add(d)) worklist.add(d.getName());
        }
      }
    }
    Report.report(3, "Dirty set: " + dirty);
    return dirty;
  }

  protected static String fullName(FClass c) {
    Codebase cb = c.getCodebase();
    return "fab://" + cb.$getStore().name() + "/" + cb.$getOnum() + "/"
        + c.getName();
  }

  protected static Set<String> convert(fabric.util.Set s) {
    Set<String> c = new HashSet<String>(s.size());
    for (fabric.util.Iterator it = s.iterator(); it.hasNext();)
      c.add((String) WrappedJavaInlineable.$unwrap(it.next()));
    return c;
  }
}
