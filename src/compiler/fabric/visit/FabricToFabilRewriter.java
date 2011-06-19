package fabric.visit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import jif.translate.JifToJavaRewriter;
import jif.types.Param;
import jif.types.label.Label;
import polyglot.ast.Call;
import polyglot.ast.ClassDecl;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.SourceFile;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.frontend.Source;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.ContextVisitor;
import polyglot.visit.NodeVisitor;
import fabil.FabILOptions;
import fabil.ast.CodebaseSourceFile;
import fabil.ast.FabILNodeFactory;
import fabil.frontend.CodebaseSource;
import fabil.frontend.CodebaseSource_c;
import fabil.types.CodebaseClassType;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.lang.Codebase;
import fabric.types.FabricContext;
import fabric.types.FabricTypeSystem;
import fabric.worker.Worker;

public class FabricToFabilRewriter extends JifToJavaRewriter {
  protected boolean principalExpected = false;

  public FabricToFabilRewriter(Job job, FabricTypeSystem fab_ts,
      FabricNodeFactory fab_nf, fabil.ExtensionInfo fabil_ext) {
    super(job, fab_ts, fab_nf, fabil_ext);
    this.job = job;
  }

  public FabricToFabilRewriter pushLocation(Expr location) {
    FabricContext context = (FabricContext) context();
    return (FabricToFabilRewriter) context(context.pushLocation(location));
  }
  
  public Expr currentLocation() {
    Expr loc = ((FabricContext) context()).location();
    if(loc == null) {
        //XXX: this should only happen for runtime checks that need 
        // to create labels.  They should *never* flow into persistent
        // objects. How to check this?
        loc = qq().parseExpr("Worker.getWorker().getLocalStore()");
    }
    return loc;
  }
  
  @Override
  public String runtimeLabelUtil() {
    return jif_ts().LabelUtilClassName();
  }

  @Override
  public TypeNode typeToJava(Type t, Position pos) throws SemanticException {
    FabILNodeFactory fabil_nf = (FabILNodeFactory) java_nf();
    FabILTypeSystem fabil_ts = (FabILTypeSystem) java_ts();
    FabricTypeSystem fabric_ts = (FabricTypeSystem) jif_ts();

    if (fabric_ts.typeEquals(t, fabric_ts.Worker())) {
      return canonical(fabil_nf, fabil_ts.Worker(), pos);
    }

    if (fabric_ts.typeEquals(t, fabric_ts.RemoteWorker())) {
      return canonical(fabil_nf, fabil_ts.RemoteWorker(), pos);
    }

    if (fabric_ts.isFabricArray(t)) {
      return fabil_nf.FabricArrayTypeNode(pos,
          typeToJava(t.toArray().base(), pos));
    }

    return super.typeToJava(t, pos);
  }

  public boolean inSignatureMode() {
    FabILOptions opts =
        (FabILOptions) ((fabil.ExtensionInfo) java_ext).getOptions();
    return opts.signatureMode();
  }

  @Override
  /*
   * We have to override this method since the superclass creates Source objects
   * directly. Ideally, it would use a factory method.
   */
  public Node leavingSourceFile(SourceFile n) {
    List l = new ArrayList(n.decls().size() + additionalClassDecls.size());
    l.addAll(n.decls());
    for (Iterator iter = this.additionalClassDecls.iterator(); iter.hasNext();) {
      ClassDecl cd = (ClassDecl) iter.next();
      if (cd.flags().isPublic()) {
        // cd is public, we will put it in it's own source file.
        SourceFile sf =
            java_nf().SourceFile(Position.compilerGenerated(), n.package_(),
                Collections.EMPTY_LIST, Collections.singletonList(cd));

        String newName =
            cd.name() + "." + job.extensionInfo().defaultFileExtension();
        
        String sourcePath = n.source().path();
        int lastSlashIdx = sourcePath.lastIndexOf('/');
        String newPath = sourcePath.substring(0, lastSlashIdx + 1) + newName;
        
        CodebaseSourceFile cbn = (CodebaseSourceFile) n;
        CodebaseSource source = (CodebaseSource) cbn.source();
        Source s =
            new CodebaseSource_c(newName, newPath, new Date(
                System.currentTimeMillis()), source.codebase(),
                ((CodebaseSource) cbn.source()).isRemote());
        sf = sf.source(s);
        this.newSourceFiles.add(sf);
      } else {
        // cd is not public; it's ok to put the class decl in the source file.
        l.add(cd);
      }
    }

    this.additionalClassDecls.clear();
    return n.decls(l);
  }
}
