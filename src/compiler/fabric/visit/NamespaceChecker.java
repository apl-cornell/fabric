package fabric.visit;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import codebases.types.CodebaseClassType;

import jif.ast.JifClassDecl;
import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;
import fabric.Topics;
import fabric.ast.FabricNodeFactory;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.security.LabelUtil;
import fabric.types.FabricTypeSystem;

public class NamespaceChecker extends ErrorHandlingVisitor {

  public NamespaceChecker(Job job, FabricTypeSystem ts,
      FabricNodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  protected Node leaveCall(Node old, Node n, NodeVisitor v)
      throws SemanticException {
//    if(n instanceof JifClassDecl) {
//      if (Report.should_report(Topics.fabric, 3))
//        Report.report(3, "Checking namespace consistency for " + n);
//      ParsedClassType pct = ((JifClassDecl) n).type();
//      String className = pct.fullName();
//      Codebase cb = ((CodebaseClassType) pct).codebase();
//      FClass fcls = cb.resolveClassName(className);      
//      Set<FClass> relink = buildRelink(fcls);
//      
//      if(relink.size() > 0){
//        throw new SemanticException(
//            "Detected namespace inconsistencies for class " + className + "." 
//                + " The following classes must be relinked with the current codebase: "
//                + relink);
//      }
//      if (Report.should_report(Topics.fabric, 3))
//        Report.report(3, "Class " + pct + " is consistent with codebase " + cb);
//    }
    return n;
  }

  protected static Set<FClass> buildRelink(FClass fcls) {
    if (Report.should_report(Topics.mobile, 3))
      Report.report(1, "Building inverse dependency graph for " + fcls.getName());

    // Invert dependency graph
    FClass depclass;
    LinkedList<FClass> worklist = new LinkedList<FClass>();
    worklist.add(fcls);
    
    Map<String, FClass> ns = new LinkedHashMap<String, FClass>();
    Map<String, Set<FClass>> invdeps = new LinkedHashMap<String, Set<FClass>>();
    Set<FClass> relink = new HashSet<FClass>();
    Set<FClass> seen = new HashSet<FClass>();
    
    while ((depclass = worklist.poll()) != null) {
      if(seen.contains(depclass))
        continue;
      
      Codebase depcb = depclass.getCodebase();
      if (!LabelUtil._Impl.relabelsTo(depclass.get$label(), depcb.get$label())) {
        Report.report(1, "WARNING: The label of class "
            + SysUtil.absoluteName(depclass)
            + " is more restrictive than the label of its codebase.");
        relink.add(depclass);
      }
      Set<String> depdeps = convert(depclass.dependencies());
      if (Report.should_report(Topics.mobile, 3))
        Report.report(3, depclass + " has dependencies: " + depdeps);
      
      for (String s : depdeps) {
        Set<FClass> hasdep = invdeps.get(s);
        if (hasdep == null) {
          hasdep = new HashSet<FClass>();
          invdeps.put(s, hasdep);
        }
        hasdep.add(depclass);
        FClass depdep = depcb.resolveClassName(s);
        if(seen.add(depclass))
          worklist.add(depdep);
        
        //check if depclass has inconsistencies
        if(ns.containsKey(s) && !ns.get(s).equals(depdep))
          relink.add(depclass);
        //otherwise, add to namespace
        else if(!ns.containsKey(s))
          ns.put(s,depdep);
      }
    }
    
    worklist.addAll(relink);
    while ((depclass = worklist.poll()) != null) {
      //for each class in the dirty set, add referring classes 
      // to the dirty set
      Set<FClass> to_add = new HashSet<FClass>(invdeps.get(depclass.getName()));
      
      //add new classes to the worklist
      to_add.removeAll(relink);
      worklist.addAll(to_add);
      relink.addAll(to_add);
    }
    return relink;
  }

  protected static Set<String> convert(fabric.lang.arrays.ObjectArray s) {
    return null;
//    Set<String> c = new HashSet<String>(s.size());
//    for (fabric.util.Iterator it = s.iterator(); it.hasNext();)
//      c.add((String) WrappedJavaInlineable.$unwrap(it.next()));
//    return c;
  }
}
