package fabric.visit;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import jif.ast.JifClassDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Typed;
import polyglot.frontend.FileSource;
import polyglot.frontend.Job;
import polyglot.frontend.Source;
import polyglot.main.Report;
import polyglot.types.Named;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;
import fabric.ExtensionInfo;
import fabric.frontend.LocalSource;
import fabric.frontend.RemoteSource;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.security.Label;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricSubstType;
import fabric.types.FabricTypeSystem;
import fabric.types.FabricTypeSystem_c;
import fabric.worker.Store;

/**
 * This class generates a FClass object for each new class in a source file and
 * creates codebase entries for the class and its dependencies. 
 */
public class FClassGenerator extends ErrorHandlingVisitor {
//  protected Source src = null;
  protected Codebase codebase = null;
  protected Set<Named> dependencies;

  public FClassGenerator(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);    
  }
  
  @SuppressWarnings("unused")
  @Override
  protected NodeVisitor enterCall(Node n) throws SemanticException {
    if(n instanceof JifClassDecl) {
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
    
    if(n instanceof Typed) {
      Type t = ((Typed) n).type();
      if(t != null && t.isClass()) {
        dependencies.add(t.toClass());
      }
    }
    else if(n instanceof JifClassDecl) {
      JifClassDecl jcd = (JifClassDecl) n;
      ParsedClassType pct = jcd.type();
      FClassGenerator fcg = (FClassGenerator) v;
      FileSource src = (FileSource) pct.fromSource();

      if(src instanceof LocalSource) {
        LocalSource loc_src = (LocalSource) src;
        //create and insert new FClass
        FabricTypeSystem fabts = (FabricTypeSystem) ts;
        Store store = fabext.destinationStore();
        Label lbl = fabext.destinationLabel();          
        String className = pct.fullName();
        
        FClass fcls;
        try {
          fcls = (FClass)new FClass._Impl(
              store, lbl, className, null, toSourceString(loc_src)).$getProxy();
        } catch (IOException e) {
          throw new SemanticException(
              "Error creating Fabric class for source file:" + loc_src);
        }
        
        //TODO: check that the integrity of the codebase is leq the integrity of c
        Codebase cb = loc_src.codebase();
        fcls.setCodebase(cb);
        cb.insertClass(className, fcls);
        
        // add dependencies to codebase;
        for(Named dep : fcg.dependencies) {
          if(fabts.isPlatformType(dep))
            continue;
          if(dep instanceof FabricSubstType)
            dep = (Named) ((FabricSubstType) dep).base();
          
          fcls.addDependency(dep.fullName());
          if(dep instanceof FabricParsedClassType) {
            FabricParsedClassType ct = (FabricParsedClassType) dep;
            Source depsrc = ct.fromSource();
            if(depsrc == null)
              throw new InternalCompilerError("No source for " + ct);         
            
            if(depsrc instanceof RemoteSource) {
              FClass depcls = ((RemoteSource)depsrc).fclass();
              cb.insertClass(depcls.getName(), depcls);
            }
            else if(!(depsrc instanceof LocalSource))
              throw new InternalCompilerError("Unexpected source type: " + depsrc.getClass());
          }
          else 
            throw new InternalCompilerError("Expected FabricParsedClassType but got " + dep.getClass());
        }
      }
      else if(src instanceof RemoteSource) {
        FClass fcls = ((RemoteSource) src).fclass();
        fabric.util.Set fclsNames = fcls.dependencies();
        Set<String> realNames = toClassNames(fcg.dependencies);
        if(fclsNames.size() != realNames.size()) 
          throw new SemanticException("Actual dependencies of " + src
              + " do not match declared names. ");

        for(fabric.util.Iterator it = fclsNames.iterator(); it.hasNext();) {
          String name = (String) fabric.lang.WrappedJavaInlineable.$unwrap(it.next());
          if(!realNames.contains(name))
            throw new SemanticException("Actual dependencies of " + src
                + " do not match declared names.");
        }
        Report.report(1, "Class " + pct + " matches the declared dependencies.");
      }
    }
    return n;
  }

  private Set<String> toClassNames(Set<Named> deps) {
    Set<String> names  = new HashSet<String>();
    FabricTypeSystem fabts = (FabricTypeSystem) ts;
    for(Named n: deps)
      if(!fabts.isPlatformType(n))
        names.add(n.fullName());
    return names;
  }

  public static String toSourceString(FileSource src) throws IOException {
    StringBuilder result = new StringBuilder();
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(src.open());
        char[] buf = new char[1024];
        int r = 0;
        while ((r = reader.read(buf)) != -1)
            result.append(buf, 0, r);
    }
    finally {
      if(reader != null)
        reader.close();
      else
        src.close();
    }
    return result.toString();
  }
}
