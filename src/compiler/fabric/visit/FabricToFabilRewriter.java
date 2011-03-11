package fabric.visit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import polyglot.util.Position;
import fabil.FabILOptions;
import fabil.ast.CodebaseSourceFile;
import fabil.ast.FabILNodeFactory;
import fabil.frontend.CodebaseSource;
import fabil.frontend.CodebaseSource_c;
import fabil.types.CodebaseClassType;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.lang.Codebase;
import fabric.types.FabricTypeSystem;

public class FabricToFabilRewriter extends JifToJavaRewriter {
  protected boolean principalExpected = false;
  // XXX: this field is in JifToJavaRewriter, but not visible.
  private Job job;

  public FabricToFabilRewriter(Job job, FabricTypeSystem fab_ts,
      FabricNodeFactory fab_nf, fabil.ExtensionInfo fabil_ext) {
    super(job, fab_ts, fab_nf, fabil_ext);
    this.job = job;
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

//    if (t instanceof CodebaseClassType) {
//      Codebase cb = ((CodebaseClassType) t).codebase();
//      Source src = ((CodebaseClassType) t).fromSource();
//      TypeNode tn = fabil_nf.TypeNodeFromQualifiedName(pos, t.toClass().fullName());
//      return fabil_nf.AmbCodebaseTypeNode(pos, src, tn);
//    }
    return super.typeToJava(t, pos);
  }

  public boolean inSignatureMode() {
    FabILOptions opts =
        (FabILOptions) ((fabil.ExtensionInfo) java_ext).getOptions();
    return opts.signatureMode();
  }

  // private Expr loc;
  // private Call addLoc(Call c) {
  // List<Expr> args = new ArrayList<Expr>(c.arguments().size() + 1);
  // args.add(loc);
  // for(Expr expr : (List<Expr>)c.arguments()) {
  // Expr addExpr = expr;
  // if(expr instanceof Call) {
  // Call subcall = (Call) expr;
  // addExpr = addLoc(subcall);
  // }
  // args.add(addExpr);
  // }
  // c = (Call)c.arguments(args);
  // return c;
  //
  // }

  public Expr updateLabelLocation(Expr labelExpr, Expr locExpr) {
    if (labelExpr instanceof Call && locExpr != null) {
      Call c = (Call) labelExpr;

      // if(!c.target().toString().contains("LabelUtil") &&
      // !c.target().toString().contains("PrincipalUtil")) {
      // System.out.println("Big problem in the compiler. Calling " +
      // c.target().toString() + "." + c.name());
      // return labelExpr;
      // }
      // XXX Hack
      // Several special cases, for which no change should be made.
      if (c.name().equals("getPrincipal") && c.arguments().size() == 0) {
        return c;
      }

      List<Expr> args = new ArrayList<Expr>(c.arguments().size() + 1);
      args.add(locExpr);
      for (Expr expr : (List<Expr>) c.arguments()) {
        if (expr instanceof Call) {
          args.add(updateLabelLocation(expr, locExpr));
        } else {
          args.add(expr);
        }
      }
      c = (Call) c.arguments(args);
      return c;
    }

    return labelExpr;
  }

  public Expr paramToJava(Param param, Expr locExpr) throws SemanticException {
    if (param instanceof Label) {
      Expr labelExpr = labelToJava((Label) param);
      return updateLabelLocation(labelExpr, locExpr);
    }

    return super.paramToJava(param);
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
        String newPath =
            n.source()
                .path()
                .substring(0,
                    n.source().path().length() - n.source().name().length())
                + newName;
        CodebaseSourceFile cbn = (CodebaseSourceFile) n;
        Source s =
            new CodebaseSource_c(newName, newPath, new Date(
                System.currentTimeMillis()), cbn.codebase(),
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
